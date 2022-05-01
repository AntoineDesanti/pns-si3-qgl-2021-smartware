package fr.unice.polytech.si3.qgl.smartware.crew;

import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import fr.unice.polytech.si3.qgl.smartware.actions.*;
import fr.unice.polytech.si3.qgl.smartware.game.Checkpoint;
import fr.unice.polytech.si3.qgl.smartware.maths.Path;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.GeoPoint;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Circle;
import fr.unice.polytech.si3.qgl.smartware.navigation.PathFinding;
import fr.unice.polytech.si3.qgl.smartware.sea.Sea;
import fr.unice.polytech.si3.qgl.smartware.sea.Wind;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Deck;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.smartware.game.Game;
import fr.unice.polytech.si3.qgl.smartware.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.smartware.maths.Case;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Sail;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Watch;
import fr.unice.polytech.si3.qgl.smartware.smartship.OarsRepartition;
import fr.unice.polytech.si3.qgl.smartware.smartship.SailorOarPath;

import java.util.*;
import java.util.stream.Collectors;

public class Captain {
    private final Game currentGame;
    private final Deck deck;
    private final QuarterMaster quarterMaster;
    private final TrajectoryAdvisor trajectoryAdvisor;
    private final SailAdvisor sailAdvisor;
    private final Checkpoint[] checkpoints;
    int currentCheckpoint = 0;
    private final Ship ship;
    private Sea sea;
    private Wind wind;
    private ArrayList<Sail> sails;
    private boolean exit = false;
    private PathFinding pathFinding;
    Position currentTargetPosition;

    public Captain(Game currentGame) {
        this.currentGame = currentGame;

        deck = currentGame.getShip().getDeck();
        quarterMaster = new QuarterMaster(deck);
        checkpoints = ((RegattaGoal) currentGame.getGoal()).getCheckpoints();
        ship = currentGame.getShip();
        sea = currentGame.getSea();
        wind = currentGame.getSea().getWind();
        trajectoryAdvisor = new TrajectoryAdvisor(currentGame.getSailors().length, deck.getOars().size());
        sailAdvisor = new SailAdvisor(currentGame.getShip().getNumberSails());
        sails = ship.getSails();
        pathFinding = new PathFinding(ship, checkpoints[0]);
    }

    public Position getCurrentTargetPosition()
    {
        return currentTargetPosition;
    }
    public PathFinding getPathFinding()
    {
        return pathFinding;
    }



    public Actions regattaHandler(Actions actions) throws Exception {


        Position ship_pos = ship.getPosition();
        ship_pos.print();
        checkPointChecker(ship_pos);

        currentTargetPosition = pathFinding.getNewTargetPosition(checkpoints[currentCheckpoint],
                Arrays.asList(sea.getSeaEntities()));

        moveShipToTarget(actions, ship_pos);
        return actions;
    }

    private void moveShipToTarget(Actions actions, Position ship_pos) throws Exception {
        OarsRepartition oarRepartition = trajectoryAdvisor.getOptimalSailorsRepartition(
                checkpoints[currentCheckpoint].getPosition(),
                ship_pos);

        actions.addAll(manageSailors(
                    oarRepartition.getSailorsToPutOnLeft(),
                    oarRepartition.getGetSailorsToPutOnRight(),
                    Arrays.asList(currentGame.getSailors()),
                    deck.getOars()
                ));
    }

    private void checkPointChecker(Position ship_pos) {
        if (Math.pow((ship_pos.getX()-checkpoints[currentCheckpoint].getPosition().getX()), 2)+
                Math.pow((ship_pos.getY()-checkpoints[currentCheckpoint].getPosition().getY()), 2)
                < Math.pow(((Circle)checkpoints[currentCheckpoint].getShape()).getRadius(),2))
            if (currentCheckpoint+1 < checkpoints.length)
                currentCheckpoint++;
            else exit = true;
    }


    public Actions manageSailors(int requiredOnLeft, int requiredOnRight, List<Sailor> sailors, List<Oar> oars) throws Exception {
        Actions actions = new Actions();
        List<Sailor> availableSailors = new ArrayList<>(sailors);
        Boolean useWatch = false;

        if(sea.getSeaEntities().length < 1) useWatch = true;


        if(useWatch && ship.watchExists()) watchManagement(availableSailors, actions);
        sailManagement(availableSailors, actions);
        System.out.println("OLD REPARTITION = "+requiredOnLeft+"/"+requiredOnRight);
        OarsRepartition oarsRepartition = new OarsRepartition(requiredOnLeft, requiredOnRight);
        OarsRepartition newOarRepartition = ship.rudderExists()? rudderManagement(availableSailors, actions, oarsRepartition) :
                oarsRepartition;

        requiredOnLeft = newOarRepartition.getSailorsToPutOnLeft();
        requiredOnRight = newOarRepartition.getGetSailorsToPutOnRight();

        List<SailorOarPath> bestMoves = quarterMaster.getBestSailorMoves(requiredOnLeft, requiredOnRight, availableSailors, oars);

        // When path length is positive, the sailor needs to move => Moving action
        List<Sailor> movingSailors = moveSailors(actions, bestMoves);

        // When path length is null, the sailor is already on the oar => Oaring action
        List<Sailor> oaringAbleSailors = getOaringAbleSailors(availableSailors, bestMoves, movingSailors);
        adjustOaringSailors(oaringAbleSailors, requiredOnLeft, requiredOnRight);

        oaringAbleSailors.forEach(sailor -> actions.add(new AOar(sailor.getId())));

        return actions;
    }

    private void watchManagement(List<Sailor> availableSailors, Actions actions) throws Exception {
        Sailor sailorForWatch = ship.getWatch().getCase(ship).getClosestSailor(ship.getDeck(), availableSailors);

        Path pathToWatch = sailorForWatch.getPathToCase(ship.getWatch().getCase(ship));

        if (pathToWatch.getLength() > 0) {
            actions.add(new Moving(sailorForWatch.getId(), pathToWatch.deltaX(), pathToWatch.deltaY()));
            sailorForWatch.moveToCase(pathToWatch);
        }

        actions.add(new UseWatch(sailorForWatch.getId()));
        availableSailors.remove(sailorForWatch);
    }

    private OarsRepartition rudderManagement(List<Sailor> availableSailors, Actions actions, OarsRepartition repartition) throws Exception {
        OarsRepartition newOarRepartition = repartition;
        double rudderAngle = trajectoryAdvisor.getOptimalRudderAngle(checkpoints[currentCheckpoint].getPosition(),
                ship.getPosition());
   //     rudderAngle = Math.round(rudderAngle*100.0)/100.0;
        System.out.println("TargetAngle [CAPTAIN]: "+rudderAngle);
        if (rudderAngle != 0)
        {
            Sailor sailorForRudder = ship.getRudder().getClosestSailor(deck, availableSailors);
            Path pathToRudder = sailorForRudder.getPathToCase(ship.getRudder().getCase(ship));
            if (pathToRudder.getLength() > 0) {
                actions.add(new Moving(sailorForRudder.getId(), pathToRudder.deltaX(), pathToRudder.deltaY()));
                sailorForRudder.moveToCase(pathToRudder);
            }
            availableSailors.remove(sailorForRudder);

            newOarRepartition = trajectoryAdvisor.getOptimalSailorsRepartition(
                    checkpoints[currentCheckpoint].getPosition(),
                    new Position(ship.getPosition().getX(),
                                 ship.getPosition().getY(), ship.getPosition().getOrientation()+rudderAngle));

            if (sailorForRudder.getPathToCase(ship.getRudder().getCase(ship)).getLength() == 0)
            {
                ship.getRudder().setAngle(rudderAngle + newOarRepartition.getResidualAngle());
                actions.add(new Turn(sailorForRudder.getId(), ship.getRudder().getAngle()));
            }

        }
        System.out.println("REPARTITION = "+newOarRepartition.getSailorsToPutOnLeft()+"/"+newOarRepartition.getGetSailorsToPutOnRight());
        System.out.println("RUDDER ANGLE = "+rudderAngle);
        return newOarRepartition;
    }

    private void sailManagement(List<Sailor> sailors, Actions actions) throws Exception {

        if(sails.isEmpty()) return;
        Boolean isSailUseful = sailAdvisor.sailUseful(ship.getPosition().getOrientation(), wind);

        for(Sail sail : sails) {
            Sailor sa = deck.getCase(sail.getX(), sail.getY()).getClosestSailor(deck, sailors);
            Path pathToSail = sa.getPathToCase(sail.getCase(ship));

            if (isSailUseful) {
                if (!sail.isOpenned()) {
                    if (pathToSail.getLength() > 1) {
                        actions.add(sa.moveToCase(pathToSail));
                    }
                    actions.add(new LiftSail(sa.getId()));
                    sailors.remove(sa);
                    sail.setOpenned(true);
                }
            }

            else {
                if (sail.isOpenned()) {
                    if (pathToSail.getLength() > 0) {
                        actions.add(new Moving(sa.getId(), pathToSail.deltaX(), pathToSail.deltaY()));
                        sa.moveToCase(pathToSail);
                    }
                    actions.add(new LowerSail(sa.getId()));
                    sailors.remove(sa);
                    sail.setOpenned(false);

                }
            }
        }

 }



    private List<Sailor> getOaringAbleSailors(List<Sailor> sailors, List<SailorOarPath> bestMoves, List<Sailor> movingSailors) {

        ArrayList<Sailor>  oaringAbleSailors = new ArrayList<>();
        List<Sailor> sailorsAlreadyInPlace = bestMoves.stream()
                .filter(move -> move.getPath().getLength() == 0)
                .map(SailorOarPath::getSailor)
                .collect(Collectors.toList());

        List<Sailor> sailorsNowInPlace = sailors.stream()
                .filter(movingSailors::contains)
                .filter(Sailor::isOnOar)
                .collect(Collectors.toList());

        oaringAbleSailors.addAll(sailorsAlreadyInPlace);
        oaringAbleSailors.addAll(sailorsNowInPlace);

        return oaringAbleSailors;

    }

    private List<Sailor> moveSailors(Actions actions, List<SailorOarPath> bestMoves) {
        List<SailorOarPath> moves = bestMoves.stream()
                .filter(move -> move.getPath().getLength() != 0)
                .collect(Collectors.toList());
        List<Sailor> movingSailors = moves.stream()
                .map(SailorOarPath::getSailor)
                .collect(Collectors.toList());

        moves.forEach(move -> actions.add(move.getSailor().moveToCase(move.getPath())));

        return movingSailors;
    }

    public int countSailors(int side)
    {
        int count = 0;
        for(int i = 0; i< deck.getLength(); i++)
        {
            Case deckCase = deck.getCase(i, side==0? 0 : deck.getWidth()-1);
            if (!deckCase.containsSailor().isEmpty() && deckCase.getOar() != null)
                count++;
        }
        return count;
    }

    public void adjustOaringSailors(List<Sailor> oaringAbleSailors, int requiredOnLeft, int requiredOnRight)
    {
        int leftDeficit = requiredOnLeft-countSailors(0);
        int rightDeficit = requiredOnRight-countSailors(1);

        while (leftDeficit > 0 && countSailors(0) > 0) {
            oaringAbleSailors.remove(oaringAbleSailors.stream().filter(sailor -> sailor.getY() != 0).findFirst().orElse(null));
            leftDeficit--;
        }
        while (rightDeficit > 0 && countSailors(1) > 0) {
            oaringAbleSailors.remove(oaringAbleSailors.stream().filter(sailor -> sailor.getY() == 0).findFirst().orElse(null));
            rightDeficit--;
        }
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}

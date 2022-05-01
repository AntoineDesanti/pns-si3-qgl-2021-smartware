package fr.unice.polytech.si3.qgl.smartware.crew;

import fr.unice.polytech.si3.qgl.smartware.maths.Case;
import fr.unice.polytech.si3.qgl.smartware.maths.Path;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Deck;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.smartware.smartship.SailorOarPath;
import fr.unice.polytech.si3.qgl.smartware.smartship.ScoredEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuarterMaster {

    private Deck deck;

    public QuarterMaster(Deck deck) {
        this.deck = deck;
    }

    public List<SailorOarPath> getBestSailorMoves(int oarsToAddLeft, int oarsToAddRight, List<Sailor> sailors, List<Oar> oars) throws Exception {
        if (oarsToAddLeft + oarsToAddRight == 0 || sailors.size() == 0 || oars.size() == 0)
            return new ArrayList<>();
        else
        {
            List<ScoredEntity> scoredMovesList = new ArrayList<>();
            List<Sailor> remainingSailors = new ArrayList<>(sailors);
            List<Oar> remainingOars = new ArrayList<>(oars);

            for (SailorOarPath move : getPotentialMoves(sailors, oars))
                scoredMovesList.add(new ScoredEntity<>(
                        (move.getOar().isOnLeft()? oarsToAddLeft : oarsToAddRight) * (6 - Math.min(move.getPath().size()-1, 6)),
                                move));
        //    scoredMovesList.stream().forEach(e -> e.print());
            SailorOarPath bestMove = (SailorOarPath) ScoredEntity.getBestObject(scoredMovesList);
            remainingSailors.remove(bestMove.getSailor());
            remainingOars.remove(bestMove.getOar());
            if (bestMove.getOar().isOnLeft()) oarsToAddLeft--; else oarsToAddRight--;

            List<SailorOarPath> bestMoves = new ArrayList<>();
            bestMoves.add(bestMove);
            bestMoves.addAll(getBestSailorMoves(oarsToAddLeft, oarsToAddRight, remainingSailors, remainingOars));
            return bestMoves;
        }

    }

    private List<SailorOarPath> getPotentialMoves(List<Sailor> sailors, List<Oar> oars) throws Exception {
        List<SailorOarPath>sailorOarPaths = new ArrayList<>();
        for (int i=0; i<sailors.size(); i++)
            for (int j=0; j<oars.size(); j++)
            {
                Path path = deck.deckPathFinding(sailors.get(i), oars.get(j).getX(), oars.get(j).getY());
                sailorOarPaths.add(new SailorOarPath(sailors.get(i), oars.get(j), path));
            }
       // sailorOarPaths.stream().forEach(entity -> System.out.println(entity.toString()));
        return sailorOarPaths;
    }

    public List<Sailor> getAvailableSailors(List<SailorOarPath> bestMoves)
    {
        return bestMoves.stream().map(SailorOarPath::getSailor).distinct().collect(Collectors.toList());
    }

    private List<Oar> getAvailableOars(List<SailorOarPath> bestMoves)
    {
        return bestMoves.stream().map(SailorOarPath::getOar).distinct().collect(Collectors.toList());
    }
}

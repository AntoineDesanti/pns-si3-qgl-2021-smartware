package fr.unice.polytech.si3.qgl.smartware.crew;

import fr.unice.polytech.si3.qgl.smartware.game.Checkpoint;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.navigation.Compass;
import fr.unice.polytech.si3.qgl.smartware.navigation.NavigationTable;
import fr.unice.polytech.si3.qgl.smartware.navigation.Navigator;
import fr.unice.polytech.si3.qgl.smartware.smartship.OarsRepartition;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TrajectoryAdvisor {

    private Compass compass;
    private Navigator navigator;
    private int numberOfOars;

    public TrajectoryAdvisor(int sailorsToSupervize, int oars)
    {
        numberOfOars = oars; // TODO: handle case with different number of sailors and oars.
        compass = new Compass();
        navigator = new Navigator(sailorsToSupervize, numberOfOars);
    }

    public OarsRepartition getOptimalSailorsRepartition(Position targetPosition, Position currentPosition)
    {
        /**
         * Returns the sailors repartition based on the position of the target checkpoint.
         @key targetPosition: The checkpoint the ship needs to reach.
         @return int[2]{ sailorsRequiredOnTheLeft, sailorsRequiredOnTheRight }
         */
        OarsRepartition oarRepartition = new OarsRepartition(numberOfOars/2, numberOfOars/2);

        for (int i = 0; i<2; i++)
        {
            double targetAngle = compass.getAngleWithTarget(currentPosition, targetPosition);
            double movingAngle = navigator.getClosestAnglePossible(targetAngle);
            double optimalSpeed = navigator.getOptimalSpeed(movingAngle, currentPosition,
                    targetPosition, oarRepartition);

            NavigationTable navigationTable = navigator.getNavigationTable();
            int matchingLineInNavTable = findLineInNavTable(navigationTable, optimalSpeed, movingAngle);

            oarRepartition = new OarsRepartition(
                            navigationTable.getLeftSailors(matchingLineInNavTable), // Sailors to put on the left
                            navigationTable.getRightSailors(matchingLineInNavTable),  // Sailors to put on the right
                            targetAngle-navigationTable.getAngle(matchingLineInNavTable) // Residual Angle
            );
        }

        return oarRepartition;
    }

    public int findLineInNavTable(NavigationTable navTable, double speed, double angle)
    {
        int line = navTable.size()-1;
        List<Integer> correctLines = new ArrayList<>();
        while (line > 0) {
            if (isCombinationCorrect(navTable, speed, angle, line))
                correctLines.add(line);
            line--;
        }
        return correctLines.stream()
                .min(Comparator.comparingDouble(navLine -> Math.abs(navTable.getSpeed(navLine) - speed))).orElse(0);
    }

    private boolean isCombinationCorrect(NavigationTable navTable, double speed, double angle, int line) {
        return Math.round(navTable.getAngle(line) * 100.0) / 100.0 == Math.round(angle * 100.0) / 100.0;
    }

    public double getOptimalRudderAngle(Position targetPosition, Position currentPosition)
    {
        double realTargetAngle = compass.getAngleWithTarget(currentPosition, targetPosition);
        double distanceToCheckpoint = navigator.getRemainingDistance(currentPosition, targetPosition);
        OarsRepartition oarRepartition = getOptimalSailorsRepartition(targetPosition, currentPosition);
        double possibleAngleWithoutRudder = calculateAngle(numberOfOars, oarRepartition.getSailorsToPutOnLeft(),
                oarRepartition.getGetSailorsToPutOnRight());
        double possibleSpeedWithoutRudder = calculateSpeed(numberOfOars, oarRepartition.getSailorsToPutOnLeft(),
                oarRepartition.getGetSailorsToPutOnRight());
        /*
        Position nextAnticipatedPositionWithoutRudder = new Position(
                possibleSpeedWithoutRudder*Math.cos(possibleAngleWithoutRudder),
                possibleSpeedWithoutRudder*Math.sin(possibleAngleWithoutRudder), 0);
    */
        if (Math.abs(realTargetAngle) <= Math.PI/4)
          /*  if (distanceToCheckpoint < navigator.getRemainingDistance(currentPosition,
                nextAnticipatedPositionWithoutRudder)) // todo: OR rudder.isOccupatedBySailor()
                    return 0;
            else */
                return realTargetAngle;
        else
            return Math.PI/4 * realTargetAngle/Math.abs(realTargetAngle);
    }

    public double calculateAngle(int totalOars, int leftOars, int rightOars) {
        return ((Math.PI / totalOars) * (rightOars - leftOars));
    }

    public double calculateSpeed(int totalOars, int leftOars, int rightOars) {
        return 165 * ((double) (rightOars + leftOars)) / (double) totalOars;
    }
}

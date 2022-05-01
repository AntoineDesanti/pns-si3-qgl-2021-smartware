package fr.unice.polytech.si3.qgl.smartware.navigation;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.OrthogonalCoordinateSystem;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.GeoPoint;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.Vector;
import fr.unice.polytech.si3.qgl.smartware.smartship.OarsRepartition;


public class Navigator {

    private int numberOfSailors;
    private int numberOfOars;

    public Navigator(int numberOfSailors, int numberOfOars)
    {
        this.numberOfOars = numberOfOars;
        this.numberOfSailors = numberOfSailors;
    }

    public static double angleReal(double angle)
    {
        if (angle < -Math.PI) angle += 2*Math.PI;
        else if (angle > Math.PI) angle -= 2*Math.PI;
        return angle;
    }

    public double getClosestAnglePossible(double angleWithTarget)
    {
        /** Returns the closest angle the sailors can give when oaring together. There are numberOfSailors angles
         * possible.
         @key angleWithTarget: Angle between the ship and the target
         @returns closestAngle: Closest angle sailors can give to the boat to reach the target.
         */
        angleWithTarget = angleReal(angleWithTarget);
        double angleSubdivision = Math.PI/numberOfOars;
        int subdivisions = numberOfOars; //(numberOfSailors % 2 == 0)? numberOfSailors : numberOfSailors + 1;
        double closestAngle = Math.PI/2;
        for (int sub = -subdivisions/2, sign = -1; sub != 1; sub += (sign == 1)? 1:0, sign = -sign) {
            double angle = sign * sub * angleSubdivision;
            if (isAngleCloser(angle, angleWithTarget, closestAngle) && haveSameSign(-sign, angleWithTarget))
                closestAngle = sign * sub * angleSubdivision;
        }
        return closestAngle;
    }

    public double getOptimalSpeed(double angleMove, Position currentPosition, Position targetPosition, OarsRepartition oarRepartition)
    {
        /**
         * Once you know the angle to give to the boat, you can calculate the speed you can get from that angle
         @key angleMove: The angle the boat will move during this round.
         @return speed: optimal speed
         */
        double remainingDistance = getRemainingDistance(currentPosition, targetPosition);
        Position nextPosition = anticipateNextPosition(currentPosition, angleMove, oarRepartition);
        double distanceToNextShipPosition = getRemainingDistance(currentPosition, nextPosition);

        GeoPoint currentPoint = new GeoPoint(currentPosition.getX(), currentPosition.getY());
        Vector currentToCheckpoint = new Vector(currentPoint,
                new GeoPoint(targetPosition.getX(), targetPosition.getY()));
        Vector currentToNextIteration = new Vector(currentPoint,
                new GeoPoint(nextPosition.getX(), nextPosition.getY()));
        Vector.haveSameSense(currentToCheckpoint, currentToNextIteration);

        return (1.0-Math.abs(angleMove/Math.PI))*Math.min(1.0, remainingDistance/distanceToNextShipPosition);
    }

    public NavigationTable getNavigationTable()
    {
        NavigationTable navigationTable = new NavigationTable(numberOfSailors, numberOfOars);
        navigationTable.fill();
      //  navigationTable.print();
        return navigationTable;
    }

    private boolean isAngleCloser(double calculatedAngle, double targetAngle, double currentClosestAngle)
    {
        double newDelta = Math.abs(Math.abs(targetAngle)-Math.abs(calculatedAngle));
        return newDelta <= Math.abs(Math.abs(targetAngle)-Math.abs(currentClosestAngle));
    }
    private boolean haveSameSign(double a, double b){ return a*b >= 0; }

    public static double getRemainingDistance(Position currentPosition, Position targetPosition)
    {
        return Math.pow(Math.pow((targetPosition.getX()-currentPosition.getX()), 2) +
                Math.pow((targetPosition.getY()-currentPosition.getY()), 2), 0.5);
    }

    public Position anticipateNextPosition(Position currentPosition, double angleMove, OarsRepartition oarRepartition)
    {
        Position finalPosition = new Position();
        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(currentPosition.getX(), currentPosition.getY(),
                currentPosition.getOrientation());
        double speed = 165 * ((double) (oarRepartition.getSailorsToPutOnLeft() +
                oarRepartition.getGetSailorsToPutOnRight())) / (double) numberOfOars;

        GeoPoint targetDestination = new GeoPoint(speed*Math.cos(angleMove), speed*Math.sin(angleMove), ocs);
        finalPosition.setX(targetDestination.getX_absolute());
        finalPosition.setY(targetDestination.getY_absolute());
        finalPosition.setOrientation(currentPosition.getOrientation()+angleMove);
        return finalPosition;
    }

}

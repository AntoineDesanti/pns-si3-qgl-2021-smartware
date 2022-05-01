package fr.unice.polytech.si3.qgl.smartware.navigation;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.OrthogonalCoordinateSystem;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.GeoPoint;

public class Compass {


    public Compass()
    {

    }

    public double getAngleWithTarget(Position currentPosition, Position targetPosition)
    {
        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(currentPosition.getX(),
                                                                        currentPosition.getY(),
                                                                        currentPosition.getOrientation());
        GeoPoint targetPoint = new GeoPoint(targetPosition.getX(), targetPosition.getY());
        targetPoint = targetPoint.changeOCS(ocs);
        return ocs.getAngle(targetPoint);
    }
}

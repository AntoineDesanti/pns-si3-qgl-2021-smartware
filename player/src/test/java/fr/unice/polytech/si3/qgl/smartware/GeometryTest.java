package fr.unice.polytech.si3.qgl.smartware;

import fr.unice.polytech.si3.qgl.smartware.maths.geometry.OrthogonalCoordinateSystem;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.GeoPoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GeometryTest {

    @Test
    public void TestPointPositionInBase()
    {
        /** Let's place a point with coordinates (0,0) in a base. */
        // If test fails, check constructor and getters
        GeoPoint O = new GeoPoint(0,0);
        // Absolute coordinates
        assertEquals(0, O.getX_absolute());
        assertEquals(0, O.getY_absolute());
        // Relative coordinates
        assertEquals(0, O.getX_relative());
        assertEquals(0, O.getY_relative());
    }

    @Test
    public void TestPointPositionInOCS()
    {
        /** Let's place a point with coordinates (1,2) in an OCS(x=2, y=1, 0). */
        // If test fails, check for the point translation formula

        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(2,1,0);
        GeoPoint P = new GeoPoint(1,2, ocs);
        // Absolute coordinates
        assertEquals(3, P.getX_absolute());
        assertEquals(3, P.getY_absolute());
        // Relative coordinates
        assertEquals(1, P.getX_relative());
        assertEquals(2, P.getY_relative());
    }

    @Test
    public void TestPointPositionInOrientedOCS()
    {
        /** Let's place a point with coordinates (1,0) in an OCS(x=0, y=0, pi/3). */
        // If test fails, check for the point rotation formula

        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(0,0, Math.PI/3);
        GeoPoint P = new GeoPoint(1,0, ocs);
        // Absolute coordinates
        assertEquals(Math.cos(Math.PI/3), P.getX_absolute());
        assertEquals(Math.sin(Math.PI/3), P.getY_absolute());
        // Relative coordinates
        assertEquals(1, P.getX_relative());
        assertEquals(0, P.getY_relative());
    }

    @Test
    public void TestPointPositionInComplexOCS()
    {
        /** Let's place a point with coordinates (1,0) in an OCS(x=0, y=0, pi/3). */
        // If test fails, check for the point rotation formula

        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(10,0, 0.1);
        GeoPoint P = new GeoPoint(10,0, ocs);
        // Absolute coordinates
        assertEquals(19.95, Math.round(P.getX_absolute()*100)/100.0);
        assertEquals(1.0, Math.round(P.getY_absolute()*100)/100.0);
        // Relative coordinates
        assertEquals(10, P.getX_relative());
        assertEquals(0, P.getY_relative());
    }

    @Test
    public void TestAngleBetweenOCSandPoint()
    {
        /** Let's place a point A = (10, 0) in the base and let's measure its angle with point B = (19.95, 1) . */
        // If test fails, check for the point rotation formula
        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(10, 0, 0);
        GeoPoint A = new GeoPoint(0,0, ocs); // => absolute coordinates : (10, 0)
        GeoPoint B = new GeoPoint(19.95,1);
        GeoPoint C = new GeoPoint(17.07,7.07);

        double epsilon = 0.01;  // consider angles equal if expectedValue-epsilon < actualValue < expectedValue-epsilon

        assertEquals(Math.PI/4, ocs.getAngle(C), epsilon);
        assertEquals(0.1,  ocs.getAngle(B), epsilon);

    }

    @Test
    public void TestAngleBetweenOrientedOCSandPointFromOtherOCS()
    {
        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(10, 10, Math.PI/6);
        GeoPoint A = new GeoPoint(5,0, ocs);
        assertEquals(14.33, A.getX_absolute(), 0.1);
        assertEquals(12.5, A.getY_absolute(), 0.1);

        OrthogonalCoordinateSystem ocs2 = new OrthogonalCoordinateSystem(14, 18, Math.PI/3);
        GeoPoint B = new GeoPoint(2.83,2.83, ocs2);
        assertEquals(12.96, B.getX_absolute(), 0.1);
        assertEquals(21.86, B.getY_absolute(), 0.1);

        GeoPoint BinOCS = B.changeOCS(OrthogonalCoordinateSystem.base).changeOCS(ocs);
        assertEquals(8.5, BinOCS.getX_relative(), 0.1);
        assertEquals(8.79, BinOCS.getY_relative(), 0.1);

        assertEquals(59.33, Math.toDegrees(OrthogonalCoordinateSystem.base.getAngle(B)), 0.1);
        assertEquals(45.97, Math.toDegrees(ocs.getAngle(B)), 0.1);
        assertEquals(45.0, Math.toDegrees(ocs2.getAngle(B)), 0.1);
    }
}

package fr.unice.polytech.si3.qgl.smartware.sea;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Polygon;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Rectangle;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Point;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class SeaEntityTest {
    @Test
    public void getPrejudiceOnCollisionReefTest()
    {
        Reef reef = new Reef();
        assertEquals(1000, reef.prejudiceOnCollision(0));
    }

    @Test
    public void collisionDetectionPolygonTest()
    {
        Position reefPosition = new Position(50, 50, 0);
        Rectangle reefShape = new Rectangle(10, 10, 0);
        Reef reef = new Reef(reefPosition, reefShape);

        Position shipPosition = new Position(0, 0, 0);
        Rectangle shipShape = new Rectangle(10, 30, Math.PI/2);
        Polygon polygon = new Polygon(0, new Point[]{
                new Point(70, 56),
                new Point(80, 50),
                new Point(90, 60),
                new Point(80, 70),
                new Point(70, 70)
        });
        Reef reef2 = new Reef(new Position(0,0,0), polygon);

        Position shipPosition2 = new Position(30, 50, Math.PI/2);
        Position shipPosition3 = new Position(70, 50, Math.PI/2);
        Position shipPosition4 = new Position(50, 40, Math.PI/2);
        Position shipPosition5 = new Position(50, 40, Math.PI/2);
        Reef ship = new Reef(shipPosition, shipShape);
        Ship realShip = new Ship();
        realShip.setShape(shipShape);

        // isInside entre deux formes
        assertEquals(false, reef.isInside(ship.getShape().toPolygon(shipPosition), shipPosition));
        assertEquals(true, reef.isInside(ship.getShape().toPolygon(shipPosition2), shipPosition2));
        assertEquals(true, reef.isInside(ship.getShape().toPolygon(shipPosition3), shipPosition3));

        // isInside entre une entité et une forme
        realShip.setPosition(shipPosition);
        assertEquals(false, reef.isInside(realShip));
        realShip.setPosition(shipPosition2);
        assertEquals(true, reef.isInside(realShip));
        realShip.setPosition(shipPosition3);
        assertEquals(true, reef.isInside(realShip));

       // ship.setPosition(shipPosition3);
        assertEquals(true, reef2.isInside(ship.getShape().toPolygon(shipPosition3), shipPosition3));
        assertEquals(false, reef2.isInside(ship.getShape().toPolygon(shipPosition2), shipPosition2));
        assertEquals(false, reef2.isInside(ship.getShape().toPolygon(shipPosition5), shipPosition5));

        realShip.setPosition(shipPosition3);
        assertEquals(true, reef2.isInside(realShip));
        realShip.setPosition(shipPosition2);
        assertEquals(false, reef2.isInside(realShip));
        realShip.setPosition(shipPosition5);
        assertEquals(false, reef2.isInside(realShip));
    }

  /*  @Test
    public void getPrejudiceOnCollisionStreamTest()
    {
        Stream stream_90deg = new Stream(Math.PI/2, 100);
        assertEquals(0, stream_90deg.prejudiceOnCollision(0), 0.001);

        assertEquals(-1, stream_90deg.prejudiceOnCollision(Math.PI/2), 0.001);
        assertEquals(-1, stream_90deg.prejudiceOnCollision(5*Math.PI/2), 0.001);

        Stream stream_0deg = new Stream(Math.PI/2, 100);
        assertEquals(-0.707, stream_0deg.prejudiceOnCollision(Math.PI/4), 0.001);
    }*/
}

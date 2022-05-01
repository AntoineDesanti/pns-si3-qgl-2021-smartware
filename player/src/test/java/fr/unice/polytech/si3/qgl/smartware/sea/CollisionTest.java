package fr.unice.polytech.si3.qgl.smartware.sea;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Circle;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Point;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Polygon;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionTest {

    @Test
    public void polygonPolygonCollideTest(){
        Position defaultPosition = new Position();
        defaultPosition.setX(0);defaultPosition.setY(0);

        Polygon p1 = new Polygon();
        p1.setVertices(new Point[]{
                new Point(-12.06,2.22),new Point(-12.94,-2.04),new Point(-10.46,-3.16),new Point(-8.14,-1.46),new Point(-6.64,-4.78),
                new Point(-3.66,0.02),new Point(-7.14,0.46),new Point(-8.84,4.72)
        });

        Polygon p2 = new Polygon();
        p2.setVertices(new Point[]{
                new Point(-13.22,5.96),new Point(-13.8,3.56),new Point(-9.56,2.24),new Point(-10.16,5.3),new Point(-7.3,7.54),
                new Point(-10.5,8.96),new Point(-11.64,6.82)
        });

        SeaEntity s1 = new Reef(defaultPosition, p1);

        Ship ship = new Ship();
        ship.setPosition(defaultPosition);
        ship.setShape(p2);

        assertTrue(s1.isInside(ship));
    }

    @Test
    public void polygonPolygonNotCollideTest(){
        Position defaultPosition = new Position();
        defaultPosition.setX(0);defaultPosition.setY(0);

        Polygon p1 = new Polygon();
        p1.setVertices(new Point[]{
                new Point(-12.9,-2.36),new Point(-13.38,-6.42),new Point(-7.2,-4.38),
                new Point(-8.52,-0.16),new Point(-11.1,-1.8),new Point(-13.82,-0.18)
        });

        Polygon p2 = new Polygon();
        p2.setVertices(new Point[]{
                new Point(-15.02,3.08),new Point(-13.5,0),new Point(-11.4,-1.48),
                new Point(-10.48,0.04),new Point(-9.5,-0.58),new Point(-8.84,-0.2),new Point(-8.94,2.32)
        });

        SeaEntity s1 = new Reef(defaultPosition, p1);

        Ship ship = new Ship();
        ship.setPosition(defaultPosition);
        ship.setShape(p2);

        assertFalse(s1.isInside(ship));
    }

    @Test
    public void circleCircleNotCollideTest(){
        Position defaultPosition = new Position();
        defaultPosition.setX(0);defaultPosition.setY(0);

        Position circle1Center = new Position();
        circle1Center.setX(-8.2);
        circle1Center.setY(2.4);

        Position circle2Center = new Position();
        circle2Center.setX(-0.26);
        circle2Center.setY(-2.54);

        Polygon p1 = new Circle(4.625538).toPolygon(circle1Center);
        Polygon p2 = new Circle(4.535813).toPolygon(circle2Center);

        SeaEntity s1 = new Reef(defaultPosition, p1);

        Ship ship = new Ship();
        ship.setPosition(defaultPosition);
        ship.setShape(p2);

        assertFalse(s1.isInside(ship));
    }

    @Test
    public void circleCircleCollideTest(){
        Position defaultPosition = new Position();
        defaultPosition.setX(0);defaultPosition.setY(0);

        Position circle1Center = new Position();
        circle1Center.setX(-10.16);
        circle1Center.setY(3.76);

        Position circle2Center = new Position();
        circle2Center.setX(-4.58);
        circle2Center.setY(-0.5);

        Polygon p1 = new Circle(3.777248).toPolygon(circle1Center);
        Polygon p2 = new Circle(3.631474).toPolygon(circle2Center);

        SeaEntity s1 = new Reef(defaultPosition, p1);

        Ship ship = new Ship();
        ship.setPosition(defaultPosition);
        ship.setShape(p2);

        assertTrue(s1.isInside(ship));
    }

    @Test
    public void circlePolygonCollideTest(){
        Position defaultPosition = new Position();
        defaultPosition.setX(0);defaultPosition.setY(0);

        Position circle1Center = new Position();
        circle1Center.setX(-9.2);
        circle1Center.setY(2.7);

        Polygon p1 = new Circle(3.480517).toPolygon(circle1Center);
        Polygon p2 = new Polygon();
        p2.setVertices(new Point[]{
                new Point(-10.9,-4.4),new Point(-11.64,-2.08),new Point(-9.52,-1.56),
                new Point(-8.62,0.88),new Point(-4.1,1.2),new Point(-4.16,-4.34)
        });

        SeaEntity s1 = new Reef(defaultPosition, p1);

        Ship ship = new Ship();
        ship.setPosition(defaultPosition);
        ship.setShape(p2);

        assertTrue(s1.isInside(ship));
    }

    @Test
    public void circlePolygonNotCollideTest(){
        Position defaultPosition = new Position();
        defaultPosition.setX(0);defaultPosition.setY(0);

        Position circle1Center = new Position();
        circle1Center.setX(-9.66);
        circle1Center.setY(3.14);

        Polygon p1 = new Circle(5.05383).toPolygon(circle1Center);
        Polygon p2 = new Polygon();
        p2.setVertices(new Point[]{
                new Point(-12.2,-6.28),new Point(-11.44,-1.86),new Point(-9.1,-2.12),
                new Point(-7.56,-1.62),new Point(-6.46,-2.24),new Point(-5.98,-0.5),new Point(-4.24,-7.42)
        });

        SeaEntity s1 = new Reef(defaultPosition, p1);

        Ship ship = new Ship();
        ship.setPosition(defaultPosition);
        ship.setShape(p2);

        assertFalse(s1.isInside(ship));
    }
}

package fr.unice.polytech.si3.qgl.smartware.game;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Circle;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Point;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Polygon;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Rectangle;
import fr.unice.polytech.si3.qgl.smartware.sea.SeaEntity;
import fr.unice.polytech.si3.qgl.smartware.sea.Stream;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SeaEntityTest {
    private Ship straightShip;
    private Ship orientedShip;
    private SeaEntity stream;
/*
    @BeforeEach
    public void init(){
        Ship s1 = new Ship(); Ship s2 = new Ship();
        Position pShip1 = new Position(); Position pShip2 = new Position();
        Rectangle sShip1 = new Rectangle(); Rectangle sShip2 = new Rectangle();

        sShip1.setWidth(20); sShip1.setHeight(20);
        pShip1.setX(2); pShip1.setY(0);

        sShip2.setWidth(11.612); sShip2.setHeight(11.612);
        pShip2.setX(25.13); pShip2.setY(1.12);
        sShip2.setOrientation(0.75258597);

        s1.setPosition(pShip1); s2.setPosition(pShip2);
        s1.setShape(sShip1); s2.setShape(sShip2);
        straightShip = s1; orientedShip = s2;

    }

    @Test
    public void isInsidePolygonTest(){
        Point[] polygonPoints = {new Point(29.32, 13.18),
                new Point(19.04, 10.79),
                new Point(14.29, 15.99),
                new Point(17.97, 18.61),
                new Point(22, 24),
                new Point(30.23, 19.33)};
        Polygon p = new Polygon(0, polygonPoints);

        Stream streamTemp = new Stream();
        streamTemp.setShape(p);
        streamTemp.setPosition(new Position());
        stream = streamTemp;

        assertTrue(stream.isInside(straightShip));
    }

    @Test
    public void isInsideCircleTest(){
        Circle c = new Circle();
        Position p = new Position();
        c.setRadius(8);
        p.setX(26.69); p.setY(21.76);

        Stream streamTemp = new Stream();

        streamTemp.setShape(c);
        streamTemp.setPosition(p);
        stream = streamTemp;

        assertTrue(stream.isInside(straightShip));
        assertTrue(stream.isInside(orientedShip));
    }

    @Test
    public void isInsideRectangleTest(){
        Rectangle rStraight = new Rectangle(); Rectangle rOriented = new Rectangle();
        Position p1 = new Position(); Position p2 = new Position();

        rStraight.setWidth(13.09); rStraight.setHeight(13.09);
        rOriented.setWidth(12.37); rOriented.setHeight(12.37); rOriented.setOrientation(0.50248029);
        p1.setX(5.02); p1.setY(17.86);
        p2.setX(26); p2.setY(0);

        Stream streamTemp = new Stream();

        streamTemp.setShape(rStraight);
        streamTemp.setPosition(p1);
        stream = streamTemp;

        //Checks rectangles collision both straight
        assertTrue(stream.isInside(straightShip));

        streamTemp.setShape(rOriented);
        streamTemp.setPosition(p2);
        stream = streamTemp;

        //TODO : Checks oriented rectangle with straight rectangle collision
        //TODO : Can use Polygon-Polygon by using vertices of Rectangles
        //assertTrue(stream.isInside(straightShip));

    }

 */
}

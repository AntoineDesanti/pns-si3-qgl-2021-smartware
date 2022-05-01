package fr.unice.polytech.si3.qgl.smartware.game;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Circle;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Rectangle;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckpointTest {
    Ship ship = new Ship();

    @Ignore
    void isInsideTest(){
        Position shipPosition = new Position();
        shipPosition.setX(100);
        shipPosition.setY(100);
        shipPosition.setOrientation(0);
        ship.setPosition(shipPosition);

        Rectangle shipShape = new Rectangle();
        shipShape.setHeight(30);
        shipShape.setWidth(30);
        Position checkpointPosition = new Position();
        checkpointPosition.setX(100);
        checkpointPosition.setY(100);
        ship.setShape(shipShape);

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setPosition(checkpointPosition);

        Circle checkpointShape = new Circle();
        checkpointShape.setRadius(20);
        checkpoint.setShape(checkpointShape);

        assertTrue(checkpoint.isInside(ship));

        shipPosition.setX(1500);
        shipPosition.setY(1500);
        ship.setPosition(shipPosition);

        assertFalse(checkpoint.isInside(ship));
    }

}

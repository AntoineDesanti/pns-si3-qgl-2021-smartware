package fr.unice.polytech.si3.qgl.smartware.smartship;

import java.util.ArrayList;
import java.util.List;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Circle;
import fr.unice.polytech.si3.qgl.smartware.sea.Reef;
import fr.unice.polytech.si3.qgl.smartware.sea.Sea;
import fr.unice.polytech.si3.qgl.smartware.sea.SeaEntity;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShipTest {

    @Test
    public void angleWithEntityTest()
    {
        Ship ship = new Ship();
        Position shipPosition = new Position(100, 0, Math.PI/6);
        Position entityPosition = new Position(50, 0, 0);
        ship.setPosition(shipPosition);

        SeaEntity entity = new Reef(entityPosition, new Circle(5));

        assertEquals(-Math.PI/6, ship.angleWithEntity(entity), 0.01);
    }
}

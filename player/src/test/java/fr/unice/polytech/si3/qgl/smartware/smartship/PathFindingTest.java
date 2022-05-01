package fr.unice.polytech.si3.qgl.smartware.smartship;
import fr.unice.polytech.si3.qgl.smartware.game.Checkpoint;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.OrthogonalCoordinateSystem;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.GeoPoint;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.Vector;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Circle;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Point;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Polygon;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Rectangle;
import fr.unice.polytech.si3.qgl.smartware.navigation.PathFinding;
import fr.unice.polytech.si3.qgl.smartware.sea.Reef;
import fr.unice.polytech.si3.qgl.smartware.sea.SeaEntity;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PathFindingTest {

    @Test
    public void getVectorToTargetPointTest()
    {
        Position shipPosition = new Position(0,0,0);
        Position checkpointPosition = new Position(707.1,707.1,0);

        GeoPoint shipPoint = new GeoPoint(0,0);
        GeoPoint targetPoint = new GeoPoint(707.1,707.1);
        Vector expectedVector = new Vector(shipPoint, targetPoint);
        Vector calculatedVector = PathFinding.getVectorToTargetPoint(shipPosition, checkpointPosition);
        assertEquals(expectedVector.toString(), calculatedVector.toString());
        assertEquals(707.1, calculatedVector.getTarget().getX_absolute(), 0.1);
        assertEquals(707.1, calculatedVector.getTarget().getY_absolute(), 0.1);
        assertEquals(1000, calculatedVector.getTarget().getX_relative(), 0.1);
        assertEquals(0, calculatedVector.getTarget().getY_relative(), 0.1);
    }

    @Test
    public void drawVirtualCircleTest()
    {
        Position shipPosition = new Position(0,0,0);
        Ship ship = new Ship();
        ship.setPosition(shipPosition);

        Position entityPosition = new Position(100,0,0); // on devra donc avoir un rayon de 100
        Reef reef = new Reef(entityPosition, new Circle(5));
        PathFinding pathFinding = new PathFinding();

        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(shipPosition.getX(), shipPosition.getY(),
                shipPosition.getOrientation());
        GeoPoint shipPoint = new GeoPoint(0,0, ocs);
        List<GeoPoint> circlePoints = pathFinding.drawVirtualCircle(ship, reef);
        assertEquals(120, circlePoints.size());
        for (GeoPoint circlePoint : circlePoints)
            assertEquals(300, new Vector(shipPoint, circlePoint).getLength(), 0.1);
    }



    public void getFarthestEntityInRaycastTest()
    {
        PathFinding pathFinding = new PathFinding();

        List<SeaEntity> entities = new ArrayList<>()
        {
            {
                add(new Reef(new Position(50, 0, 0), new Circle(5)));
                add(new Reef(new Position(60, 0, 0), new Circle(5)));
                add(new Reef(new Position(80, 0, 0), new Circle(5)));
                add(new Reef(new Position(90, 6, 0), new Circle(5)));
            }
        };

        GeoPoint origin = new GeoPoint(0,0, OrthogonalCoordinateSystem.base);
        GeoPoint target = new GeoPoint(100,0, OrthogonalCoordinateSystem.base);
        Vector raycast = new Vector(origin, target);
        SeaEntity farthestEntity = pathFinding.getClosestEntityInRaycast(entities, raycast);
        assertEquals(entities.get(2), farthestEntity);
    }


    public void findNewTargetPointTest()
    {
        // Test d'intégration

        PathFinding pathFinding = new PathFinding();
        Position shipPosition = new Position(0,0,0);
        Ship ship = new Ship();
        ship.setPosition(shipPosition);
        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setPosition(new Position(86.5,50,0));
        Vector raycast = PathFinding.getVectorToTargetPoint(shipPosition, checkpoint.getPosition());
        List<SeaEntity> entities = new ArrayList<>()
        {
            {
                add(new Reef(new Position(20, 10, 0), new Circle(5)));
            }
        };

        SeaEntity entity = pathFinding.getClosestEntityInRaycast(entities, raycast);
        assertEquals(new Position(20,10,0).toJsonString(), entity.getPosition().toJsonString());

        List<GeoPoint> circlePoints = pathFinding.drawVirtualCircle(ship, entity);

        GeoPoint point = pathFinding.findBestNewTargetPoint(circlePoints, entities, ship.getPosition(), checkpoint);
     //   System.out.println(point.toJsonString());
        assertEquals(21.74, point.getX_absolute(), 0.3); // todo check if it's right
        assertEquals(5.25, point.getY_absolute(), 0.3); //todo check if it's right

        // maintenant avec une orientation initiale différente
        ship.setPosition(new Position(0,0, Math.PI/2));
        List<GeoPoint> circlePoints2 = pathFinding.drawVirtualCircle(ship, entity);

        GeoPoint point2 = pathFinding.findBestNewTargetPoint(circlePoints2, entities, ship.getPosition(), checkpoint);
     //   System.out.println(point2.toJsonString());
        assertEquals(17.24, point2.getX_absolute(), 0.1);
        assertEquals(14.24, point2.getY_absolute(), 0.1);

    }
/*
    @Test
    public void entityDeviationTest() // pathfinding simplifié (sans tour d'avance)
    {
        Ship ship = new Ship();
        ship.setPosition(new Position(0,0,0));
        ship.setShape(new Rectangle(1, 0.2, 0));

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setPosition(new Position(8,1,0));
        checkpoint.setShape(new Circle(0.5));

        List<SeaEntity> seaEntities = new ArrayList<>();
        seaEntities.add(new Reef(new Position(4,0,0), new Circle(1)));
        PathFinding pathFinding = new PathFinding(ship, checkpoint);
        GeoPoint nextTargetPoint = pathFinding.getNewTargetPoint(checkpoint, seaEntities);

        //todo integration checkpoint
        assertEquals(3.5, nextTargetPoint.getX_absolute(), 0.1);
        assertEquals(1.94, nextTargetPoint.getY_absolute(), 0.1);
        nextTargetPoint.printPoint();
    }*/



    @Test
    public void getClosestEntityTest()
    {
        Checkpoint targetCheckpoint = new Checkpoint();
        targetCheckpoint.setPosition(new Position(50,50,0));
        targetCheckpoint.setShape(new Rectangle(10,10,0));

        Ship ship = new Ship();
        ship.setShape(new Rectangle(3.54,3.54,0));
        ship.setPosition(new Position(0,0,Math.PI/4));

        Polygon polygon = new Polygon(0, new Point[]{
                new Point(50, 36),
                new Point(60, 30),
                new Point(70, 40),
                new Point(60, 50),
                new Point(50, 50)
        });

        List<SeaEntity> entities = new ArrayList<>()
        {
            {
                add(new Reef(new Position(20, 31, 0), new Circle(5)));
            }
        };

        PathFinding path = new PathFinding(ship, targetCheckpoint);
        Vector raycast = path.getVectorToTargetPoint(ship.getPosition(), targetCheckpoint.getPosition());
        SeaEntity foundEntity = path.getClosestEntityInRaycast(entities, raycast);
        assertEquals(null, foundEntity);

        entities.remove(0);
        entities.add(new Reef(new Position(20, 30, 0), new Circle(5)));
        foundEntity = path.getClosestEntityInRaycast(entities, raycast);
        assertEquals(entities.get(0), foundEntity);

        entities.remove(0);
        entities.add(new Reef(new Position(20, 10, 0), new Circle(5)));
        foundEntity = path.getClosestEntityInRaycast(entities, raycast);
        assertEquals(entities.get(0), foundEntity);

        entities.remove(0);
        entities.add(new Reef(new Position(20, 9, 0), new Circle(5)));
        foundEntity = path.getClosestEntityInRaycast(entities, raycast);
        assertEquals(null, foundEntity);

        entities.remove(0);
        entities.add(new Reef(new Position(0, 0, 0), polygon));
        foundEntity = path.getClosestEntityInRaycast(entities, raycast);
        assertEquals(entities.get(0), foundEntity);
    }
}

package fr.unice.polytech.si3.qgl.smartware.navigation;

import fr.unice.polytech.si3.qgl.smartware.game.Checkpoint;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.OrthogonalCoordinateSystem;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.GeoPoint;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.Vector;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Circle;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Polygon;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Rectangle;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Shape;
import fr.unice.polytech.si3.qgl.smartware.sea.Reef;
import fr.unice.polytech.si3.qgl.smartware.sea.SeaEntity;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import fr.unice.polytech.si3.qgl.smartware.smartship.ScoredEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PathFinding {

    private Ship ship;
    private double shipSafetyPerimeter = 1;
    private List<GeoPoint> points;
    private Circle shipSafeArea;
    private GeoPoint currentTargetPoint;

    public PathFinding()
    {
        /***
         * todo : generate recursively
         */
    }

    public PathFinding(Ship ship, Checkpoint firstCheckpoint)
    {
        this.ship = ship;
        shipSafetyPerimeter = getSecurityPerimeter(ship.getShape());
        shipSafeArea = new Circle(shipSafetyPerimeter);
        currentTargetPoint = new GeoPoint(firstCheckpoint.getPosition().getX(), firstCheckpoint.getPosition().getY());
    }

    public List<GeoPoint> getCirclePoints()
    {
        return points;
    }

    public GeoPoint getNewTargetPoint(Checkpoint targetCheckpoint, List<SeaEntity> seaEntities)
    {
        if (seaEntities.size() == 0)
            return new GeoPoint(targetCheckpoint.getPosition().getX(), targetCheckpoint.getPosition().getY());

        seaEntities = processedEntities(seaEntities);
        System.out.println("PATHFINDING entities: "+seaEntities.stream().map(s->s.toString()).collect(Collectors.toList()));
        Position shipPosition = ship.getPosition();
        Vector raycast = PathFinding.getVectorToTargetPoint(shipPosition, targetCheckpoint.getPosition());
        SeaEntity entity = getClosestEntityInRaycast(seaEntities, raycast);
        List<GeoPoint> circlePoints = drawVirtualCircle(ship, entity);
        points = circlePoints;
        currentTargetPoint = findBestNewTargetPoint(circlePoints, seaEntities, ship.getPosition(), targetCheckpoint);
        if (entity == null)
            currentTargetPoint = new GeoPoint(targetCheckpoint.getPosition().getX(), targetCheckpoint.getPosition().getY());
        return currentTargetPoint;
    }

    public Position getNewTargetPosition(Checkpoint targetCheckpoint, List<SeaEntity> seaEntities)
    {
        return getNewTargetPoint(targetCheckpoint, seaEntities).toSeaPosition();
    }

    public List<SeaEntity> processedEntities(List<SeaEntity> seaEntities)
    {
        for (int i = 0; i<seaEntities.size(); i++) {
            SeaEntity seaEntity = seaEntities.get(i);
            if (seaEntity.getShape() instanceof Polygon)
                seaEntity.setShape(((Polygon) seaEntity.getShape()).getSafeCircle());
        }
        return seaEntities;
    }

    public static Vector getVectorToTargetPoint(Position shipPosition, Position targetPosition)
    {
        OrthogonalCoordinateSystem localOCS = new OrthogonalCoordinateSystem(shipPosition.getX(), shipPosition.getY(), 0);
        double deltaAngle = localOCS.getAngle(new GeoPoint(targetPosition.getX(), targetPosition.getY()));
        localOCS = new OrthogonalCoordinateSystem(shipPosition.getX(), shipPosition.getY(), deltaAngle);
        GeoPoint origin = new GeoPoint(0,0,localOCS);
        GeoPoint target = new GeoPoint(Position.getDistance(shipPosition, targetPosition), 0, localOCS);
        return new Vector(origin, target);
    }

    public SeaEntity getClosestEntityInRaycast(List<SeaEntity> entities, Vector raycast)
    {
        if (entities.size() == 0)
            return null;

        final double pas = 2;
        double raycastDistance = raycast.getLength();
        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(raycast.getOrigin().getX_absolute(),
                raycast.getOrigin().getY_absolute(), Math.atan2(raycast.getY(), raycast.getX()));
        for (double distance = 0; distance < raycastDistance; distance += pas) {
            GeoPoint newPoint = new GeoPoint(distance, 0, ocs);
            Position position = new Position(newPoint.getX_absolute(), newPoint.getY_absolute(), ocs.getAngle(raycast.getTarget()));
            Ship shipAtNewPosition = new Ship();
            shipAtNewPosition.setShape(ship.getShape());
            shipAtNewPosition.setPosition(position);
            SeaEntity entityAtPosition = entities.stream()
                    .filter(entity -> entity.isInside(shipAtNewPosition))
                    .filter(entity -> entity instanceof Reef)
                    .findFirst().orElse(null);
            if (entityAtPosition != null)
                if (entityAtPosition instanceof Reef)
                    return entityAtPosition;
        }
        // todo throw exception
        return null;
    }


    public List<GeoPoint> drawVirtualCircle(Ship ship, SeaEntity entity)
    {
        /*** Donne une liste de points du demi-cercle discrétisé par angle
         */
        List<GeoPoint> circlePoints = new ArrayList<>();
        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(ship.getPosition().getX(),
                                                                        ship.getPosition().getY(),
                                                                        ship.getPosition().getOrientation());
        double distanceToTarget = 300; //entity == null? 1000 : Position.getDistance(ship.getPosition(), entity.getPosition());

        for (int signe = -1; signe <= 1; signe += 2)
            for (int i = 0; signe<0? i > -180 : i<180; i+=signe*3)
                circlePoints.add(new GeoPoint(distanceToTarget*Math.cos(Math.toRadians(i)),
                                            distanceToTarget*Math.sin(Math.toRadians(i)), ocs));
        return circlePoints;
    }

    public GeoPoint findBestNewTargetPoint(List<GeoPoint> circlePoints, List<SeaEntity> entities, Position shipPosition, Checkpoint checkpoint)
    {
        List<ScoredEntity> scoredPoints = new ArrayList<>();
        OrthogonalCoordinateSystem shipOcs = new OrthogonalCoordinateSystem(shipPosition.getX(),
                shipPosition.getY(),
                0);
        GeoPoint shipPoint = new GeoPoint(0,0, shipOcs);


        for (int i = 0; i<circlePoints.size(); i++) {
            GeoPoint currentPoint = circlePoints.get(i);
            SeaEntity closestEntity = getClosestEntityInRaycast(entities, new Vector(shipPoint, currentPoint));

            double angle = shipOcs.getAngle(currentPoint);
            double score = 1*Math.abs(Math.toDegrees(angle))+
                    2*Math.abs(Math.toDegrees(shipOcs.getAngle(currentPoint)-angle));
            if (closestEntity != null) {
                    score += closestEntity.prejudiceOnCollision(i);
            }


            scoredPoints.add(new ScoredEntity<GeoPoint>((int) score, currentPoint));
        }
        return (GeoPoint) ScoredEntity.getWorstObject(scoredPoints);
    }

    public double getSecurityPerimeter(Shape shape)
    {
        if (shape instanceof Circle)
            return ((Circle) shape).getRadius();
        if (shape instanceof Rectangle) {
            double max = Math.pow(Math.pow(((Rectangle) shape).getHeight(), 2) + Math.pow(((Rectangle) shape).getWidth(), 2), 0.5);
            return max*2;
        }
        else
            return 5; //todo arbitraire!!!!!!!!
    }
}

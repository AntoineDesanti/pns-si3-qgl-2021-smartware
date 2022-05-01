package fr.unice.polytech.si3.qgl.smartware.maths.shape;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.GeoPoint;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Rectangle.class, name = "rectangle"),
        @JsonSubTypes.Type(value = Circle.class, name = "circle"),
        @JsonSubTypes.Type(value = Polygon.class, name = "polygon")


})
public abstract class Shape implements Polygonizable{
    public abstract Point[] getVertices(Position p);
    public abstract Polygon toPolygon(Position p);

    public Point getClosestVerticeToShip(Ship s, Position shapePosition){

        Point[] vertices = getVertices(shapePosition);
        Point closestPoint = vertices[0];
        for(int i=1; i<vertices.length; i++){
            if(GeoPoint.getDistance(s.getPosition().getX(), s.getPosition().getY(), vertices[i].getX(), vertices[i].getY())
                < GeoPoint.getDistance(s.getPosition().getX(), s.getPosition().getY(), closestPoint.getX(), closestPoint.getY())) {
                closestPoint = vertices[i];
            }
        }
        return closestPoint;
    }

    public boolean isContainedIn(Rectangle rectangle)
    {
        return false;
    }

    public double getOrientation()
    {
        return 0;
    }
}

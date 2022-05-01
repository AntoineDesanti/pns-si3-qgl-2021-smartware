package fr.unice.polytech.si3.qgl.smartware.maths;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Circle;

import java.util.Objects;

public class Position implements JsonHandler {

    private double x;
    private double y;
    private double orientation;


    public Position() {
    }

    public Position(double x, double y, double orientation) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getOrientation() {
        return orientation;
    }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    public void setOrientation(double orientation) { this.orientation = orientation; }

    @JsonIgnore
    public static double getDistanceX(Position origin, Position destination)
    {
        return destination.getX()-origin.getX();
    }
    @JsonIgnore
    public static double getDistanceY(Position origin, Position destination)
    {
        return destination.getY()-origin.getY();
    }

    @JsonIgnore
    public static double getDistance(Position origin, Position destination)
    {
        return Math.pow(Math.pow(getDistanceX(origin, destination), 2)+Math.pow(getDistanceY(origin, destination), 2),
                0.5);
    }

    @JsonIgnore
    public boolean isAround(Position otherPosition, double radius)
    {
        return Math.pow((otherPosition.getX()-x), 2)+ Math.pow((otherPosition.getY()-y), 2) <= Math.pow(radius, 2);
    }

    @JsonIgnore
    public boolean virtualCircleCollidesWithOtherCircle(double virtualRadius, Position otherPosition, double otherRadius)
    {
        var dx = otherPosition.getX() - x;
        var dy = otherPosition.getY() - y;
        var distance = Math.sqrt(dx * dx + dy * dy);

        return distance < otherRadius + virtualRadius;
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                ", orientation=" + orientation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(Math.round(position.x*100.0)/100.0, Math.round(x*100.0)/100.0) == 0 &&
                Double.compare(Math.round(position.y*100.0)/100.0, Math.round(y*100.0)/100.0) == 0 &&
                Double.compare(Math.round(position.orientation*100.0)/100.0, Math.round(orientation*100.0)/100.0) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, orientation);
    }
}

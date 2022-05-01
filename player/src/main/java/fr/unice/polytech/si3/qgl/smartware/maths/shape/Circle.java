package fr.unice.polytech.si3.qgl.smartware.maths.shape;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;

import java.util.ArrayList;
import java.util.List;

public class Circle extends Shape implements Polygonizable{

   private double radius;


    public Circle() {
    }

    public Circle(double radius)
    {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getOrientation()
    {
        return 0;
    }

    @Override
    public Point[] getVertices(Position center) {
        List<Point> points = new ArrayList<>();
        for(int i=0; i<360; i+=20)
            points.add(new Point((Math.cos(Math.toRadians(i))*radius)+center.getX(), (Math.sin(Math.toRadians(i))*radius)+center.getY()));
        return points.toArray(Point[]::new);
    }

    @Override
    public Polygon toPolygon(Position center) {
        return new Polygon(center.getOrientation(), getVertices(center));

    }

    @Override
    public String toString() {
        return "Circle{" +
                "radius=" + radius +
                '}';
    }
}

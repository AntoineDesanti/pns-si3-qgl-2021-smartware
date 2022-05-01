package fr.unice.polytech.si3.qgl.smartware.maths.shape;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;

import java.util.Arrays;

public class Polygon extends Shape{
   private double orientation;
   private Point[] vertices;

   public Polygon()
   {

   }
    public Polygon(double orientation, Point[] vertices) {
        this.orientation = orientation;
        this.vertices = vertices;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public Point[] getVertices(Position origin) {
        return vertices;
    }

    public void setVertices(Point[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public Polygon toPolygon(Position p) {
       Point[] newVertices = new Point[vertices.length];
       for (int i=0; i< newVertices.length; i++)
           newVertices[i] =  new Point(p.getX()+vertices[i].getX(), p.getY()+vertices[i].getY());
        return new Polygon(p.getOrientation(), newVertices);
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "orientation=" + orientation +
                ", vertices=" + Arrays.toString(vertices) +
                '}';
    }

    public Circle getSafeCircle()
    {
        if (vertices == null)
            return new Circle(300);
        double maxDistance = Arrays.stream(vertices)
                .mapToDouble(v -> Math.pow(Math.pow(v.getX(), 2)+Math.pow(v.getY(), 2), 0.5))
                .max().orElse(100);
        return new Circle(maxDistance);
    }
}

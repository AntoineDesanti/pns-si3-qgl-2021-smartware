package fr.unice.polytech.si3.qgl.smartware.maths.shape;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.OrthogonalCoordinateSystem;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.GeoPoint;

public class Rectangle extends Shape implements Polygonizable{
    private double width;
    private double height;
    private double orientation;

    public Rectangle() {

    }

    public Rectangle(double width, double height, double orientation) {
        this.width = width;
        this.height = height;
        this.orientation = orientation;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    @Override
    public Point[] getVertices(Position center) {
        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(center.getX(), center.getY(),
                center.getOrientation());
        double w = width/2;
        double h = height/2;

        GeoPoint A = new GeoPoint(-w, h, ocs);
        GeoPoint B = new GeoPoint(w, h, ocs);
        GeoPoint C = new GeoPoint(w, -h, ocs);
        GeoPoint D = new GeoPoint(-w, -h, ocs);

        Point Abis = new Point(A.getX_absolute(), A.getY_absolute());
        Point Bbis = new Point(B.getX_absolute(), B.getY_absolute());
        Point Cbis = new Point(C.getX_absolute(), C.getY_absolute());
        Point Dbis = new Point(D.getX_absolute(), D.getY_absolute());

        Point[] points = new Point[]{ Abis,Bbis,Cbis,Dbis };
        return points;
    }


    @Override
    public Polygon toPolygon(Position origin) {
        return new Polygon(origin.getOrientation(), getVertices(origin));
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "width=" + width +
                ", height=" + height +
                ", orientation=" + orientation +
                '}';
    }
}

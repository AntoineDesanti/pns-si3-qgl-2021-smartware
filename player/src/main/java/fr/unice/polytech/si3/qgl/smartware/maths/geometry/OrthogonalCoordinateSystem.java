package fr.unice.polytech.si3.qgl.smartware.maths.geometry;

public class OrthogonalCoordinateSystem {

    private double x;
    private double y;
    private double orientation;

    public final static OrthogonalCoordinateSystem base = new OrthogonalCoordinateSystem(0,0,0);

    public OrthogonalCoordinateSystem(double x, double y, double orientation)
    {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
    }

    public double getAngle(GeoPoint point)
    {
        GeoPoint pointCopy = point.changeOCS(this);
        return Math.atan2(pointCopy.getY_relative(), pointCopy.getX_relative());
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

    public void printOCS()
    {
        System.out.println(x+ ", "+y+", "+Math.toDegrees(orientation));
    }
}

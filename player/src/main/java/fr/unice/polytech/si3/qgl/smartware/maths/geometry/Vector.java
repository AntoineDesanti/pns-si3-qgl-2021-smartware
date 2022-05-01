package fr.unice.polytech.si3.qgl.smartware.maths.geometry;

public class Vector {

    private GeoPoint origin;
    private GeoPoint target;

    public Vector(GeoPoint origin, GeoPoint target)
    {
        this.origin = origin;
        this.target = target;
    }

    public double getX()
    {
        return target.getX_absolute()-origin.getX_absolute();
    }

    public double getY()
    {
        return target.getY_absolute()-origin.getY_absolute();
    }

    public GeoPoint getOrigin() { return origin; }

    public GeoPoint getTarget() { return target; }

    public static boolean haveSameSense(Vector vector1, Vector vector2)
    {
        return vector1.getX()* vector2.getX() >= 0 && vector1.getY()* vector2.getY() >= 0;
    }

    public String toString()
    {
        return "("+Math.round(getX()*100)/100.0+";"+Math.round(getY()*100)/100.0+")";
    }

    public double getLength()
    {
        return Math.pow(Math.pow(getX(), 2)+Math.pow(getY(), 2), 0.5);
    }
}

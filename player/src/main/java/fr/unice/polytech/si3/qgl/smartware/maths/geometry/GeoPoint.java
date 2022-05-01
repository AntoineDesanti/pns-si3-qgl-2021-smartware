package fr.unice.polytech.si3.qgl.smartware.maths.geometry;

import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;

public class GeoPoint implements JsonHandler {

    private double x_absolute;
    private double y_absolute;
    private double x_relative;
    private double y_relative;
    private OrthogonalCoordinateSystem ocs;

    public GeoPoint(double x, double y, OrthogonalCoordinateSystem ocs) {
        this.x_relative = x;
        this.y_relative = y;
        this.x_absolute = x;
        this.y_absolute = y;
        this.ocs = ocs;

        if (!ocs.equals(OrthogonalCoordinateSystem.base))
        {
            double sin = Math.sin(ocs.getOrientation());
            double cos = Math.cos(ocs.getOrientation());
            x_absolute = ocs.getX() + x_relative*cos - y_relative*sin;
            y_absolute = ocs.getY() + x_relative*sin + y_relative*cos;
        }
    }
//todo position relative point pr tangente !!!!!!!!!!!!!!
    public GeoPoint(double x, double y) { // si on se place dans la base, relatives et absolues sont identiques
        this.x_relative = x;
        this.y_relative = y;
        this.x_absolute = x;
        this.y_absolute = y;
        this.ocs = OrthogonalCoordinateSystem.base;
    }

    public double getX_absolute() { return x_absolute; }
    public double getY_absolute() {
        return y_absolute;
    }
    public double getX_relative() { return x_relative; }
    public double getY_relative() { return y_relative; }

    public OrthogonalCoordinateSystem getOcs(){ return ocs; }

    public GeoPoint changeOCS(OrthogonalCoordinateSystem newOCS)
    {
        if (newOCS == ocs)
            return this;

        double tmpX_relative = x_absolute-newOCS.getX();
        double tmpY_relative = y_absolute-newOCS.getY();

        double angle = -newOCS.getOrientation();
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double rotatedX_rel = tmpX_relative * cos - tmpY_relative * sin;
        double rotatedY_rel = tmpX_relative * sin + tmpY_relative * cos;

        GeoPoint newPoint = new GeoPoint(rotatedX_rel, rotatedY_rel, newOCS);
        return newPoint;

    }

    public static double getDistance( double fromX, double fromY, double toX, double toY ) {
        var dX = Math.abs( fromX - toX );
        var dY = Math.abs( fromY - toY );

        return Math.sqrt( ( dX * dX ) + ( dY * dY ) );
    }

    public void printPoint()
    {
        System.out.println(toString());
    }

    @Override
    public String toString()
    {
        return "Point: ("+Math.round(x_absolute*100)/100.0+", "+Math.round(y_absolute*100)/100.0+")";
    }

    public Position toSeaPosition()
    {
        return new Position(x_absolute, y_absolute, ocs.getOrientation());
    }
}

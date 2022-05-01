package fr.unice.polytech.si3.qgl.smartware.maths.geometry;

public class Angle {

    public static double framed(double angle)
    {
        while(angle < -Math.PI/2)
            angle += Math.PI;
        while(angle > Math.PI/2)
            angle -= Math.PI;
        return angle;
    }

    public static double normalized(double angle)
    {
        while(angle < -Math.PI)
            angle += 2*Math.PI;
        while(angle > Math.PI)
            angle -= 2*Math.PI;
        return angle;
    }
}

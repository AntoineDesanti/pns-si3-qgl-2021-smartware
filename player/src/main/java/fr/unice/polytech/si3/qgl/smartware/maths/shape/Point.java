package fr.unice.polytech.si3.qgl.smartware.maths.shape;

public class Point{
    private double x;
    private double y;

    public Point() {

    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    static float[] intersect(float lx1, float ly1, float lx2, float ly2,
                            float px1, float py1, float px2, float py2) {
        // calc slope
        float ml = (ly1-ly2) / (lx1-lx2);
        float mp = (py1-py2) / (px1-px2);

        // calc intercept
        float bl = ly1 - (ml*lx1);
        float bp = py1 - (mp*px1);

        float x = (bp - bl) / (ml - mp);
        float y = ml * x + bl;

        return new float[]{x,y};
    }
    /*
    public static void main(String[] args) {

        float[] coords = intersect(1,1,5,5,1,4,5,3);
        System.out.println(coords[0] + " " + coords[1]);

    } */
}

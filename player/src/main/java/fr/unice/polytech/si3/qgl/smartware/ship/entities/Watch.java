package fr.unice.polytech.si3.qgl.smartware.ship.entities;

public class Watch extends Entity{
    private int x;
    private int y;

    public Watch() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        super.setX(x);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        super.setY(y);
    }

    @Override
    public String toString() {
        return "Watch{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

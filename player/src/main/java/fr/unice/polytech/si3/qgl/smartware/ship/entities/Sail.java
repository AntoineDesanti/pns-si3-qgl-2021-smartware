package fr.unice.polytech.si3.qgl.smartware.ship.entities;

public class Sail extends Entity{
    private int x;
    private int y;
    private boolean openned;

    public Sail() {
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

    public boolean isOpenned() {
        return openned;
    }

    public void setOpenned(boolean openned) {
        this.openned = openned;
    }
}

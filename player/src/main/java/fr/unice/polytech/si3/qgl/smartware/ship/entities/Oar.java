package fr.unice.polytech.si3.qgl.smartware.ship.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Oar extends Entity{
    private int x;
    private int y;

    public Oar() {
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @JsonIgnore
    public boolean isOnLeft() { return y == 0; }

    @Override
    public String toString() {
        return "Oar{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

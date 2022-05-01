package fr.unice.polytech.si3.qgl.smartware.ship.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;

import java.util.List;

public class Rudder extends Entity{
    int x;
    int y;
    @JsonIgnore
    private double angle = 0;

    public Rudder() {
    }

    public Rudder(double angle)
    {
        this.angle = angle;
    }

    @JsonIgnore
    public double getAngle()
    {
        return angle;
    }
    @JsonIgnore
    public void setAngle(double angle)
    {

        this.angle = angle < 0 ? Math.max(-Math.PI/4, angle) : Math.min(Math.PI/4, angle);
        /*
        if (angle <= Math.PI/4 && angle >= -Math.PI/4)
            this.angle = angle;
         */
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
        return "Rudder{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public Sailor getClosestSailor(Deck deck, List<Sailor> availableSailors){
        return deck.getCase(this.x,this.y).getClosestSailor(deck,availableSailors);
    }
}

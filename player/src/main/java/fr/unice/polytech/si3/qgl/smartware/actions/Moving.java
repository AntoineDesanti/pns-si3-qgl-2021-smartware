package fr.unice.polytech.si3.qgl.smartware.actions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;
import fr.unice.polytech.si3.qgl.smartware.maths.Path;

import java.util.HashMap;

public class Moving extends Action implements JsonHandler {
    private int xdistance;
    private int ydistance;


    public Moving(){

    }

    @Override
    public void execute(Game gameSimu, HashMap<String, Object> valuesForCalculation, Sailor s) {
        int finalX = s.getX()+ getXdistance();
        int finalY = s.getY() + getYdistance();
        s.moveToCase(new Path(){{add(gameSimu.getShip().getDeck().getCase(finalX, finalY));}});
    }


    public Moving(int sailorId, int xdistance, int ydistance) {
        super(sailorId, ActionNameEnum.MOVING.getType());
        this.xdistance = xdistance;
        this.ydistance = ydistance;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName().toUpperCase();
    }

    public int getXdistance() {
        return xdistance;
    }

    public void setXdistance(int xdistance) {
        this.xdistance = xdistance;
    }

    public int getYdistance() {
        return ydistance;
    }

    public void setYdistance(int ydistance) {
        this.ydistance = ydistance;
    }

    @Override
    public String toString() { return toJsonString(); }

    /*
    public String toString() {
        return "Moving{" +
                "xdistance=" + xdistance +
                ", ydistance=" + ydistance +
                '}';
    }*/
}

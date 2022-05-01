package fr.unice.polytech.si3.qgl.smartware.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.sea.Sea;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import fr.unice.polytech.si3.qgl.smartware.game.goal.Goal;


public class Game implements JsonHandler, Cloneable {
    private Goal goal;
    private Ship ship;
    private Sailor[] sailors;
    private int shipCount;

    @JsonIgnore
    private Sea sea;


    public Game() {
        sea = new Sea();
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Sailor[] getSailors() {
        return sailors;
    }

    public Sailor getSailorById(int sailorId){
        for(Sailor sailor : sailors){
            if(sailor.getId() == sailorId) return sailor;
        }
        return null;
    }

    public void setSailor(Sailor[] sailor) {
        this.sailors = sailor;
    }

    public int getShipCount() {
        return shipCount;
    }

    public void setShipCount(int shipCount) {
        this.shipCount = shipCount;
    }

    public void setSailors(Sailor[] sailors) {
        this.sailors = sailors;
    }

    public Sea getSea() {
        return sea;
    }

    public void setSea(Sea sea) {
        this.sea = sea;
    }

    public boolean instanciateDeck(){
        return getShip().getDeck().createDeck();
    }

    public boolean fillDeck(){
      return this.getShip().fillDeck(sailors);
    }

    public int getTotalOars(){ return getShip().getDeck().getOars().size(); }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

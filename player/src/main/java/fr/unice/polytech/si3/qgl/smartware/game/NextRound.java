package fr.unice.polytech.si3.qgl.smartware.game;

import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.sea.SeaEntity;
import fr.unice.polytech.si3.qgl.smartware.sea.Wind;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;

public class NextRound implements JsonHandler{
    private Ship ship;
    private SeaEntity[] visibleEntities;
    private Wind wind;

    public NextRound(Ship ship, Wind wind){
        this.ship = ship;
        this.wind = wind;
    }

    public NextRound(Ship ship, SeaEntity[] visibleEntities, Wind wind){
        this.ship = ship;
        this.visibleEntities = visibleEntities;
        this.wind = wind;
    }

    public NextRound()
    {

    }

    public Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public SeaEntity[] getVisibleEntities() {
        return visibleEntities;
    }

    public void setVisibleEntities(SeaEntity[] visibleEntities) {
        this.visibleEntities = visibleEntities;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }
}

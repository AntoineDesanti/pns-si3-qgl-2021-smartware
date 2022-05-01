package fr.unice.polytech.si3.qgl.smartware.actions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Sail;

import java.util.ArrayList;
import java.util.HashMap;

public class LiftSail extends Action implements JsonHandler {
    public LiftSail(){

    }

    @Override
    public void execute(Game gameSimu, HashMap<String, Object> valuesForCalculation, Sailor s) {
        if(gameSimu.getShip().getDeck().whatOnCase(s.getX(), s.getY()).size()>1){
            for(Object o : gameSimu.getShip().getDeck().whatOnCase(s.getX(), s.getY())){
                if(o instanceof Sail){
                    if(!((Sail) o).isOpenned()) ((Sail)o).setOpenned(true);
                }
            }
        }
    }

    public LiftSail(int sailorId){
        super(sailorId, ActionNameEnum.LIFT_SAIL.getType());
    }

    @Override
    public String getType() {
        return "LIFT_SAIL";
    }
    @Override
    public String toString() { return toJsonString(); }

}

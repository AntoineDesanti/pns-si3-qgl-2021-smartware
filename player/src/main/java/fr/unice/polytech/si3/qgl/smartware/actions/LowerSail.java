package fr.unice.polytech.si3.qgl.smartware.actions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Sail;

import java.util.HashMap;

public class LowerSail extends Action implements JsonHandler {
    public LowerSail(){

    }

    @Override
    public void execute(Game gameSimu, HashMap<String, Object> valuesForCalculation, Sailor s) {
        if(gameSimu.getShip().getDeck().whatOnCase(s.getX(), s.getY()) != null){
            for(Object o : gameSimu.getShip().getDeck().whatOnCase(s.getX(), s.getY())){
                if(o instanceof Sail){
                    if(((Sail) o).isOpenned()) ((Sail)o).setOpenned(false);
                }
            }
        }
    }

    public LowerSail(int sailorId){
        super(sailorId, ActionNameEnum.LOWER_SAIL.getType());
    }

    @Override
    public String getType() {
        return "LOWER_SAIL";
    }
}

package fr.unice.polytech.si3.qgl.smartware.actions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Watch;

import java.util.HashMap;

public class UseWatch extends Action implements JsonHandler {
    public UseWatch(){}

    @Override
    public void execute(Game gameSimu, HashMap<String, Object> valuesForCalculation, Sailor s) {
        for(Object o : gameSimu.getShip().getDeck().whatOnCase(s.getX(), s.getY())){
            if(o instanceof Watch){
                Boolean hasUseWatch = (Boolean)valuesForCalculation.get("hasUseWatch");
                hasUseWatch = true;
                Integer ENTITIES_AROUND_SHIP_RADIUS = (Integer)valuesForCalculation.get("ENTITIES_AROUND_SHIP_RADIUS");
                ENTITIES_AROUND_SHIP_RADIUS = 5000;
            }
        }
    }

    public UseWatch(int sailorId){
        super(sailorId, ActionNameEnum.USE_WATCH.getType());
    }

    @Override
    public String getType() {
        return "USE_WATCH";
    }
}

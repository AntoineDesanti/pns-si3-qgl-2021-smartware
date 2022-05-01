package fr.unice.polytech.si3.qgl.smartware.actions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;

import java.util.HashMap;

public class AOar extends Action implements JsonHandler {
    public AOar(){}

    @Override
    public void execute(Game gameSimu, HashMap<String, Object> valuesForCalculation, Sailor s) {
        Integer leftOars = (Integer)valuesForCalculation.get("leftOars");
        Integer rightOars = (Integer)valuesForCalculation.get("rightOars");
        if (s.getY() == 0) leftOars++;
        else rightOars++;
    }

    public AOar(int sailorId){
        super(sailorId, ActionNameEnum.OAR.getType());
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName().substring(1).toUpperCase();
    }

    @Override
    public String toString() { return toJsonString(); }

}

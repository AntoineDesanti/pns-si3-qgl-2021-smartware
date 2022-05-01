package fr.unice.polytech.si3.qgl.smartware.actions;

import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;

import java.util.HashMap;

public class Reload extends Action implements JsonHandler {
    public Reload(){}

    @Override
    public void execute(Game gameSimu, HashMap<String, Object> valuesForCalculation, Sailor s) {

    }

    public Reload(int sailorId){
        super(sailorId, "RELOAD");
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName().toUpperCase();
    }
}

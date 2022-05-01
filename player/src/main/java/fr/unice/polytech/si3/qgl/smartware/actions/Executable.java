package fr.unice.polytech.si3.qgl.smartware.actions;

import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;

import java.util.HashMap;

public interface Executable {
    public void execute(Game gameSimu, HashMap<String, Object> valuesForCalculation, Sailor s);
}

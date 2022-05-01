package fr.unice.polytech.si3.qgl.smartware.actions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Rudder;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Sail;

import java.util.HashMap;

public class Turn extends Action implements JsonHandler {

    private double rotation;

    public Turn(){

    }

    @Override
    public void execute(Game gameSimu, HashMap<String, Object> valuesForCalculation, Sailor s) {
        if( (getRotation() >= -Math.PI/4) && (getRotation() <= Math.PI/4) ){
            if(gameSimu.getShip().getDeck().whatOnCase(s.getX(), s.getY()) != null){
                for(Object o : gameSimu.getShip().getDeck().whatOnCase(s.getX(), s.getY())){
                    if(o instanceof Rudder){
                        gameSimu.getShip().getRudder().setAngle(getRotation());
                    }
                }
            }
        }
    }


    public Turn(int sailorId){
        super(sailorId, ActionNameEnum.TURN.getType());
    }

    public Turn(int sailorId, double rotation)
    {
        super(sailorId, ActionNameEnum.TURN.getType());
        setRotation(rotation);
    }

    public double getRotation() {
        return rotation;
    }

    public void setRotation(double rotation) {
        if(rotation >= (-Math.PI)/4 && rotation <= (Math.PI)/4)
            this.rotation = rotation;
    }

    @Override
    public String getType() {
        return this.getClass().getSimpleName().toUpperCase();
    }
}

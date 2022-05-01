package fr.unice.polytech.si3.qgl.smartware.actions;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;

import java.util.HashMap;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Moving.class, name = "MOVING"),
        @JsonSubTypes.Type(value = Aim.class, name = "AIM"),
        @JsonSubTypes.Type(value = Fire.class, name = "FIRE"),
        @JsonSubTypes.Type(value = LiftSail.class, name = "LIFT_SAIL"),
        @JsonSubTypes.Type(value = LowerSail.class, name = "LOWER_SAIL"),
        @JsonSubTypes.Type(value = AOar.class, name = "OAR"),
        @JsonSubTypes.Type(value = Reload.class, name = "RELOAD"),
        @JsonSubTypes.Type(value = Turn.class, name = "TURN"),
        @JsonSubTypes.Type(value = UseWatch.class, name = "USE_WATCH"),
})

public abstract class Action implements Executable{
    private int sailorId;
    private String type;

    public int getSailorId() {
        return sailorId;
    }

    public void setSailorId(int sailorId) {
        this.sailorId = sailorId;
    }

    public abstract String getType();

    public void setType(String type){
        this.type = type;
    }

    public Action(){

    }

    public Action(int sailorId, String type) {
        this.sailorId = sailorId;
        this.type = type;
    }

    public abstract void execute(Game gameSimu, HashMap<String, Object> valuesForCalculation, Sailor s);

}

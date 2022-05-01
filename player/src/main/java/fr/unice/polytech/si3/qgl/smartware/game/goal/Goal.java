package fr.unice.polytech.si3.qgl.smartware.game.goal;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "mode", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegattaGoal.class, name = "REGATTA"),
        @JsonSubTypes.Type(value = BattleGoal.class, name = "BATTLE")
})

public abstract class Goal implements JsonHandler {

}

package fr.unice.polytech.si3.qgl.smartware.ship.entities;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.maths.Case;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Void.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Oar.class, name = "oar"),
        @JsonSubTypes.Type(value = Sail.class, name = "sail"),
        @JsonSubTypes.Type(value = Rudder.class, name = "rudder"),
        @JsonSubTypes.Type(value = Watch.class, name = "watch"),
        @JsonSubTypes.Type(value = Canon.class, name = "canon"),
})

public class Entity{

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Case getCase(Ship s){
        return s.getDeck().getCase(this.x,this.y);
    }

    @Override
    public String toString() {
        return "Entity{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

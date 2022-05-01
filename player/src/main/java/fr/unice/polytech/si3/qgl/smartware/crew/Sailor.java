package fr.unice.polytech.si3.qgl.smartware.crew;
import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.actions.*;
import fr.unice.polytech.si3.qgl.smartware.maths.Path;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Deck;
import fr.unice.polytech.si3.qgl.smartware.maths.Case;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Entity;

import java.util.ArrayList;


public class Sailor implements JsonHandler {
    private int id;
    private int x;
    private int y;
    private String name;
    @JsonIgnore
    private Deck currentDeck;
    @JsonIgnore
    private int actionNumber;


    public Sailor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) { this.x = x; }

    public int getY() {
        return y;
    }

    public void setY(int y) { this.y = y; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    /***
     * moveToCase moves a sailor to the farthest case accessible from the given Path. It removes the sailor from actual deck case and add it to the destination case. Can be null if couldn't put the sailor on the final case
     * @param path
     * @return
     */
    @JsonIgnore
    public Moving moveToCase(Path path){

            int initialX = this.getX();
            int initialY = this.getY();

            if(path.size()>5){
                this.setX(path.get(5).getX());
                this.setY(path.get(5).getY());

                if(this.getCurrentDeck().putOnDeck(this, path.get(5).getX(), path.get(5).getY())){
                    currentDeck.removeFromCase(this,initialX,initialY);
                    return new Moving(this.id,path.get(5).getX()-initialX,path.get(5).getY()-initialY);
                }
            }
            else{
                if(this.getCurrentDeck().putOnDeck(this, path.getLast().getX(), path.getLast().getY())){
                    this.setX(path.getLast().getX());
                    this.setY(path.getLast().getY());

                    currentDeck.removeFromCase(this,initialX,initialY);
                    return new Moving(this.id,path.deltaX(), path.deltaY());
                }
            }

        return new Moving(this.id,0, 0);
    }

    public Path getPathToCase(Case targetCase) throws Exception {
        return currentDeck.deckPathFinding(this, targetCase.getX(), targetCase.getY());
    }

    @JsonIgnore
    public int getActionNumber() {
        return actionNumber;
    }

    @JsonIgnore
    public void setActionNumber(int actionNumber) {
        this.actionNumber = actionNumber;
    }

    @JsonIgnore
    public boolean isOnOar()
    {
        return currentDeck.getOar(x, y) != null;
    }

    @JsonIgnore
    public void setDeck(Deck deck) {
        currentDeck=deck;
    }

    @JsonIgnore
    public Deck getCurrentDeck() {
        return currentDeck;
    }
}

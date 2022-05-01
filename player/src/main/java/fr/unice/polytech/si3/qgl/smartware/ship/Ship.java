package fr.unice.polytech.si3.qgl.smartware.ship;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.Angle;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.OrthogonalCoordinateSystem;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.GeoPoint;
import fr.unice.polytech.si3.qgl.smartware.sea.SeaEntity;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.*;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Shape;

import java.util.ArrayList;
import java.util.Arrays;


public class Ship implements JsonHandler {

    private int life;
    private Position position;
    private String name;
    private Entity[] entities;
    private Deck deck;
    private Shape shape;
    private String type;

    public Ship() {
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) { this.position = position; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Entity[] getEntities() {
        return entities;
    }

    public void setEntities(Entity[] entities) {
        this.entities = entities;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonIgnore
    public ArrayList<Oar> getRightOars(){
        ArrayList<Oar> rightOars = new ArrayList<>();

        for(Oar entity : deck.getOars()){
            if(!entity.isOnLeft())
                rightOars.add(entity);
        }
        return rightOars;
    }

    @JsonIgnore
    public ArrayList<Oar> getLeftOars(){
        ArrayList<Oar> leftOar = new ArrayList<>();

        for(Oar entity : deck.getOars()){
            if(entity.isOnLeft())
                leftOar.add(entity);
        }
        return leftOar;
    }

    public boolean fillDeck(Sailor[] sailors){
        for(Entity entity : this.getEntities()){
            this.getDeck().putOnDeck(entity,entity.getX(),entity.getY());
        }

        for(Sailor sailor : sailors){
            sailor.setDeck(deck);
            this.getDeck().putOnDeck(sailor,sailor.getX(),sailor.getY());
        }
        return true;
    }

    public ArrayList<Sail> getSails() {
        ArrayList<Sail> sails = new ArrayList<>();
        for(Entity e :  entities){
            if(e instanceof Sail){
                sails.add((Sail)e);
            }
        }
        return sails;
    }

    public boolean watchExists() {
        for(Entity e :  entities)
            if(e instanceof Watch)
                return true;
        return false;
    }

    public boolean rudderExists() {
        for(Entity e :  entities)
            if(e instanceof Rudder)
                return true;
        return false;
    }

    public ArrayList<Sail> getOpennedSails(){
        ArrayList<Sail> opennedSails = new ArrayList<Sail>();
        for(Sail s : getSails()){
            if(s.isOpenned()){
                opennedSails.add(s);
            }
        }
        return opennedSails;
    }

    public int getSailsCount(){
        return getSails().size();
    }

    public Rudder getRudder()
    {
        for(Entity e: entities)
            if(e instanceof Rudder)
                return (Rudder) e;
        return null;
    }

    public int getNumberSails(){
        return getSails().size();
    }

    public SeaEntity giveCurrentSeaEntity(SeaEntity[] entitiesAround){
        for(SeaEntity s : entitiesAround){
            //if(s.isInside(this)){
                return s;
            //}
        }
        return null;
    }

    public double angleWithEntity(SeaEntity entity)
    {
        if (entity == null)
            return 0;
        OrthogonalCoordinateSystem ocs = new OrthogonalCoordinateSystem(position.getX(), position.getY(), position.getOrientation());
        GeoPoint entityPoint = new GeoPoint(entity.getPosition().getX(), entity.getPosition().getY());
     //   entityPoint = entityPoint.changeOCS(ocs);
        return Angle.framed(ocs.getAngle(entityPoint));
    }

    public Watch getWatch() {
        for(Entity e :  entities){
            if(e instanceof Watch){
              return  ((Watch)e);
            }
        }
        return null;
    }
}

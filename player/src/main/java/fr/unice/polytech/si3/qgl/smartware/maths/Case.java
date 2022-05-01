package fr.unice.polytech.si3.qgl.smartware.maths;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Deck;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Sail;
import fr.unice.polytech.si3.qgl.smartware.smartship.ScoredEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Case {
    private int x;
    private int y;
    private int mvtCostFromStart;
    private int estimatedDistanceFromEnd;
    private Case previous = null;
    private ArrayList<Object> elements;

    public Case(int x, int y, int mvtCostFromStart, int estimatedDistanceFromEnd) {
        this.x = x;
        this.y = y;
        this.mvtCostFromStart = mvtCostFromStart;
        this.estimatedDistanceFromEnd = estimatedDistanceFromEnd;
        elements = new ArrayList<>();
    }

    public Case(int x,int y){
        this.x = x;
        this.y = y;
        this.mvtCostFromStart = 0;
        this.estimatedDistanceFromEnd = 0;
        elements = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

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

    public boolean isEmpty(){
        if(elements.size()>0) return false;
        return true;
    }

    public boolean add(Object elem){
       return this.elements.add(elem);
    }

    public ArrayList<Object> getElements() {
        return elements;
    }

    public void setElements(ArrayList<Object> elements) {
        this.elements = elements;
    }

    public void addElements(Object ... elements)
    {
        for (Object element : elements)
            this.elements.add(element);
    }

    public int getMvtCostFromStart() {
        return mvtCostFromStart;
    }

    public void setMvtCostFromStart(int mvtCostFromStart) {
        this.mvtCostFromStart = mvtCostFromStart;
    }


    public void setEstimatedDistanceFromEnd(int estimatedDistanceFromEnd) {
        this.estimatedDistanceFromEnd = estimatedDistanceFromEnd;
    }

    public Case getPrevious() {
        return previous;
    }

    public void setPrevious(Case previous) {
        this.previous = previous;
    }

    public int getScore(){
        return mvtCostFromStart+estimatedDistanceFromEnd;
    }

    @Override
    public boolean equals(Object anotherCase) {
        if (this == anotherCase) return true;
        if (anotherCase == null || getClass() != anotherCase.getClass()) return false;

        Case aCase = (Case) anotherCase;

        if (x != aCase.x) return false;
        if (y != aCase.y) return false;

        return true;
    }

    public ArrayList<Sailor> containsSailor(){
        ArrayList <Sailor> sailors = new ArrayList<>();
        for(Object object : getElements())
            if (object instanceof Sailor)
                sailors.add((Sailor)object);
        return sailors;
    }

    public Oar getOar(){
        for(Object oar : getElements()){
            if (oar instanceof Oar){
               return (Oar)oar;
            }
        }
        return null;
    }

    public Sail getSail(){
        for(Object sail : getElements()){
            if (sail instanceof Sail){
                return (Sail)sail;
            }
        }
        return null;
    }

    public Sailor getClosestSailor(Deck deck, List<Sailor> sailors)
    {
        List<ScoredEntity> sailorsDistanceToCase = sailors.stream()
            .map(sailor -> {
                try {
                    return new ScoredEntity(deck.deckPathFinding(sailor, x, y).size()-1, sailor);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            })
            .collect(Collectors.toList());
        return (Sailor) ScoredEntity.getWorstObject(sailorsDistanceToCase);
    }

    @Override
    public String toString() {
        return "Case{" +
                "x=" + x +
                ", y=" + y +
                ", elements=" + elements +
                '}';
    }
}

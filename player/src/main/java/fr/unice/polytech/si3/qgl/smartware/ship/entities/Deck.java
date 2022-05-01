package fr.unice.polytech.si3.qgl.smartware.ship.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.maths.Case;
import fr.unice.polytech.si3.qgl.smartware.maths.Path;

import java.util.*;

public class Deck implements JsonHandler {
    private int width; //y is on width
    private int length; //x is on length
    @JsonIgnore
    private Case[][] deckGrid;


    public Deck() {
    }

    public Deck(int length,int width ) {
        this.width = width;
        this.length = length;

    }

    public Case[][] getDeckGrid() {
        return this.deckGrid;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public boolean createDeck(){
        if(this.getWidth()>0 && this.getLength()>0){
            deckGrid = new Case[length][width];
            for(int i=0; i<length; i++){
                for(int j=0; j<width; j++){
                    deckGrid[i][j]=new Case(i,j,0,0);
                }
            }
            return true;
        }
        return false;
    }

    public boolean putOnDeck(Object e, int x, int y){
        if(!validPosition(x,y)) return false;
        if( !(e instanceof Sailor) && !(e instanceof Entity)) return false;

         return deckGrid[x][y].add(e);
    }


    public void setWidth(int width) { this.width = width; }

    public void setLength(int length) {
        this.length = length;
    }

    public void setDeckGrid(Case[][] deckGrid) {
        this.deckGrid = deckGrid;
    }

    public boolean validPosition(int x, int y){
        if(x > length-1 || y > width-1 || x<0 || y<0) return false;
        return true;
    }

    public boolean positionAvailableForSailor(int x, int y){
        return this.validPosition(x,y);
    }

    public boolean isCaseEmpty(int x, int y){
        return deckGrid[x][y].isEmpty();
    }

    public ArrayList<Object> whatOnCase(int x,int y){
        if(!validPosition(x,y)) return null;
        return deckGrid[x][y].getElements();
    }

    public Path deckPathFinding(Sailor s, int x, int y) throws Exception {
        ArrayList<Case> open = new ArrayList<>();
        ArrayList<Case> close = new ArrayList<>();
        ArrayList<Case> currentCases = new ArrayList<>();

        if(s.getX()==x && s.getY()==y) return new Path();

        open.add(new Case(s.getX(),s.getY(),0, estimatedCityBlockDistance(s.getX(),s.getY(),x,y)));

          do{
            Case c = open.stream().min(Comparator.comparing(Case::getScore)).orElseThrow(()-> new Exception()); //taking the case with lowest score
            close.add(c);
            open.clear();

            Case c1 = new Case(c.getX()+1, c.getY(),0,0);
            Case c2 = new Case(c.getX()-1, c.getY(),0,0);
            Case c3 = new Case(c.getX(), c.getY()+1,0,0);
            Case c4 = new Case(c.getX(), c.getY()-1,0,0);
            c1.setPrevious(c);
            c2.setPrevious(c);
            c3.setPrevious(c);
            c4.setPrevious(c);

            currentCases.addAll(Arrays.asList(c,c1,c2,c3,c4));

            for(Case workingCase : currentCases){
                if(workingCase.getX()==x && workingCase.getY()==y){
                    close.add(workingCase);

                    return traceBack(workingCase);

                }
                if(this.validPosition(workingCase.getX(), workingCase.getY()) && !close.contains(workingCase)){
                    workingCase.setMvtCostFromStart(c.getMvtCostFromStart()+1);
                    workingCase.setEstimatedDistanceFromEnd(estimatedCityBlockDistance(workingCase.getX(),workingCase.getY(), x,y));
                    open.add(workingCase);
                }

            }
            currentCases.clear();
        }while(open.size()>0);


        return traceBack(close.get(close.size()-1));
    }

     public Path traceBack(Case workingCase) {
        Path trace = new Path();
        Case iterating = workingCase;

        trace.add(iterating);
        while (iterating.getPrevious() != null) {
            trace.add(iterating.getPrevious());
            iterating = iterating.getPrevious();
        }

        Collections.reverse(trace);
        return trace;
    }

    public int estimatedCityBlockDistance(int x1, int y1, int x2, int y2){
                return  (Math.abs(x1-x2)+Math.abs(y1-y2));
    }

    public Case getCase(int x, int y){
        if(validPosition(x,y)) return deckGrid[x][y];
        else return null;
    }

    public Oar getOar(int x,int y){
        if(validPosition(x,y)){
            return deckGrid[x][y].getOar();
        }

        return null;
    }
    @JsonIgnore
     public List<Oar> getOars()
    {
        List<Oar> oars = new ArrayList<>();

        for(int i=0; i<length;i++){
            Oar left = deckGrid[i][0].getOar();
            Oar right = deckGrid[i][width-1].getOar();
            if (left != null)
                oars.add(left);
            if (right != null)
                oars.add(right);
        }
        return oars;
    }
    @JsonIgnore
    public List<Sail> getSails()
    {
        List<Sail> sails = new ArrayList<>();

        for(int i=0; i<length;i++)
            for (int j=0; j<width; j++)
            {
                Sail sail = deckGrid[i][j].getSail();
                if (sail != null)
                    sails.add(sail);
            }
        return sails;
    }

    public boolean containsOar(int x,int y){
        if(getOar(x,y) != null) return true;
        return false;
    }


    public boolean removeFromCase(Object o, int x, int y){
        return getCase(x,y).getElements().remove(o);
    }


    @Override
    public String toString() {
        String str = "Deck[";
        for(int i=0; i<length-1;i++){
            for(int j=0; j<width-1; j++){
                str+= deckGrid[i][j].toString();
            }
        }
        return str;
  }

}

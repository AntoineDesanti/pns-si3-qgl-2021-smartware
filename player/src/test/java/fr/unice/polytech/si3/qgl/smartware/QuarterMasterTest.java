package fr.unice.polytech.si3.qgl.smartware;
import fr.unice.polytech.si3.qgl.smartware.crew.QuarterMaster;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.maths.Case;
import fr.unice.polytech.si3.qgl.smartware.maths.Path;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Deck;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Sail;
import fr.unice.polytech.si3.qgl.smartware.smartship.SailorOarPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class QuarterMasterTest {

    QuarterMaster quarterMaster;
    ArrayList<Sailor> sailors = new ArrayList<Sailor>();
    ArrayList<Oar> oars = new ArrayList<Oar>();
    Deck deck;
    SailorOarPath[] sailorOarPaths;

    @BeforeEach
    public void initQuarterMaster() throws Exception {
        deck = new Deck();
        deck.setWidth(4);
        deck.setLength(5);
        deck.createDeck();

        quarterMaster = new QuarterMaster(deck);

        sailors = new ArrayList<Sailor>() {
            {
                add(new Sailor());
                add(new Sailor());
                add(new Sailor());
                add(new Sailor());
            } };
        oars = new ArrayList<Oar>() {
            {
                add(new Oar());
                add(new Oar());
                add(new Oar());
                add(new Oar());
            } };


        IntStream.range(0, sailors.size()).forEach(i -> sailors.get(i).setId(i));
        sailors.get(0).setX(0);  oars.get(0).setX(1);
        sailors.get(0).setY(0);  oars.get(0).setY(0);
        sailors.get(1).setX(1);  oars.get(1).setX(1);
        sailors.get(1).setY(3);  oars.get(1).setY(3);
        sailors.get(2).setX(4);  oars.get(2).setX(3);
        sailors.get(2).setY(1);  oars.get(2).setY(0);
        sailors.get(3).setX(2);  oars.get(3).setX(3);
        sailors.get(3).setY(2);  oars.get(3).setY(3);

        sailorOarPaths = new SailorOarPath[sailors.size()* oars.size()];

        for (int i=0, k=0; i<sailors.size(); i++)
            for (int j=0; j<oars.size(); j++, k++)
            {
                Path path = deck.deckPathFinding(sailors.get(i), oars.get(j).getX(), oars.get(j).getY());
                sailorOarPaths[k] = new SailorOarPath(sailors.get(i), oars.get(j), path);
            }
                        /*
        Arrays.stream(sailorOarPaths)
                .forEach(e -> System.out.println("Sailor "+e.getSailor().getId()
                        +" | Oar: ("+e.getOar().getX()+","+e.getOar().getY()+")"));
                        */
    }
    @Test
    public void getAvailableSailorsTest()
    {
        assertEquals(Arrays.asList(0,1,2,3),
                quarterMaster.getAvailableSailors(Arrays.asList(sailorOarPaths)).stream()
                        .map(s -> s.getId()).collect(Collectors.toList()));

    }

    @Test
    public void getBestSailorMoveTest() throws Exception {
        List<SailorOarPath> bestMoves = quarterMaster.getBestSailorMoves(2,2, sailors, oars);
        List<Sailor> movedSailors = bestMoves.stream().map(e -> e.getSailor()).collect(Collectors.toList());
        List<Oar> oars2 = bestMoves.stream().map(e -> e.getOar()).collect(Collectors.toList());
        List<Integer> pathLength = bestMoves.stream().map(e -> e.getPath().size()).collect(Collectors.toList());

        assertEquals(sailors.get(1), movedSailors.get(0));
        assertEquals(oars.get(1), oars2.get(0));
        assertEquals(0, (int) pathLength.get(0));

        assertEquals(sailors.get(0), movedSailors.get(1));
        assertEquals(oars.get(0), oars2.get(1));
        assertEquals(2, (int) pathLength.get(1));

        assertEquals(sailors.get(3), movedSailors.get(2));
        assertEquals(oars.get(3), oars2.get(2));
        assertEquals(3, (int) pathLength.get(2));

        assertEquals(sailors.get(2), movedSailors.get(3));
        assertEquals(oars.get(2), oars2.get(3));
        assertEquals(3, (int) pathLength.get(3));

    }

    @Test
    public void getPotentialMovesTest(){
        List<Sailor> sailors = new ArrayList<Sailor>();
        List<Oar> oars = new ArrayList<Oar>();
    }
}

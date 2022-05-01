package fr.unice.polytech.si3.qgl.smartware.ship.entities;

import fr.unice.polytech.si3.qgl.smartware.Cockpit;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.maths.Case;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Cockpit cockpit;
    Deck deck;
    Deck deck2;
    String init;


    @BeforeEach
    void setUp() throws IOException {

        deck = new Deck(); //empty deck, for test purpose
        this.cockpit = new Cockpit();
        init = Files.readString(Path.of("./src/test/initgame.json"), StandardCharsets.US_ASCII);

        cockpit.initGame(init); //It includes deck.createdeck() and newGame.fillDeck()

        deck.setLength(20);
        deck.setWidth(20);

        deck2 = cockpit.getNewGame().getShip().getDeck();
        deck.createDeck();

    }


    @Test
    void positionAvailable() {
        assertTrue(deck.validPosition(19,19));
        assertTrue(deck.validPosition(0,0));
    }


    @Test
    void positionAvailableForSailor() {
        assertTrue(deck.validPosition(19,19));
        assertTrue(deck.validPosition(0,0));
        assertFalse(deck.validPosition(19,20));
        assertFalse(deck.validPosition(-1,0));
    }

    @Test
    void isCaseEmpty() {
        assertFalse(deck2.isCaseEmpty(0,0));
        assertFalse(deck2.isCaseEmpty(0,1));
        assertFalse(deck2.isCaseEmpty(0,2));
        assertFalse(deck2.isCaseEmpty(1,0));
        assertFalse(deck2.isCaseEmpty(1,1));
        assertFalse(deck2.isCaseEmpty(1,2));
        assertTrue(deck2.isCaseEmpty(2,0));
        assertFalse(deck2.isCaseEmpty(2,1));
        assertTrue(deck2.isCaseEmpty(2,2));
        assertFalse(deck2.isCaseEmpty(3,0));
        assertTrue(deck2.isCaseEmpty(3,1));
        assertFalse(deck2.isCaseEmpty(3,2));
        assertFalse(deck2.isCaseEmpty(4,0));
        assertTrue(deck2.isCaseEmpty(4,1));
        assertFalse(deck2.isCaseEmpty(4,2));
        assertFalse(deck2.isCaseEmpty(5,0));
        assertTrue(deck2.isCaseEmpty(5,1));
        assertTrue(deck2.isCaseEmpty(5,2));

    }


    @Test
    void wrongPositionAvailable() {
        assertTrue(deck.validPosition(19,19));
        assertFalse(deck.validPosition(20,20));
        assertFalse(deck.validPosition(19,20));
        assertFalse(deck.validPosition(-1,0));
        assertFalse(deck.validPosition(-1,0));
    }


    @Test
    void estimatedCityBlockDistance()
    {
        assertEquals(4, deck.estimatedCityBlockDistance(2,4,0,2));
        assertEquals(4, deck.estimatedCityBlockDistance(6,4,4,2));
        assertEquals(5, deck.estimatedCityBlockDistance(0,0,3,2));
        assertEquals(7, deck.estimatedCityBlockDistance(0,5,4,2));
        assertEquals(2, deck.estimatedCityBlockDistance(5,1,4,2));
    }

    @Test
    void traceBack(){
        Case c1 = new Case(1,2,0,0);
        Case c2 = new Case(2,2,0,0);
        Case c3 = new Case(3,2,0,0);
        Case c4 = new Case(4,2,0,0);

        c4.setPrevious(c3);
        c3.setPrevious(c2);
        c2.setPrevious(c1);

        ArrayList<Case> traceBack =  deck.traceBack(c4);

        assertEquals(c1,traceBack.get(0));
        assertEquals(c2,traceBack.get(1));
        assertEquals(c3,traceBack.get(2));
        assertEquals(c4,traceBack.get(3));

    }

    @Test
    void deckPathFindingAlreadyOnTargetTest() throws Exception {
        deck.createDeck();
        Sailor s = new Sailor();
        s.setX(1);
        s.setY(1);
        assertEquals(new ArrayList<>(), deck.deckPathFinding(s,1,1));

    }

    @Test
    void deckPathFinding1Test() throws Exception {

        ArrayList<Case> expected = new ArrayList<>(Arrays.asList(new Case(0,0,0,0), new Case(0,1,0,0), new Case(0,2,0,0)));

        Sailor s = new Sailor();
        s.setX(0);
        s.setY(0);
        assertEquals(expected,deck2.deckPathFinding(s,0,2));
    }

    @Test
    void deckPathFinding2Test() throws Exception {
        ArrayList<Case> expected = new ArrayList<>(Arrays.asList(new Case(1,0,0,0), new Case(0,0,0,0), new Case(0,1,0,0), new Case(0,2,0,0)));

        Sailor s = new Sailor();
        s.setX(1);
        s.setY(0);
        assertEquals(expected,deck2.deckPathFinding(s,0,2));
    }

    @Test
    void deckPathFinding3Test() throws Exception {
        ArrayList<Case> expected = new ArrayList<>(Arrays.asList(new Case(0,0,0,0), new Case(1,0,0,0), new Case(2,0,0,0), new Case(3,0,0,0), new Case(4,0,0,0), new Case(5,0,0,0), new Case(5,1,0,0)));

        Sailor s = new Sailor();
        s.setX(0);
        s.setY(0);
        assertEquals(expected,deck2.deckPathFinding(s,5,1));
    }


    @Test
    void deckPathFinding4Test() throws Exception {
        deck.createDeck();
        ArrayList<Case> expected = new ArrayList<>(Arrays.asList(new Case(0,0,0,0), new Case(1,0,0,0), new Case(2,0,0,0), new Case(3,0,0,0), new Case(4,0,0,0), new Case(5,0,0,0), new Case(6,0,0,0), new Case(7,0,0,0), new Case(8,0,0,0), new Case(8,1,0,0), new Case(8,2,0,0), new Case(8,3,0,0), new Case(8,4,0,0), new Case(8,5,0,0), new Case(8,6,0,0), new Case(8,7,0,0), new Case(8,8,0,0), new Case(8,9,0,0)));

        Sailor s = new Sailor();
        s.setX(0);
        s.setY(0);
        assertEquals(expected,deck.deckPathFinding(s,8,9));
    }

    @Test
    void whatOnCase() {
        assertEquals(deck2.getCase(0,0).getElements(),deck2.whatOnCase(0,0));
    }

    @Test
    void getCase() {
        assertEquals(deck2.getCase(0,0).getElements().get(0).getClass(),Sailor.class);
        assertEquals(deck2.getCase(5,0).getElements().get(0).getClass(),Rudder.class);
        assertEquals(deck2.getCase(2,1).getElements().get(0).getClass(),Sail.class);

        assertTrue(deck2.getCase(3,1).isEmpty());
    }

    @Test
    void removeFromCase(){
        Sailor s1 = cockpit.getNewGame().getSailorById(0);
        Sailor s2 = cockpit.getNewGame().getSailorById(3);

        assertTrue(deck2.getCase(s1.getX(),s1.getY()).getElements().contains(s1));
        assertTrue(deck2.getCase(s2.getX(),s2.getY()).getElements().contains(s2));

        assertTrue(deck2.removeFromCase(s1,s1.getX(),s1.getY()));
        assertTrue(deck2.removeFromCase(s2,s2.getX(),s2.getY()));

        assertFalse(deck2.getCase(s1.getX(),s1.getY()).containsSailor().contains(s1));
        assertFalse(deck2.getCase(s2.getX(),s2.getY()).containsSailor().contains(s2));
    }

    @Test
    void impossibleRemoveFromCase(){
        Sailor s1 = cockpit.getNewGame().getSailorById(0);
        Sailor s2 = cockpit.getNewGame().getSailorById(3);

        assertTrue(deck2.getCase(s1.getX(),s1.getY()).getElements().contains(s1));
        assertTrue(deck2.getCase(s2.getX(),s2.getY()).getElements().contains(s2));

        assertFalse(deck2.removeFromCase(s1,s1.getX()+1,s1.getY()+1));
        assertTrue(deck2.removeFromCase(s2,s2.getX(),s2.getY()));

        assertTrue(deck2.getCase(s1.getX(),s1.getY()).containsSailor().contains(s1));
        assertFalse(deck2.getCase(s2.getX(),s2.getY()).containsSailor().contains(s2));
    }
}
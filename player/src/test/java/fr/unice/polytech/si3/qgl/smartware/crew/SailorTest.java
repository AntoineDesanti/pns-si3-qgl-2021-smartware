package fr.unice.polytech.si3.qgl.smartware.crew;

import fr.unice.polytech.si3.qgl.smartware.actions.Moving;
import fr.unice.polytech.si3.qgl.smartware.maths.Case;
import fr.unice.polytech.si3.qgl.smartware.maths.Path;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SailorTest {

    Sailor s1;
    Sailor s2;
    Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck(6,4);
        deck.createDeck();
        s1 = new Sailor();
        s1.setX(0);
        s1.setY(0);
        s1.setDeck(deck);
        deck.putOnDeck(s1,0,0);

    }

    @Test
    void moveToCase() {
        Path p = new Path();
        ((ArrayList<Case>) p).addAll(Arrays.asList(new Case(0, 0), new Case(1, 0), new Case(2, 0), new Case(3, 0)));

        Moving m = s1.moveToCase(p);
        assertEquals(3, m.getXdistance());
        assertEquals(0, m.getYdistance());
        assertEquals(0, m.getSailorId());
        assertFalse(deck.getCase(0,0).containsSailor().size()>0);
        assertTrue(deck.getCase(3,0).containsSailor().size()>0);
    }

    @Test
    void moveToCase2() {
        Path p = new Path();
        ((ArrayList<Case>) p).addAll(Arrays.asList(new Case(0, 0), new Case(1, 0), new Case(2, 0), new Case(3, 0), new Case(4, 0), new Case(5, 0)));

        Moving m = s1.moveToCase(p);
        assertEquals(5, m.getXdistance());
        assertEquals(0, m.getYdistance());
        assertEquals(0, m.getSailorId());
        assertFalse(deck.getCase(0,0).containsSailor().size()>0);
        assertTrue(deck.getCase(5,0).containsSailor().size()>0);
    }

    @Test
    void moveToCase3() {
        Path p = new Path();
        ((ArrayList<Case>) p).addAll(Arrays.asList(new Case(0, 0), new Case(1, 0), new Case(1, 1), new Case(2, 1), new Case(2, 2), new Case(3, 2),new Case(3, 3)));

        Moving m = s1.moveToCase(p);
        assertEquals(3, m.getXdistance());
        assertEquals(2, m.getYdistance());
        assertEquals(0, m.getSailorId());
        assertFalse(deck.getCase(0,0).containsSailor().size()>0);
        assertTrue(deck.getCase(3,2).containsSailor().size()>0);
    }


}
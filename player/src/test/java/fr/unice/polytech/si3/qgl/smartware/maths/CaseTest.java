package fr.unice.polytech.si3.qgl.smartware.maths;

import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Oar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CaseTest {
    Case case1,case2;

    @BeforeEach
    void setUp(){
        case1 = new Case(0,1,0,5);
    }

    @Test
    void isEmpty() {
        assertTrue(case1.isEmpty());
        case1.add(new Oar());
        assertFalse(case1.isEmpty());
    }


    @Test
    void testEquals() {
        case2 = new Case(0,1,0,5);;
        assertTrue(case1.equals(case1));
        assertTrue(case1.equals(case2));

    }

    @Test
    void containsSailor() {

        assertFalse(case1.containsSailor().size()>0);
        case1.add(new Oar());
        assertFalse(case1.containsSailor().size()>0);
        case1.add(new Sailor());
        assertTrue(case1.containsSailor().size()>0);
    }

    @Test
    void getOar() {
        assertTrue(case1.getOar()==null);
        case1.add(new Oar());
        assertFalse(case1.getOar()==null);
    }
}
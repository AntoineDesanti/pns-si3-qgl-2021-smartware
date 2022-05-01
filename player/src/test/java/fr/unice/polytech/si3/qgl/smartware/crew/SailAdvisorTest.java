package fr.unice.polytech.si3.qgl.smartware.crew;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SailAdvisorTest {
    SailAdvisor sailAdvisor;

    @BeforeEach
    void setup(){
        sailAdvisor = new SailAdvisor(1);
    }

    @Test
    void isSailRequired() {
    }

    @Test
    void calculateAngle() {
        assertEquals(1.570796, sailAdvisor.calculateAngle(1.570796,0));
       // assertEquals(1.5707, sailAdvisor.calculateAngle(0.7853982,2.356194));

    }

    @Test
    void angleReal() {
        assertEquals(1.548667764616276,sailAdvisor.angleReal(-55));
    }

    @Test
    void windSpeed() {
        assertEquals(100.0,sailAdvisor.windSpeed(1,100,0));
        assertEquals(-100,sailAdvisor.windSpeed(1,100,3.1416),0.1); //180 degrés
        assertEquals(-100,sailAdvisor.windSpeed(1,100,-3.1416),0.1);
        assertEquals(0.0,sailAdvisor.windSpeed(1,100,-1.5708),0.1); //90 degrés
        assertEquals(70.7,sailAdvisor.windSpeed(1,100,0.785398), 0.1); //45 degrés
        assertEquals(50,sailAdvisor.windSpeed(1,100,1.047),0.1); //45 degrés



    }
}
package fr.unice.polytech.si3.qgl.smartware;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si3.qgl.smartware.game.goal.RegattaGoal;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.sea.Wind;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class CockpitTest {

    Cockpit cockpit, cockpit2;
    String init;


    @BeforeEach
    void setUp() throws IOException {
        this.cockpit = new Cockpit();
        this.cockpit2 = new Cockpit();
        init = Files.readString(Path.of("./src/test/initgame.json"), StandardCharsets.US_ASCII);
        cockpit2.initGame(init);
    }

    @Test
    void nextRoundTest() throws IOException {

//        assertEquals("[]", this.cockpit.nextRound(init));
    }

    @Test
    void testInitGame() {
        cockpit.initGame(init);
        assertEquals(1,cockpit.getNewGame().getShipCount());
    }

    @Test
    void testInitGameSailors() {
        cockpit.initGame(init);
        assertEquals(6, cockpit.getNewGame().getSailors().length);
    }

    @Test
    void testInitGameShipCount() {
        cockpit.initGame(init);
        assertEquals(1,cockpit.getNewGame().getShipCount());
    }

    @Test
    void testShip() {
        cockpit.initGame(init);
        assertEquals(100, cockpit.getNewGame().getShip().getLife());
        assertEquals(0, cockpit.getNewGame().getShip().getPosition().getX());
        assertEquals(0, cockpit.getNewGame().getShip().getPosition().getY());
        assertEquals(0, cockpit.getNewGame().getShip().getPosition().getOrientation());
    }

    @Test
    void testGoal() throws IOException {
        init = Files.readString(Path.of("./src/test/initgame.json"), StandardCharsets.US_ASCII);
        cockpit.initGame(init);
        if (cockpit.getNewGame().getGoal().getClass() == RegattaGoal.class){
            assertEquals(2, ((RegattaGoal) cockpit.getNewGame().getGoal()).getCheckpoints().length);
        }
        else
            fail();
    }

    @Ignore
    public void updateShipPositionTest() throws IOException {
        String nextRoundJson = Files.readString(Path.of(System.getProperty("user.dir") + "/src/test/nextRound.json"), StandardCharsets.US_ASCII);
        String init = Files.readString(Path.of(System.getProperty("user.dir") + "/src/test/initgame.json"), StandardCharsets.US_ASCII);
        cockpit.initGame(init);
        cockpit.getNewGame().getShip().setPosition(new Position(25,35,1));
        ObjectMapper o = new ObjectMapper();
        o = new ObjectMapper();
        o.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        o.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
   //  todo fix that   JsonNode test = o.readTree(nextRoundJson);
        assertEquals(1, cockpit.newGame.getShip().getPosition().getOrientation());
        assertEquals(25, cockpit.newGame.getShip().getPosition().getX());
        assertEquals(35, cockpit.newGame.getShip().getPosition().getY());
   //  todo   cockpit.updateShip(test);
        System.out.println(cockpit.newGame.getShip().getPosition().getY());
        assertEquals(0, cockpit.newGame.getShip().getPosition().getOrientation());
        assertEquals(0, cockpit.newGame.getShip().getPosition().getX());
        assertEquals(0, cockpit.newGame.getShip().getPosition().getY());
    }

    @Test
    void testUpdateWind() throws IOException {
        String nextRoundJson = Files.readString(Path.of(System.getProperty("user.dir") + "/src/test/nextRound.json"), StandardCharsets.US_ASCII);


        Wind w = cockpit2.getNewGame().getSea().getWind();
        double initialDirection = w.getOrientation();
        double initialStrength = w.getStrength();

        assertEquals(0,initialDirection);
        assertEquals(0,initialStrength);
        cockpit2.nextRound(nextRoundJson);

        assertEquals(0,w.getOrientation());
        assertEquals(110,w.getStrength());


    }


}
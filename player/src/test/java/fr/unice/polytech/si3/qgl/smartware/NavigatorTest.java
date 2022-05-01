package fr.unice.polytech.si3.qgl.smartware;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.navigation.Compass;
import fr.unice.polytech.si3.qgl.smartware.navigation.Navigator;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class NavigatorTest {

    @Test
    public void TestClosestAngle()
    {
        Navigator navigator = new Navigator(6, 6);
        assertEquals(Math.PI/3, navigator.getClosestAnglePossible(Math.PI/4));
        assertEquals(Math.PI/6, navigator.getClosestAnglePossible(Math.PI/8));
        assertEquals(Math.PI/6, navigator.getClosestAnglePossible(Math.PI/6));
        assertEquals(-Math.PI/6, navigator.getClosestAnglePossible(-Math.PI/6));
        assertEquals(0, navigator.getClosestAnglePossible(Math.toRadians(14)));

        // 15° has the same distance with 30° and 0°. 0° should be prioritized as it enables a greater ship speed.
        assertEquals(0, navigator.getClosestAnglePossible(Math.toRadians(15)));
        assertNotEquals(Math.toDegrees(30), navigator.getClosestAnglePossible(Math.toRadians(16)));
    }

    @Test
    public void getAngleWithTargetTest()
    {
        Compass compass = new Compass();

        Position origin = new Position(0,0,0);
        Position destination = new Position(0,0,Math.PI/4);
        assertEquals(0, compass.getAngleWithTarget(origin, destination));

        origin = new Position(0,0,0);
        destination = new Position(1,1,Math.PI/3);
        assertEquals(Math.PI/4, compass.getAngleWithTarget(origin, destination));


        origin = new Position(-2,1,Math.PI/4);
        destination = new Position(1,2,Math.PI/3);
        assertEquals(-0.46373398, compass.getAngleWithTarget(origin, destination), 0.01);
    }

    @Test
    public void getRemainingDistanceTest()
    {
        Navigator navigator = new Navigator(6, 6);

        Position currentPosition = new Position(0,0,0);
        Position targetPosition = new Position(0,0,0);
        assertEquals(0, navigator.getRemainingDistance(currentPosition, targetPosition));

        currentPosition = new Position(100,100,0);
        targetPosition = new Position(200,200,0);
        assertEquals(141.4, navigator.getRemainingDistance(currentPosition, targetPosition), 0.1);
    }
}
package fr.unice.polytech.si3.qgl.smartware.sea;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    public void isAroundTest()
    {
        Position position1 = new Position(0,0,0);
        Position position2 = new Position(4.24,4.24,0);

        assertEquals(false, position1.isAround(position2, 2));
        assertEquals(true, position1.isAround(position2, 6));
    }
}

package fr.unice.polytech.si3.qgl.smartware;
import fr.unice.polytech.si3.qgl.smartware.crew.TrajectoryAdvisor;
import fr.unice.polytech.si3.qgl.smartware.game.Checkpoint;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.navigation.NavigationTable;
import fr.unice.polytech.si3.qgl.smartware.navigation.Navigator;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import fr.unice.polytech.si3.qgl.smartware.smartship.OarsRepartition;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TrajectoryAdvisorTest {

    @Test
    public void TestFindLineInNavTable()
    {
        int numberOfSailors = 6;
        int numberOfOars = 6;
        TrajectoryAdvisor trajectoryAdvisor = new TrajectoryAdvisor(numberOfSailors, numberOfOars);
        Navigator navigator = new Navigator(numberOfSailors, numberOfOars);

        double desiredSpeed = (double) 3/6;
        double movingAngle = Math.PI/6;

        NavigationTable navigationTable = navigator.getNavigationTable();
//        navigationTable.print();
        assertEquals(6, trajectoryAdvisor.findLineInNavTable(navigationTable, desiredSpeed, movingAngle));
    }

    @Test
    public void TestGetOptimalSailorsRepartition()
    {
        /**
         * Best repartition of sailors on the left and on the right of the deck according to ship speed and angle
         */
        int numberOfSailors = 6;
        int numberOfOars = 6;
        Ship ship = new Ship();
        ship.setPosition(new Position());

        TrajectoryAdvisor trajectoryAdvisor = new TrajectoryAdvisor(numberOfSailors,numberOfOars);
        Navigator navigator = new Navigator(numberOfSailors, numberOfOars);

        Position checkpoint_position = new Position();
        checkpoint_position.setX(17.2);
        checkpoint_position.setY(25.0);
        // This creates an angle of 55° between the ship and the checkpoint
        // => for a maximum speed, there must be 3 sailors oaring on the left and 1 on the right.
        // => Thus the boat will have an angle of PI/3

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setPosition(checkpoint_position);
        OarsRepartition sailors_left_right = trajectoryAdvisor.getOptimalSailorsRepartition(checkpoint.getPosition(), ship.getPosition());
        assertEquals(0, sailors_left_right.getSailorsToPutOnLeft());
        assertEquals(2, sailors_left_right.getGetSailorsToPutOnRight());

        checkpoint_position.setX(17.2);
        checkpoint_position.setY(250.0);
        // The closest angle is PI/2 and it implies having 3 sailors on the left and 0 on the right (for max speed)
        checkpoint.setPosition(checkpoint_position);
        sailors_left_right = trajectoryAdvisor.getOptimalSailorsRepartition(checkpoint.getPosition(), ship.getPosition());
        assertEquals(0, sailors_left_right.getSailorsToPutOnLeft());
        assertEquals(3, sailors_left_right.getGetSailorsToPutOnRight());
    }

    @Test
    public void TestGetOptimalSailorsRepartition2()
    {
        /**
         * Best repartition of sailors on the left and on the right of the deck according to ship speed and angle
         */
        int numberOfSailors = 6;
        int numberOfOars = 8;
        Ship ship = new Ship();
        ship.setPosition(new Position());

        TrajectoryAdvisor trajectoryAdvisor = new TrajectoryAdvisor(numberOfSailors,numberOfOars);

        Position checkpoint_position = new Position();
        checkpoint_position.setX(-200);
        checkpoint_position.setY(200);
        // This creates an angle of 55° between the ship and the checkpoint
        // => for a maximum speed, there must be 3 sailors oaring on the left and 1 on the right.
        // => Thus the boat will have an angle of PI/3

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setPosition(checkpoint_position);
        OarsRepartition sailors_left_right = trajectoryAdvisor.getOptimalSailorsRepartition(checkpoint.getPosition(), ship.getPosition());
        assertEquals(0, sailors_left_right.getSailorsToPutOnLeft());
        assertEquals(4, sailors_left_right.getGetSailorsToPutOnRight());

    }

    @Test
    public void TestGetOptimalSailorsRepartition3()
    {
        /**
         * Best repartition of sailors on the left and on the right of the deck according to ship speed and angle
         * WHEN the ship is already very close to the target => sailor repartition should be minimal.
         */
        int numberOfSailors = 6;
        int numberOfOars = 8;
        Ship ship = new Ship();
        ship.setPosition(new Position());

        TrajectoryAdvisor trajectoryAdvisor = new TrajectoryAdvisor(numberOfSailors,numberOfOars);

        Position checkpoint_position = new Position();
        checkpoint_position.setX(20);
        checkpoint_position.setY(0);

        Checkpoint checkpoint = new Checkpoint();
        checkpoint.setPosition(checkpoint_position);
        OarsRepartition sailors_left_right = trajectoryAdvisor.getOptimalSailorsRepartition(checkpoint.getPosition(), ship.getPosition());
        assertEquals(1, sailors_left_right.getSailorsToPutOnLeft());
        assertEquals(1, sailors_left_right.getGetSailorsToPutOnRight());

    }
}

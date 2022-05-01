package fr.unice.polytech.si3.qgl.smartware.tooling;

import fr.unice.polytech.si3.qgl.smartware.actions.Actions;
import fr.unice.polytech.si3.qgl.smartware.actions.Moving;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static fr.unice.polytech.si3.qgl.smartware.tooling.simulation.ActionsHandler.*;
import static org.junit.jupiter.api.Assertions.*;

class ActionsHandlerTest {

    @Test
    void testGetActionsFromJSON() throws IOException {
        String actionsString = Files.readString(Path.of("./src/test/jsonTest/actions1.json"), StandardCharsets.US_ASCII);
        Actions parsedActions = getActionsFromJSON(actionsString);

        assertEquals(2, parsedActions.get(0).getSailorId());
        assertEquals("MOVING", parsedActions.get(4).getType());
        assertEquals(5, ((Moving)parsedActions.get(4)).getXdistance());
        assertEquals(0, ((Moving)parsedActions.get(4)).getYdistance());
    }

    @Test
    void testcheckActionsValidity() throws IOException{
        String RightActionsString = Files.readString(Path.of("./src/test/jsonTest/actions1.json"), StandardCharsets.US_ASCII);
        String WrongActionsString = Files.readString(Path.of("./src/test/jsonTest/wrongActions.json"), StandardCharsets.US_ASCII);

        Actions parsedRightActions = getActionsFromJSON(RightActionsString);
        Actions parsedWrongActions = getActionsFromJSON(WrongActionsString);

        assertFalse(checkActionsValidity(parsedWrongActions));
        assertTrue(checkActionsValidity(parsedRightActions));
    }

    @Test
    void testSortActionsByExecutionOrder() throws IOException{
        String actionsString = Files.readString(Path.of("./src/test/jsonTest/actions1.json"), StandardCharsets.US_ASCII);
        Actions parsedActions = getActionsFromJSON(actionsString);

        assertEquals("OAR", parsedActions.get(6).getType());
        assertEquals("TURN", parsedActions.get(5).getType());

        Actions sortedActions = sortActionsByExecutionOrder(parsedActions);

        assertEquals("OAR", sortedActions.get(5).getType());
        assertEquals("MOVING", sortedActions.get(0).getType());
    }
}

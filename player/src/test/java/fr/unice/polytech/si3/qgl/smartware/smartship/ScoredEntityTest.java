package fr.unice.polytech.si3.qgl.smartware;

import fr.unice.polytech.si3.qgl.smartware.smartship.ScoredEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoredEntityTest {

    @Test
    public void getBestScore()
    {
        List<ScoredEntity> scoredEntities = Arrays.asList(
                new ScoredEntity(1, "Object1"),
                new ScoredEntity(2, "Object2"),
                new ScoredEntity(5, "Object3"),
                new ScoredEntity(4, "Object4"),
                new ScoredEntity(3, "Object5")
        );

        assertEquals("Object3", ScoredEntity.getBestObject(scoredEntities));
    }

    @Test
    public void getWorstScore()
    {
        List<ScoredEntity> scoredEntities = Arrays.asList(
                new ScoredEntity(1, "Object1"),
                new ScoredEntity(2, "Object2"),
                new ScoredEntity(5, "Object3"),
                new ScoredEntity(4, "Object4"),
                new ScoredEntity(3, "Object5")
        );

        assertEquals("Object1", ScoredEntity.getWorstObject(scoredEntities));
    }
}

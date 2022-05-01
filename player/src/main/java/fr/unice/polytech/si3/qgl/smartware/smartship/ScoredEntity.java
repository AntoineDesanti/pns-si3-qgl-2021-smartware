package fr.unice.polytech.si3.qgl.smartware.smartship;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ScoredEntity<EntityType> {
    private int score;
    private EntityType entity;

    public ScoredEntity(int score, EntityType entity)
    {
        this.score = score;
        this.entity = entity;
    }

    public int getScore() {
        return score;
    }

    public EntityType getEntity() {
        return entity;
    }

    public static List<Object> getScoreOrderedObjects(List<ScoredEntity> scoredEntities)
    {
        return scoredEntities.stream()
                .sorted(Comparator.comparing(ScoredEntity::getScore))
                .map(scoredObject -> scoredObject.getEntity())
                .collect(Collectors.toList());
    }
    public static Object getBestObject(List<ScoredEntity> scoredEntities)
    {
        List<Object> orderedScoredEntities = getScoreOrderedObjects(scoredEntities);
        return orderedScoredEntities.get(orderedScoredEntities.size()-1);
    }

    public static Object getWorstObject(List<ScoredEntity> scoredEntities)
    {
        return getScoreOrderedObjects(scoredEntities).get(0);
    }

    public void print()
    {
        System.out.println("Score: "+score+" | Entity: "+entity.toString());
    }
}

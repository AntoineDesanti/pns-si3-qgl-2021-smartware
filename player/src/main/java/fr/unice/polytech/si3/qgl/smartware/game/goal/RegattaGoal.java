package fr.unice.polytech.si3.qgl.smartware.game.goal;

import fr.unice.polytech.si3.qgl.smartware.game.Checkpoint;

import java.util.ArrayList;
import java.util.List;

public class RegattaGoal extends Goal {
    private Checkpoint[] checkpoints;

    public RegattaGoal() {

    }

    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Checkpoint[] checkpoints) {
        this.checkpoints = checkpoints;
    }
}

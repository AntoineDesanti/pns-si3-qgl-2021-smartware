package fr.unice.polytech.si3.qgl.smartware.smartship;

import fr.unice.polytech.si3.qgl.smartware.game.Checkpoint;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.navigation.NavigationTable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OarsRepartition {

    private int sailorsToPutOnLeft = 0;
    private int getSailorsToPutOnRight = 0;
    private double residualAngle = 0;
    private double speed = 0;
    private double angle = 0;

    public OarsRepartition(int sailorsToPutOnLeft, int getSailorsToPutOnRight)
    {
        this.sailorsToPutOnLeft = sailorsToPutOnLeft;
        this.getSailorsToPutOnRight = getSailorsToPutOnRight;
    }

    public OarsRepartition(int sailorsToPutOnLeft, int getSailorsToPutOnRight, double residualAngle) {
        this.sailorsToPutOnLeft = sailorsToPutOnLeft;
        this.getSailorsToPutOnRight = getSailorsToPutOnRight;
        this.residualAngle = residualAngle;
    }

    public OarsRepartition(int sailorsToPutOnLeft, int getSailorsToPutOnRight, double residualAngle, double speed, double angle) {
        this.sailorsToPutOnLeft = sailorsToPutOnLeft;
        this.getSailorsToPutOnRight = getSailorsToPutOnRight;
        this.residualAngle = residualAngle;
        this.speed = speed;
        this.angle = angle;
    }

    public int getSailorsToPutOnLeft() {
        return sailorsToPutOnLeft;
    }

    public int getGetSailorsToPutOnRight() {
        return getSailorsToPutOnRight;
    }

    public double getResidualAngle() {
        return residualAngle;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAngle() {
        return angle;
    }
}

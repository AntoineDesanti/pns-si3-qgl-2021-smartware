package fr.unice.polytech.si3.qgl.smartware.sea;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.geometry.Vector;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Shape;

public class Stream extends SeaEntity {

    private double strength;
    private Shape shape;
    private Position position;

    public Stream()
    {
        super();
    }

    public Stream(double strength)
    {
        super();
        this.strength = strength;
    }

    public Stream(Position position, Shape shape, double strength)
    {
        super(shape, position);
        this.strength = strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public float prejudiceOnCollision(double angle)
    {
        return 0;
    }

    public double getStrength() {
        return strength;
    }
}

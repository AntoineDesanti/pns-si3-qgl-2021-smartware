package fr.unice.polytech.si3.qgl.smartware.sea;

import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Shape;

public class Reef extends SeaEntity {

    Position position;
    Shape shape;

    public Reef()
    {
        super();
    }


    public Reef(Position position, Shape shape)
    {
        super(shape, position);
        this.position = position;
        this.shape = shape;
    }

    @Override
    public void setShape(Shape shape) {
        super.setShape(shape);
        this.shape = shape;
    }

    @Override
    public void setPosition(Position position) {
        super.setPosition(position);
        this.position = position;
    }
}

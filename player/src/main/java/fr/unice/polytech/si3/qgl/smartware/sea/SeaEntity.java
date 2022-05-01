package fr.unice.polytech.si3.qgl.smartware.sea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import fr.unice.polytech.si3.qgl.smartware.game.Checkpoint;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.*;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = Checkpoint.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Reef.class, name = "reef"),
        @JsonSubTypes.Type(value = Stream.class, name = "stream"),
        @JsonSubTypes.Type(value = Stream.class, name = "checkpoints")
})

public abstract class SeaEntity {
    private Shape shape;
    private Position position;

    public SeaEntity(Shape shape, Position position)
    {
        this.shape = shape;
        this.position = position;
    }

    public SeaEntity()
    {
    }

    @JsonIgnore
    public int getHashcode() {
        return hashCode();
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Shape getShape() {
        return shape;
    }

    public Position getPosition() {
        return position;
    }

    public float prejudiceOnCollision(double angle)
    {
        return 1000;
    }

    public double getSecurityDistance()
    {
        if (shape instanceof Circle)
            return ((Circle) shape).getRadius();
        if (shape instanceof Rectangle)
            return Math.max(((Rectangle) shape).getHeight(), ((Rectangle) shape).getWidth());
        else
            return 10; //todo arbitraire!!!!!!!!
    }

    public boolean isInside(Ship ship){
        return polyPoly(ship.getShape().getVertices(ship.getPosition()),
                this.getShape().getVertices(position));
    }

    public boolean isInside(Polygon polygon, Position polygonPosition){
        return polyPoly(polygon.getVertices(polygonPosition), this.getShape().getVertices(position));
    }

    // POLYGON/POLYGON
    private boolean polyPoly(Point[] p1, Point[] p2) {

        // go through each of the vertices, plus the next
        // vertex in the list
        int next = 0;
        if(p1 == null || p2 == null ) return false;

        for (int current=0; current<p1.length; current++) {

            // get next vertex in list
            // if we've hit the end, wrap around to 0
            next = current+1;
            if (next == p1.length) next = 0;

            // get the PVectors at our current position
            // this makes our if statement a little cleaner
            Point vc = p1[current];    // c for "current"
            Point vn = p1[next];       // n for "next"

            // now we can use these two points (a line) to compare
            // to the other polygon's vertices using polyLine()
            boolean collision = polyLine(p2, vc.getX(),vc.getY(),vn.getX(),vn.getY());
            if (collision) return true;

            // optional: check if the 2nd polygon is INSIDE the first
            collision = polyPoint(p1, p2[0].getX(), p2[0].getY());
            if (collision) return true;
        }

        return false;
    }


    // POLYGON/LINE
    private boolean polyLine(Point[] vertices, double x1, double y1, double x2, double y2) {

        // go through each of the vertices, plus the next
        // vertex in the list
        int next = 0;
        if(vertices == null) return false;

        for (int current=0; current<vertices.length; current++) {

            // get next vertex in list
            // if we've hit the end, wrap around to 0
            next = current+1;
            if (next == vertices.length) next = 0;

            // get the PVectors at our current position
            // extract X/Y coordinates from each
            double x3 = vertices[current].getX();
            double y3 = vertices[current].getY();
            double x4 = vertices[next].getX();
            double y4 = vertices[next].getY();

            // do a Line/Line comparison
            // if true, return 'true' immediately and
            // stop testing (faster)
            boolean hit = lineLine(x1, y1, x2, y2, x3, y3, x4, y4);
            if (hit) {
                return true;
            }
        }

        // never got a hit
        return false;
    }


    // LINE/LINE
    private boolean lineLine(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {

        // calculate the direction of the lines
        double uA = ((x4-x3)*(y1-y3) - (y4-y3)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));
        double uB = ((x2-x1)*(y1-y3) - (y2-y1)*(x1-x3)) / ((y4-y3)*(x2-x1) - (x4-x3)*(y2-y1));

        // if uA and uB are between 0-1, lines are colliding
        if (uA >= 0 && uA <= 1 && uB >= 0 && uB <= 1) {
            return true;
        }
        return false;
    }


    // POLYGON/POINT
// used only to check if the second polygon is
// INSIDE the first
    private boolean polyPoint(Point[] vertices, double px, double py) {
        boolean collision = false;

        // go through each of the vertices, plus the next
        // vertex in the list
        int next = 0;
        if (vertices == null) return false;
        for (int current=0; current<vertices.length; current++) {

            // get next vertex in list
            // if we've hit the end, wrap around to 0
            next = current+1;
            if (next == vertices.length) next = 0;

            // get the PVectors at our current position
            // this makes our if statement a little cleaner
            Point vc = vertices[current];    // c for "current"
            Point vn = vertices[next];       // n for "next"

            // compare position, flip 'collision' variable
            // back and forth
            if (((vc.getY() > py && vn.getY() < py) || (vc.getY() < py && vn.getY() > py)) &&
                    (px < (vn.getX()-vc.getX())*(py-vc.getY()) / (vn.getY()-vc.getY())+vc.getX())) {
                collision = !collision;
            }
        }
        return collision;
    }




    @Override
    public String toString() {
        return "SeaEntity{" +
                "shape=" + shape +
                ", position=" + position +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeaEntity seaEntity = (SeaEntity) o;

        if (!shape.toString().equals(seaEntity.shape.toString())) return false;
        return position.equals(seaEntity.position);
    }

    @Override
    public int hashCode() {
        int result = shape.hashCode();
        result = 31 * result + position.hashCode();
        return result;
    }
}

package fr.unice.polytech.si3.qgl.smartware.sea;

public class Wind {
    private double orientation;
    private double strength;

    public Wind(double orientation, double strength) {
        this.orientation = orientation;
        this.strength = strength;
    }

    public Wind() {
        this.orientation = 0;
        this.strength = 0;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "orientation=" + orientation +
                ", strength=" + strength +
                '}';
    }
}

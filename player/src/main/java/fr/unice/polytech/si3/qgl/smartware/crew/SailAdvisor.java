package fr.unice.polytech.si3.qgl.smartware.crew;

import fr.unice.polytech.si3.qgl.smartware.sea.Wind;

public class SailAdvisor {
   int numberOfSail;

    public SailAdvisor(int numberOfSail) {
        if(numberOfSail==0)
            System.out.println("Can't create sailAdvisor");
        this.numberOfSail = numberOfSail;
    }

    public boolean sailUseful(double shipOrientation, Wind wind){
        double shipWindAngle = angleReal(wind.getOrientation()-shipOrientation);

        double speed = windSpeed(1,wind.getStrength(),shipWindAngle);
           if(speed>1) return true;

           return false;
    }

    public static double calculateAngle(double shipOrientation, double windOrientation){
        double angle = angleReal(shipOrientation)-angleReal(windOrientation);
        return angle;
    }

    public static double angleReal(double angle)
    {
        while(angle < -Math.PI){
            angle += 2*Math.PI;
        }
        while(angle > Math.PI){
            angle -= 2*Math.PI;
        }
        return angle;
    }

    public double windSpeed(int nbActiveSail, double windStrength, double angle){
            return (((double)nbActiveSail/(double)numberOfSail))*windStrength*Math.cos(angle);
    }
}

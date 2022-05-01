package fr.unice.polytech.si3.qgl.smartware.sea;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.unice.polytech.si3.qgl.smartware.sea.SeaEntity;
import fr.unice.polytech.si3.qgl.smartware.sea.Stream;
import fr.unice.polytech.si3.qgl.smartware.maths.Position;
import fr.unice.polytech.si3.qgl.smartware.maths.shape.Circle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Sea {

    private Wind wind;
    private SeaEntity[] seaEntities;
    @JsonIgnore
    public final int NB_SEA_ENTITIES = 10000;
    @JsonIgnore
    public final int OFFSET_BETWEEN_ENTITIES = 100;

    public Sea() {
        this.wind = new Wind(0,0);
    }

    public Wind getWind() {
        return this.wind;
    }


    public void setWind(double direction, double strength) {
        this.wind.setOrientation(direction);
        this.wind.setStrength(strength);
    }

    public SeaEntity[] getSeaEntities() {
        if (seaEntities != null)
            if (seaEntities.length > 0)
                Arrays.stream(seaEntities).forEach(s -> s.getPosition().setOrientation(0));
        return seaEntities;
    }

    public void setSeaEntities(SeaEntity[] seaEntities) {
        this.seaEntities = seaEntities;
        Arrays.stream(seaEntities).forEach(s -> System.out.println());
    }

    public Stream[] generateUniformStreams(){
        ArrayList<Stream> streams = new ArrayList<Stream>();
        for(double i=0.5; i<NB_SEA_ENTITIES; i += OFFSET_BETWEEN_ENTITIES){
            for(double j=0.5; j<NB_SEA_ENTITIES; j += OFFSET_BETWEEN_ENTITIES){
                Stream s = new Stream();
                Circle c = new Circle();
                Position p = new Position();

                c.setRadius(1);
                p.setX(i); p.setY(j);
                s.setShape(c);
                s.setStrength(Math.random() * (100 - 10));
                s.setPosition(p);
                streams.add(s);
            }
        }
        Stream[] streamsArray = new Stream[streams.size()];
        int index = 0;
        for(Stream s : streams){
            streamsArray[index] = s;
            index++;
        }
        return streamsArray;
    }
}

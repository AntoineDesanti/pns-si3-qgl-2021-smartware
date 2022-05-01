package fr.unice.polytech.si3.qgl.smartware.smartship;

import fr.unice.polytech.si3.qgl.smartware.JsonHandler;
import fr.unice.polytech.si3.qgl.smartware.maths.Path;
import fr.unice.polytech.si3.qgl.smartware.ship.entities.Oar;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.maths.Case;

import java.util.ArrayList;

public class SailorOarPath {
    private Sailor sailor;
    private Oar oar;
    private Path path;

    public SailorOarPath(Sailor sailor, Oar oar, Path path) {
        this.sailor = sailor;
        this.oar = oar;
        this.path = path;
    }

    public Sailor getSailor() {
        return sailor;
    }

    public void setSailor(Sailor sailor) {
        this.sailor = sailor;
    }

    public Oar getOar() {
        return oar;
    }

    public void setOar(Oar oar) {
        this.oar = oar;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    @Override
    public String toString(){ return sailor.getId() + " | ("+oar.getX()+", "+oar.getY()+") | "+path.size(); }
}

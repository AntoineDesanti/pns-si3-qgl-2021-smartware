package fr.unice.polytech.si3.qgl.smartware.maths;

import java.util.ArrayList;
import java.util.List;

public class Path extends ArrayList<Case> {

    public Case getLast()
    {
        return get(size()-1);
    }

    public int getLength()
    {
        return size();
    }

    public int deltaX() { return size()>0? getLast().getX()-get(0).getX() : 0; }
    public int deltaY() { return size()>0? getLast().getY()-get(0).getY() : 0; }
}

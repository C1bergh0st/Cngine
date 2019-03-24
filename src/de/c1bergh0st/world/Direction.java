package de.c1bergh0st.world;

import de.c1bergh0st.geometry.Vector;

public enum Direction {
    LEFT(new Vector(-1, 0)),
    RIGHT(new Vector(1, 0)),
    UP(new Vector(0, -1)),
    DOWN(new Vector(0, 1));
    
    private Vector vector;
    
    Direction(Vector vec){
        this.vector = vec;
    }
    
    public Vector getVector(){
        return vector;
    }
    
    
    
}

package de.c1bergh0st.geometry;

import java.awt.geom.Path2D;
import java.util.LinkedList;
import java.util.List;

public class Polygon extends Path2D.Double{
    private static final long serialVersionUID = 1432243L;
    private List<Vector> vertecies; 
    
    public Polygon(){
        super();
        vertecies = new LinkedList<Vector>();
    }
    
    public void add(Vector v){
        if(vertecies.isEmpty()){
            moveTo(v.x, v.y);
        } else {
            lineTo(v.x, v.y);
        }
        vertecies.add(v);
        
    }

    public void close(){
        closePath();
    }
    
}

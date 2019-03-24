package de.c1bergh0st.geometry;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import de.c1bergh0st.debug.Util;

public class Vector extends Point2D.Double{
    private static final long serialVersionUID = 1321L;

    public Vector(double x, double y){
        super(x, y);
    }

    public Vector add(Point2D.Double vector) {
        return new Vector(x + vector.x, y + vector.y);
    }

    public Vector substract(Vector vector) {
        return new Vector(x - vector.x, y - vector.y);
    }
    
    public Vector turnLeft(){
        //noinspection SuspiciousNameCombination
        return new Vector(-y, x);
    }
    

    public Vector turnRight(){
        //noinspection SuspiciousNameCombination
        return new Vector(y, -x);
    }
    
    public Vector multiply(double skalar){
        return new Vector(x * skalar, y * skalar);
    }
    
    public double abs(){
        return Math.sqrt(x * x + y * y);
    }
    
    public Vector getUnitVector(){
        double length = abs();
        return new Vector(x / length, y / length);
    }
    
    public double distance(Vector vector){
        Vector dist = this.substract(vector);
        return dist.abs();
    }
    
    /**
     * Generates 4 points around this vector, like the face of a 5-face on a dice
     * @param expansion the half side of the square
     * @return the List
     */
    public List<Vector> getSqaure(double expansion){
        List<Vector> result = new LinkedList<>();
        result.add(new Vector(x - expansion, y - expansion));
        result.add(new Vector(x + expansion, y - expansion));
        result.add(new Vector(x - expansion, y + expansion));
        result.add(new Vector(x + expansion, y + expansion));
        return result;
    }
    
    public void draw(Graphics2D g, double radius){
        g.fillOval(Util.toPix(x - radius / 2), Util.toPix(y - radius / 2), Util.toPix(radius), Util.toPix(radius));
    }
    
    public String toString(){
        return Util.posconv(x, y);
    }
}

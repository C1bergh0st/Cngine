package de.c1bergh0st.world.objects.human;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Layer;

public class Kone implements Drawable{
    private Human source;
    
    public Kone(Human source){
        if(source == null){
            throw new NullPointerException();
        }
        this.source = source;
    }
    
    public Path2D getCone(){
        double width = 0.15;
        double legth = 1;
        Path2D result = new Path2D.Double();
        Vector start = source.getCenter();
        Vector dir = source.getDir();
        Vector extrusionpoint = start.add(dir.multiply(legth));
        Vector left = extrusionpoint.add(dir.turnLeft().multiply(width));
        Vector right = extrusionpoint.add(dir.turnRight().multiply(width));
        result.moveTo(start.x, start.y);
        result.lineTo(right.x, right.y);
        result.lineTo(left.x, left.y);
        result.closePath();
        return result;
    }
    
    public boolean intersects(Rectangle2D.Double rect){
        return getCone().intersects(rect);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(0, 0, 255, 150));
        Path2D s = getCone();
        s.transform(DrawUtil.getTransform());
        g.fill(s);
    }
    
    public String toString(){
        return "Kone of " + source.toString();
    }

    @Override
    public Layer getLayer() {
        return Layer.DEV;
    }
}

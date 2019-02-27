package de.c1bergh0st.world.objects;

import java.awt.Color;
import java.awt.Graphics2D;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Layer;

public class DevPoint implements Drawable{
    private Vector pos;
    private double radius;
    
    
    public DevPoint(Vector pos, double radius){
        if(pos == null){
            throw new NullPointerException();
        }
        this.pos = pos;
        this.radius = radius;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillOval(Util.toPix(pos.x - radius / 2), Util.toPix(pos.y - radius / 2), Util.toPix(radius), Util.toPix(radius));
    }
    
    public void setPosition(Vector v){
        if(v != null){
            pos = v;
        }
    }
    
    public Vector getPos(){
        return pos;
    }

    @Override
    public Layer getLayer() {
        return Layer.DEV;
    }
    
}

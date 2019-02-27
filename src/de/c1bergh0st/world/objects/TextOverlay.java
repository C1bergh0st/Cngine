package de.c1bergh0st.world.objects;

import java.awt.Color;
import java.awt.Graphics2D;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Layer;

public class TextOverlay implements Drawable{
    private String s;
    private Vector pos;
    
    public TextOverlay(Vector pos, String msg){
        s = msg;
        this.pos = pos.add(new Vector(0, 1));
    }
    
    public TextOverlay(double x, double y, String msg){
        this(new Vector(x, y), msg);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString(s, Util.toPix(pos.x), Util.toPix(pos.y));
    }

    @Override
    public Layer getLayer() {
        return Layer.FRONT;
    }
}

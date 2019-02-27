package de.c1bergh0st.world.objects;

import java.awt.Graphics2D;

import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Tickable;

public abstract class Active implements Tickable, Drawable{
    protected Vector pos;
    
    public abstract void draw(Graphics2D g);

    public abstract void tick();

    public abstract void secondTick();
    
    public Vector getPosition(){
        return pos;
    }

}

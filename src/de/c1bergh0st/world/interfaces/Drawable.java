package de.c1bergh0st.world.interfaces;

import java.awt.Graphics2D;

public interface Drawable {
    
    public void draw(Graphics2D g);
    
    public Layer getLayer();
}

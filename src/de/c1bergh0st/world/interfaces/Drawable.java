package de.c1bergh0st.world.interfaces;

import java.awt.Graphics2D;

public interface Drawable {
    
    void draw(Graphics2D g);
    
    Layer getLayer();
}

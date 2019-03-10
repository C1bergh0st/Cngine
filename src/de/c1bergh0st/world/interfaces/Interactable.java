package de.c1bergh0st.world.interfaces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.world.objects.human.Human;

public interface Interactable {
    
    public void interact(Human human);
    
    public void setEnabled(boolean enabled);
    
    public boolean isEnabled();
    
    default void drawInteractionField(Graphics2D g){
        Util.drawRect(getInteractionField(), g, new Color(0,0,255,150));
    }
    
    public Rectangle2D.Double getInteractionField();
}

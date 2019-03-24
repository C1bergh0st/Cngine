package de.c1bergh0st.world.interfaces;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.world.objects.human.Human;

public interface Interactable {
    
    void interact(Human human);
    
    void setEnabled(boolean enabled);
    
    boolean isEnabled();
    
    default void drawInteractionField(Graphics2D g){
        g.setColor(new Color(0,0,255,150));
        DrawUtil.drawRect(getInteractionField(), g);
    }
    
    Rectangle2D.Double getInteractionField();
}

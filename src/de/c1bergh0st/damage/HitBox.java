package de.c1bergh0st.damage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.c1bergh0st.debug.DrawUtil;

public interface HitBox {
    
    Rectangle2D.Double getHitBox();
    
    void hit(Team team, int damage);
    
    default void drawHitBox(Graphics2D g){
        g.setColor(new Color(255, 0, 0,130));
        DrawUtil.fillRect(getHitBox(), g);
    }
    
    int getContactDamage();
    
    Team getTeam();
    
    boolean isDead();
    
    boolean shouldRemove();
}

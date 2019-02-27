package de.c1bergh0st.damage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import de.c1bergh0st.debug.Util;

public interface HitBox {
    
    public Rectangle2D.Double getHitBox();
    
    public void hit(Team team, int damage);
    
    public default void drawHitBox(Graphics2D g){
        Util.fillRect(getHitBox(), g, new Color(255, 0, 0,130));
    }
    
    public int getContactDamage();
    
    public Team getTeam();
    
    public boolean isDead();
    
    public boolean shouldRemove();
}

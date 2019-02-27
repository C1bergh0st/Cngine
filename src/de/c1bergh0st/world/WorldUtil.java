package de.c1bergh0st.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.geometry.Vector;

public class WorldUtil {

    public static void drawDevSpquares(Graphics2D gg) {
        int gridsize = 50;
        gg.setColor(Color.BLACK);
        for(int x = 0; x < gridsize; x++){
            for(int y = 0; y < gridsize; y++){
                Util.drawSqr(new Vector(x, y), gg);
            }
        }
    }
    
    public static void drawEdge(Graphics2D gg){
        int size = 5;
        Util.drawRect(new Rectangle2D.Double(0, -size, World.MAX, size), gg, Color.RED);
        Util.drawRect(new Rectangle2D.Double(-size, -size, size, size * 2 + World.MAX), gg, Color.RED);
        Util.drawRect(new Rectangle2D.Double(World.MAX, -size, size, World.MAX + size * 2), gg, Color.RED);
        Util.drawRect(new Rectangle2D.Double(0, World.MAX, World.MAX, size), gg, Color.RED);
    }
    
}

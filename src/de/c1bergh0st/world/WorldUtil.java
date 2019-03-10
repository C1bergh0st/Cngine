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

    
}

package de.c1bergh0st.world.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.game.image.Statics;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Layer;

public class Floor implements Drawable{
    private Vector position;
    private BufferedImage img;
    
    public Floor(int x, int y, String path){
        img = Statics.getImageProvider().getImage(path);
        position = new Vector(x, y);
    }
    
    
    @Override
    public void draw(Graphics2D g) {
        Util.drawImage(img, position.x, position.y, 1, 1, g);
    }


    @Override
    public Layer getLayer() {
        return Layer.FLOOR;
    }

}

package de.c1bergh0st.world.objects;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;

import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.game.image.Statics;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Layer;
import de.c1bergh0st.world.interfaces.Solid;

public class Wall implements Drawable, Solid{
    private Point2D.Double position;
    private BufferedImage img;
    
    
    public Wall(Point2D.Double pos, String imagePath){
        position = pos;
        img = Statics.getImageProvider().getImage(imagePath);
    }
    
    public Wall(int x, int y, String image){
        this(new Point2D.Double(x, y), image);
    }
    
    public Point2D.Double getPosition(){
        return position;
    }

    @Override
    public Double getBounds() {
        return new Rectangle2D.Double(position.getX(), position.getY(), 1, 1);
    }

    @Override
    public void draw(Graphics2D g) {
        DrawUtil.drawImage(img, position, new Vector(1,1), g);
    }

    @Override
    public Layer getLayer() {
        return Layer.MIDDLE;
    }
    
}

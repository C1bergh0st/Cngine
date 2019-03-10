package de.c1bergh0st.debug;

import de.c1bergh0st.geometry.Vector;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class DrawUtil {
    private static AffineTransform transform;
    //OFFSET FUNCTIONS

    public static AffineTransform getTransform(){
        if(transform == null){
            transform = new AffineTransform();
            transform.scale(Util.toPix(1), Util.toPix(1));
        }
        return transform;
    }

    public static Rectangle2D.Double getViewPort(){
        Point2D.Double offset = Util.offset;
        Vector dim = new Vector(Util.toUnits(1920), Util.toUnits(1080));
        return new Rectangle2D.Double(offset.x, offset.y, dim.x, dim.y);
    }

    //COLOR FUNCTIONS

    public static Color makeTransparent(int r, int g, int b){
        return makeTransparent(new Color(r, g, b));
    }

    public static Color makeTransparent(Color c){
        return new Color(c.getRed(), c.getGreen(), c.getGreen(), 60);
    }



    //DRAWING FUNCTIONS

    public static void drawImage(BufferedImage img, Vector pos, Vector dim, Graphics2D g){
        g.drawImage(img, Util.toPix(pos.x), Util.toPix(pos.y), Util.toPix(dim.x), Util.toPix(dim.y), null);
    }

    public static void drawRect(Rectangle2D.Double rect, Graphics2D g){
        drawRect(new Vector(rect.x, rect.y), new Vector(rect.width, rect.height), g);
    }

    public static void drawRect(Point2D.Double pos, Point2D.Double dim, Graphics2D g){
        if(!getViewPort().intersects(new Rectangle2D.Double(pos.x, pos.y, dim.x, dim.y))){
            return;
        }
        g.drawRect(Util.toPix(pos.x), Util.toPix(pos.y), Util.toPix(dim.x), Util.toPix(dim.y));
    }

    public static void fillRect(Rectangle2D.Double rect, Graphics2D g){
        fillRect(new Vector(rect.x, rect.y), new Vector(rect.width, rect.height), g);
    }

    public static void fillRect(Point2D.Double pos, Point2D.Double dim, Graphics2D g){
        if(!getViewPort().intersects(new Rectangle2D.Double(pos.x, pos.y, dim.x, dim.y))){
            return;
        }
        g.fillRect(Util.toPix(pos.x), Util.toPix(pos.y), Util.toPix(dim.x), Util.toPix(dim.y));
    }

    public static void drawSqr(Point2D.Double pos, Graphics2D g){
        if(pos == null){
            return;
        }
        drawRect(pos, new Vector(1,1), g);
    }
}

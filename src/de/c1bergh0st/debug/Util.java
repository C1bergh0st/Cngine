package de.c1bergh0st.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;

import de.c1bergh0st.geometry.Vector;

public class Util {
	public static Color REDTRANSPARENT = new Color(255,0,0,60);
	public static Color GREENTRANSPARENT = new Color(0,255,0,60);
	public static Color BLUETRANSPARENT = new Color(0,0,255,60);
	public static int TILESIZE = 96;
	public static Point2D.Double offset = new Point2D.Double(0, 0);
	
	public static int toPix(double units){
		return (int)(units * TILESIZE);
	}
	
	public static double toUnits(int pixels){
		return (double)(pixels)/(TILESIZE * 1d);
	}

	@Deprecated
    public static void drawSqr(Point2D.Double pos, Graphics g){
        DrawUtil.drawSqr(pos, (Graphics2D) g);
    }
    
    public static void drawVectorDirection(Point2D.Double pos, Vector vector, Graphics g){
        Vector start = new Vector(pos.x, pos.y);
        Vector end = start.add(vector);
        g.setColor(Color.BLACK);
        g.drawLine(toPix(start.x), toPix(start.y), toPix(end.x), toPix(end.y));
    }

	@Deprecated
	public static void drawRect(Rectangle2D.Double rect, Graphics g, Color c){
	    g.setColor(c);
        DrawUtil.drawRect(rect, (Graphics2D) g);
	}

	public static String posconv(double x, double y){
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return "("+df.format(x)+";"+df.format(y)+")";
	}
	
	public static int randInt(int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	@Deprecated
	public static AffineTransform getScaleTransForm(){
	    return DrawUtil.getTransform();
	}

	@Deprecated
	public static void drawImage(BufferedImage img, double x, double y, double width, double height, Graphics g){
		DrawUtil.drawImage(img, new Vector(x, y), new Vector(width, height), (Graphics2D) g);
 	}


	@Deprecated
	/*
	 * @Deprecated use {@link DrawUtil#fillRect(Double, Graphics2D)}
	 */
    public static void fillRect(Rectangle2D.Double rect, Graphics2D g, Color color) {
        g.setColor(color);
        DrawUtil.fillRect(rect, g);
    }
}

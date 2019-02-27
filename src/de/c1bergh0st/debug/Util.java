package de.c1bergh0st.debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
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
	private static AffineTransform scaling;
	
	public static int toPix(double units){
		return (int)(units * TILESIZE);
	}
	
	public static double toUnits(int pixels){
		return (double)(pixels)/(TILESIZE * 1d);
	}
	
    public static void drawSqr(Point2D.Double pos, Graphics g){
        if(pos!= null){
            g.drawRect(toPix(pos.x), toPix(pos.y), toPix(1), toPix(1));
        }
    }
    
    public static void drawVectorDirection(Point2D.Double pos, Vector vector, Graphics g){
        Vector start = new Vector(pos.x, pos.y);
        Vector end = start.add(vector);
        g.setColor(Color.BLACK);
        g.drawLine(toPix(start.x), toPix(start.y), toPix(end.x), toPix(end.y));
    }
	
	public static void drawRect(Rectangle2D.Double rect, Graphics g, Color c){
	    g.setColor(c);
        if(rect!= null){
            g.drawRect(toPix(rect.x), toPix(rect.y), toPix(rect.width), toPix(rect.height));
        }
	}
	
	public static String posconv(double x, double y){
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return "("+df.format(x)+";"+df.format(y)+")";
	}
	
	public static int randInt(int min, int max){
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}
	
	public static AffineTransform getScaleTransForm(){
	    if(scaling == null){
	        scaling = new AffineTransform();
	        scaling.scale(toPix(1), toPix(1));
	    }
	    return scaling;
	}
	
	public static void drawImage(BufferedImage img, double x, double y, double width, double height, Graphics g){
        g.drawImage(img, Util.toPix(x), Util.toPix(y), Util.toPix(width), Util.toPix(height),  null);
	}
	
	public static void draw2DArray(Object[][] array){
	    for(int i = 0; i < array.length; i++){
	        for(int j = 0; j < array[i].length; j++){
	            if(array[i][j] != null){
	                System.out.print("+");
	            } else {
	                System.out.print("-");
	            }
	        }
	        System.out.println();
	    }
	}
	
    public static void fillRect(Rectangle2D rect, Graphics2D g, Color color) {
        g.setColor(color);
        if(rect!= null){
            g.fillRect(toPix(rect.getX()), toPix(rect.getY()), toPix(rect.getWidth()), toPix(rect.getHeight()));
        }
    }

    public static void fillRect(Rectangle2D.Double rect, Graphics2D g, Color color) {
        g.setColor(color);
        if(rect!= null){
            g.fillRect(toPix(rect.x), toPix(rect.y), toPix(rect.width), toPix(rect.height));
        }
    }
}

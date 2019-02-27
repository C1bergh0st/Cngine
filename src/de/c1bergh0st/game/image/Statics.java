package de.c1bergh0st.game.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Statics {
    private static ImageProvider singleton;
    public static boolean devDraw = true;
    
    private Statics(){
        throw new AssertionError("This Constructor should never be called");
    }
    
    public static ImageProvider getImageProvider(){
        if(singleton == null){
            try {
                singleton = new ImageProvider("src/res");
            } catch (NoSuchFolderException e) {
                throw new Error("ImageProviderSingleTon could not load the root Folder");
            }
        }
        return singleton;
    }
    

    /**
     * Generates a BufferedImage based on a given width
     * Can Always be relied on the return a drawable Image
     * @param width the width of the Image
     * @param height the heigth of the Image
     * @return the generated Image
     */
    public static BufferedImage getErrorImage(int width, int height){
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = temp.getGraphics();
        g.setColor(Color.PINK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        g.drawRect(0, 0, width, height);
        g.setColor(Color.BLACK);
        for(int i = 0; i < (width / 100); i++){
            for(int j = 0; j < (height / 100) + 1; j++){
                g.drawString("ERROR", i * 100, j * 100 + 50);
                g.drawString("Image not Found", i * 100, j * 100 + 100);
            }
        }
        return temp;
    }

}

package de.c1bergh0st.game.image;

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
        return ImageProvider.getErrorImage(width, height);
    }

}

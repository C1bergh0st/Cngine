package de.c1bergh0st.game.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;

public class ImageProvider {
    private File rootFolder;
    private Map<String, BufferedImage> textures;
    private BufferedImage fallBack;
    
    //package private !
    ImageProvider(String rootpath) throws NoSuchFolderException{
        rootFolder = new File(rootpath);
        if(!rootFolder.exists() || !rootFolder.isDirectory()){
            throw new NoSuchFolderException("The folder" + rootpath + " does not exist");
        }
        textures = new HashMap<String, BufferedImage>();
        
        loadRoot(rootFolder);
        
        lodFallBack();
        
        Debug.send("ImageLoader: Image Buffering Complete from: " + rootpath);
    }
    
    private void lodFallBack() {
        fallBack = getErrorImage(Util.TILESIZE, Util.TILESIZE);
    }

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


    private void loadRoot(File file){
        File[] filesInFolder = file.listFiles();
        for(File recursive : filesInFolder){
            load(recursive, "");
        }
    }
    
    private void load(File file, String prefix){
        //If the File is a directory load its contents recursively
        //NOTE: might cause a StackOverFlow for folder Systems that are too big
        //      but in this case the Program might as well crash!
        if(file.isDirectory()){
            File[] filesInFolder = file.listFiles();
            for(File recursive : filesInFolder){
                load(recursive, prefix + file.getName() + "/");
            }
        }
        if(file.getName().matches(".*\\.png")){
            try {
                textures.put(prefix + file.getName(), ImageIO.read(file));
                System.out.println(prefix + file.getName());
            } catch (IOException e) {
                Debug.sendErr("Error while trying to load " + file.getPath());
            }
        }
    }

    public boolean hasImage(String name){
        return textures.containsKey(name);
    }

    /**
     * Returns the previously loaded image from the given path (relative path starts in root folder)
     * @param name the path to the Image
     * @return the image
     */
    public BufferedImage getImage(String name){
        if(!hasImage(name)){
            return fallBack;
        }
        return textures.get(name);
    }
}

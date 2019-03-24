package de.c1bergh0st.world.objects;

import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.game.image.ImageProvider;
import de.c1bergh0st.game.image.Statics;
import de.c1bergh0st.geometry.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Table extends HalfHeight{
    private BufferedImage img;

    public Table(double x, double y){
        this(new Vector(x, y), new Vector(1,1));
    }

    public Table(Vector pos, Vector dim){
        super(pos, dim);
        img = Statics.getImageProvider().getImage("desk.png");
    }

    @Override
    public void draw(Graphics2D g) {
        DrawUtil.drawImage(img, pos, dim, g);
    }

    @Override
    public void tick() {

    }

    @Override
    public void secondTick() {

    }
}

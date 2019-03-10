package de.c1bergh0st.world.objects;

import de.c1bergh0st.damage.HitBox;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Collisions;

import java.awt.geom.Rectangle2D;
import java.util.List;

public abstract class PhysicalActive extends Active implements Collisions, HitBox {
    protected Vector dim;
    protected int health;
    protected Rectangle2D.Double downBox, topBox, leftBox, rightBox;


    @Override
    public Rectangle2D.Double getHitBox() {
        return new Rectangle2D.Double(pos.x, pos.y, dim.x, dim.y);
    }


    @Override
    public boolean isDead() {
        return health <= 0;
    }


    @Override
    public void collision(List<Rectangle2D.Double> list) {
        for(Rectangle2D.Double rect : list){
            collision(rect);
        }
    }

    protected void collision(Rectangle2D.Double box){
        if(downBox.intersects(box)){
            pos.y = box.y - dim.y;
            updateBox();
        }
        if(topBox.intersects(box)){
            pos.y = box.y + box.getHeight();
            updateBox();
        }
        if(leftBox.intersects(box)){
            pos.x = box.x + box.getWidth();
            updateBox();
        }
        if(rightBox.intersects(box)){
            pos.x = box.x - dim.x;
            updateBox();
        }
    }

    /**
     * In most instances returning the current velocity suffices
     * @return a tolerance value to be deducted in collision operation
     */
    protected abstract double getTolerance();

    public void updateBox(){
        double speed = getTolerance();
        double dh = 0.3f;
        double dw = dim.x - 0.1 - speed * 2;
        if(dw >0.9){
            dw = 0.9;
        }
        double vh = (dim.y * 0.9) - speed * 2;
        //                                  |           X          |            Y          |    WIDTH  |HEIGHT
        downBox     = new Rectangle2D.Double(pos.x + (dim.x -dw)/2, pos.y+dim.y-dh,         dw,         dh);
        topBox      = new Rectangle2D.Double(pos.x + (dim.x-dw)/2,  pos.y,                  dw,         dh);
        rightBox    = new Rectangle2D.Double(pos.x + 0.8*dim.x,     pos.y+((dim.y-vh)/2),   0.2*dim.x,  vh);
        leftBox     = new Rectangle2D.Double(pos.x,                 pos.y+((dim.y-vh)/2),   0.2,        vh);
    }

}

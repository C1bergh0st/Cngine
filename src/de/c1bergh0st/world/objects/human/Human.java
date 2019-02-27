package de.c1bergh0st.world.objects.human;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import de.c1bergh0st.damage.HitBox;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.Direction;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.interfaces.Collisions;
import de.c1bergh0st.world.interfaces.Layer;
import de.c1bergh0st.world.objects.Active;
import de.c1bergh0st.world.objects.human.weapons.Weapon;

public abstract class Human extends Active implements Collisions, HitBox{
    protected World world;
    protected Vector dim;
    protected Vector lastPos;
    protected Vector direction;
    protected Rectangle2D.Double downBox, topBox, leftBox, rightBox;
    protected int health;
    protected Kone kone;
    protected Weapon weapon;
    
    public Human(Vector pos, World world){
        lastPos = pos;
        this.pos = pos;
        this.world = world;
        direction = new Vector(-1, 0);
        dim = new Vector(0.65, 0.65);
        health = 100;
        kone = new Kone(this);
    }
    
    public abstract void move(Direction dir, double sqrsPerSecond);
    
    public abstract void performAction(Action action);
    
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillRect(Util.toPix(pos.x), Util.toPix(pos.y), Util.toPix(dim.x), Util.toPix(dim.y));
        if(world.devDraw){
            drawphysbox(g);
            Util.drawVectorDirection(pos.add(dim.multiply(0.5d)), direction.multiply(1.3), g);
            kone.draw(g);
        }
        if(weapon != null){
            weapon.draw(g);
        }
    }

    public void tick() {
        updateBox();
        lastPos = pos;
    }
    

    public void setWeapon(Weapon w){
        if(w == null){
            weapon = w;
        } else {
            weapon = w;
            w.setCarrier(this);
        }
    }
    
    public World getWorld(){
        return world;
    }
    
    public Vector getDir(){
        return direction;
    }
    
    public Vector getDim(){
        return dim;
    }
    
    public Vector getCenter(){
        return pos.add(dim.multiply(0.5d));
    }
    
    public void secondTick() {
        //EMPTY
    }
    
    public void setDirection(Vector direction){
        this.direction = direction.getUnitVector();
    }
    
    public int getHealth(){
        return health;
    }
    
    public Layer getLayer(){
        return Layer.MIDDLE;
    }
    
    //-----------HitBoxes

    @Override
    public Rectangle2D.Double getHitBox() {
        return new Rectangle2D.Double(pos.x, pos.y, dim.x, dim.y);
    }
    
    
    
    
    //--------------------------------- PHYSICS
    
    public void collision(List<Rectangle2D.Double> list){
        for(Rectangle2D.Double rect : list){
            collision(rect);
        }
    }

    public void collision(Rectangle2D.Double box) {
        //System.out.println("A " + box);
        //System.out.println("B " + downBox);
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
    
    private void updateBox() {
        double speed = pos.distance(lastPos);
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
    
    
    protected void drawphysbox(Graphics g) {
        g.setColor(Color.RED);
        if(downBox != null){
            g.drawRect(Util.toPix(downBox.x),Util.toPix(downBox.y),Util.toPix(downBox.width),Util.toPix(downBox.height));
        }
        if(leftBox != null){
            g.drawRect(Util.toPix(leftBox.x),Util.toPix(leftBox.y),Util.toPix(leftBox.width),Util.toPix(leftBox.height));
        }
        if(rightBox != null){
            g.drawRect(Util.toPix(rightBox.x),Util.toPix(rightBox.y),Util.toPix(rightBox.width),Util.toPix(rightBox.height));
        }
        if(topBox != null){
            g.drawRect(Util.toPix(topBox.x),Util.toPix(topBox.y),Util.toPix(topBox.width),Util.toPix(topBox.height));
        }
        g.setColor(Color.BLACK);
    }
}

package de.c1bergh0st.world.objects.human;

import de.c1bergh0st.damage.HitBox;
import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.Direction;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.interfaces.Collisions;
import de.c1bergh0st.world.interfaces.Layer;
import de.c1bergh0st.world.objects.PhysicalActive;
import de.c1bergh0st.world.objects.human.inventory.Inventory;
import de.c1bergh0st.world.objects.human.inventory.SlotInventory;
import de.c1bergh0st.world.objects.human.weapons.Weapon;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Human extends PhysicalActive implements Collisions, HitBox{
    protected World world;
    protected Vector lastPos;
    protected Vector direction;
    protected Kone kone;
    protected Weapon currWeapon;
    protected Inventory<Weapon> weapons = new SlotInventory<Weapon>(10);
    
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
        if(World.devDraw){
            drawphysbox(g);
            Util.drawVectorDirection(pos.add(dim.multiply(0.5d)), direction.multiply(1.3), g);
            kone.draw(g);
        }
        if(currWeapon != null){
            currWeapon.draw(g);
        }
    }

    public void tick() {
        updateBox();
        lastPos = pos;
    }

    public void equipWeaponSlot(int i){
        currWeapon = weapons.get(i);
        Debug.send("Equiping " + currWeapon.getClass().getSimpleName() + " in slot " + i);
    }

    public int equipWeapon(Weapon w){
        if(weapons.canEquip(w)){
            w.setCarrier(this);
            return weapons.equip(w);
        }
        return -1;
    }

    public void setWeapon(Weapon w, int slot){
        if(weapons.isEmpty(slot)){
            weapons.equip(w, slot);
        } else {
            weapons.clearSlot(slot);
            weapons.equip(w, slot);
        }
        w.setCarrier(this);
        currWeapon = w;
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


    protected double getTolerance(){
        return pos.distance(lastPos);
    }

    
    
    protected void drawphysbox(Graphics g) {
        g.setColor(Color.RED);
        drawRect(g,topBox);
        drawRect(g,downBox);
        drawRect(g,leftBox);
        drawRect(g,rightBox);
        g.setColor(Color.BLACK);
    }

    //TODO: Move to Util
    private void drawRect(Graphics g, Rectangle2D.Double rect){
        if(rect != null){
            g.drawRect(Util.toPix(rect.x),Util.toPix(rect.y),Util.toPix(rect.width),Util.toPix(rect.height));
        }
    }
}

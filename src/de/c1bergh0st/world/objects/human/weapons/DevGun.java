package de.c1bergh0st.world.objects.human.weapons;

import java.awt.Color;
import java.awt.Graphics2D;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.geometry.Edge;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Layer;
import de.c1bergh0st.world.objects.human.Human;

public class DevGun extends Weapon{
    private static final int MAGSIZE = 30;
    private static final int RPM = 750;
    private static final double RPS = RPM / 60d;
    private static final int DELAYMS = (int) (1000 / RPS);
    private double width;
    private double length;
    private int ammo;
    private Human carrier;
    private long lastShot;
    
    public DevGun(Human carrier){
        if(carrier == null){
            throw new NullPointerException();
        }
        this.carrier = carrier;
        this.length = 0.4;
        this.width  = 0.1;
    }
    
    @Override
    public void tick() {
        //EMPTY
    }

    @Override
    public void secondTick() {
        //EMPTY
    }
    
    @Override
    public void draw(Graphics2D g) {
        drawGun(g);
        drawInfo(g);
    }

    private void drawInfo(Graphics2D g) {
        Vector p = carrier.getCenter().add(new Vector(0.5,0.5));
        double height = 0.15;
        g.setColor(Color.GRAY);
        g.fillRect(Util.toPix(p.x - height), Util.toPix(p.y - 0.1), Util.toPix(1), Util.toPix(height));
        g.setColor(Color.BLACK);
        g.drawString("Ammo : " + getAmmo(), Util.toPix(p.x), Util.toPix(p.y));
        
    }
    
    private void drawGun(Graphics2D g){
        Vector start = carrier.getCenter().add(carrier.getDir().multiply(carrier.getDim().x / 2.1));
        Vector end = start.add(carrier.getDir().multiply(length));
        Edge shape = new Edge(start, end, width);
        g.setColor(Color.BLACK);
        shape.transform(Util.getScaleTransForm());
        g.fill(shape);
        
    }

    @Override
    public Layer getLayer() {
        return Layer.MIDDLE;
    }

    @Override
    public void fire() {
        if(lastShot + DELAYMS > System.currentTimeMillis()){
            return;
        }
        lastShot = System.currentTimeMillis();
        double speed = 20;
        if(ammo > 0){
            Vector start1 = carrier.getCenter().add(carrier.getDir().multiply(carrier.getDim().x / 2.1));
            Vector start = start1.add(carrier.getDir().multiply(length / 3));
            carrier.getWorld().add(new DevBullet(start, 0.1, carrier.getDir().multiply(speed), 25));
            ammo--;
        }
    }

    @Override
    public void reload() {
        ammo = MAGSIZE;
    }

    @Override
    public int getAmmo() {
        return ammo;
    }

    @Override
    public int getMagazineSize() {
        return MAGSIZE;
    }

    @Override
    public double getAmmoPercentage() {
        return ((double) ammo) / MAGSIZE;
    }
    

}

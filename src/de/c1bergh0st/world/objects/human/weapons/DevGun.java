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
    private int reloadtime = 1300;
    private int MAGSIZE;
    private int RPM;
    private double RPS;
    private int DELAYMS;
    private int dmg;
    private double bulletSpeed;
    private double width;
    private double length;
    private int ammo;
    private Human carrier;
    private long lastShot;
    private long reloadstart;
    private boolean reloading;

    public DevGun(Human carrier, int rpm, int magsize, int dmg, double length, double bulletSpeed){
        this.bulletSpeed = bulletSpeed;
        this.dmg = dmg;
        this.carrier = carrier;
        this.length = length;
        this.width  = 0.1;
        MAGSIZE = magsize;
        RPM = rpm;
        RPS = RPM / 60d;
        DELAYMS = (int) (1000 / RPS);
    }
    
    @Override
    public void tick() {
        if(reloading){
            if(reloadstart + reloadtime < System.currentTimeMillis()) {
                reloading = false;
                ammo = MAGSIZE;
            }
        }
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
        if(carrier == null){
            Debug.sendErr("No Carrier set for " + toString());
            return;
        }
        Vector p = carrier.getCenter().add(new Vector(0.5,0.5));
        double height = 0.3;
        g.setColor(Color.BLACK);
        g.fillRect(Util.toPix(p.x - height), Util.toPix(p.y - 0.1), Util.toPix(1), Util.toPix(height));
        g.setColor(Color.WHITE);
        g.drawString("Ammo : " + getAmmo(), Util.toPix(p.x), Util.toPix(p.y));
        if(reloading){
            int max = 20;
            char loading = '|';
            String str = "";
            int showing = (int)(getReloadProgress() * max);
            for(int i = 0; i <= showing; i++){
                str = str + loading;
            }
            g.drawString(str, Util.toPix(p.x), Util.toPix(p.y + 0.15));
        }
        
    }
    
    private void drawGun(Graphics2D g){
        if(carrier == null){
            Debug.sendErr("No Carrier set for " + toString());
            return;
        }
        Vector start = carrier.getCenter().add(carrier.getDir().multiply(carrier.getDim().x / 2.1));
        Vector end = start.add(carrier.getDir().multiply(length));
        Edge shape = new Edge(start, end, width);
        g.setColor(Color.BLACK);
        shape.transform(Util.getScaleTransForm());
        g.fill(shape);
        
    }

    @Override
    public double getReloadProgress(){
        if(!reloading){
            return 1;
        }
        return (double)(System.currentTimeMillis() - reloadstart) / (double)(reloadtime);
    }

    @Override
    public Layer getLayer() {
        return Layer.MIDDLE;
    }

    @Override
    public void fire() {
        reloading = false;
        reloadstart = 0;
        if(carrier == null){
            Debug.sendErr("No Carrier set for " + toString());
            return;
        }
        if(lastShot + DELAYMS > System.currentTimeMillis()){
            return;
        }
        lastShot = System.currentTimeMillis();
        if(ammo > 0){
            Vector start1 = carrier.getCenter().add(carrier.getDir().multiply(carrier.getDim().x / 2.1));
            Vector start = start1.add(carrier.getDir().multiply(length));
            carrier.getWorld().add(new DevBullet(start, 0.1, carrier.getDir().multiply(bulletSpeed), dmg));
            ammo--;
        }
    }

    @Override
    public void reload() {
        if(reloadstart + reloadtime < System.currentTimeMillis()){
            reloadstart = System.currentTimeMillis();
            reloading = true;
        }
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

    @Override
    public boolean isDroppable() {
        return false;
    }

    @Override
    public String getSideviewImagePath() {
        return null;
    }


}

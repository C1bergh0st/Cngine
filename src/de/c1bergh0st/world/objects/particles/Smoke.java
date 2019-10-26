package de.c1bergh0st.world.objects.particles;

import de.c1bergh0st.damage.Team;
import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Layer;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Smoke extends Particle{
    private long falloffstart;

    public Smoke(Vector pos, long lifespan){
        super(pos, lifespan);
        falloffstart = lifespan / 2;
    }
    @Override
    public Rectangle2D.Double getHitBox() {
        return new Rectangle2D.Double(0,0,0,0);
    }

    @Override
    public void hit(Team team, int damage) {
        this.lifespan = 0;
    }

    @Override
    public int getContactDamage() {
        return 0;
    }

    @Override
    public Team getTeam() {
        return Team.ENVIRONMENT;
    }


    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(30,30,30, getAlpha()));
        double radius = 0.4;
        g.fillOval(Util.toPix(pos.x - radius), Util.toPix(pos.y - radius), Util.toPix( radius * 2), Util.toPix( radius * 2));
    }

    private int getAlpha(){
        if(System.currentTimeMillis() < spawntime + falloffstart){
            return 90;
        } else {
            double percent = 1 - (double)((System.currentTimeMillis() - spawntime) - falloffstart) / falloffstart;
            int alpha =  (int)(90 * percent);
            if(alpha > 0 && alpha < 255){
                return alpha;
            } else {
                return 1;
            }
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void secondTick() {

    }
}

package de.c1bergh0st.world.objects.human.weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.List;

import de.c1bergh0st.damage.Team;
import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.gamecode.MainGame;
import de.c1bergh0st.geometry.Edge;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Layer;

public class DevBullet extends Bullet{
    
    public DevBullet(Vector position, double size, Vector direction, int dmg){
        this.pos = position.substract(new Vector(size / 2, size / 2));
        this.size = size;
        this.dir = direction;
        this.damage = dmg;
        this.team = Team.GOOD;

    }
    
    
    @Override
    public void hit(Team team, int damage) {
        health -= 100;
    }

    @Override
    public void draw(Graphics2D g) {
        Vector start = pos.add(new Vector(size / 2, size / 2));
        Vector end = start.add(dir.multiply(-2d / MainGame.TICKSPEED));
        Edge edge = new Edge(start, end, size);
        edge.transform(DrawUtil.getTransform());
        g.setColor(Color.YELLOW);
        g.fill(edge);
    }

    @Override
    public Layer getLayer() {
        return Layer.MIDDLE;
    }

    @Override
    public void tick() {
        pos = pos.add(dir.multiply(1d / MainGame.TICKSPEED));
    }

    @Override
    public void secondTick() {
        //EMPTY
    }



    @Override
    public boolean shouldRemove() {
        return isDead();
    }

}

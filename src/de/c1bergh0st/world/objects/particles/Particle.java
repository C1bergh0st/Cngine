package de.c1bergh0st.world.objects.particles;

import de.c1bergh0st.damage.HitBox;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Collisions;
import de.c1bergh0st.world.interfaces.Layer;
import de.c1bergh0st.world.objects.Active;
import javafx.geometry.Pos;

import java.awt.geom.Rectangle2D;
import java.util.List;

public abstract class Particle extends Active implements HitBox {
    protected long lifespan;
    protected long spawntime;

    public Particle(Vector pos, long lifespan){
        this.pos = pos;
        spawntime = System.currentTimeMillis();
        this.lifespan = lifespan;
    }

    @Override
    public boolean isDead() {
        return spawntime + lifespan <= System.currentTimeMillis();
    }

    @Override
    public boolean shouldRemove() {
        return isDead();
    }


    @Override
    public Layer getLayer() {
        return Layer.FRONT;
    }

}

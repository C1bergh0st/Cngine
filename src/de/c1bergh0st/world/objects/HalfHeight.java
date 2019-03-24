package de.c1bergh0st.world.objects;

import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Layer;
import de.c1bergh0st.world.interfaces.Solid;

import java.awt.geom.Rectangle2D;

public abstract class HalfHeight extends Active implements Solid {
    protected Vector dim;

    public HalfHeight(Vector pos, Vector dim){
        this.pos = pos;
        this.dim = dim;
    }

    @Override
    public Layer getLayer() {
        return Layer.LOWER_MIDDLE;
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return new Rectangle2D.Double(pos.x, pos.y, dim.x, dim.y);
    }
}

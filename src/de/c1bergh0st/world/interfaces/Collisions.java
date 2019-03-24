package de.c1bergh0st.world.interfaces;

import java.awt.geom.Rectangle2D;
import java.util.List;

public interface Collisions {

    void collision(List<Rectangle2D.Double> list);
}

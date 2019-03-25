package de.c1bergh0st.world.objects;

import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Layer;

import java.awt.*;
import java.util.List;


public class DevPath implements Drawable {
    private List<Vector> path;

    public DevPath(List<Vector> path){
        this.path = path;
    }


    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.YELLOW);
        g.setStroke(new BasicStroke(3));
        for(int i = 1; i < path.size(); i++){
            DrawUtil.drawLine(path.get(i - 1), path.get(i), g);
        }
        g.setStroke(new BasicStroke(1));
    }

    @Override
    public Layer getLayer() {
        return Layer.DEV;
    }
}

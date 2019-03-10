package de.c1bergh0st.world.objects.human.npc;

import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Layer;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;


public class Node implements Drawable {
    private Vector pos;
    private Vector dim;
    private boolean traversable;
    private List<Edge> edgeList;

    public Node(Vector pos, Vector dim, boolean traversable){
        this.pos = pos;
        this.dim = dim;
        this.traversable = traversable;
    }

    public Node(Vector pos){
        this(pos, new Vector(1,1), true);
    }

    @Override
    public void draw(Graphics2D g) {
        //g.setColor(DrawUtil.makeTransparent(170, 0,255));
        g.setColor(Color.MAGENTA.darker());
        DrawUtil.fillRect(pos, dim, g);
        g.setColor(Color.BLACK);
        DrawUtil.drawRect(pos, dim, g);
    }

    @Override
    public Layer getLayer() {
        return Layer.FLOOR;
    }

    public boolean intersects(Rectangle2D.Double rect){
        return rect.intersects(new Rectangle2D.Double(pos.x, pos.y, dim.x, dim.y));
    }

    public Vector getPos(){
        return pos;
    }

}

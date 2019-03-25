package de.c1bergh0st.world.objects.human.npc;

import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Layer;

import java.awt.*;

public class Edge implements Drawable {
    private Node[] nodes;
    private double dist;

    public Edge(Node first, Node second){
        nodes = new Node[2];
        nodes[0] = first;
        nodes[1] = second;
        dist = first.getCenter().distance(second.getCenter());
    }

    public Node getNext(Node current){
        for(Node result : nodes){
            if(current != result){
                return result;
            }
        }
        throw new RuntimeException("AssertionException");
    }

    public double getDist(){
        return dist;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(Color.GREEN);
        DrawUtil.drawLine(nodes[0].getCenter(), nodes[1].getCenter(), g);
    }

    @Override
    public Layer getLayer() {
        return Layer.DEV;
    }
}

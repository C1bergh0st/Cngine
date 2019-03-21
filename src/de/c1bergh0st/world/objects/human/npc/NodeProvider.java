package de.c1bergh0st.world.objects.human.npc;

import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.interfaces.Solid;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NodeProvider {
    private List<Node> nodeList;
    private World world;

    public NodeProvider(World world){
        this.world = world;
        if(world == null){
            throw new NullPointerException("World can't be null");
        }
        nodeList = new ArrayList<>();
    }

    public void reload(){
        nodeList.clear();
        List<Rectangle2D.Double> solidList = world.getSolidBounds();
        //List<Node> detailList = new LinkedList<>();
        boolean[][] walls = world.getWalls();
        for(int x = 0; x < walls.length; x++){
            for(int y = 0; y < walls[x].length; y++){
                if(!walls[x][y]){
                    nodeList.add(new Node(new Vector(x, y)));
                }
            }
        }
        /*
        for(Node node : nodeList){
            for(Rectangle2D.Double rect : solidList){
                if(node.intersects(rect)){
                    detailList.add(node);
                }
            }
        }
        nodeList.removeAll(detailList);
        for(Node n : detailList){
            nodeList.add(new Node(n.getPos().add(new Vector(0.2,0.2)), new Vector(0.6, 0.6),true));
        }*/
    }

    public List<Node> getNodes(){
        return nodeList;
    }

}

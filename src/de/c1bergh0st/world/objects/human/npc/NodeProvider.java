package de.c1bergh0st.world.objects.human.npc;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.World;
import org.w3c.dom.css.Rect;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NodeProvider {
    private List<Node> nodeList;
    private List<Edge> edgeList;
    private World world;

    public NodeProvider(World world){
        this.world = world;
        if(world == null){
            throw new NullPointerException("World can't be null");
        }
        nodeList = new ArrayList<>();
        edgeList = new LinkedList<>();
    }

    public void reload(){
        long start = System.nanoTime();
        nodeList.clear();
        edgeList.clear();
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
        double threshhold = Math.sqrt(2.01);
        List<Rectangle2D.Double> solids = world.getWallBounds();
        for(Node source : nodeList){
            skip:for(Node target : nodeList){
                if(source != target){
                    if(source.getCenter().distance(target.getCenter()) < threshhold){
                        Line2D.Double line = new Line2D.Double(source.getCenter().x, source.getCenter().y, target.getCenter().getX(), target.getCenter().y);
                        for(Rectangle2D.Double rect : solids){
                            if(rect.intersectsLine(line)){
                                continue skip;
                            }
                        }
                        edgeList.add(source.link(target));

                    }
                }
            }
        }
        long time = System.nanoTime() - start;
        double seconds = time / Math.pow(10, 9);
        Debug.send("Elapsed Time while generating: " + seconds + "s");
    }

    public Node getNearest(Vector pos){
        double dist = 10000;
        Node result= null;
        for(Node n: nodeList){
            if(pos.distance(n.getCenter()) < dist){
                dist = pos.distance(n.getCenter());
                result = n;
            }
        }
        return result;

    }


    public List<Node> getNodes(){
        return nodeList;
    }

    public List<Edge> getEdges(){
        return edgeList;
    }

}

package de.c1bergh0st.world.objects.human.npc;

import de.c1bergh0st.geometry.Vector;

import java.beans.VetoableChangeListener;
import java.util.*;

public class PathFinder {
    private static final int depth = 100;
    private NodeProvider master;
    private Map<Node, Node> path;
    private Set<Node> visited;
    private Set<Node> current;
    private Set<Node> next;
    private int safety;
    private boolean found;
    private Node endNode;
    Node startNode;

    public PathFinder(NodeProvider master){
        this.master = master;
    }

    public List<Vector> getPath(Vector start, Vector end){
        path = new HashMap<>();
        visited = new HashSet<>();
        current = new HashSet<>();
        next = new HashSet<>();
        safety = 0;
        found = false;
        startNode = master.getNearest(start);
        endNode = master.getNearest(end);

        current.add(startNode);
        while(safety < depth && !found){
            safety++;
            for(Node n : current){
                if(n == endNode){
                    return resolve(n);
                }
                List<Node> neighbors = n.getNeighbors();
                neighbors.sort(new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        double dist1 = o1.getCenter().distance(n.getCenter());
                        double dist2 = o2.getCenter().distance(n.getCenter());
                        if (dist1 > dist2){
                            return 1;
                        }
                        if (dist1 < dist2){
                            return -1;
                        }
                        return 0;
                    }
                });
                for(Node neighbor :neighbors){
                    if (!visited.contains(neighbor) && !current.contains(neighbor) && !next.contains(neighbor)){
                        next.add(neighbor);
                        path.put(neighbor, n);
                    }
                }
            }
            visited.addAll(current);
            current.clear();
            current.addAll(next);
            next.clear();
        }

        return null;
    }

    private List<Vector> resolve(Node n){
        LinkedList<Vector> result = new LinkedList<>();
        Node curr = n;
        while(curr != startNode){
            result.add(curr.getCenter());
            curr = path.get(curr);
        }
        result.add(startNode.getCenter());
        Collections.reverse(result);
        return result;
    }



}

package de.c1bergh0st.world.objects.human.npc;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.Direction;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.interfaces.Tickable;
import de.c1bergh0st.world.objects.DevPath;
import de.c1bergh0st.world.objects.DevPoint;
import de.c1bergh0st.world.objects.human.Human;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class AI implements Tickable {
    private World world;
    private Human target;
    private Queue<Vector> movementQ;
    private Vector currentGoal;
    private double speed;
    private DevPath last;

    public AI(World world, Human target){
        this.world = world;
        this.target = target;
        movementQ = new LinkedList<>();
        speed = 4;
    }

    @SuppressWarnings("Duplicates")
    public void moveTo(Vector goal){
        List<Vector> path = new PathFinder(world.getNodeProvider()).getPath(target.getCenter(), goal);
        if(path != null){
            last = new DevPath(path);
            world.add(last);
        } else{
            Debug.send("no path found");
        }
        movementQ.addAll(path);
    }



    @Override
    public void tick() {
        if(target.shouldRemove()){
            world.remove(this);
            world.getAiMaster().remove(this);
            if(last != null){
                world.remove(last);
            }
        }

        if(currentGoal != null){
            if(reached(currentGoal)){
                if(!movementQ.isEmpty()){
                    currentGoal = movementQ.poll();
                } else if(last != null){
                    world.remove(last);
                    last = null;
                }
            } else {
                moveTowards(currentGoal);
            }
        } else if(!movementQ.isEmpty()){
            currentGoal = movementQ.poll();
        }

    }

    private void moveTowards(Vector currentGoal) {
        Vector location = target.getCenter();
        Vector direction = currentGoal.substract(location);
        target.setDirection(direction);
        target.moveRelative(speed);
    }

    private boolean reached(Vector currentGoal) {
        double dist = target.getCenter().distance(currentGoal);
        return dist < 0.2;
    }

    @Override
    public void secondTick() {

    }
}

package de.c1bergh0st.world;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import de.c1bergh0st.damage.HitBox;
import de.c1bergh0st.damage.Team;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.MainGame;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Collisions;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Interactable;
import de.c1bergh0st.world.interfaces.Layer;
import de.c1bergh0st.world.interfaces.Solid;
import de.c1bergh0st.world.interfaces.Tickable;
import de.c1bergh0st.world.objects.Active;
import de.c1bergh0st.world.objects.Door;
import de.c1bergh0st.world.objects.Wall;
import de.c1bergh0st.world.objects.human.Controller;
import de.c1bergh0st.world.objects.human.npc.Edge;
import de.c1bergh0st.world.objects.human.npc.Node;
import de.c1bergh0st.world.objects.human.npc.NodeProvider;
import de.c1bergh0st.world.terminal.CLI;

@SuppressWarnings("Duplicates")
public class World {
    public static final int MAX = 25;
    public static final double SCALE = 1;
    private List<Tickable> tickables;
    private HashMap<Layer, List<Drawable>> drawLayers;
    private List<Collisions> collisionables;
    private List<HitBox> hitboxes;
    private List<Interactable> interactables;
    private List<Solid> solids;
    private List<Object> removalList;
    private Wall[][] walls;
    private Active center;
    private MainGame game;
    private Controller controller;
    private CLI commandLine;
    private NodeProvider nodeProvider;
    public static boolean devDraw = true;
    private boolean paused;
    
    public World(MainGame game){
        this.game = game;
        paused = false;
        //variable initialization
        tickables = new LinkedList<>();
        drawLayers = new HashMap<>();
        for(Layer l : Layer.values()){
            drawLayers.put(l, new LinkedList<>());
        }
        removalList = new LinkedList<>();
        collisionables = new LinkedList<>();
        hitboxes = new LinkedList<>();
        interactables = new LinkedList<>();
        solids = new LinkedList<>();
        controller = new Controller(this);
        walls = new Wall[MAX][MAX];
        nodeProvider = new NodeProvider(this);
        
        commandLine = new CLI();
    }
    
    
    //-----------------------------------TICKS AND DRAW
    public void tick(){
        commandLine.testForInput(this);
        if(paused){
            return;
        }
        controller.tick();
        for(Tickable t : tickables){
            t.tick();
        }
        List<Rectangle2D.Double> solidList = getSolidBounds();
        for (Collisions c : collisionables) {
            c.collision(solidList);
        }
        for(HitBox a: hitboxes){
            for(HitBox b : hitboxes){
                if(a != b){
                    if(a.getHitBox().intersects(b.getHitBox())){
                        a.hit(b.getTeam(), b.getContactDamage());
                    }
                }
            }

            if(a.shouldRemove()){
                remove(a);
            }
        }
        //Util.offset = new Point2D.Double(-2, -1);
        cleanLists();
    }
    
    
    
    public void secondTick(){
        if(paused){
            return;
        }
        controller.secondTick();
        for(Tickable t : tickables){
            t.secondTick();
        }
        orderRenderLists();
    }
    
    /**
     * Used to make sure no Drawable is in a wrong layer
     * This can happen when a Drawable changes Layers
     */
    private void orderRenderLists() {
        List<Drawable> correction = new LinkedList<>();
        for(Layer l : Layer.values()){
            for(Drawable d : drawLayers.get(l)){
                if(d.getLayer() != l){
                    correction.add(d);
                }
            }
        }
        for(Drawable d : correction){
            removeDrawable(d);
            addDrawable(d);
        }
    }

    public void setPaused(boolean paused){
        this.paused = paused;
    }
    
    public void togglePause(){
        paused = !paused;
    }

    public Point2D.Double getOffset(){
        double halfwidth = Util.toUnits(1920) / 2d;
        double halfheight = Util.toUnits(1080) / 2d;
        Vector offsetFromCorner = new Vector(halfwidth, halfheight);
        Vector offset = center.getPosition().substract(offsetFromCorner);
        return offset;
    }
    
    public void draw(Graphics g){
        Point2D.Double offset = getOffset();
        Util.offset = offset;
        Graphics2D gg = (Graphics2D) g.create();
        gg.scale(SCALE, SCALE);
        gg.translate(Util.toPix(-offset.getX()), Util.toPix(-offset.getY()));
        WorldUtil.drawDevSpquares(gg);
        for(Layer l : Layer.values()){
            for(Drawable d : drawLayers.get(l)){
                d.draw(gg);
            }
            if(l == Layer.FLOOR && devDraw){
                for(Node n: nodeProvider.getNodes()){
                    n.draw(gg);
                }
                for(Edge e: nodeProvider.getEdges()){
                    e.draw(gg);
                }
            }
        }
        if(devDraw){
            for(HitBox h : hitboxes){
                h.drawHitBox(gg);
            }
        }
    }
    
    
    //------------------------------------ Getters and Setters
    public List<Interactable> getInteractables(){
        return interactables;
    }
    
    public MainGame getGame(){
        return game;
    }

    public NodeProvider getNodeProvider(){
        return nodeProvider;
    }

    public void setCenter(Active a){
        if(a == null){
            throw new NullPointerException("You cannot Center on Nothing!");
        }
        center = a;
    }

    public List<Rectangle2D.Double> getWallBounds(){
        List<Rectangle2D.Double> result = new LinkedList<>();
        for(int x = 0; x < walls.length; x++){
            for(int y = 0; y < walls[x].length; y++){
                if(walls[x][y] != null){
                    result.add(walls[x][y].getBounds());
                }
            }
        }
        for(Solid s : solids){
            if(s instanceof Door){
                continue;
            }
            result.add(s.getBounds());
        }
        return result;
    }
    
    public List<Rectangle2D.Double> getSolidBounds(){
        List<Rectangle2D.Double> result = new LinkedList<>();
        for(Solid s : solids){
            result.add(s.getBounds());
        }
        //noinspection ForLoopReplaceableByForEach
        for(int x = 0; x < walls.length; x++){
            for(int y = 0; y < walls[x].length; y++){
                if(walls[x][y] != null){
                    result.add(walls[x][y].getBounds());
                }
            }
        }
        //Util.draw2DArray(walls);
        //System.out.println(result.size());
        return result;
    }
    
    public boolean[][] getWalls(){
        boolean[][] result = new boolean[MAX][MAX];
        for(int x = 0; x < result.length; x++){
            for(int y = 0; y < result[0].length; y++){
                if(walls[x][y] != null){
                    result[x][y] = true;
                }
            }
        }
        return result;
    }
    

    public Controller getController(){
        return controller;
    }
    
    /**
     * Adds an Object to its responding Lists
     * @param o the Object to add to this World
     */
    public void add(Object o){
        if(o instanceof Tickable){
            tickables.add((Tickable)o);
        }
        if(o instanceof Drawable){
            addDrawable((Drawable) o);
        }
        if(o instanceof Collisions){
            collisionables.add((Collisions)o);
        }
        if(o instanceof HitBox){
            hitboxes.add((HitBox)o);
        }
        if(o instanceof Interactable){
            interactables.add((Interactable)o);
        }
        if(o instanceof Solid){
            solids.add((Solid) o);
        }
        if(o instanceof Wall){
            Wall w = (Wall) o;
            addWall(w.getPosition().getX(), w.getPosition().getY(), w);
        }
    }
    
    /**
     * Marks an Object to be removed after the end of this tick
     * @param o the Object to remove
     */
    public void remove(Object o){
        if (!removalList.contains(o)){
            removalList.add(o);
        }
    }
    
    private void addDrawable(Drawable d){
        Layer l = d.getLayer();
        drawLayers.get(l).add(d);
    }
    
    private void removeDrawable(Drawable d){
        for(Layer l : Layer.values()){
            drawLayers.get(l).remove(d);
        }
    }
    
    /**
     * Actually removes Objects from the Lists
     */
    private void cleanLists(){
        for(Object o : removalList){
            if(o instanceof Tickable){
                tickables.remove(o);
            }
            if(o instanceof Drawable){
                removeDrawable((Drawable)o);
            }
            if(o instanceof Collisions){
                collisionables.remove(o);
            }
            if(o instanceof HitBox){
                hitboxes.remove(o);
            }
            if(o instanceof Interactable){
                interactables.remove(o);
            }
            if(o instanceof Solid){
                solids.remove(o);
            }
            if(o instanceof Wall){
                Wall w = (Wall) o;
                destroyWall((int)w.getPosition().getX(), (int)w.getPosition().getY());
            }
        }
        removalList.clear();
    }
    
    private void destroyWall(int x, int y){
        if(x < 0 || x >= MAX || y < 0 || y >= MAX){
            return;
        }
        Wall wall = walls[x][y];
        if(wall == null){
            return;
        }
        walls[x][y] = null;
    }
    
    private void addWall(double x, double y, Wall wall){
        addWall((int)x , (int)y , wall);
    }
    
    private void addWall(int x, int y, Wall wall){
        if(wall == null){
            throw new NullPointerException();
        }
        walls[x][y] = wall;
    }
}

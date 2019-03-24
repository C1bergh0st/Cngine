package de.c1bergh0st.world.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.List;

import de.c1bergh0st.damage.HitBox;
import de.c1bergh0st.damage.Team;
import de.c1bergh0st.debug.DrawUtil;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.Direction;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Interactable;
import de.c1bergh0st.world.interfaces.Layer;
import de.c1bergh0st.world.interfaces.Solid;
import de.c1bergh0st.world.objects.human.Human;

public class Door implements Drawable, Interactable, Solid, HitBox{
    private static final double WIDTH = 0.1;
    private static final double OFFSET = 1 - WIDTH / 2;
    private Vector hinge;
    private Vector closed;
    private Vector open;
    private boolean enabled;
    private boolean isOpen;
    private int health;
    private Vector pos;
    private Direction dir;
    
    public Door(int x, int y, Direction dir, int health, boolean open){
        this(x, y, dir, health);
        isOpen = open;
    }
    
    public Door(int x, int y, Direction dir, int health){
        isOpen = false;
        enabled = true;
        this.health = health;
        this.dir = dir;
        if(dir == null){
            dir = Direction.UP;
        }
        pos = new Vector(x, y);
        switch (dir) {
            case UP:
                initUp();
                break;
            case RIGHT:
                initRight();
                break;
            case DOWN:
                initDown();
                break;
            case LEFT:
                initLeft();
                break;
            default:
                throw new IllegalArgumentException("Door does not support" + dir);
        }
    }

    private Rectangle2D.Double getCurrentRectangle() {
        List<Vector> positions = hinge.getSqaure(WIDTH / 2);
        if (isOpen) {
            positions.addAll(open.getSqaure(WIDTH / 2));
        } else{
            positions.addAll(closed.getSqaure(WIDTH / 2));
        }
        
        return generateRectangle(positions);
    }
    
    @SuppressWarnings("Duplicates")
    private Rectangle2D.Double generateRectangle(List<Vector> positions) {
        Vector upL = positions.get(0);
        for(Vector v : positions){
            if(v.abs() <= upL.abs()){
                upL = v;
            }
        }
        Vector downR = positions.get(0);
        for(Vector v : positions){
            if(v.abs() >= downR.abs()){
                downR = v;
            }
        }
        double width = downR.x - upL.x;
        double height = downR.y - upL.y;
        return new Rectangle2D.Double(upL.x, upL.y, width, height);
    }

    private void initLeft() {
        //Lower right
        hinge = new Vector(pos.x + OFFSET, pos.y + OFFSET);
        //upper right
        closed = new Vector(pos.x + OFFSET, pos.y + WIDTH / 2);
        //lower left
        open = new Vector(pos.x + WIDTH / 2, pos.y + OFFSET);
    }

    private void initDown() {
        //upper right
        hinge = new Vector(pos.x + OFFSET, pos.y + WIDTH / 2);
        //upper left
        closed = new Vector(pos.x + WIDTH / 2, pos.y + WIDTH / 2);
        //Lower right
        open = new Vector(pos.x + OFFSET, pos.y + OFFSET);
    }

    private void initRight() {
        //upper left
        hinge = new Vector(pos.x + WIDTH / 2, pos.y + WIDTH / 2);
        //lower left
        closed = new Vector(pos.x + WIDTH / 2, pos.y + OFFSET);
        //upper right
        open = new Vector(pos.x + OFFSET, pos.y + WIDTH / 2);
    }

    private void initUp() {
        //lower left
        hinge = new Vector(pos.x + WIDTH / 2, pos.y + OFFSET);
        //Lower right
        closed = new Vector(pos.x + OFFSET, pos.y + OFFSET);
        //upper left
        open = new Vector(pos.x + WIDTH / 2, pos.y + WIDTH / 2);
    }

    @Override
    public Rectangle2D.Double getBounds() {
        return getCurrentRectangle();
    }

    @Override
    public void interact(Human human) {
        if (enabled) {
            isOpen = !isOpen;
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(61,35,0));
        DrawUtil.fillRect(getBounds(), g);
        if(World.devDraw){
            g.setColor(new Color(0, 255, 0, 150));
            drawOpeningArc(g);
            g.setColor(Color.BLACK);
            hinge.draw(g, 0.1);
            g.setColor(Color.WHITE);
            closed.draw(g, 0.1);
            open.draw(g, 0.1);
        }
    }
    
    private void drawOpeningArc(Graphics2D g){
        double x;
        double y;
        double w;
        double h;
        int startangle;
        switch(dir){
            case RIGHT:
                x = pos.x - 1;
                y = pos.y - 1;
                w = 2;
                h = 2;
                startangle = 270;
                break;
            case UP:
                x = pos.x - 1;
                y = pos.y ;
                w = 2;
                h = 2;
                startangle = 0;
                break;
            case DOWN:
                x = pos.x;
                y = pos.y - 1;
                w = 2;
                h = 2;
                startangle = 180;
                break;
            case LEFT:
                x = pos.x;
                y = pos.y;
                w = 2;
                h = 2;
                startangle = 90;
                break;
            default:
                x = pos.x;
                y = pos.y;
                w = 1;
                h = 1;
                startangle = 0;
                break;
        }
        int angle = 90;
        g.fillArc(Util.toPix(x), Util.toPix(y), Util.toPix(w), Util.toPix(h), startangle, angle);
    }

    @Override
    public Rectangle2D.Double getHitBox() {
        return getBounds();
    }

    @Override
    public void hit(Team team, int damage) {
        health -= damage;
    }

    @Override
    public int getContactDamage() {
        return 0;
    }

    @Override
    public Team getTeam() {
        return Team.NEUTRAL;
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }

    @Override
    public Double getInteractionField() {
        return getBounds();
    }
    
    @Override
    public String toString(){
        return "Door at " + pos.toString() + " currently " +  (isOpen ? "open":"closed");
    }

    @Override
    public Layer getLayer() {
        return Layer.MIDDLE;
    }

    @Override
    public boolean shouldRemove() {
        return isDead();
    }

}

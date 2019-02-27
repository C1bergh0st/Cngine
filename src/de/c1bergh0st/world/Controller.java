package de.c1bergh0st.world;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.MainGame;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.input.InputHandler;
import de.c1bergh0st.world.interfaces.Tickable;
import de.c1bergh0st.world.objects.DevPoint;
import de.c1bergh0st.world.objects.human.Action;
import de.c1bergh0st.world.objects.human.Human;

public class Controller implements Tickable{
    private Human target;
    private InputHandler input;
    private MainGame engine;
    private World world;
    private Vector cursor;
    private Point lastMousePos;
    private DevPoint drawPos;
    private Vector mouseInWorld;
    
    public Controller(World world){
        if(world == null){
            throw new NullPointerException();
        }
        this.world = world;
        this.engine = this.world.getGame();
        this.input = engine.input;
        lastMousePos = new Point();
        mouseInWorld = new Vector(0, 0);
        drawPos = new DevPoint(new Vector(0, 0), 0.1);
        world.add(drawPos);
    }
    
    public void setTarget(Human target){
        this.target = target;
    }
    
    
    @Override
    public void tick() {
        retrieveCursorPosition();
        drawPos.setPosition(mouseInWorld);
        target.setDirection(mouseInWorld.substract(target.getPosition()).substract(target.getDim().multiply(0.5d)));
        if(target != null){
            double speed = 3;
            if(input.isDown("shift")){
                speed = speed * 1.5;
            }
            if(input.isDown("use")){
                target.performAction(Action.USE);
            }
            if(input.isDown("left")){
                target.move(Direction.LEFT, speed);
            }
            if(input.isDown("right")){
                target.move(Direction.RIGHT, speed);
            }
            if(input.isDown("up")){
                target.move(Direction.UP, speed);
            }
            if(input.isDown("down")){
                target.move(Direction.DOWN, speed);
            }
            if(input.isDown("reload")){
                target.performAction(Action.RELOAD);
            }
            if(input.isClicked(InputHandler.LEFTCLICK)){
                target.performAction(Action.SHOOT);
            }
            if(input.isDown("space")){
                world.setPaused(true);;
            }
        }
    }

    private void retrieveCursorPosition() {
        Point p1 = engine.getPanel().getMousePosition();
        if(p1 == null){
            p1 = lastMousePos;
        }
        lastMousePos = p1;
        Vector p = new Vector(p1.x, p1.y);
        Point2D.Double offset = world.getOffset();
        p = p.multiply(1.0 / (World.SCALE * Util.toPix(1)));
        p = p.add(offset);
        mouseInWorld = p;
        //System.out.println("Cursor at : ["+ Math.round(p.x * 100) / 100d + ";" + Math.round(p.y * 100) / 100d + "]" );
    }

    @Override
    public void secondTick() {
    }

    
    
}

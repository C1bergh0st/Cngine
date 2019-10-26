package de.c1bergh0st.world.objects.human;

import java.awt.*;
import java.util.List;

import de.c1bergh0st.damage.Team;
import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;
import de.c1bergh0st.gamecode.MainGame;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.input.InputHandler;
import de.c1bergh0st.world.Direction;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.interfaces.Interactable;
import de.c1bergh0st.world.objects.particles.Smoke;

public class Player extends Human{
    private static final int INTERACTIONDELAY = 300;
    private int phasedelay = 4 * 1000;
    private int phaseduration = 300;
    private double phasespeed = 2.5;
    private MainGame game;
    private InputHandler input;
    private long lastSucessfulUse;
    private long lastSuccessfulPhase;
    
    public Player(double x, double y, World world){
        super(new Vector(x, y), world);
        this.game = world.getGame();
        input = game.input;
    }

    public void draw(Graphics2D g){
        super.draw(g);
        //Phase recharging
        if(!(lastSuccessfulPhase + phasedelay < System.currentTimeMillis())){
            Vector p = this.getCenter().add(new Vector(-0.5,0.5));
            double progress = (double)(System.currentTimeMillis() - lastSuccessfulPhase) / phasedelay;
            int max = 20;
            char loading = '|';
            StringBuilder str = new StringBuilder();
            int showing = (int)(progress * max);
            for(int i = 0; i <= showing; i++){
                str.append(loading);
            }
            g.drawString(str.toString(), Util.toPix(p.x), Util.toPix(p.y + 0.15));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(currWeapon != null){
            currWeapon.tick();
        }
        if(!(lastSuccessfulPhase + phaseduration < System.currentTimeMillis())) {
            getWorld().safeAdd(new Smoke(this.getCenter(), 1000));
        }
    }


    @Override
    public void move(Direction dir, double speed) {
        Vector change = dir.getVector().multiply(speed / MainGame.TICKSPEED);
        if(System.currentTimeMillis() < lastSuccessfulPhase + phaseduration){
            change = change.multiply(phasespeed);
        }
        pos = pos.add(change);
    }

    @Override
    public void moveRelative(double sqrsPersecond) {
        Vector change = direction.getUnitVector().multiply(sqrsPersecond / MainGame.TICKSPEED);
        pos = pos.add(change);
    }

    @Override
    public void performAction(Action action) {
        if(action == Action.USE && lastSucessfulUse + INTERACTIONDELAY < System.currentTimeMillis()){
            List<Interactable> interactables = world.getInteractables();
            for (Interactable inter: interactables){
                if(kone.intersects(inter.getInteractionField())){
                    inter.interact(this);
                    lastSucessfulUse = System.currentTimeMillis();
                    Debug.send("Used " + inter.toString());
                    break;
                }
            }
        }
        if(action == Action.RELOAD && lastSucessfulUse + INTERACTIONDELAY < System.currentTimeMillis()){
            if(currWeapon != null){
                currWeapon.reload();
                lastSucessfulUse = System.currentTimeMillis();
            }
        }
        if(action == Action.SHOOT){
            if(currWeapon != null){
                currWeapon.fire();
            }
        }
        if(action == Action.PHASE && lastSuccessfulPhase + phasedelay < System.currentTimeMillis() ){
            lastSuccessfulPhase = System.currentTimeMillis();
        }
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
        return Team.GOOD;
    }

    @Override
    public boolean isDead() {
        return false;
    }
    
    public String toString(){
        return "PLayer at " + pos + " and direction " + direction; 
    }

    @Override
    public boolean shouldRemove() {
        return false;
    }
    
}

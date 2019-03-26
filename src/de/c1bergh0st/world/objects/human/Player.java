package de.c1bergh0st.world.objects.human;

import java.util.List;

import de.c1bergh0st.damage.Team;
import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.gamecode.MainGame;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.input.InputHandler;
import de.c1bergh0st.world.Direction;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.interfaces.Interactable;

public class Player extends Human{
    private static final int INTERACTIONDELAY = 300;
    private MainGame game;
    private InputHandler input;
    private long lastSucessfulUse;
    
    
    public Player(double x, double y, World world){
        super(new Vector(x, y), world);
        this.game = world.getGame();
        input = game.input;
    }

    @Override
    public void tick() {
        super.tick();
        if(currWeapon != null){
            currWeapon.tick();
        }
    }


    @Override
    public void move(Direction dir, double speed) {
        Vector change = dir.getVector().multiply(speed / MainGame.TICKSPEED);
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

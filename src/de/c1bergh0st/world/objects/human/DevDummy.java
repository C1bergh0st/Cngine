package de.c1bergh0st.world.objects.human;

import de.c1bergh0st.damage.Team;
import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.gamecode.MainGame;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.Direction;
import de.c1bergh0st.world.World;

public class DevDummy extends Human{

    public DevDummy(Vector pos, World world) {
        super(pos.add(new Vector(0.15, 0.15)), world);
    }
    
    
    public void tick(){
        super.tick();
        //setDirection(direction.add(direction.turnLeft().multiply(0.04)));
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
    public boolean shouldRemove() {
        return isDead();
    }

    @Override
    public void move(Direction dir, double sqrsPerSecond) {
        Debug.sendErr("Dummys dont move!");
    }

    @Override
    public void moveRelative(double sqrsPersecond) {
        Vector change = direction.getUnitVector().multiply(sqrsPersecond / MainGame.TICKSPEED);
        pos = pos.add(change);
    }

    @Override
    public void performAction(Action action) {
        Debug.send("Dummys dont do sh*t");
    }

}

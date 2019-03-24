package de.c1bergh0st.world.objects.human.weapons;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import de.c1bergh0st.damage.HitBox;
import de.c1bergh0st.damage.Team;
import de.c1bergh0st.gamecode.MainGame;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.interfaces.Collisions;
import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Tickable;

@SuppressWarnings("WeakerAccess")
public abstract class Bullet implements HitBox, Drawable, Tickable, Collisions{
    private static final double SAFETY = 0.05;
    protected Vector pos;
    protected double size;
    protected Vector dir;
    protected int health = 1;
    protected int damage;
    protected Team team;

    @Override
    public Double getHitBox() {
        return new Rectangle2D.Double(pos.x, pos.y, size, size);
    }


    @Override
    public int getContactDamage() {
        return damage;
    }

    @Override
    public Team getTeam() {
        return team;
    }

    @Override
    public boolean isDead() {
        return health <= 0;
    }
    
    protected boolean isSafe(){
        double speed = dir.abs() / MainGame.TICKSPEED;
        return speed < (size + SAFETY);
    }
    
}

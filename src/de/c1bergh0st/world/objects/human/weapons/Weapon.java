package de.c1bergh0st.world.objects.human.weapons;

import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Tickable;
import de.c1bergh0st.world.objects.human.Human;

public abstract class Weapon implements Tickable, Drawable{
    protected Human carrier;
    
    public abstract void fire();
    
    public abstract void reload();
    
    public abstract int getAmmo();
    
    public abstract int getMagazineSize();
    
    public abstract double getAmmoPercentage();
    
    public void setCarrier(Human h){
        carrier = h;
    }
    
}

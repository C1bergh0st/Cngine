package de.c1bergh0st.world.objects.human.weapons;

import de.c1bergh0st.world.interfaces.Drawable;
import de.c1bergh0st.world.interfaces.Tickable;
import de.c1bergh0st.world.objects.human.Human;
import de.c1bergh0st.world.objects.human.inventory.Equipable;
import de.c1bergh0st.world.objects.human.inventory.ItemType;

import java.awt.image.BufferedImage;

public abstract class Weapon implements Tickable, Drawable, Equipable {
    protected Human carrier;
    
    public abstract void fire();
    
    public abstract void reload();
    
    public abstract int getAmmo();
    
    public abstract int getMagazineSize();
    
    public abstract double getAmmoPercentage();
    
    public void setCarrier(Human h){
        carrier = h;
    }

    public ItemType getItemType(){
        return ItemType.WEAPON;
    }

    public abstract boolean isDroppable();

    public abstract String getSideviewImagePath();

    public abstract double getReloadProgress();

}

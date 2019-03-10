package de.c1bergh0st.world.objects.human.inventory;

import de.c1bergh0st.world.objects.human.Human;

public interface Equipable {

    ItemType getItemType();

    void setCarrier(Human human);

    
}

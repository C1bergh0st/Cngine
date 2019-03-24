package de.c1bergh0st.world.objects.human.inventory;

public interface Inventory<T extends Equipable> {

    boolean isEmpty(int slot);

    boolean canEquip(Equipable e);

    int equip(T e);

    void equip(T e, int id);

    int getSlot(T e);

    boolean contains(T e);

    T get(int slot);

    boolean remove(T e);

    void clearSlot(int slot);

    int getMaxSize();

    int getEmptySpace();

    double getFillPercentage();

    String toString();
}

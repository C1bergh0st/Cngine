package de.c1bergh0st.world.objects.human.inventory;

import de.c1bergh0st.debug.Debug;

import java.util.ArrayList;
import java.util.List;

public class SlotInventory<T extends Equipable> implements Inventory<T>{
    private final int size;
    private final List<T> slots;

    public SlotInventory(int size){
        if(size < 0){
            throw new IllegalArgumentException("Size cannot be smaller than zero");
        }
        this.size = size;
        slots = new ArrayList<>();
        for(int i = 0; i < size; i++){
            slots.add(i, null);
        }
    }

    @Override
    public boolean isEmpty(int slot) {
        if(slot < 0 || slot >= size){
            return false;
        }
        return slots.get(slot) == null;
    }

    @Override
    public boolean canEquip(Equipable e) {
        if(e == null){
            return false;
        }
        try{
            @SuppressWarnings("unchecked") T type = (T) e;
        } catch (ClassCastException ex){
            return false;
        }
        return getEmptySpace() > 0;
    }

    @Override
    public int equip(T e) {
        if(!canEquip(e)){
            return -1;
        }
        int id = -1;
        for (int i = 0; i < size; i++) {
            if(slots.get(i) == null){
                slots.set(i, e);
                id = i;
                break;
            }
        }
        return id;
    }

    @Override
    public void equip(T e, int id) {
        if(id < 0 || id >= size){
            Debug.sendErr("Trying to equip " + " in invalid slot (" + id + ")");
            return;
        }
        slots.set(id, e);
    }

    @Override
    public int getSlot(T e) {
        if(!contains(e)){
            return -1;
        }
        return 0;
    }

    @Override
    public boolean contains(T e) {
        if(e == null){
            return false;
        }
        for(T t : slots){
            if(t == e){
                return true;
            }
        }
        return false;
    }

    @Override
    public T get(int slot) {
        if(slot < 0 || slot >= size){
            return null;
        }
        return slots.get(slot);
    }

    @Override
    public boolean remove(T e) {
        if(!contains(e)){
            return false;
        }
        for(int i = 0; i < size; i++){
            if(slots.get(i) == e){
                slots.set(i, null);
            }
        }
        return true;
    }

    @Override
    public void clearSlot(int slot) {
        if(slot < 0 || slot >= size){
            return;
        }
        slots.set(slot, null);
    }

    @Override
    public int getMaxSize() {
        return size;
    }

    @Override
    public int getEmptySpace() {
        int free = 0;
        for(int i = 0; i < size; i++){
            if(slots.get(i) == null){
                free++;
            }
        }
        return free;
    }

    @Override
    public double getFillPercentage() {
        return 1d - ((double)(getEmptySpace())/ (double)(size));
    }
}

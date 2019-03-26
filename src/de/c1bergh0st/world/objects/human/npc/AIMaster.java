package de.c1bergh0st.world.objects.human.npc;

import de.c1bergh0st.gamecode.MainGame;

import java.util.Map;
import java.util.TreeMap;

public class AIMaster {
    private Map<String, AI> aiMap;

    public AIMaster(){
        aiMap = new TreeMap<>();
    }

    public void add(String id,AI ai){
        aiMap.put(id, ai);
    }

    public AI get(String id){
        return aiMap.get(id);
    }

    public boolean contains(String id){
        return aiMap.containsKey(id);
    }

    public void remove(String id){
        aiMap.remove(id);
    }

    public void remove(AI ai){
        String name = "";
        for(String s : aiMap.keySet()){
            if(get(s) == ai){
                name = s;
            }
        }
        remove(name);
    }

}

package de.c1bergh0st.world.loader;

import de.c1bergh0st.world.Direction;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.objects.Door;
import de.c1bergh0st.world.objects.Floor;
import de.c1bergh0st.world.objects.TextOverlay;
import de.c1bergh0st.world.objects.Wall;
import de.c1bergh0st.world.objects.human.Player;
import de.c1bergh0st.world.objects.human.weapons.DevGun;

public class DevLoader implements WorldLoader{
    
    public void load(World world){
        Player p = new Player(4,3, world);
        p.setWeapon(new DevGun(p, 150, 12,34, 0.3, 12), 1);
        p.setWeapon(new DevGun(p,800, 30, 23,0.5, 25), 2);
        world.add(p);
        world.setCenter(p);
        world.getController().setTarget(p);
        Wall t;
        int[][] temp = {{1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 0, 1, 0, 1, 0, 1, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 1, 0, 1, 0, 1, 0, 1}};
        /**int[][] temp = {{1, 1, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0}};*/
        
        for(int x = 0; x < temp.length; x++){
            for(int y = 0; y < temp[0].length; y++){
                if(temp[x][y] == 1){
                    t = new Wall(x, y, "tiles/dev.png");
                    world.add(t);
                }
                if(x < 4){
                    world.add(new Floor(x, y, "tiles/dev_floor.png"));
                } else {
                    world.add(new Floor(x, y, "tiles/dev_floor_alt.png"));
                }
            }
        }
        
        world.add(new Door(1, 2, Direction.UP, 100));
        world.add(new Door(1, 4, Direction.RIGHT, 100));
        world.add(new Door(1, 6, Direction.DOWN, 100));
        world.add(new Door(1, 8, Direction.LEFT, 100));
        
        /*
        world.add(new TextOverlay(2, 2, "UP"));
        world.add(new TextOverlay(2, 4, "RIGHT"));
        world.add(new TextOverlay(2, 6, "DOWN"));
        world.add(new TextOverlay(2, 8, "LEFT"));
        
        Door te;
        te = new Door(3, 2, Direction.UP, 100);
        te.interact();
        world.add(te);
        te = new Door(3, 4, Direction.RIGHT, 100);
        te.interact();
        world.add(te);
        te = new Door(3, 6, Direction.DOWN, 100);
        te.interact();
        world.add(te);
        te = new Door(3, 8, Direction.LEFT, 100);
        te.interact();
        world.add(te); */
    }

}

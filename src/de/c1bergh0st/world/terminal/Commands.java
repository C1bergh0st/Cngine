package de.c1bergh0st.world.terminal;

import javax.swing.JButton;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.loader.DevLoader;
import de.c1bergh0st.world.objects.Wall;
import de.c1bergh0st.world.objects.human.DevDummy;

public enum Commands implements Command{
    QUIT("quit"){

        @Override
        public void execute(World world, String line) {
            System.out.println("quits");
        }
    }, WALL("wall " + POSITIVENUMBER + "," + POSITIVENUMBER + "(,.*=)?"){

        @Override
        public void execute(World world, String line) {
            String onlyArgs = line.substring(line.indexOf(" ") + 1);
            String[] args = onlyArgs.split(",");
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            String path;
            if(args.length == 3){
                path = args[2];
            } else {
                path = "tiles/dev.png";
            }
            
            Wall newWall = new Wall(x, y, path);
            if(world.getWalls()[x][y]){
                Debug.sendErr("[" + x + ";" + y + "] already occupied");
            } else{
                world.add(newWall);
                System.out.println("Added Wall");
            }
        }
        
    }, DUMMY("dummy " + POSITION){

        @Override
        public void execute(World world, String line) {
            String onlyArgs = line.substring(line.indexOf(" ") + 1);
            String[] args = onlyArgs.split(POSITIONSEPARATOR);
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            DevDummy newDummy = new DevDummy(new Vector(x, y), world);
            world.add(newDummy);
        }
        
    }, DEVDRAW("devdraw " + NUMBER){

        @Override
        public void execute(World world, String line) {
            String onlyArgs = line.substring(line.indexOf(" ") + 1);
            int v = Integer.parseInt(onlyArgs);
            if(v == 1){
                World.devDraw = true;
            } else {
                World.devDraw = false;
            }
        }
        
    }, SHOWFPS("showFPS " + NUMBER){

        @Override
        public void execute(World world, String line) {
            String onlyArgs = line.substring(line.indexOf(" ") + 1);
            int v = Integer.parseInt(onlyArgs);
            if(v == 1){
                world.getGame().showFPS = true;
            } else {
                world.getGame().showFPS = false;
            }
        }
        
    }, DUMMYSQR("dummysqr " + POSITION){

        @Override
        public void execute(World world, String line) {
            String onlyArgs = line.substring(line.indexOf(" ") + 1);
            String[] args = onlyArgs.split(POSITIONSEPARATOR);
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            for(int i = 0; i < 3 ; i++){
                for(int j = 0; j < 3 ; j++){
                    world.add(new DevDummy(new Vector(x + i, y + j), world));
                }
            }
        }
        
    }, PAUSE("pause"){

        @Override
        public void execute(World world, String line) {
            world.togglePause();
        }
        
    }, DEVLOAD("devload"){

        @Override
        public void execute(World world, String line) {
            world.getGame().load(new DevLoader());
            System.out.println("shouldLoad");
        }
        
    };
    
    
    private String regex;
    
    private Commands(String regex){
        if(regex == null){
            throw new NullPointerException();
        }
        this.regex = regex;
    }
    
    
    @Override
    public String getRegex() {
        return regex;
    }


}

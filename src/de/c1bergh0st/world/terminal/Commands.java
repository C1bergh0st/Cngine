package de.c1bergh0st.world.terminal;

import javax.swing.JButton;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.loader.DevLoader;
import de.c1bergh0st.world.objects.Wall;
import de.c1bergh0st.world.objects.human.DevDummy;
import de.c1bergh0st.world.objects.human.Human;
import de.c1bergh0st.world.objects.human.npc.NodeProvider;
import de.c1bergh0st.world.objects.human.weapons.DevGun;

public enum Commands implements Command{
    QUIT("quit"){

        @Override
        public void execute(World world, String line) {
            System.out.println("quits");
            System.exit(0);
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
        
    }, DEVGUN("devGun " + POSITIVENUMBER + SEP + POSITIVENUMBER + SEP + POSITIVENUMBER + SEP + FLOAT + SEP + FLOAT + SEP + POSITIVENUMBER){

        @Override
        public void execute(World world, String line) {
            String[] inputs = separateLine(line);
            int rpm = Integer.parseInt(inputs[0]);
            int magsize = Integer.parseInt(inputs[1]);
            int dmg = Integer.parseInt(inputs[2]);
            double length = Double.parseDouble(inputs[3]);
            double speed = Double.parseDouble(inputs[4]);
            int slot = Integer.parseInt(inputs[5]);
            Human p = world.getController().getTarget();
            p.setWeapon(new DevGun(p, rpm, magsize, dmg,length, speed), slot);
        }
    }, RELOADNODES("reloadnodes"){
        @Override
        public void execute(World world, String line) {
            NodeProvider nodeProvider = world.getNodeProvider();
            nodeProvider.reload();
        }
    };


    
    private String regex;
    
    private Commands(String regex){
        if(regex == null){
            throw new NullPointerException();
        }
        this.regex = regex;
    }

    public String getInfo(){
        //TODO: implement info
        return getRegex();
    }
    
    @Override
    public String getRegex() {
        return regex;
    }

    private static String[] separateLine(String line){
        String onlyArgs = line.substring(line.indexOf(" ") + 1);
        return  onlyArgs.split(SEP);
    }

}

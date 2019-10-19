package de.c1bergh0st.world.terminal;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.loader.DevLoader;
import de.c1bergh0st.world.objects.DevPath;
import de.c1bergh0st.world.objects.DevPoint;
import de.c1bergh0st.world.objects.Wall;
import de.c1bergh0st.world.objects.human.DevDummy;
import de.c1bergh0st.world.objects.human.Human;
import de.c1bergh0st.world.objects.human.npc.AI;
import de.c1bergh0st.world.objects.human.npc.Node;
import de.c1bergh0st.world.objects.human.npc.NodeProvider;
import de.c1bergh0st.world.objects.human.npc.PathFinder;
import de.c1bergh0st.world.objects.human.weapons.DevGun;

import java.util.List;

@SuppressWarnings("Duplicates")
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
            World.devDraw = (v == 1);
        }
        
    }, SHOWFPS("showFPS " + NUMBER){

        @Override
        public void execute(World world, String line) {
            String onlyArgs = line.substring(line.indexOf(" ") + 1);
            int v = Integer.parseInt(onlyArgs);
            world.getGame().showFPS = (v == 1);
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
    }, NEAREST("getNearest"){
        @Override
        public void execute(World world, String line) {
            Vector v = world.getController().getTarget().getCenter();
            Node n = world.getNodeProvider().getNearest(v);
            n.highlight = !n.highlight;
        }
    }, PATHTEST("pathtest"){
        @Override
        public void execute(World world, String line) {
            Vector start = world.getController().getTarget().getCenter();
            List<Vector> path = new PathFinder(world.getNodeProvider()).getPath(start, new Vector(7,7));
            if(path != null){
                world.add(new DevPath(path));
                world.add(new DevPoint(path.get(0), 0.2));
                world.add(new DevPoint(path.get(path.size() - 1), 0.2));
            } else{
                Debug.send("no path found");
            }
        }
    }, AITEST("ai [a-zA-Z]+"){
        @Override
        public void execute(World world, String line) {
            String name = Commands.separateLine(line)[0];
            if(!world.getAiMaster().contains(name)){
                Debug.send("Ai " + name +" does not exist");
                return;
            }
            Vector start = world.getController().getTarget().getCenter();
            world.getAiMaster().get(name).moveTo(start);
        }
    }
    , AISPAWN("aispawn [a-zA-Z]+" + Command.SEPARATOR + Command.POSITIVENUMBER + Command.SEPARATOR + Command.POSITIVENUMBER){
        @Override
        public void execute(World world, String line) {
            String[] args = Commands.separateLine(line);
            String name = args[0];
            Integer x = Integer.parseInt(args[1]);
            Integer y = Integer.parseInt(args[2]);
            if(world.getAiMaster().contains(name)){
                Debug.send("Ai " + name +" already not exist");
                return;
            }
            Human te = new DevDummy(new Vector(x, y), world);
            AI ai = new AI(world, te);
            world.getAiMaster().add(name, ai);
            world.add(ai);
            world.add(te);
        }
    };


    
    private String regex;
    
    Commands(String regex){
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

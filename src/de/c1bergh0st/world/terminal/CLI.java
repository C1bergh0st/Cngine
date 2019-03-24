package de.c1bergh0st.world.terminal;

import java.io.IOException;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.world.World;

public class CLI {
    private boolean notified;
    
    public CLI(){
        notified = false;
    }
    
    public void testForInput(World world){
        try {
            if(System.in.available() > 0){
                StringBuilder lineBuilder = new StringBuilder();
                while (System.in.available() > 0){
                    lineBuilder.append((char) System.in.read());
                }
                String line = lineBuilder.toString();
                line = line.replaceAll(System.lineSeparator(), "");
                line = line.replaceAll("\n", "");
                line = line.replaceAll("\r", "");
                //System.out.println("Recognized input \"" + line + "\"");
                boolean found = false;
                for(Command c : Commands.values()){
                    if(line.matches(c.getRegex())){
                        c.execute(world, line);
                        found  = true;
                        break;
                    }
                }
                if(!found && !line.matches("help")){
                    Debug.send("Cound not recognize \"" + line + "\", type \"help\" for help");
                }
                if(line.matches("help")){
                    Debug.send("Available Commands");
                    for(Command c: Commands.values()){
                        Debug.send(c.getInfo());
                    }
                }
            }
        } catch (IOException e) {
            if(!notified){
                e.printStackTrace();
                notified = true;
            }
        }
    }
    
}

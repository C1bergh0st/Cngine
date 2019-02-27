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
                String line = "";
                while (System.in.available() > 0){
                    line += (char) System.in.read();
                }
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
                if(!found){
                    Debug.send("Cound not recognize \"" + line + "\"");
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

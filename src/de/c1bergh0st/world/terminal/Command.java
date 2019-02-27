package de.c1bergh0st.world.terminal;

import de.c1bergh0st.world.World;

public interface Command {
    String SEPARATOR = ",";
    String NUMBER = "-?\\d{1,4}";
    String POSITIVENUMBER = "\\d{1,4}";
    String POSITIONSEPARATOR = ";";
    String POSITION = NUMBER + POSITIONSEPARATOR + NUMBER;
    
    String getRegex();
    
    void execute(World world, String line);
    
}

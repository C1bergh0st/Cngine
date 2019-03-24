package de.c1bergh0st.world.terminal;

import de.c1bergh0st.world.World;

interface Command {
    String SEPARATOR = ",";
    String SEP = SEPARATOR;
    String NUMBER = "-?\\d{1,4}";
    String POSITIVENUMBER = "\\d{1,4}";
    String POSITIONSEPARATOR = ";";
    String POSITION = NUMBER + POSITIONSEPARATOR + NUMBER;
    String FLOAT = "[-+]?([0-9]*\\.[0-9]+|[0-9]+)";
    
    String getRegex();

    String getInfo();
    
    void execute(World world, String line);
    
}

package de.c1bergh0st.world.terminal;

import de.c1bergh0st.debug.Debug;

import java.io.*;
import java.util.*;

public class Configuration {
    private static final String DIVIDER = "=";
    private static final String PREFERENCEDEC = ".+=.+";
    private static final int MAXLINES = 4096;
    private static Map<String, String> preferences = new HashMap<>();

    public static void loadPreferncesFromFile(File prefFile) throws IOException {
        //Make sure all Preferences are initialized
        if(preferences.isEmpty()){
            for(Preference p: Preference.values()){
                preferences.put(p.name(), p.getDefaultValue());
            }
        }
        if(!prefFile.exists()){
            throw new FileNotFoundException("The specified File did not exist " + prefFile.toString());
        }
        BufferedReader reader = new BufferedReader(new FileReader(prefFile));
        //Set of all Preferences
        Set<String> possiblePrefs = new HashSet<>();
        for(Preference p : Preference.values()){
            possiblePrefs.add(p.name());
            Debug.send("Adding Possible Pref \"" + p.name() + "\"", 3);
        }
        String line;
        for (int guard = 0; guard < MAXLINES; guard++){
            line = reader.readLine();
            if(line == null){
                break;
            }
            //get rid of whitespace
            line = line.replaceAll(" ", "");
            //Make sure it Follows the Pattern
            if (!line.matches(PREFERENCEDEC)){
                Debug.sendErr("Error while parsing Line:\"" + line + "\". Did not Conform to " + PREFERENCEDEC);
                continue;
            }
            //get the two Values
            int middle = line.indexOf("=");
            String pref = line.substring(0, middle);
            if(!possiblePrefs.contains(pref)){
                Debug.sendErr("Error while parsing Line:\"" + line + "\". Unknown Preference " + pref);
                continue;
            }
            String value = line.substring(middle + 1);
            preferences.put(pref, value);

            Debug.send(String.format("Loaded Preference %20s to %20s", pref, value));
        }
    }

    public static String getValue(Preference key){
        return preferences.get(key.name());
    }



}

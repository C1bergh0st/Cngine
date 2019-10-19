package de.c1bergh0st.main;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.ui.Window;
import de.c1bergh0st.world.terminal.Configuration;
import de.c1bergh0st.world.terminal.Preference;

import java.io.File;
import java.io.IOException;

public class MainEngine {
public static final String ABOUTURL = "https://github.com/C1bergh0st";
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		MainEngine main = new MainEngine();
		main.start();
	}
	
	public void start(){
		File config = new File("config.txt");
		try {
			Configuration.loadPreferncesFromFile(config);
		} catch (IOException e) {
			Debug.sendErr("Failed to load Config File:" + e.getMessage());
		}
		double scaling = Double.parseDouble(Configuration.getValue(Preference.D_DISPLAY_PRESCALER));
		//double scaling = 1;
		int width = (int)(Integer.parseInt(Configuration.getValue(Preference.I_DISPLAY_RES_X)) * scaling);
		int height = (int)(Integer.parseInt(Configuration.getValue(Preference.I_DISPLAY_RES_Y)) * scaling);
		new Window("Creon", width, height, Double.parseDouble(Configuration.getValue(Preference.D_DISPLAY_ZOOM)));
		//new Window("Creon",3840,2160);
	}

}

package de.c1bergh0st.main;

import de.c1bergh0st.ui.Window;

public class MainEngine {
public static final String ABOUTURL = "https://github.com/C1bergh0st";
	
	public static void main(String[] args) {
		System.setProperty("sun.java2d.opengl", "true");
		MainEngine main = new MainEngine();
		main.start();
	}
	
	public void start(){
		double scaling = 1;
		int width = (int)(1920 * scaling);
		int height = (int)(1080 * scaling);
		new Window("Creon", width, height);
		//new Window("Creon",3840,2160);
	}

}

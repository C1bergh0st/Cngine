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
		new Window("Creon",1920,1080);
	}

}

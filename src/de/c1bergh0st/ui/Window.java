package de.c1bergh0st.ui;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.debug.Util;

public class Window extends JFrame{

	private static final long serialVersionUID = 1L;
	private GamePanel gpanel;
	private Dimension size;

	public Window (String title, int width, int height, double preferredZoom){
		super(title);
		Util.PRESCALER = width / 1920d;
		Debug.send("Prescaler was determined to be: " + Util.PRESCALER, 1);
		Util.ZOOM = preferredZoom;
		Util.refreshTilesize();
		size = new Dimension(width, height);
		Util.SCREEN = size;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension size = new Dimension(width+5, height+25);	//Adjusting for Border
		try {
            setIconImage(ImageIO.read(new File("src/res/icon.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		//setUndecorated(true);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		pack();
		setResizable(false);
		setVisible(true);
        //setLocation(1920, 0);
		add(new Menu(this));
		pack();
	}
	
	public void showLayout(String type){
		if(Objects.equals(type, "Menu")){
			getContentPane().removeAll();
			add(new Menu(this));
			revalidate();
			repaint();
			gpanel = null;
		}
		else if(Objects.equals(type, "Game")){
			getContentPane().removeAll();
			gpanel = new GamePanel(this);
			add(gpanel);
			revalidate();
			repaint();
			gpanel.getMainGame().start();
		}
		else{
			Debug.sendErr("Window.showLayout(str) only accepts {Menu; Game}");
		}
	}

	public Dimension getSize(){
		return size;
	}
	
	public GamePanel getPanel(){
	    return gpanel;
	}
}

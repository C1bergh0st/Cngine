package de.c1bergh0st.ui;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.gamecode.MainGame;
import de.c1bergh0st.geometry.Vector;
import de.c1bergh0st.input.InputHandler;
import de.c1bergh0st.input.KeyStatusChange;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class GamePanel extends JPanel {
	
    private static final long serialVersionUID = 1213123131L;
    
    public Window parent;
	public MainGame maingame;
	private Map<Integer, Boolean> mousebuttons;
	    
	public GamePanel(Window parent) {
		Debug.send("EDITORPANEL");
		this.parent = parent;
		mousebuttons = new HashMap<Integer, Boolean>();
		setLayout(null);
		
		Canvas canvas = new MainGame(parent);
		maingame = (MainGame) canvas;
		maingame.setPanel(this);
		
		setCursor(false);
        setCursor(true);
		
		canvas.addMouseListener(new MouseAdapter() {
		    public void mouseMoved(MouseEvent e){
		        //NOTHING
		    }

            public void mouseDragged(MouseEvent e){
                //NOTHING
		    }
		    
			@Override
			public void mousePressed(MouseEvent e) {
				setMouseButton(e.getButton(), true);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				setMouseButton(e.getButton(), false);
			}
		});
		
		canvas.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent me) {
			    //NOTHING
			}
		});
		
        
		canvas.setBounds(0, 0, 1920, 1080);
		add(canvas);
	}
	
	private void setMouseButton(int button, boolean down){
        mousebuttons.put(button, down);
	}
	
	public boolean isMouseButtonDown(int button){
	    if(!mousebuttons.containsKey(button)){
	        return false;
	    }
	    return mousebuttons.get(button);
	}
	
	
	public void setCursor(boolean enabled){
	    if(enabled){
	        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	    } else {
	        // Transparent 16 x 16 pixel cursor image.
	        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

	        // Create a new blank cursor.
	        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
	            cursorImg, new Point(0, 0), "blank cursor");

	        // Set the blank cursor to the JFrame.
	        setCursor(blankCursor);
	    }
	}
	
	public MainGame getMainGame() {
		return maingame;
	}

	
}

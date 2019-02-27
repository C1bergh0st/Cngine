package de.c1bergh0st.input;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.ui.GamePanel;

public class InputHandler {
	public static final int LEFTCLICK = 1;
    public static final int MIDDLECLICK = 2;
    public static final int RIGHTCLICK = 3;
    public static final int SIDECLICK = 5;
    private boolean showPresses = false;
	
	
	private Map<String, Integer> map = new HashMap<String, Integer>();
	//TODO find a better way to organize these lists
	private String[] inputKeys = {"up","down","left","right","shift","use", "reload", "space"};
    private char[] inputKeyCodes = {'W','S','A','D', KeyEvent.VK_SHIFT ,'E', 'R', KeyEvent.VK_SPACE};
	private boolean[] keyStatus;
	private GamePanel source;
	private InputMap inputMap;
	private ActionMap actionMap;
	
	public InputHandler(){
	    if(inputKeys.length != inputKeyCodes.length){
	        throw new IllegalStateException("There needs to be a key assigned to every input keyword");
	    }
		keyStatus = new boolean[inputKeys.length];
		for(int i = 0; i < inputKeys.length; i++){
			map.put(inputKeys[i], i);
		}
		
		
		Debug.send("INPUTHANDLER initialized");
	}
	
	public boolean isClicked(int mousebutton){
	    return source.isMouseButtonDown(mousebutton);
	}
	
	/**
	 * Returns whether a given Key is currently being pressed
	 * @param key a String representing the pressed key
	 * @return
	 */
	public boolean isDown(String key){
	    validate();
		if(!map.containsKey(key)){
			throw new IllegalArgumentException("Key \"" + key +  "\" not supported");
		}
		return keyStatus[map.get(key)];
	}

    public void pressed(String key){
        validate();
		if(!map.containsKey(key)){
            throw new IllegalArgumentException("Key \"" + key +  "\" not supported");
		}
		keyStatus[map.get(key)] = true;
		logStatus(key,true);
	}

	public void released(String key){
        validate();
		if(!map.containsKey(key)){
            throw new IllegalArgumentException("Key \"" + key +  "\" not supported");
		}
		keyStatus[map.get(key)] = false;
		logStatus(key,false);
	}
	
	public void setSource(GamePanel panel){
        if(panel == null){
            throw new NullPointerException();
        }
        source = panel;
        inputMap = source.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
        actionMap = source.getActionMap();
        registerAll();
        /*
        register(KeyEvent.VK_S, "down");
        register(KeyEvent.VK_A, "left");
        register(KeyEvent.VK_W, "up");
        register(KeyEvent.VK_D, "right");
        register(KeyEvent.VK_SHIFT, "shift");
        register(KeyEvent.VK_E, "use");*/
    }
    
    private void registerAll() {
        for(int i = 0; i < inputKeys.length; i++){
            register((int) inputKeyCodes[i], inputKeys[i]);
        }
    }


    private void register(int key, String identifier){
        String pressedId = identifier + "pressed";
        List<KeyStroke> strokes = getKeyStrokes(key, true);
        for(KeyStroke k : strokes){
            inputMap.put(k, pressedId);
        }
        actionMap.put(pressedId, new KeyStatusChange(this, identifier, true));
        
        
        String releasedId = identifier + "released";
        strokes = getKeyStrokes(key, false);
        for(KeyStroke k : strokes){
            inputMap.put(k, releasedId);
        }
        actionMap.put(releasedId, new KeyStatusChange(this, identifier, false));
        
    }
	
	private List<KeyStroke> getKeyStrokes(int key, boolean pressed){
	    List<KeyStroke> result = new LinkedList<KeyStroke>();
	    if(key == KeyEvent.VK_SHIFT){
	        result.add(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT, InputEvent.SHIFT_DOWN_MASK, !pressed));
	    } else {
	        result.add(KeyStroke.getKeyStroke(key, 0, !pressed));
            result.add(KeyStroke.getKeyStroke(key, InputEvent.SHIFT_DOWN_MASK, !pressed));
	    }
	    return result;
	}
	
	private void logStatus(String key, boolean pressed) {
		if(showPresses){
			if(pressed){
				Debug.send("Key: "+key+" was pressed");
			}
			else{
				Debug.send("Key: "+key+" was released");
			}
		}
	}

    private void validate() {
        if(source == null || inputMap == null || actionMap == null){
            throw new IllegalStateException("No Source has been set for this InputHandler");
        }
    }
	
}

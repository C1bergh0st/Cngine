package de.c1bergh0st.input;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.*;

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
	
	
	private Map<String, Integer> map = new HashMap<>();
	//TODO find a better way to organize these lists
	private List<String> inputKeys = new ArrayList<>();
    private List<Character> inputKeyCodes = new ArrayList<>();
	private boolean[] keyStatus;
	private GamePanel source;
	private InputMap inputMap;
	private ActionMap actionMap;
	
	public InputHandler(){
		load();
	    if(inputKeys.size() != inputKeyCodes.size()){
	        throw new IllegalStateException("There needs to be a key assigned to every input keyword");
	    }
		keyStatus = new boolean[inputKeys.size()];
		for(int i = 0; i < inputKeys.size(); i++){
			map.put(inputKeys.get(i), i);
		}
		
		
		Debug.send("INPUTHANDLER initialized");
	}

	private void load(){
		add("up",'W');
		add("down",'S');
		add("left",'A');
		add("right",'D');
		add("shift",(char)KeyEvent.VK_SHIFT);
		add("use",'E');
		add("reload",'R');
		add("space",(char)KeyEvent.VK_SPACE);
		//Adds numbers one to ten
		for(int i = 0; i < 10; i++){
			add(Integer.toString(i),(char)(48+i));
		}
	}

	private void add(String key, char keyCode){
		inputKeys.add(key);
		inputKeyCodes.add(keyCode);
	}
	
	public boolean isClicked(int mousebutton){
	    return source.isMouseButtonDown(mousebutton);
	}
	
	/**
	 * Returns whether a given Key is currently being pressed
	 * @param key a String representing the pressed key
	 * @return true if the key is down
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
        for(int i = 0; i < inputKeys.size(); i++){
            register((int) inputKeyCodes.get(i), inputKeys.get(i));
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
	    List<KeyStroke> result = new LinkedList<>();
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

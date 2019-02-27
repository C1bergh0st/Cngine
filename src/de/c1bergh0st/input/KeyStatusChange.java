package de.c1bergh0st.input;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class KeyStatusChange extends AbstractAction{
    private static final long serialVersionUID = 614539850559369699L;
    
    private InputHandler handler;
	private String key;
	private boolean pressed;
	
	public KeyStatusChange(InputHandler handler, String key, boolean pressed){
		this.handler = handler;
		this.key = key;
		this.pressed = pressed;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(pressed){
			handler.pressed(key);
		}
		else{
			handler.released(key);
		}
	}

}

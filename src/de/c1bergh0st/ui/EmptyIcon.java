package de.c1bergh0st.ui;

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.Icon;

public final class EmptyIcon implements Icon {

	  private int width;
	  private int height;
	  
	  public EmptyIcon() {
	    this(0, 0);
	  }
	  
	  public EmptyIcon(int width, int height) {
	    this.width = width;
	    this.height = height;
	  }

	  public int getIconHeight() {
	    return height;
	  }

	  public int getIconWidth() {
	    return width;
	  }

	@Override
	public void paintIcon(Component arg0, Graphics arg1, int arg2, int arg3) {
		//Intentionally empty because an EmptyIcon should not be drawn
	}

	}
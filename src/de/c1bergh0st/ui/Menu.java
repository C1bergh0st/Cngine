package de.c1bergh0st.ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import de.c1bergh0st.game.image.Statics;

public class Menu extends JPanel {
    private static final long serialVersionUID = -6541116115665319636L;
    @SuppressWarnings("unused")
    private Window parent;
	/**
	 * Create the panel.
	 */
	public Menu(Window parent) {
		this.parent = parent;
		ImageIcon image = null;
		try {
			image = new ImageIcon(ImageIO.read(Menu.class.getResourceAsStream("/res/MenuBackdrop.png")));
		} catch (IOException e) {
		    //Just make sure the menu loads, looks are optional
            image = new ImageIcon(Statics.getErrorImage(1920, 1080));
		}
		setLayout(null);
		
		JPanel middle = new JPanel();
		middle.setBounds(640, 0, 640, 1080);
		add(middle);
		middle.setBackground(new Color(0,0,0,0));
		middle.setLayout(null);
		
		JButton button1 = new JButton("Start");
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				parent.showLayout("Game");
			}
		});
		button1.setEnabled(false);
		button1.setLocation(200, 500);
		button1.setSize(240, 60);
		button1.setBackground(new Color(59, 89, 182));
		button1.setForeground(Color.WHITE);
		button1.setFocusPainted(false);
		button1.setFont(new Font("Tahoma", Font.BOLD, 18));//http://answers.yahoo.com/question/index?qid=20070906133202AAOvnIP
        
		middle.add(button1);
		
		JButton button3 = new JButton("About");
		button3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				openWebpage(de.c1bergh0st.main.MainEngine.ABOUTURL);
			}
		});
		button3.setForeground(Color.WHITE);
		button3.setFont(new Font("Tahoma", Font.BOLD, 18));
		button3.setFocusPainted(false);
		button3.setEnabled(false);
		button3.setBackground(new Color(59, 89, 182));
		button3.setBounds(200, 600, 240, 60);
		middle.add(button3);
		
		JButton button4 = new JButton("Exit");
		button4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/**
				 * DONT EVEN ASK ME ABOUT THIS SYNTAX
				 */
				int n = JOptionPane.showConfirmDialog(parent, "Do you want to quit the game?", "", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, (Icon)new EmptyIcon());
				if(n == 0){
					System.exit(1);
				}
			}
		});
		button4.setForeground(Color.WHITE);
		button4.setFont(new Font("Tahoma", Font.BOLD, 18));
		button4.setFocusPainted(false);
		button4.setEnabled(false);
		button4.setBackground(new Color(59, 89, 182));
		button4.setBounds(200, 700, 240, 60);
		middle.add(button4);
		
		JLabel label = new JLabel("", image, JLabel.CENTER);
		label.setBackground(Color.BLACK);
		label.setBounds(0, 0, 1920, 1080);
		
		JPanel background = new JPanel();
		background.setBounds(0, 0, 1920, 1080);
		background.setLayout(null);
		background.add(label );
		add(background);

	}
	
	public static void openWebpage(String urlString) {
	    try {
	        Desktop.getDesktop().browse(new URL(urlString).toURI());
	    } catch (Exception e) {
	        e.printStackTrace();
		}
	}
}

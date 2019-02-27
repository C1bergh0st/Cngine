package de.c1bergh0st.gamecode;

import java.awt.*;
import java.awt.image.BufferStrategy;
import de.c1bergh0st.audio.AudioController;
import de.c1bergh0st.debug.Debug;
import de.c1bergh0st.game.image.ImageProvider;
import de.c1bergh0st.game.image.Statics;
import de.c1bergh0st.input.InputHandler;
import de.c1bergh0st.ui.GamePanel;
import de.c1bergh0st.ui.Window;
import de.c1bergh0st.world.World;
import de.c1bergh0st.world.loader.DevLoader;
import de.c1bergh0st.world.loader.WorldLoader;

public class MainGame extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	//Config Constants
	public static final int TICKSPEED = 120; //Ticks per Second
	private static final double NSPERTICK = 1000000000D / TICKSPEED;
	//private static final Color BACKGROUNDCOLOR = new Color(79,194,232);
	private static final Color BACKGROUNDCOLOR = new Color(242,242,242);
	
	//Low level required Objects
	public Window parent;
	public Thread tickThread;
    public InputHandler input;
    private GamePanel gamepanel;
	
	//Lower level varaiables
	public boolean shouldRun;
	public boolean showFPS;
	private long frames;
	private double fps;
	private double ticks;
	private double tps;
    @SuppressWarnings("unused")
	private double allticks;
	
	//Higher Level stuff like Sound / Audio
	@SuppressWarnings("unused")
    private ImageProvider textures;
    @SuppressWarnings("unused")
	private AudioController audio;
	
	public World world;
	private WorldLoader loader;

	public MainGame(Window p){
		input = new InputHandler();
		shouldRun = true;
		showFPS = true;
		parent = p;
		textures = Statics.getImageProvider();
		audio = new AudioController();
		world = new World(this);
		new DevLoader().load(world);
	}
	
	public void load(WorldLoader loader){
	    this.loader = loader;
	}
	
	public void start(){
		tickThread = new Thread(this,"TickThread");
		tickThread.start();
        input.setSource(parent.getPanel());
	}

	public void run() {
		init();
		//Nanoseconds per Tick
		frames = 0;
		ticks = 0;
		long fpsTimer = System.currentTimeMillis();
		double delta = 0;
		double lastTime = System.nanoTime();
		Debug.send("Mainloop startet at:"+System.nanoTime());
		while(shouldRun){
			long now = System.nanoTime();
 			delta += (now - lastTime) / NSPERTICK;
 			lastTime = now;
 			while(delta >= 1 ){
 				ticks++;
 				allticks++;
	 			tick();
 				delta -= 1;
 			}
 			render();
 			if (fpsTimer < System.currentTimeMillis() - 1000){
 				fpsTimer = System.currentTimeMillis();
 				secondTick();
 			}
 			if(loader != null){
 			    world = new World(this);
 			    loader.load(world);
 			    loader = null;
 			}
		}
	}
	
	private void init() {
		drawLoadingScreen();
		
	}

	private void drawLoadingScreen() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null){
			createBufferStrategy(2);
			bs = getBufferStrategy();
		}
		Graphics g = bs.getDrawGraphics();
		applySettings(g);
		g.dispose();
		bs.show();
		Debug.send("Loadingscreen shown at"+System.nanoTime());
	}

	private void secondTick() {
		tps = ticks;
		ticks = 0;
		fps = frames;
		frames = 0;
		world.secondTick();
	}

	private void tick() {
	    world.tick();
	}

	public void render(){
		frames++;
		BufferStrategy bs = getBufferStrategy();
		if (bs == null){
			createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();

	    ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		applySettings(g);
		//▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼▼
		
		//g.drawImage(textures.getImage("tiles/dev.png"), 100, 100, 100, 100, null);
		world.draw(g);
		
		//▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲▲
		if(showFPS){
	        drawFPS(g);
		}
		g.dispose();
		bs.show();
	}

	private void applySettings(Graphics g) {
		g.setColor(BACKGROUNDCOLOR);
		g.fillRect(0, 0, getWidth(), getHeight()); 
	}

	private void drawFPS(Graphics g){
		g.setColor(Color.BLACK);
		g.drawString("FPS: "+ (int)fps, 5, 20);
		g.drawString("TPS: "+ (int)tps, 5, 45);
	}
	
	public void setPanel(GamePanel p){
	    gamepanel = p;
	}
	
	public GamePanel getPanel(){
        return gamepanel;
	}
	
	
	public InputHandler getInputHandler(){
		return input;
	}
}

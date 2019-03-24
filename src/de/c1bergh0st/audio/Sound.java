package de.c1bergh0st.audio;

import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private Clip clip;
	private AudioInputStream ais;
	private int id;
	@SuppressWarnings("unused")
    private String path;
	private boolean terminated;
	
	/**
	 * Constructs a new Sound
	 * @param path The path to the SoundFile
	 * @param id a (hopefully) unique id for this Sound
	 * @param looping whether this Sound should loop or not
	 */
	public Sound(String path, int id, boolean looping){
		this.path = path;
		this.id = id;
        terminated = false;
		try {
            clip = AudioSystem.getClip();
            ais = AudioSystem.getAudioInputStream(Sound.class.getResourceAsStream(path));
            clip.open(ais);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException  e) {
            //TODO Exception-Handling
            throw new IllegalStateException(e.getMessage());
        }
		if(looping){
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	/**
	 * @return the id assigned to this Sound
	 */
	public int getId(){
	    return id;
	}
	
	/**
	 * Starts playing the Sound
	 */
	public void play(){
	    if(terminated){
            throw new IllegalStateException("This Sound was already terminated");
        }
	    clip.start();
	}
	
	/**
	 * Stops playing the Sound
	 */
	public void stop(){
	    if(terminated){
	        throw new IllegalStateException("This Sound was already terminated");
	    }
	    clip.stop();
	}
	
	/**
	 * Determines whether this Sound has finished playback.
	 * !Will always return true if this Sound is set to loop!
	 * @return true if the sound has finished playback
	 */
	public boolean isFinished(){
		return !clip.isRunning();
	}
	
	/**
	 * Stops playing the sound and terminates all Resources associated with this Object
	 */
	public void terminate(){
		stop();
		try {
			ais.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		terminated = true;
	}
	
	/**
	 * @return whether this Sound has been Terminated
	 */
	public boolean isTerminated(){
	    return terminated;
	}
	
	
}

package common;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 * Used to play sounds and music.
 */
public class Sound {

	private AudioClip audioClip;

	/**
	 * Creates a new instance of a Sound from the given fileName.
	 * 
	 * @param fileName the name of the file in .wav format.
	 */
	public Sound(String fileName) {
		URL url = Sound.class.getClassLoader().getResource(fileName);
		if (url == null) {
			throw new IllegalArgumentException("Could not find the sound file specified");
		}
		audioClip = Applet.newAudioClip(url);
	}

	/**
	 * Plays this sound. If this sound is already being played, it will be restarted
	 * from the beginning.
	 */
	public void play() {
		audioClip.play();
	}

	/**
	 * Makes this sound repeat in a loop.
	 */
	public void loop() {
		audioClip.loop();
	}

	/**
	 * Stops playing this sound.
	 */
	public void stop() {
		audioClip.stop();
	}

}

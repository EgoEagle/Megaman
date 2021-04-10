import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound implements Runnable {
	public static boolean musicflag = true;
	private Clip clip;

	public Sound(String fileName) {
		// specify the sound to play
		// (assuming the sound can be played by the audio system)
		// from a wave File

		try {

			AudioInputStream sound = AudioSystem.getAudioInputStream(NewView.class.getResource(fileName));

			// load the sound into memory (a Clip)
			clip = AudioSystem.getClip();
			clip.open(sound);

		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Malformed URL: " + e);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Unsupported Audio File: " + e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Input/Output Error: " + e);
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			throw new RuntimeException("Sound: Line Unavailable Exception Error: " + e);
		}

		// play, stop, loop the sound clip
	}

	public void play() {
		clip.setFramePosition(0); // Must always rewind!
		clip.start();
	}

	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {

		clip.stop();
		clip.close();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		play();
		while (musicflag) {

			loop();
		}
		stop();
	}
}
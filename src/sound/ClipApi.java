package sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

import sun.reflect.ReflectionFactory.GetReflectionFactoryAction;

public class ClipApi {
	
	public static Mixer mixer;
	public static Clip clip;
	
	public static void main(String[] args) {
		
		//Getting audio system ready
		Mixer.Info[] mixerInfoArray =  AudioSystem.getMixerInfo();
		for (Mixer.Info info : mixerInfoArray) {
			System.out.println(info);
			
		}
		mixer = AudioSystem.getMixer(mixerInfoArray[0]);
		DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, null);
		try {
			clip =  (Clip) mixer.getLine(dataLineInfo);
		}catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		
		//Getting Audio clip ready
		try {
			URL soundURL = ClipApi.class.getResource("/sound/music.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
			clip.open(audioStream);
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clip.start();
		
		//To prevent Java ending application before audio finish playing
		do {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}while(clip.isActive());
	}

}

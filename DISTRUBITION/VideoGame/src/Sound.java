import java.io.File;
import javax.sound.sampled.*;

	public class Sound {
		
		private Clip clip;
		private String fileName;
		private File soundFile;
		private AudioInputStream audioIn;
		
		public Sound(String f) {
			fileName = f;
		}
		
		public void play()
		{
			try {
		         // Open an audio input stream.           
		          soundFile = new File(fileName);//you could also get the sound file with an URL
		          audioIn = AudioSystem.getAudioInputStream(soundFile);              
		         // Get a sound clip resource.
		         
		         clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         
		         clip.start();
		      } catch(Exception e)
			{}
		}
		
		public void stop()
		{
			clip.stop();
		}
		
	
}

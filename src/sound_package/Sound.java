package sound_package;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound 
{
	//variables
	private String url;
	
	public Sound(String url)
	{
		this.setUrl(url);
	}

	public synchronized void playSound() {

		try 
		{
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(Sound.class.getResource("/sounds/" + url));
			clip.open(inputStream);
			clip.start(); 
	    } 
		catch (Exception e) 
		{
	      System.err.println(e.getMessage());
	    }
	  }

	//----ACCESS----\\
	
	public String getUrl() 
	{
		return url;
	}

	public void setUrl(String url) 
	{
		this.url = url;
	}
		
}

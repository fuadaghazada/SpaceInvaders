package game_package;

import java.awt.Image;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
	This class will be  used to load the explosion animation images
*/
public class ExplosionImageLoader 
{
	//constants
	private static final int SIZE = 16;
	
	//properties
	private ArrayList<Image> images;
	
	//default constructor
	public ExplosionImageLoader()
	{
		images = new ArrayList<>();
	}
	
	//load images
	public void loadImages()
	{
		for(int i = 0; i < SIZE; i++)
		{
			images.add(new ImageIcon(Animation.class.getResource("/images/animation_images/explosion/explode" + i + ".png")).getImage());
		}
	}
	
	//access the image list
	public ArrayList<Image> getImages()
	{
		return images;
	}
}

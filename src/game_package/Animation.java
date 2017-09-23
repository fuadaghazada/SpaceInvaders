package game_package;

import java.awt.Image;
import java.util.ArrayList;

/**
	This class will create animation for the image lists
*/
public class Animation
{
	//properties
	private boolean isContinious;
	
	private int size;
	private ArrayList<Image> images;
	private Image currentImage;
	private int counter;
	
	//constructor
	public Animation(ArrayList<Image> images, boolean isContinious)
	{
		counter = 0;
		
		this.images = images;
		this.isContinious = isContinious;
		this.size = images.size();
	}
	
	//to update all the images in the list
	public void updateImages()
	{
		currentImage = images.get(counter);
		
		if(isContinious)
		{
			if(counter < size - 1)
				counter++;
			else
				counter = 0;
		}
		else
		{
			if(counter < size - 1)
				counter++;
		}
		
	}
	
	//---ACCESS-&-MUTATE---\\
	
	public Image getCurrentImage()
	{
		return currentImage;
	}
	
	public void setCurrentImage(Image currentImage)
	{
		this.currentImage = currentImage;
	}
	
	public int getCounter()
	{
		return counter;
	}
	
	public ArrayList<Image> getImgList()
	{
		return images;
	}
	
	public void setContinuity(boolean b)
	{
		this.isContinious = b;
	}
}

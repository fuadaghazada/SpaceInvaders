package game_package;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

/**
 	This class will represent the background and update it.
 	
 	@author Fuad Aghazada
	@date 23/07/2017
	@version 1.1	
  */
public class Background 
{
	//properties
	private int x, speed;
	private Image image1;
	private Image image2;
	private static int y;
	
	//constructor
	public Background()
	{
		image1 = new ImageIcon(Background.class.getResource("/images/backgrounds/space3.png")).getImage();
		image2 = new ImageIcon(Background.class.getResource("/images/backgrounds/space3.png")).getImage();
		
		this.setSpeed(1);
		
		this.setX(0);
		this.setY(0);
	}
	
    //draw/paint the background
    public void draw(Graphics g)
    {
        g.drawImage(image1, x, y, null);
        g.drawImage(image2, x, y - GameFrame.getFrameHeight(), null);
    }
    
    //to update the background so that it moves continuously during the game play
    public void update()
    {
        if(y == GameFrame.getFrameHeight())
        {
            this.setY(0);
        }
        this.setY(y += speed);
    }

	//ACCESS - MUTATE
	
	public int getX() 
	{
		return x;
	}

	public void setX(int x) 
	{
		this.x = x;
	}

	public static int getY() 
	{
		return y;
	}

	public void setY(int ny) 
	{
		y = ny;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
}

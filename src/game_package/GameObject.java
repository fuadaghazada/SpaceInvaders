package game_package;

import java.awt.Graphics2D;

public abstract class GameObject 
{
	//properties
	protected float posX, posY;
	protected static int width, height;
	protected int speed;
	
	//to point
	public abstract void draw(Graphics2D g, GamePanel gp);
	
	//ACCESS -- SET\\
	
	public float getPosX()
	{
		return posX;
	}
	
	public void setPosX(float posX)
	{
		this.posX = posX;
	}
	
	public float getPosY()
	{
		return posY;
	}
	
	public void setPosY(float posY)
	{
		this.posY = posY;
	}
	
	public static int getWidth()
	{
		return width;
	}
	
	public void setWidth(int newWidth)
	{
		width = newWidth;
	}
	
	public static int getHeight()
	{
		return height;
	}
	
	public void setHeight(int newHeight)
	{
		height = newHeight;
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

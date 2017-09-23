package game_package;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.ImageIcon;

/**
	This class will display and customize the bullet
*/
public class Bullet extends GameObject
{
	//Constants
	private static final int HEIGHT = 30;
	private static final int WIDTH = 15;
	
	//Variables
	private static int damage;
	private ImageIcon img;
	private int type; //0 for ship/ 1 enemy
	
	//default constructor
	public Bullet()
	{
		this.setSpeed(3);
		this.setType(1);
		setDamage(50);
	}
	
	//Constructor with parameters
	public Bullet(float posX, float posY)
	{
		this.posX = posX;
		this.posY = posY;
		this.setSpeed(7);
		setDamage(50);
	}
	
	//to draw the bullet
	public void draw(Graphics2D g, GamePanel gp)
	{
		if(type == 0)
			img = new ImageIcon(Bullet.class.getResource("/images/game_elements/bullet.png"));
		else if (type == 1)
			img = new ImageIcon(Bullet.class.getResource("/images/game_elements/enemy-bullet.png"));
		
		img.paintIcon(gp, (Graphics)g, (int)posX, (int)posY);
		
	}
	
	//to move
	public void move(int steps)
	{
		this.setPosY(this.posY + speed * steps);	
	}
	
	//---ACCESS---\\
	
	public static int getHeight() 
	{
		return HEIGHT;
	}
	public static int getWidth() 
	{
		return WIDTH;
	}

	public static int getDamage() 
	{
		return damage;
	}

	public static void setDamage(int ndamage) 
	{
		damage = ndamage;
	}

	public int getType() 
	{
		return type;
	}

	public void setType(int type) 
	{
		this.type = type;
	}
}

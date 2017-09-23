package game_package;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
	This class will display and customize the enemy objects
*/
public class Enemy extends GameObject
{
	//Constants
	private static final int HEIGHT = 40;
	private static final int WIDTH = 40;
	
	//Variables
	private int health;
	private int dir;
	private ImageIcon img;
	
	//Constructs
	public Enemy(float posX, float posY)
	{
		this.posX = posX;
		this.posY = posY;
		this.setSpeed(1);
		this.setHealth(100);
		this.setDir(1);
	}
	
	@Override
	public void draw(Graphics2D g, GamePanel gp)
	{		
		img = new ImageIcon(Enemy.class.getResource("/images/game_elements/enemy.png"));
		img.paintIcon(gp, (Graphics)g, (int)posX, (int)posY);
	}
	
	//to check whether the enemy is dead
	public boolean isShot(Bullet bullet)
	{
		Rectangle bulletRect = new Rectangle((int)bullet.getPosX(), (int)bullet.getPosY(), Bullet.getWidth(), Bullet.getHeight());
		Rectangle enemyRect = new Rectangle((int)this.getPosX(), (int)this.getPosY(), getWidth(), getHeight());
		
		return enemyRect.intersects(bulletRect);
	}
	
	//to move the enemies
	public void move()
	{
		this.setPosX(posX + dir * speed);
	}
	
	//---ACCESS-&-MUTATE---\\
	
	public static int getHeight() 
	{
		return HEIGHT;
	}

	public static int getWidth() 
	{
		return WIDTH;
	}
	
	public void setDir(int dir)
	{
		this.dir = dir;
	}
	
	public int getDir()
	{
		return dir;
	}
	
	public int getHealth() 
	{
		return health;
	}

	public void setHealth(int health) 
	{
		this.health = health;
	}	
}

package game_package;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
	This class will display and customize the space ship
*/
public class Spaceship extends GameObject
{
	//constants
	private static final int HEIGHT = 50;
	private static final int WIDTH = 80;
	private static final int GAP = 100;
	
	//variables
	private ImageIcon img;
	
	//Constructs the ship
	public Spaceship()
	{
		this.setDefaultPosition();
		this.setSpeed(3);
	}
	
	@Override
	public void draw(Graphics2D g, GamePanel gp)
	{
		img = new ImageIcon(Spaceship.class.getResource("/images/game_elements/ship.png"));
		img.paintIcon(gp, (Graphics)g, (int)posX, (int)posY);
	}
	
	//to set the position of the ship
	public void setDefaultPosition()
	{
		this.setPosX(GameFrame.getFrameWidth()/2 - WIDTH/2);
		this.setPosY(GameFrame.getFrameHeight() - GAP);
	}
	
	//to move the ship (only horizontally)
	public void move(int steps)
	{
		if(this.posX >= 0)
			this.setPosX(this.posX + speed * steps);
		else
			this.setPosX(this.posX + WIDTH/2);
		
		if(this.posX + WIDTH <= GameFrame.getFrameWidth())
			this.setPosX(this.posX + speed * steps);
		else
			this.setPosX(this.posX - WIDTH/2);
	}
	
	//to shoot by the ship
	public void shoot(Bullet bullet)
	{
		bullet.move(-1);
	}
	
	//to check whether the ship is dead by the enemy itself (touching)
	public boolean isShot(Enemy enemy)
	{
		Rectangle shipRect = new Rectangle((int)this.getPosX(), (int)this.getPosY(), getWidth(), getHeight());
		Rectangle enemyRect = new Rectangle((int)enemy.getPosX(), (int)enemy.getPosY(), Enemy.getWidth(), Enemy.getHeight());
		
		return enemyRect.intersects(shipRect);
	}
	
	//to check whether the ship is dead by the enemy bullet
	public boolean isShot(Bullet bullet)
	{
		Rectangle shipRect = new Rectangle((int)this.getPosX(), (int)this.getPosY(), getWidth(), getHeight());
		Rectangle bulletRect = new Rectangle((int)bullet.getPosX(), (int)bullet.getPosY(), Bullet.getWidth(), Bullet.getHeight());
		
		return bulletRect.intersects(shipRect);
	}

	//size of the space ship
	public static int getHeight() 
	{
		return HEIGHT;
	}

	public static int getWidth() 
	{
		return WIDTH;
	}
}

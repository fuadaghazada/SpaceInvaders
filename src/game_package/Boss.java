package game_package;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
	This class will display and customize the boss enemy
*/
public class Boss extends Enemy
{
	//Constants
	private static final int BHEIGHT = 250;
	private static final int BWIDTH = 250;
	private static final int MAX_HEALTH = 10000;
	
	//image
	private ImageIcon img;
	
	//constructor
	public Boss(float posX, float posY) 
	{
		super(posX, posY);
		this.setHealth(MAX_HEALTH);
		this.setSpeed(1);
	}
	
	@Override
	public void move()
	{
		if(this.getPosX() <= 0 || this.getPosX() + BWIDTH >= GameFrame.getFrameWidth())
			this.setDir(this.getDir() * (-1));
		
		this.setPosX(this.getPosX() + this.getDir() * this.getSpeed());			
	}
	
	@Override
	public void draw(Graphics2D g, GamePanel gp)
	{
		//health bar
		if(this.getHealth() >= 2000)
			g.setColor(Color.GREEN);
		else
			g.setColor(Color.RED);
		g.drawRect(10, 10, MAX_HEALTH/100, 30);
		g.fillRect(10, 10, this.getHealth()/100, 30);
		
		img = new ImageIcon(Enemy.class.getResource("/images/game_elements/boss.png"));
		
		img.paintIcon(gp, (Graphics)g, (int)this.getPosX(), (int)this.getPosY() + 50);
	}
	
	@Override
	public boolean isShot(Bullet bullet)
	{
		Rectangle bulletRect = new Rectangle((int)bullet.getPosX(), (int)bullet.getPosY(), Bullet.getWidth(), Bullet.getHeight());
		Rectangle enemyRect = new Rectangle((int)this.getPosX(), (int)this.getPosY(), BWIDTH, BHEIGHT);
		
		return enemyRect.intersects(bulletRect);
	}

	
	//---ACCESS---\\
	
	public static int getHeight() 
	{
		return BHEIGHT;
	}

	public static int getWidth() 
	{
		return BWIDTH;
	}

}

package game_package;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import sound_package.Sound;

/**
	This class will initialize and draw the game elements and will provide UI
	
	@author Fuad Aghazada
	@date 23/07/2017
	@version 1.1
*/
public class GamePanel extends JPanel implements KeyListener, ActionListener
{
	//serial ID
	private static final long serialVersionUID = 1L;
	
	//image & animation staff
	private ExplosionImageLoader explosion_images;
	private Animation game_animation;
	private Background background;
	
	//Game elements 
	private Spaceship ship;
	private ArrayList<Bullet> bullets;
	private ArrayList<Bullet> enemy_bullets;
	private EnemyGenerator eg;
	private Enemy randomEnemy;
	
	//Variables
	private boolean leftPressed, rightPressed, upPressed, downPressed, spacePressed, enterPressed;
	private Timer timer, animation_timer, menu_timer;
	private int score;
	private int delay;
	
	//sounds
	private Sound shoot_sound, won_sound, lost_sound, level_sound, enemy_explode_sound, enemy_bullet, boss_level_sound, ship_explode_sound;
	private int time;
	
	//for test
	private int level;
	private static int enemy_bullet_chance;
	
	//game state properties
	private boolean menu, start, isWon, isLost, pause;
	private MenuState menuState;
	
	
	//Construct the panel for the game
	public GamePanel()
	{
		this.init();
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		
		this.timer.start();
		this.animation_timer.start();
		this.menu_timer.start();
	}
	
	//initialize the elements
	public void init()
	{
		//level
		level = 1;
		enemy_bullet_chance = 50;
		score = 0;
		time = 1;
		
		//spaceship
		ship = new Spaceship();
		
		//bullets
		bullets = new ArrayList<>();
		enemy_bullets = new ArrayList<>();
		
		//enemies
		eg = new EnemyGenerator(level);
		randomEnemy = eg.randEnemy();
		
		//timer stuff
		this.setDelay(10);
		timer = new Timer(delay, this);
		animation_timer = new Timer(50, new AnimationListener());
		menu_timer = new Timer(100, new MenuListener());
		
		background = new Background();
		
		//sounds
		shoot_sound = new Sound("shoot.wav");
		won_sound = new Sound("win.wav");
		lost_sound = new Sound("lost.wav");
		level_sound = new Sound("level_up.wav");
		enemy_explode_sound = new Sound("boom.wav");
		enemy_bullet = new Sound("rocket_sound.wav");
		boss_level_sound = new Sound("bossLevel.wav");
		ship_explode_sound = new Sound("ship_explode.wav");
		
		//image/animation stuff
		explosion_images = new ExplosionImageLoader();
		explosion_images.loadImages();
		game_animation = new Animation(explosion_images.getImages(), false);
		
		//buttons
		setLeftPressed(false);
		setRightPressed(false);
		setUpPressed(false);
		setDownPressed(false);

		setSpacePressed(false);
		setEnterPressed(false);
		
		//game state
		isWon = false;
		isLost = false;
		start = false;
		menu = true;
		pause = false;
		
		menuState = new MenuState();
	}
	
	//to draw thing/elements
	public void paint(Graphics g)
	{
		//background
		background.draw(g);
		
		if(menu && !pause)
		{
			menuState.draw(g);
		}
		else
		{
			draw(g);
		}
	
		
		g.dispose();
		
	}
	
	//game play drawings
	private void draw(Graphics g)
	{
		//spaceship
		if(!isLost)
		{
			ship.draw((Graphics2D)g, this);
		}
		else
		{		
			g.drawImage(game_animation.getCurrentImage(), (int)ship.posX, (int)ship.posY, null);
		}
			
		//bullets
		for(Bullet bullet : bullets)
		{
			bullet.draw((Graphics2D)g, this);
		}
		
		for(Bullet bullet : enemy_bullets)
		{
			bullet.draw((Graphics2D)g, this);
		}
		
		//enemies
		eg.drawEnemies((Graphics2D)g, this);		
		
		//score label
		g.setColor(Color.WHITE);
		g.drawString("Score: " + score, GameFrame.getFrameWidth() - 80, 30);
		
		//level label
		if(level != eg.getBossLevel())
		{
			g.setColor(Color.WHITE);
			g.drawString("Level " + level, GameFrame.getFrameWidth()/2 - 20, 30);
		}
		else
		{
			g.setColor(Color.WHITE);
			g.drawString("Boss Level ", GameFrame.getFrameWidth()/2 - 20, 30);
		}
		
		//start screen
		if(!start && !isWon && !isLost)
		{
			g.drawImage(new ImageIcon(GamePanel.class.getResource("/images/game_states/start.png")).getImage(), GameFrame.getFrameWidth()/2 - 100, GameFrame.getFrameHeight()/2 - 10, null);
		}
		
		//win / lose screen
		if(isWon)
		{
			g.setColor(Color.WHITE);
			g.drawImage(new ImageIcon(GamePanel.class.getResource("/images/game_states/won.png")).getImage(), GameFrame.getFrameWidth()/2 - 70, GameFrame.getFrameHeight()/2 - 50, null);
			g.drawString("Press enter for main menu", GameFrame.getFrameWidth()/2 - 75, GameFrame.getFrameHeight()/2 + 50);
		}
		
		if(isLost)
		{
			g.setColor(Color.WHITE);
			g.drawImage(new ImageIcon(GamePanel.class.getResource("/images/game_states/game_over.png")).getImage(), GameFrame.getFrameWidth()/2 - 100, GameFrame.getFrameHeight()/2 - 10, null);
			g.drawString("Press enter for main menu", GameFrame.getFrameWidth()/2 - 75, GameFrame.getFrameHeight()/2 + 80);
		}
	}
	
	//to control the ship
	public void controllShip()
	{
		if(rightPressed && !isLost)
			ship.move(1);
		
		if(leftPressed && !isLost)
			ship.move(-1);
		
		if(spacePressed)
		{
			if(bullets.size() != 0)
			{
				if(bullets.get(bullets.size()-1).getPosY() <= 500)
					bullets.add(new Bullet(ship.getPosX() + Spaceship.getWidth()/2 - Bullet.getWidth()/2, ship.getPosY()));
			}
			else
			{
				bullets.add(new Bullet(ship.getPosX() + Spaceship.getWidth()/2 - Bullet.getWidth()/2, ship.getPosY()));
			}
		}
	}
		
	//to move all different bullets
	public void moveBullets()
	{
		if(start)
		{
			//to move the bullets when the space is pressed
			for(Bullet bullet : bullets)
			{
				ship.shoot(bullet);
			}
					
			//moving the bullets coming from the enemies
			for(Bullet bullet : enemy_bullets)
			{
				if(!isLost && !isWon && !menu)
					bullet.move(1);
			}
		}
	}
	
	//to move the background
	public void updateBackground()
	{
		if(!isLost)
		{
			background.update();
		}
	}
	
	//to clean the array list
	public void cleanBulletList()
	{
		for(int i = 0; i < bullets.size(); i++)
		{
			if(bullets.get(i).getPosY() <= 0 || isWon || isLost)
			{
				bullets.remove(i);
				
				if(!isWon && !isLost && EnemyGenerator.getEnemyList().size() > 0)
					this.updateRandomEnemy();
			}
		}
		
		for(int i = 0; i < enemy_bullets.size(); i++)
			
		{
			if(enemy_bullets.get(i).getPosY() >= GameFrame.getFrameHeight() || isWon || isLost)
			{
				enemy_bullets.remove(i);
				
				if(!isWon && !isLost && EnemyGenerator.getEnemyList().size() > 0 && !menu)
					this.updateRandomEnemy();
			}
		}
	}
	
	//check if the enemies are shot
	public void checkShot()
	{
		//for ship bullets
		for(int i = 0; i < EnemyGenerator.getEnemyList().size(); i++)
		{
			for(int j = 0; j < bullets.size(); j++)
			{
				if(EnemyGenerator.getEnemyList().get(i).isShot(bullets.get(j)))
				{
					enemy_explode_sound.playSound();
					score += 10;
					EnemyGenerator.getEnemyList().get(i).setHealth(EnemyGenerator.getEnemyList().get(i).getHealth() - Bullet.getDamage());
					bullets.remove(j);
				}
			}
		}
	}
	
	//random attack
	public boolean isRandomShot()
	{
		int randNum = (int)(Math.random()*this.delay * enemy_bullet_chance);
		
		return randNum == 7;
	}
	
	//update the random enemy
	public void updateRandomEnemy()
	{
		randomEnemy = eg.randEnemy();
	}
	
	//checking the game state
	public void checkGameState()
	{
		//incrementing the level and checking the game state won
		if(EnemyGenerator.getEnemyList().size() == 0)
		{
			if(level < EnemyGenerator.getMaxLevel())
			{
				level_sound.playSound();
				eg = new EnemyGenerator(++level);
				
				if(level == eg.getBossLevel())
					boss_level_sound.playSound();
			}
			else
			{
				isWon = true;
				
				if(time < 2)
				{
					won_sound.playSound();
					++time;
				}
			}
		}
		
		//to check if the any enemy is collided with the ship
		for(Enemy enemy : EnemyGenerator.getEnemyList())
		{
			if(ship.isShot(enemy))
			{
				isLost = true;

				if(time < 2)
				{
					enemy_explode_sound.playSound();
					ship_explode_sound.playSound();
					lost_sound.playSound();
					++time;
				}
			
				start = false;
				
				break;
			}
		}
		
		//to check if the any enemy bullet is collided with the ship
		for(Bullet bullet : enemy_bullets)
		{
			if(ship.isShot(bullet))
			{
				isLost = true;
				
				if(time < 2)
				{
					enemy_explode_sound.playSound();
					ship_explode_sound.playSound();
					lost_sound.playSound();
					++time;
				}
				
				start = false;
			}
		}
		
		if(!isLost && start && !isWon && !menu)
			eg.moveAllEnemies();
	}
	
	public void updateEnemyBullets()
	{
		if(this.isRandomShot() && (!isLost && !isWon && !menu) && start)
		{
			Bullet bullet = new Bullet();
			
			if(level != eg.getBossLevel())
			{
				bullet.setPosX(randomEnemy.getPosX() + Enemy.getWidth()/2 - Bullet.getWidth()/2);
				bullet.setPosY(randomEnemy.getPosY());
			}
			else
			{
				bullet.setPosX(randomEnemy.getPosX() + Boss.getWidth()/2 - Bullet.getWidth()/2);
				bullet.setPosY(300);
			}
			
			bullet.setType(1);
			
			enemy_bullets.add(bullet);
			
			enemy_bullet.playSound();
		}
	}
	
	public void update()
	{
		this.updateBackground();
		
		if(!menu)
		{
			this.controllShip();
			this.moveBullets();
			this.cleanBulletList();
			this.checkShot();
			this.checkGameState();
			this.updateEnemyBullets();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.update();
		this.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_UP && menu && !isWon && !isLost)
		{
			this.setUpPressed(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN && menu && !isWon && !isLost)
		{
			this.setDownPressed(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && !isLost && !isWon && start)
		{
			this.setRightPressed(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT && !isLost && !isWon && start && !menu)
		{
			this.setLeftPressed(true);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE && !isLost && !isWon && !menu)
		{
			this.setStart(true);
			this.setSpacePressed(true);
			shoot_sound.playSound();
		}
		
		//to reset
		if(isLost || isWon)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				this.init();
			}
		}
		else
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				this.setEnterPressed(true);
						
//				if(menu)
//				{
//					this.init();
//					this.setMenu(false);
//				}
				if(menuState.getSelectedIndex() == 0)
				{
					menu = false;
				}
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
//			if(pause == false)
//			{
//				this.setPause(true);
//			}
//			else
//			{
//				this.setPause(false);
//			}
			MenuState.setSubMenu(false);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			this.setRightPressed(false);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			this.setLeftPressed(false);
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			this.setSpacePressed(false);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			this.setUpPressed(false);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			this.setDownPressed(false);
		}		
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			this.setEnterPressed(false);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	///animation listener inner class
	private class AnimationListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(isLost)
			{
				game_animation.updateImages();
			}
		}
	}
	
	//to update the menu items while in the menu state
	private class MenuListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if(menu)
			{
				menuState.updateSelectedItem(upPressed, downPressed);
			}
			menuState.pauseScreen(pause);
			menuState.updateSelection(enterPressed);
		}
			
	}
	
	//---ACCESS---\\
	
	public boolean isLeftPressed() 
	{
		return leftPressed;
	}
	
	public void setMenu(boolean menu) 
	{
		this.menu = menu;
	}

	public void setLeftPressed(boolean leftPressed) 
	{
		this.leftPressed = leftPressed;
	}

	public boolean isRightPressed() 
	{
		return rightPressed;
	}
	
	public void setPause(boolean pause) 
	{
		this.pause = pause;
	}
	
	public void setRightPressed(boolean rightPressed) 
	{
		this.rightPressed = rightPressed;
	}
	
	public void setEnterPressed(boolean enterPressed) 
	{
		this.enterPressed = enterPressed;
	}
	
	public void setUpPressed(boolean upPressed) 
	{
		this.upPressed = upPressed;
	}
	
	public void setDownPressed(boolean downPressed) 
	{
		this.downPressed = downPressed;
	}
	
	public void setDelay(int delay)
	{
		this.delay = delay;
	}

	public boolean isSpacePressed() 
	{
		return spacePressed;
	}

	public void setSpacePressed(boolean spacePressed) 
	{
		this.spacePressed = spacePressed;
	}

	public boolean isStart() 
	{
		return start;
	}

	public void setStart(boolean start) 
	{
		this.start = start;
	}

	public static int getEnemy_bullet_chance() 
	{
		return enemy_bullet_chance;
	}

	public static void setEnemy_bullet_chance(int enemy_bullet_chance) 
	{
		GamePanel.enemy_bullet_chance = enemy_bullet_chance;
	}
}

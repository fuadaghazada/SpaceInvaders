package game_package;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
	This class will design and draw the enemies in different shapes according to different levels
*/
public class EnemyGenerator 
{
	//constants
	private final int GAP = 10;
	private final int ROW = 6;
	private final int COLUMN = 6;
	
	private static final int MAX_LEVEL = 6;	

	
	//Variables
	private static boolean [][] enemyLocs;
	private static ArrayList<Enemy> enemies;
	private Boss boss;
	private int bossLevel = MAX_LEVEL;
	private int level;
	
	//Constructor
	public EnemyGenerator(int level)
	{
		this.setLevel(level);
		enemyLocs = new boolean [ROW][COLUMN];
		enemies = new ArrayList<>();
		
		this.generateAccToLevel(level);
		this.addEnemies();
		
		System.out.println(enemies.size());
	}
	
	//draws the enemies
	public void drawEnemies(Graphics2D g, GamePanel gp)
	{
		int a = 0;
		
		for(int i = 0; i < enemyLocs.length; i++)
		{
			for(int j = 0; j < enemyLocs[i].length; j++)
			{
				if(enemyLocs[i][j])
				{
					if(enemies.get(a).getHealth() != 0)
					{
						if(level != bossLevel)
						{
							enemies.get(a).draw(g, gp);
							a++;
						}
						else
						{
							boss.draw(g, gp);
						}
					}
					else
					{
						enemyLocs[i][j] = false;
						enemies.remove(a);
					}
				}		
			}
		}
	}
	
	//to move all the enemies at the same time
	public void moveAllEnemies()
	{
		if(level != bossLevel)
		{
			for(int i = 0; i < enemies.size(); i++)
			{
				if(enemies.get(this.mostLeftEdge()).getPosX() >= 0 && enemies.get(this.mostRightEdge()).getPosX() + Enemy.getWidth() <= GameFrame.getFrameWidth())
				{
					enemies.get(i).move();
				}
				else
				{
					enemies.get(i).setDir(enemies.get(i).getDir() * (-1));
					enemies.get(i).setPosY(enemies.get(i).getPosY() + 20);
					enemies.get(i).move();
				}
			}
		}
		else
		{
			boss.move();
		}
	}
	
	//to add the enemies to the enemy list
	public void addEnemies()
	{
		for(int i = 0; i < enemyLocs.length; i++)
		{
			for(int j = 0; j < enemyLocs[i].length; j++)
			{
				if(enemyLocs[i][j])
				{
					if(level != bossLevel)
					{
						Enemy enemy = new Enemy(j * (Enemy.getWidth() + GAP) + this.getMargin(), i * (Enemy.getHeight() + GAP) + this.getMargin());
						enemies.add(enemy);
					}
					else
					{
						boss = new Boss(GameFrame.getFrameWidth()/2 - Boss.getWidth()/2, 50);
						enemies.add(boss);
					}
				}		
			}
		}
	}
	
	//adjusting the generation form according to the game level
	//if you add a new level, do not forget increment the MAX_LEVEL on line 16
	
	public void generateAccToLevel(int level)
	{
		switch(level)
		{
		case 1:																		
			{		
				GamePanel.setEnemy_bullet_chance(40);
				
				for(int i = 2; i < enemyLocs.length-2; i++)							
				{																	
					for(int j = 0; j < enemyLocs[i].length; j++)	
					{
						enemyLocs[i][j] = true;
					}
				}
				break;
			}
		
		case 2:
		{
			GamePanel.setEnemy_bullet_chance(30);
			
			for(int i = 1; i < enemyLocs.length-1; i++)
			{
				for(int j = 0; j < enemyLocs[i].length; j++)
				{
					enemyLocs[i][j] = true;
				}
			}
			break;
		}
		
		case 3:
		{
			GamePanel.setEnemy_bullet_chance(20);
			
			for(int i = 2; i < enemyLocs.length-2; i++)
			{
				for(int j = 0; j < enemyLocs[i].length; j++)
				{
					enemyLocs[i][j] = true;
				}
			}
			
			for(int i = 2; i < enemyLocs.length-2; i++)
			{
				for(int j = 0; j < enemyLocs[i].length; j++)
				{
					enemyLocs[j][i] = true;
				}
			}
			break;
		}
		
		case 4:
		{
			GamePanel.setEnemy_bullet_chance(10);
			
			for(int i = 0; i < enemyLocs.length; i++)
			{
				for(int j = 0; j < enemyLocs[i].length; j++)
				{
					if((j >= 1 && j < enemyLocs[i].length-1) && (i >= 1 && i < enemyLocs.length-1))
						enemyLocs[i][j] = false;
					else
						enemyLocs[i][j] = true;
				}
			}
			break;
		}
		
		case 5:
			{
				enemyLocs[2][2] = true;
				enemyLocs[3][3] = true;
				enemyLocs[2][3] = true;
				enemyLocs[3][2] = true;
				enemyLocs[2][0] = true;
				enemyLocs[3][0] = true;
				enemyLocs[0][2] = true;
				enemyLocs[0][3] = true;
				enemyLocs[1][1] = true;
				enemyLocs[2][0] = true;
				enemyLocs[3][0] = true;
				enemyLocs[1][4] = true;
				enemyLocs[2][5] = true;
				enemyLocs[3][5] = true;
				enemyLocs[4][4] = true;
				enemyLocs[5][3] = true;
				enemyLocs[5][2] = true;
				enemyLocs[4][1] = true;
				
				break;
			}
		case 6:
			{
				GamePanel.setEnemy_bullet_chance(5);
				
				enemyLocs[0][0] = true; //boss
				
				break;
			}
		}
		
	}
	
	//to center the enemies as default
	private int getMargin()
	{
		
		int totalEnemyLength = this.findMaxRow() * Enemy.getWidth() + (this.findMaxRow() - 1) * GAP;
		int margin = (GameFrame.getFrameWidth() - totalEnemyLength) / 2;
		
		return margin;
	}
	
	//to find the row with the max column
	private int findMaxRow()
	{
		int [] numOfEnemies = new int[enemyLocs.length];
		
		for(int i = 0; i < enemyLocs.length; i++)
		{
			for(int j = 0; j < enemyLocs[i].length; j++)
			{
				numOfEnemies[i]++;
			}
		}
		
		int max = numOfEnemies[0];
		
		for(int i = 0; i < numOfEnemies.length; i++)
		{
			if(numOfEnemies[i] >= max)
			{
				max = numOfEnemies[i];
			}
		}
		
		return max;
	}
	
	//to find index of the brick on the most right edge
	private int mostRightEdge()
	{
		float max = enemies.get(0).getPosX();
		
		int index = 0;

		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).getPosX() >= max)
			{
				max = enemies.get(i).getPosX();
				index = i;
			}
		}
		
		return index;
	}
	
	//to find index of the brick on the most right edge
	private int mostLeftEdge()
	{
		float min = enemies.get(0).getPosX();
		
		int index = 0;

		for(int i = 0; i < enemies.size(); i++)
		{
			if(enemies.get(i).getPosX() <= min)
			{
				min = enemies.get(i).getPosX();
				index = i;
			}
		}
		
		return index;
	}
	
	//to choose a random enemy from the list
	public Enemy randEnemy()
	{
		int randIndex = (int)(Math.random() * (enemies.size() - 1));
		
		return getEnemyList().get(randIndex);

	}
	
	//---ACCESS---\\

	public Boss getBoss()
	{
		return boss;
	}
	
	public static ArrayList<Enemy> getEnemyList()
	{
		return enemies;
	}
	
	public static boolean [][] getEnemyLocs()
	{
		return enemyLocs;
	}
	
	public int getLevel() 
	{
		return level;
	}

	public void setLevel(int level) 
	{
		this.level = level;
	}

	public static int getMaxLevel() 
	{
		return MAX_LEVEL;
	}
	
	public int getBossLevel()
	{
		return bossLevel;
	}
}

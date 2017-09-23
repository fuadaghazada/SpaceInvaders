package game_package;

import java.awt.Color;

import javax.swing.ImageIcon;

/**
 	This class will represent the menu of the game
 */

public class MenuState 
{
	//properties
	private int selectedIndex, index;
	private String [] menuItems = {"START", "CONTROLS", "ABOUT"};
	private boolean pause;
	private static boolean subMenu;
	
	//constructor 
	public MenuState()
	{
		setSelectedIndex(-1);
		setIndex(0);
		setPause(false);
		setSubMenu(false);
	}
	
	//to draw the menu state on the panel
	public void draw(java.awt.Graphics g)
	{
		if(selectedIndex == 1 && subMenu)
		{
			g.setColor(Color.WHITE);
			String move = "<- -> (RIGHT & LEFT arrow keys) - for moving";
			String shoot = "Spacebar - for shooting the enemies";
			g.drawString(move, GameFrame.getFrameWidth()/2 - 120, 300);
			g.drawString(shoot, GameFrame.getFrameWidth()/2 - 120, 350);
			
			g.drawString("ESC for back to menu", GameFrame.getFrameWidth()/2 - 230, GameFrame.getFrameHeight() - 40);
		}
		else if(selectedIndex == 2 && subMenu)
		{
			g.setColor(Color.WHITE);
			String about = "This game has been coded by Fuad AGHAZADA in 2017";
			String about2 = "(because he was fucking bored)";
			String about3 = "by getting inspiration from a retro game - Space Invaders by Taito coorparation";
			g.drawString(about, GameFrame.getFrameWidth()/2 - about.length()/2 * 6, 130);
			g.drawString(about2, GameFrame.getFrameWidth()/2 - about2.length()/2 * 6, 150);
			g.drawString(about3, GameFrame.getFrameWidth()/2 - about3.length()/2 * 6, 170);
			
			
			String about4 = "There are some bugs in the game, espacially in the menu part";
			String about5 = "But do not worry!";
			String about6 = "I will not fix them, because you will not play this game more than 2-3 times";
			g.drawString(about4, GameFrame.getFrameWidth()/2 - about4.length()/2 * 6, 210);
			g.drawString(about5, GameFrame.getFrameWidth()/2 - about5.length()/2 * 6, 230);
			g.drawString(about6, GameFrame.getFrameWidth()/2 - about6.length()/2 * 6, 250);
			
			String about7 = "Why am I sure about this fact?";
			String about8 = "Because, this game will not be released anywhere!";
			String about9 = "If you are playing this game now";
			String about10 = "It means that you are either a member from my family, friends or relatives";
			g.drawString(about7, GameFrame.getFrameWidth()/2 - about7.length()/2 * 6, 290);
			g.drawString(about8, GameFrame.getFrameWidth()/2 - about8.length()/2 * 6, 310);
			g.drawString(about9, GameFrame.getFrameWidth()/2 - about9.length()/2 * 6, 330);
			g.drawString(about10, GameFrame.getFrameWidth()/2 - about10.length()/2 * 6, 350);
			
			g.drawString("ESC for back to Main menu", GameFrame.getFrameWidth()/2 - 230, GameFrame.getFrameHeight() - 40);
		}
		else
		{	
			g.setColor(Color.WHITE);
			
			g.drawImage(new ImageIcon(this.getClass().getResource("/images/game_states/menu_logo.png")).getImage(), GameFrame.getFrameWidth()/2 - 150, 100, null);
			
			String version = "Fuad AGHAZADA version 1.2.0.0";
			g.drawString(version, GameFrame.getFrameWidth()/2 - version.length()/2 * 7, 250);
			
			String rights = "Copyright © Fuad Aghazada 2017";
			g.drawString(rights, GameFrame.getFrameWidth()/2 - rights.length()/2 * 6, GameFrame.getFrameHeight() - 40);
			
			for(int i = 0; i < menuItems.length; i++)
			{
				if(i == index)
				{
					g.setColor(Color.RED);
				}
				else
				{
					g.setColor(Color.WHITE);
				}
				g.drawString(menuItems[i], GameFrame.getFrameWidth()/2 - menuItems[i].length()/2 * 5, GameFrame.getFrameHeight()/2 + i*30);
			}
		}
	}
	
	public void updateSelectedItem(boolean upPressed, boolean downPressed)
	{
		if(upPressed)
		{
			if(index > 0)
			{
				index--;
			}
			else
			{
				index = menuItems.length - 1;
			}
		}
		else if(downPressed)
		{
			if(index < menuItems.length - 1)
			{
				index++;
			}
			else
			{
				index = 0;
			}
		}
	}
	
	public void updateSelection(boolean enterPressed)
	{
		if(enterPressed)
		{
			this.setSelectedIndex(index);
			setSubMenu(true);
		}
	}
	
	public void pauseScreen(boolean escKeyPressed)
	{
		if(escKeyPressed)
		{
			if(!pause)
			{
				menuItems[0] = "RESUME"; 
				setPause(true);
			}
			else
			{
				menuItems[0] = "START"; 
				setPause(false);
			}
		}

	}

	//-ACCESS & MUTATE--\\
	
	public int getSelectedIndex() 
	{
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) 
	{
		this.selectedIndex = selectedIndex;
	}

	public String [] getMenuItem() 
	{
		return menuItems;
	}

	public void setMenuItems(String [] menuItems) 
	{
		this.menuItems = menuItems;
	}

	public int getIndex() 
	{
		return index;
	}

	public void setIndex(int index) 
	{
		this.index = index;
	}

	public boolean isPause() 
	{
		return pause;
	}

	public void setPause(boolean pause) 
	{
		this.pause = pause;
	}

	public static boolean isSubMenu() 
	{
		return subMenu;
	}

	public static void setSubMenu(boolean subMenu) 
	{
		MenuState.subMenu = subMenu;
	}
}

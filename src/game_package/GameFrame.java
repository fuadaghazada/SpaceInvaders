package game_package;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 	This class will display and customize the game frame
 */
public class GameFrame 
{
	//Constants
	private static final int FRAME_HEIGHT = 700;
	private static final int FRAME_WIDTH = 500;
	
	//to implement and run the game
	public static void main(String[] args) 
	{
		JFrame frame = new JFrame();
		GamePanel gp = new GamePanel();
		
		
		frame.setTitle("Space Invaders");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		frame.setResizable(false);
		frame.add(gp);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	//---ACCESS---\\
	public static int getFrameHeight() 
	{
		return FRAME_HEIGHT;
	}

	public static int getFrameWidth() 
	{
		return FRAME_WIDTH;
	}

}

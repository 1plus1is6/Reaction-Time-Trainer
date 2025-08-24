package reactiontimetester;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{
	
	private static final long serialVersionUID = -5232355711462691684L;
	
	public Window(int width, int height, String title, Main main){
		
		JFrame frame = new JFrame();
		
		frame.setSize(new Dimension(width, (int)height));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle(title);
		frame.setLocationRelativeTo(null);
		frame.add(main);
		main.start();
		
	}
	
}
package reactiontimetester;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends MouseAdapter{
	
	public void mousePressed(MouseEvent e){
		
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx, my, 50, 50, 135, 36)){
			
			String str = "https://www.youtube.com/watch?v=E7HOlJ_OhEo";
			Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection selection = new StringSelection(str);
			clip.setContents(selection, selection);
			
		}
		
	}
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height){
		
		if(mx > x && mx < x + width){
			
			if(my > y && my < y + height)
				return true;
			else
				return false;
			
		}else
			return false;
		
	}
	
	public void render(Graphics g){
		
		Font font = new Font("arial", 1, 50);
		
		g.setColor(Color.black);
		g.setFont(font);
		g.drawString("Video", 50, 86);
		
	}
	
}
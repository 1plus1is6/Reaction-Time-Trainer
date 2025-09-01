package reactiontimetester;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Menu extends MouseAdapter{
	
	public Main main;
	public boolean copied = false;
	
	public Menu(Main main){
		
		this.main = main;
		
	}
	
	public void mousePressed(MouseEvent e){
		
		int mx = e.getX();
		int my = e.getY();
		
		if(mouseOver(mx, my, 50, 50, 135, 36)){
			
			String str = "https://www.youtube.com/watch?v=E7HOlJ_OhEo";
			Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();
			StringSelection selection = new StringSelection(str);
			clip.setContents(selection, selection);
			copied = true;
			Timer timer = new Timer();
			
			TimerTask task = new TimerTask(){
				
				public void run(){
					
					copied = false;
					
				}
				
			};
			
			timer.schedule(task, 1_500);
			
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
		
		Font font1 = new Font("arial", 1, 50);
		Font font2 = new Font("arial", 1, 20);
		
		g.setColor(Color.black);
		g.setFont(font1);
		
		if(!copied)
			g.drawString("Video", 50, 86);
		else if(copied)
			g.drawString("Link Copied!", 50, 86);
		
		g.setFont(font2);
		g.drawString("Avg.", 195, 325);
		
		if(main.average == 0)
			g.drawString("--", 206, 350);
		else
			g.drawString(Long.toString(main.average), 212 - g.getFontMetrics().stringWidth(Long.toString(main.average)) / 2, 350);
		
		g.drawString("Wrong Inputs", 348, 325);
		g.drawString(Integer.toString(main.wrongCount), 412 - g.getFontMetrics().stringWidth(Integer.toString(main.wrongCount)) / 2, 350);
		
	}
	
}
package reactiontimetester;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter{
	
	private Main main;
	
	public KeyInput(Main main){
		
		this.main = main;
		
	}
	
	public void keyPressed(KeyEvent e){
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_F){
			
			if(main.is1On)
				main.is1On = false;
			else{
				
				main.wrong = true;
				main.wrongCount++;
				
			}
			
		}
		
		if(key == KeyEvent.VK_J){
			
			if(main.is2On)
				main.is2On = false;
			else{
				
				main.wrong = true;
				main.wrongCount++;
				
			}
		}
		
		if(key == KeyEvent.VK_SPACE && !main.started){
			
			main.newRound();
			
		}
		
	}
	
}
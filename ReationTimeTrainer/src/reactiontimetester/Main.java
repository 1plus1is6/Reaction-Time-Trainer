package reactiontimetester;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -4413067955327971402L;
	public static final int WIDTH = 640, HEIGHT = 480;
	private Thread thread;
	private boolean running = false;
	private Menu menu;
	private Random random;
	public boolean is1On = false;
	public boolean is2On = false;
	public boolean bothOff = true;
	public boolean started = false;
	public boolean called = false;
	public boolean wrong = false;
	
	public Main(){
		
		menu = new Menu();
		this.addMouseListener(menu);
		this.addKeyListener(new KeyInput(this));
		random = new Random();
		
		new Window(WIDTH, HEIGHT, "Reaction Time Trainer", this);
		
	}
	
	public synchronized void start(){
		
		thread = new Thread(this);
		
		thread.start();
		running = true;
		
	}
	
	public synchronized void stop(){
		
		try{
			
			thread.join();
			
		}catch(Exception e){
			
			e.printStackTrace();
			
		}
		
		running = false;
		
	}
	
	public void run(){
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1_000_000_000 / amountOfTicks;
		double delta = 0;
		
		while(running){
			
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while(delta >= 1){
				
				tick();
				delta--;
				
			}
			
			if(running)
				render();
			
		}
		
		stop();
		
	}
	
	public void newRound(){
		
		started = true;
		wrong = false;
		Timer timer = new Timer();
		
		TimerTask task1 = new TimerTask(){
			
			public void run(){
				
				int num = random.nextInt(3);
				
				if(num == 0)
					is1On = true;
				else if(num == 1)
					is2On = true;
				else if(num == 2){
					
					is1On = true;
					is2On = true;
					
				}
				
				called = false;
				
			}
			
		};
		
		timer.schedule(task1, 500);
		
	}
	
	private void tick(){
		
		if(!is1On && !is2On)
			bothOff = true;
		else
			bothOff = false;
		
		if(started && bothOff){
			
			if(called)
				return;
			
			else{
				
				newRound();
				called = true;
				
			}
			
		}
		
	}
	
	private void render(){
		
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null){
			
			this.createBufferStrategy(3);
			
			return;
			
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.white);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(wrong){
			
			g.setColor(Color.red);
			g.fillRect(262, 100, 100, 20);
			g.setColor(Color.black);
			g.drawRect(262, 100, 100, 20);
			
		}
		
		menu.render(g);
		
		if(!is1On)
			g.setColor(Color.red);
		else
			g.setColor(Color.green);
		
		g.fillOval(162, 171, 100, 100);
		g.setColor(Color.black);
		g.drawOval(162, 171, 100, 100);
		
		if(!is2On)
			g.setColor(Color.red);
		else
			g.setColor(Color.green);
		
		g.fillOval(362, 171, 100, 100);
		g.setColor(Color.black);
		g.drawOval(362, 171, 100, 100);
		
		g.dispose();
		bs.show();
		
	}
	
	public static void main(String[] args){
		
		new Main();
		
	}
	
}
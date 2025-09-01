package reactiontimetester;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

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
	public int wrongCount = 0;
	public long startTime;
	public ArrayList<Long> times;
	public long time;
	public long average = 0;
	public boolean first = true;;
	
	public Main(){
		
		menu = new Menu(this);
		this.addMouseListener(menu);
		this.addKeyListener(new KeyInput(this));
		random = new Random();
		times = new ArrayList<Long>();
		
		window();
		
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
		is1On = false;
		is2On = false;
		Timer timer = new Timer();
		
		TimerTask task = new TimerTask(){
			
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
				
				startTime = System.currentTimeMillis();
				called = false;
				
			}
			
		};
		
		timer.schedule(task, 500);
		
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
				
				long endTime = System.currentTimeMillis();
				
				if(!first){
					
					times.add(endTime - startTime);
					time += endTime - startTime;
					average = time / times.size();
					
				}
				
				newRound();
				called = true;
				first = false;
				
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
			
			int[] xPoints1 = {304, 340, 355, 284, 269};
			int[] xPoints2 = {320, 284, 269, 340, 355};
			int[] yPoints1 = {122, 87, 102, 173, 158};
			
			int[] xPoints3 = {305, 340, 354, 284, 270};
			int[] xPoints4 = {319, 284, 270, 340, 354};
			int[] yPoints2 = {123, 88, 102, 172, 158};
			
			g.setColor(Color.black);
			g.fillPolygon(xPoints1, yPoints1, 5);
			g.fillPolygon(xPoints2, yPoints1, 5);
			
			g.setColor(Color.red);
			g.fillPolygon(xPoints3, yPoints2, 5);
			g.fillPolygon(xPoints4, yPoints2, 5);
			
		}
		
		menu.render(g);
		
		if(!started)
			g.setColor(Color.gray);
		else{
			
			if(!is1On)
				g.setColor(Color.red);
			else
				g.setColor(Color.green);
			
		}
		
		g.fillOval(162, 171, 100, 100);
		g.setColor(Color.black);
		g.drawOval(162, 171, 100, 100);
		
		if(!started)
			g.setColor(Color.gray);
		else{
			
			if(!is2On)
				g.setColor(Color.red);
			else
				g.setColor(Color.green);
			
		}
		
		g.fillOval(362, 171, 100, 100);
		g.setColor(Color.black);
		g.drawOval(362, 171, 100, 100);
		
		g.dispose();
		bs.show();
		
	}
	
	public void window(){
		
		JFrame frame = new JFrame();
		
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Reaction Time Trainer");
		frame.setLocationRelativeTo(null);
		frame.add(this);
		start();
		
	}
	
	public static void main(String[] args){
		
		new Main();
		
	}
	
}
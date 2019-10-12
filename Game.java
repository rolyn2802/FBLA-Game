package ca.wade.game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;



import javax.swing.JFrame;

import gfx.Renderer;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	
	//Width and Height of box set
	public static final int WIDTH = 640;
	public static final int HEIGHT = WIDTH /4*3;

	
	//Title
	public static final String TITLE = "Gamer";
	
	//Initializing game 
	private static Game game = new Game();
	

	private boolean running = false;
	private Thread thread;
	private Renderer gfx;
	
	public static Game getInstance() {
		return game;
	}
	
	public void init() {
		gfx = new Renderer();
	}
	
	public void tick() {
		
	}
	
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(0, 120, 255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		///////////////////////////////////////
		
		gfx.renderBackground(g);
		gfx.renderForeground(g);
		
		g.dispose();
		bs.show();
	}
	
	@Override
	public void run() {
		init();
		long lastTime = System.nanoTime();
		//60 Ticks in a second (Updates)
		final double numTicks = 60.0;
		double n = 1000000000 / numTicks;
		double delta = 0;
		int frames = 0;
		int ticks = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			long currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / n;
			lastTime = currentTime;
			
			if(delta >= 1) {
				ticks++;
				delta--;
			}
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(ticks + " Ticks, FPS: " + frames);
				ticks = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public static void main(String args[]) {
		
		//Creates java frame
		JFrame frame = new JFrame(TITLE);
		//adds the game component 
		frame.add(game);
		//sets frame size
		frame.setSize(WIDTH, HEIGHT);
		//Closes when you click the "x"
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//enables key and mouse input
		frame.setFocusable(true);
		//Creates window in center of screen
		frame.setLocationRelativeTo(null);
		//disables resizing of frame 
		frame.setResizable(false);
		//Makes frame visible
		frame.setVisible(true);
		//"packs" all of these into the frame
		frame.pack();
		
		game.start();
	}
	
	private synchronized void start() {
		if(running)
			return;
		else
			running = true;
		thread = new Thread(this);
		thread.start();	
	}
	
	private synchronized void stop() {
		if(!running)
			return;
		else
			running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.exit(1);
	}
	//test
}

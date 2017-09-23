/**
 * SnapGames
 * 
 * Game Development Java
 * 
 * GDJ102
 * 
 * @year 2017
 */
package com.snapgames.gdj.gdj102;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * the basic Game container is a JPanel child.
 * 
 * @author Frederic
 *
 */
public class Game extends JPanel implements Runnable {

	/**
	 * The title for the game instance.
	 */
	private String title = "game";

	private Dimension dimension = null;

	private Window window;

	private boolean exit = false;

	private Graphics2D g;

	private BufferedImage image;

	private InputHandler inputHandler;

	private FPSCounter fpsCounter;

	private Thread thread;

	int x = 0, y = 0, dx = 5, dy = 5;
	Color color = Color.RED;

	/**
	 * the default constructor for the {@link Game} panel with a game
	 * <code>title</code>.
	 * 
	 * @param title
	 *            the title for the game.
	 */
	private Game(String title) {
		this.title = title;
		this.dimension = new Dimension(640, 400);

		fpsCounter = new FPSCounter();
		inputHandler = new InputHandler();
		exit = false;
	}

	/**
	 * Initialize the Game object with <code>g</code>, the Graphics2D interface to
	 * render things.
	 */
	private void initialize() {
		image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#addNotify()
	 */
	@Override
	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * The main Loop !
	 */
	private void loop() {
		int fps = 60;
		int delay = 1000 / fps;
		
		long current = 0;
		
		while (!exit) {
			current = System.currentTimeMillis();
			
			input();
			update();
			render(g);

			drawToScreen();
			fpsCounter.tick();

			long wait = delay - (System.currentTimeMillis() - current);
			System.out.println("wait:"+wait);

			if (wait < 0) {
				wait = 1;
			}

			sleep((int) wait);

		}
	}

	/**
	 * Draw Buffer to screen.
	 */
	private void drawToScreen() {
		Graphics g2 = this.getGraphics();

		// draw some text
		g.setColor(Color.WHITE);
		g.drawString("FPS:" + fpsCounter.getFPS(), 10, 380);

		g2.drawImage(image, 0, 0, null);
		g2.dispose();

		// clear area
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

	}

	/**
	 * @param delay
	 */
	private void sleep(int delay) {
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			System.err.println("Unable to wait for " + delay + "ms : " + e.getMessage());
		}
	}

	/**
	 * Manage Game input.
	 */
	private void input() {
		// Process keys
		if (inputHandler.getKeyReleased(KeyEvent.VK_ESCAPE)) {
			setExit(true);
		}
		if(inputHandler.getKeyReleased(KeyEvent.VK_S)) {
			ImageUtils.screenshot(image);
		}
		inputHandler.clean();
	}

	/**
	 * Update game internals
	 */
	private void update() {
		x += dx;
		y += dy;
		if (x <= 0 || x >= getWidth() - 16) {
			dx = -dx;
			color = Color.YELLOW;
		}
		if (y <= 0 || y >= getHeight() - 16) {
			dy = -dy;
			color = Color.YELLOW;
		}
	}

	/**
	 * render all the beautiful things.
	 * 
	 * @param g
	 */
	private void render(Graphics2D g) {

		g.setColor(color);
		g.fillArc(x, y, 16, 16, 0, 360);
		color = Color.RED;
	}

	/**
	 * free all resources used by the Game.
	 */
	private void release() {
		window.frame.dispose();
	}

	/**
	 * the only public method to start game.
	 */
	public void run() {
		initialize();
		loop();
		release();
		System.exit(0);
	}

	/**
	 * return the title of the game.
	 * 
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * return the dimension of the Game display.
	 * 
	 * @return
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * Set the active window for this game.
	 * 
	 * @param window
	 *            the window to set as active window for the game.
	 */
	public void setWindow(Window window) {
		this.window = window;
	}

	/**
	 * @return the exit
	 */
	public boolean isExit() {
		return exit;
	}

	/**
	 * @param exit
	 *            the exit to set
	 */
	public void setExit(boolean exit) {
		this.exit = exit;
	}

	/**
	 * @return the inputHandler
	 */
	public InputHandler getInputHandler() {
		return inputHandler;
	}

	/**
	 * The main entry point to start our GDJ102 game.
	 * 
	 * @param argv
	 */
	public static void main(String[] argv) {
		Game game = new Game("GDJ102");
		new Window(game);
		game.run();
	}
}
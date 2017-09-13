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
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

/**
 * the basic Game container is a JPanel child.
 * 
 * @author Frederic
 *
 */
public class Game extends JPanel {

	/**
	 * The title for the game instance.
	 */
	private String title = "game";

	private Dimension dimension = null;

	private Window window;

	private boolean exit = false;

	private Graphics2D g;

	private InputHandler inputHandler;

	private int frame;

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

		exit = false;
		inputHandler = new InputHandler();
	}

	/**
	 * Initialize the Game object with <code>g</code>, the Graphics2D interface
	 * to render things.
	 */
	private void initialize() {

		g = (Graphics2D) getGraphics();
	}

	/**
	 * The main Loop !
	 */
	private void loop() {
		int FPS = 60;
		int delay = 1000 / FPS;
		int cpt = 0;
		while (!exit) {
			input();
			update();
			frame = cpt;
			render(g);
			wait(delay);
			cpt++;
			if (cpt > 60) {
				cpt = 0;
			}
		}
	}

	@Override
	public void addNotify() {
		super.addNotify();
	}

	/**
	 * @param delay
	 */
	private void wait(int delay) {
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
		inputHandler.clean();
	}

	/**
	 * Update game internals
	 */
	private void update() {

	}

	/**
	 * render all the beautiful things.
	 * 
	 * @param g
	 */
	private void render(Graphics2D g) {
		// clear area
		g.clearRect(0, 0, 640, 400);

		// draw some text
		g.setColor(Color.BLACK);
		g.drawString("Looping " + frame + " times !", 280, 200);
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
		Window window = new Window(game);
		game.run();
	}
}

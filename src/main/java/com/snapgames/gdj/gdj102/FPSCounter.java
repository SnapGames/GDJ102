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

/**
 * A Frame-Per-Second counter.
 * 
 * @author Frederic
 *
 */
public class FPSCounter {
	public int currentFPS = 0;
	public int FPS = 0;
	public long start = 0;

	public void tick() {
		currentFPS++;
		if (System.currentTimeMillis() - start >= 1000) {
			FPS = currentFPS;
			currentFPS = 0;
			start = System.currentTimeMillis();
		}
	}

	public int getFPS() {
		return FPS;
	}
}

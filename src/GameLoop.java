/*** In The Name of Allah ***/

import java.io.IOException;
import java.util.PrimitiveIterator;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods 
 * in the while loop (update() and render()) should be 
 * long running! Both must execute very quickly, without 
 * any waiting and blocking!
 * 
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 *    http://gameprogrammingpatterns.com/game-loop.html
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GameLoop implements Runnable {
	
	/**
	 * Frame Per Second.
	 * Higher is better, but any value above 24 is fine.
	 */
	public static final int FPS = 30;
	
	private GameFrame canvas;
	private GameState state;
	private GameState state1;
	private GameState state2;

	public GameLoop(GameFrame frame) {
		canvas = frame;
	}
	
	/**
	 * This must be called before the game loop starts.
	 */
	public void init() {
		state = new GameState(1);
		state1 = new GameState(2);
		state2 = new GameState(3);
		canvas.addKeyListener(state.getKeyListener());
		canvas.addMouseListener(state.getMouseListener());
		canvas.addMouseMotionListener(state.getMouseMotionListener());
		canvas.addKeyListener(state1.getKeyListener());
		canvas.addMouseListener(state1.getMouseListener());
		canvas.addMouseMotionListener(state1.getMouseMotionListener());
		canvas.addKeyListener(state2.getKeyListener());
		canvas.addMouseListener(state2.getMouseListener());
		canvas.addMouseMotionListener(state2.getMouseMotionListener());
	}

	@Override
	public void run() {
		boolean gameOver = false;
		while (!gameOver) {
			try {
				long start = System.currentTimeMillis();
				//
				state.update();
				state1.update();
				state2.update();
				canvas.render(state,state2,state1);

				gameOver = state.gameOver;
				//
				long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
				if (delay > 0)
					Thread.sleep(delay);
			} catch (InterruptedException | IOException ex) {
			}
		}
		try {
			canvas.render(state,state1,state2);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

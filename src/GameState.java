/*** In The Name of Allah ***/

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {

	public int locX, locY, diam;
	public boolean gameOver;

	private boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
	private boolean mousePress;
	private int mouseX, mouseY;
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	public double rotateAmount;

	public GameState() {
		locX = 100;
		locY = 100;
		diam = 42;
		rotateAmount = 0;
		gameOver = false;
		//
		keyUP = false;
		keyDOWN = false;
		keyRIGHT = false;
		keyLEFT = false;
		//
		mousePress = false;
		mouseX = 0;
		mouseY = 0;
		//
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
	}

	/**
	 * The method which updates the game state.
	 */
	public void update() {
		if (rotateAmount >= 360 || rotateAmount <= -360) rotateAmount = 0;
		if (mousePress) {
			locY = mouseY - diam / 2;
			locX = mouseX - diam / 2;
		}
		if (keyUP) {
			move(+5);

		}
		if (keyDOWN) {
			move(-5);

		}
		if (keyLEFT)
			rotateAmount -= 10;

		if (keyRIGHT)
			rotateAmount += 10;

		locX = Math.max(locX, 42 );
		locX = Math.min(locX, GameFrame.GAME_WIDTH - diam -160);
		locY = Math.max(locY, 50);
		locY = Math.min(locY, GameFrame.GAME_HEIGHT - diam-160);
	}

	public void move(int px) {
		double d;

		if (rotateAmount == 0) locX += px;
		if (rotateAmount == 90 || rotateAmount == -270) locY += px;
		if (rotateAmount == -90 || rotateAmount == 270) locY -= px;
		if (rotateAmount == 180 || rotateAmount == -180) locX -= px;

		if ((rotateAmount > 0 && rotateAmount < 90) || (rotateAmount < -270 && rotateAmount > -360)) {
			if (px > 0) {
				locX += px * Math.cos(rotateAmount * Math.PI / 180);
				locY += px * Math.sin(rotateAmount * Math.PI / 180);
			} else {
				locX += px * Math.cos(rotateAmount * Math.PI / 180);
				locY += px * Math.sin(rotateAmount * Math.PI / 180);
			}
		}
		if ((rotateAmount > 90 && rotateAmount < 180) || (rotateAmount < -180 && rotateAmount > -270)) {
			if (px > 0) {
				d = rotateAmount - 90;
				locX -= px * Math.sin(d * Math.PI / 180);
				locY += px * Math.cos(d * Math.PI / 180);
			} else {
				d = rotateAmount + 270;
				locX -= px * Math.sin(d * Math.PI / 180);
				locY += px * Math.cos(d * Math.PI / 180);
			}
		}
		if ((rotateAmount > 180 && rotateAmount < 270) || (rotateAmount < -90 && rotateAmount > -180)) {

			if (px > 0) {
				d = rotateAmount - 180;
				locX -= px * Math.cos(d * Math.PI / 180);
				locY -= px * Math.sin(d * Math.PI / 180);
			} else {
				d = rotateAmount + 180;
				locX -= px * Math.cos(d * Math.PI / 180);
				locY -= px * Math.sin(d * Math.PI / 180);
			}
		}
		if ((rotateAmount > 270 && rotateAmount < 360) || (rotateAmount < 0 && rotateAmount > -90)) {

			if (px > 0) {
				d = rotateAmount - 270;
				locX += px * Math.sin(d * Math.PI / 180);
				locY -= px * Math.cos(d * Math.PI / 180);
			} else {
				d = rotateAmount + 90;
				locX += px * Math.sin(d * Math.PI / 180);
				locY -= px * Math.cos(d * Math.PI / 180);
			}

		}

	}

	public KeyListener getKeyListener() {
		return keyHandler;
	}

	public MouseListener getMouseListener() {
		return mouseHandler;
	}

	public MouseMotionListener getMouseMotionListener() {
		return mouseHandler;
	}


	/**
	 * The keyboard handler.
	 */
	class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					keyUP = true;
					break;
				case KeyEvent.VK_DOWN:
					keyDOWN = true;
					break;
				case KeyEvent.VK_LEFT:
					keyLEFT = true;
					break;
				case KeyEvent.VK_RIGHT:
					keyRIGHT = true;
					break;
				case KeyEvent.VK_ESCAPE:
					gameOver = true;
					break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					keyUP = false;
					break;
				case KeyEvent.VK_DOWN:
					keyDOWN = false;
					break;
				case KeyEvent.VK_LEFT:
					keyLEFT = false;
					break;
				case KeyEvent.VK_RIGHT:
					keyRIGHT = false;
					break;
			}
		}

	}

	/**
	 * The mouse handler.
	 */
	class MouseHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			mousePress = true;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mousePress = false;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}
	}
}


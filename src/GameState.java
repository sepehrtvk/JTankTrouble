/*** In The Name of Allah ***/

import org.w3c.dom.css.RGBColor;

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
    private boolean one = false;
    private boolean two = false;
    private boolean three = false;
    boolean PermissionDown = true;
    boolean PermissionUp = true;


    public GameState(int num) {
        locX = 100;
        locY = 100;
        //changed from 42 to 25
        diam = 25;
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
        if (num == 1) {
            one = true;
        }
        if (num == 2) {
            two = true;
        }
        if (num == 3) {
            three = true;
        }
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();
    }


    public Rectangle getBounds(int locX, int locY) {
        return new Rectangle(locX, locY, 28, 28);
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
            PermissionUp = true;
            for (Wall wall : Controller.walls) {
                //if (Math.abs(locX - wall.getX()) <= 5 && Math.abs(locY - wall.getY()) <= 5) {
                if (wall.getWidth() == 5 && wall.getHeight() == 50 && rotateAmount != 90 && rotateAmount != -90 && rotateAmount != 270 && rotateAmount != -270) {
                    if ((getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 5, 50)))) {
                        System.out.println("2");
                        //if (PermissionDown)
                            PermissionUp = false;
                        break;
                    }
                }

            }
            if (PermissionUp)
                move(+5);
        }

        if (keyDOWN) {
            PermissionDown = true;
            for (Wall wall : Controller.walls) {

                if (wall.getWidth() == 5 && wall.getHeight() == 50) {
                    if ((getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 5, 50)))) {
                        System.out.println("3");
                        if (PermissionUp)
                            PermissionDown = false;
                        break;
                    }
                }

                if (wall.getWidth() == 50 && wall.getHeight() == 5) {
                    if ((getBounds(locX, locY - 2).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 50, 5)))) {
                        System.out.println("4");
                        if (PermissionUp)
                            PermissionDown = false;
                        break;
                    }
                }
            }
            if (PermissionDown)
                move(-5);

        }


        if (keyLEFT) {
            rotateAmount -= 15;
        }

        if (keyRIGHT) {
            rotateAmount += 15;
        }

        locX = Math.max(locX, 40);
        //Formula for tank movement limit
        locX = Math.min(locX, 20 + (((Controller.col - 1) / 2) * 50) + (((Controller.col - 1) / 2) + 1) * 5 - 25);

        locY = Math.max(locY, 70);
        //Formula for tank movement limit
        locY = Math.min(locY, 50 + ((Controller.row - 1) / 2) * 50 + (((Controller.row - 1) / 2) + 1) * 5 - 25);
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

    public void checkWall() {
        for (Wall wall : Controller.walls) {
            if (wall.getX() == locX + 5 || wall.getY() == locY + 5) {
                keyDOWN = false;
            } else if (wall.getX() == locX - 5 || wall.getY() == locY - 5) {
                keyUP = false;
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
            if (one) {
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
            if (two) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        keyUP = true;
                        break;
                    case KeyEvent.VK_S:
                        keyDOWN = true;
                        break;
                    case KeyEvent.VK_A:
                        keyLEFT = true;
                        break;
                    case KeyEvent.VK_D:
                        keyRIGHT = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        gameOver = true;
                        break;
                }
            }
            if (three) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_I:
                        keyUP = true;
                        break;
                    case KeyEvent.VK_K:
                        keyDOWN = true;
                        break;
                    case KeyEvent.VK_J:
                        keyLEFT = true;
                        break;
                    case KeyEvent.VK_L:
                        keyRIGHT = true;
                        break;
                    case KeyEvent.VK_ESCAPE:
                        gameOver = true;
                        break;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (one) {
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
            if (two) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        keyUP = false;
                        break;
                    case KeyEvent.VK_S:
                        keyDOWN = false;
                        break;
                    case KeyEvent.VK_A:
                        keyLEFT = false;
                        break;
                    case KeyEvent.VK_D:
                        keyRIGHT = false;
                        break;
                }
            }
            if (three) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_I:
                        keyUP = false;
                        break;
                    case KeyEvent.VK_K:
                        keyDOWN = false;
                        break;
                    case KeyEvent.VK_J:
                        keyLEFT = false;
                        break;
                    case KeyEvent.VK_L:
                        keyRIGHT = false;
                        break;
                }
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


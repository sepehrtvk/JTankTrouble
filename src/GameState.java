/*** In The Name of Allah ***/

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 * @version 2.1
 */
public class GameState {
    //keep location of tank
    public int locX, locY, diam;
    //keep location of enemy
    public int pcX, pcY;
    public boolean gameOver, keyM;
    public boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress;
    private int mouseX, mouseY;
    private int bulletTime = 0;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    public double rotateAmountTank;
    public double rotateAmountPC;
    private boolean one = false;
    private boolean two = false;
    private boolean three = false;
    public double rotateAmountBullet;
    public ArrayList<Bullet> bullets;
    //count number of enemy moves
    public int move = 0;
    public boolean firstTimePC = true;
    public boolean firstTime = true;


    /**
     * create a new Game State with given number which is used for tank type
     *
     * @param num of tank
     */
    public GameState(int num) {

        //set default value
//        locX = 40;
//        locY = 100;
        diam = 32;
        //
//        pcX = 300;
//        pcY = 400;
        //
        rotateAmountTank = 0;
        rotateAmountBullet = 0;
        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        keyM = false;
        //
        mousePress = false;
        mouseX = 0;
        mouseY = 0;
        //
        keyHandler = new KeyHandler();
        mouseHandler = new MouseHandler();

        if (num == 1) {
            one = true;
        }
        if (num == 2) {
            two = true;
        }
        if (num == 3) {
            three = true;
        }


        bullets = new ArrayList<>();
    }

    public Rectangle getBounds(int locX, int locY) {
        return new Rectangle(locX, locY, 25, 25);
    }

    public Rectangle getBoundsPC(int locX, int locY) {
        return new Rectangle(locX, locY, 30, 30);
    }

    public Wall verticalWallCollision() {
        for (Wall wall : Controller.walls) {
            if (wall.getWidth() == 5 && wall.getHeight() == 50) {
                if ((getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 5, 50)))) {
                    return wall;
                }
            }
        }
        return null;
    }

    public Wall horizontalWallCollision() {
        for (Wall wall : Controller.walls) {
            if (wall.getWidth() == 50 && wall.getHeight() == 5) {
                if ((getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 50, 5)))) {
                    return wall;
                }
            }
        }
        return null;
    }

    public boolean isMoveAllowed(int px) {

        if (verticalWallCollision() != null) {
            if (verticalWallCollision().getX() > locX && px > 0) {
                if ((rotateAmountTank <= 90 && rotateAmountTank >= -90) || (rotateAmountTank <= -270 && rotateAmountTank > -360) || (rotateAmountTank >= 270 && rotateAmountTank < 360)) {
                    return false;
                } else return true;

            }
            if (verticalWallCollision().getX() < locX && px < 0) {
                if ((rotateAmountTank <= 90 && rotateAmountTank >= -90) || (rotateAmountTank <= -270 && rotateAmountTank > -360) || (rotateAmountTank >= 270 && rotateAmountTank < 360)) {
                    return false;
                } else return true;
            }
            if (verticalWallCollision().getX() > locX && px < 0) {
                if ((rotateAmountTank >= 90 && rotateAmountTank <= 270) || (rotateAmountTank <= -90 && rotateAmountTank >= -270)) {
                    return false;
                } else return true;

            }
            if (verticalWallCollision().getX() < locX && px > 0) {
                if ((rotateAmountTank >= 90 || rotateAmountTank <= 270) || (rotateAmountTank <= -90 && rotateAmountTank >= -270)) {
                    return false;
                } else return true;
            }

        }
        if (horizontalWallCollision() != null) {
            if (horizontalWallCollision().getY() > locY && px > 0) {
                if ((rotateAmountTank <= 180 && rotateAmountTank >= 0) || (rotateAmountTank <= -180 && rotateAmountTank > -360)) {
                    return false;
                } else return true;
            }
            if (horizontalWallCollision().getY() < locY && px < 0) {
                if ((rotateAmountTank <= 180 && rotateAmountTank >= 0) || (rotateAmountTank <= -180 && rotateAmountTank > -360)) {
                    return false;
                } else return true;
            }
            if (horizontalWallCollision().getY() < locY && px > 0) {
                if ((rotateAmountTank >= -180 && rotateAmountTank <= 0) || (rotateAmountTank >= 180 && rotateAmountTank < 360)) {
                    return false;
                } else return true;
            }
            if (horizontalWallCollision().getY() > locY && px < 0) {
                if ((rotateAmountTank >= -180 && rotateAmountTank <= 0) || (rotateAmountTank >= 180 && rotateAmountTank < 360)) {
                    return false;
                } else return true;
            }

        }
        return true;
    }

    public Wall verticalWallCollisionPC() {
        for (Wall wall : Controller.walls) {
            if ((wall.getWidth() == 5 && wall.getHeight() == 50)) {
                if ((getBoundsPC(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 5, 50)))) {
                    return wall;
                }
            }
        }
        return null;
    }

    public Wall horizontalWallCollisionPC() {
        for (Wall wall : Controller.walls) {
            if ((wall.getWidth() == 50 && wall.getHeight() == 5)) {
                if ((getBoundsPC(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 50, 5)))) {
                    return wall;
                }
            }
        }
        return null;
    }


    public boolean isMoveAllowedPC() {
        if (verticalWallCollisionPC() != null) {
            if (verticalWallCollisionPC().getX() > pcX) {
                if (rotateAmountPC == 0) {
                    return false;
                } else return true;
            }
            if (verticalWallCollisionPC().getX() < pcX) {
                if (rotateAmountPC == 180) {
                    return false;
                } else return true;
            }
        }
        if (horizontalWallCollisionPC() != null) {
            if (horizontalWallCollisionPC().getY() > pcY) {
                if (rotateAmountPC == 90) {
                    return false;
                } else return true;
            }
            if (horizontalWallCollisionPC().getY() < pcY) {
                if (rotateAmountPC == 270) {
                    return false;
                } else return true;
            }
        }
        return true;
    }

    public void pcUpdate() {
        if (firstTimePC) {
            Random random = new Random();
            pcY = (random.nextInt(Controller.row)) * 50;
            pcX = (random.nextInt(Controller.col)) * 50;
            firstTimePC = false;
        } else {
            pcX = Math.max(pcX, 40);
            pcX = Math.min(pcX, 20 + (((Controller.col - 1) / 2) * 50) + (((Controller.col - 1) / 2) + 1) * 5 - 25);

            pcY = Math.max(pcY, 70);
            pcY = Math.min(pcY, 50 + ((Controller.row - 1) / 2) * 50 + (((Controller.row - 1) / 2) + 1) * 5 - 25);
        }
        if (move > 0 && move < 200) {
            if (move < 50) {
                rotateAmountPC = 90;
                if (isMoveAllowedPC()) {
                    pcY += 2;
                }
            } else if (move < 100) {
                rotateAmountPC = 0;
                if (isMoveAllowedPC()) {
                    pcX += 2;
                }
            } else if (move < 150) {
                rotateAmountPC = 270;
                if (isMoveAllowedPC()) {
                    pcY -= 2;
                }
            } else {
                rotateAmountPC = 180;
                if (isMoveAllowedPC()) {
                    pcX -= 2;
                }
            }
        }
        if (move >= 200 && move < 500) {
            if (move < 250) {
                rotateAmountPC = 0;
                if (isMoveAllowedPC()) {
                    pcX += 2;
                }
            } else if (move < 300) {
                rotateAmountPC = 90;
                if (isMoveAllowedPC()) {
                    pcY += 2;
                }
            } else if (move < 400) {
                rotateAmountPC = 180;
                if (isMoveAllowedPC()) {
                    pcX -= 2;
                }
            } else {
                rotateAmountPC = 270;
                if (isMoveAllowedPC()) {
                    pcY -= 2;
                }
            }
        }
        if (move >= 500 && move < 800) {
            if (move < 550) {
                rotateAmountPC = 270;
                if (isMoveAllowedPC()) {
                    pcY -= 2;
                }
            } else if (move < 600) {
                rotateAmountPC = 180;
                if (isMoveAllowedPC()) {
                    pcX -= 2;
                }
            } else if (move < 700) {
                rotateAmountPC = 90;
                if (isMoveAllowedPC()) {
                    pcY += 2;
                }
            } else {
                rotateAmountPC = 0;
                if (isMoveAllowedPC()) {
                    pcX += 2;
                }
            }
        }
        if (move >= 800 && move < 1200) {
            if (move < 900) {
                rotateAmountPC = 180;
                if (isMoveAllowedPC()) {
                    pcX -= 2;
                }
            } else if (move < 1000) {
                rotateAmountPC = 270;
                if (isMoveAllowedPC()) {
                    pcY -= 2;
                }
            } else if (move < 1100) {
                rotateAmountPC = 0;
                if (isMoveAllowedPC()) {
                    pcX += 2;
                }
            } else {
                rotateAmountPC = 90;
                if (isMoveAllowedPC()) {
                    pcY += 2;
                }
            }
        }
        move++;
        if (move == 1200)
            move = 0;
    }


    /**
     * The method which updates the game state.
     */
    public void update() throws IOException {
        if (firstTime) {
            Random random = new Random();
            locY = (random.nextInt(Controller.row)) * 50;
            locX = (random.nextInt(Controller.col)) * 50;
            firstTime = false;
        } else {
            locX = Math.max(locX, 40);
            locX = Math.min(locX, 20 + (((Controller.col - 1) / 2) * 50) + (((Controller.col - 1) / 2) + 1) * 5 - 25);

            locY = Math.max(locY, 70);
            locY = Math.min(locY, 50 + ((Controller.row - 1) / 2) * 50 + (((Controller.row - 1) / 2) + 1) * 5 - 25);
        }

        if (rotateAmountTank >= 360 || rotateAmountTank <= -360) rotateAmountTank = 0;
        if (mousePress) {
            locY = mouseY - diam / 2;
            locX = mouseX - diam / 2;
        }
        if (keyUP) {
            calculateBullet("x");
            calculateBullet("y");
            move(+5);

        }
        if (keyDOWN) {
            calculateBullet("x");
            calculateBullet("y");
            move(-5);
        }

        for (Prize prize : Controller.prizes) {
            if ((getBounds(locX, locY).intersects(new Rectangle((int) prize.getX(), (int) prize.getY(), prize.getWidth(), prize.getHeight())))) {
                if (Controller.tanks.get(0).getPrize().equals("empty")||Controller.tanks.get(0).getPrize().equals("bullet2")||
                        Controller.tanks.get(0).getPrize().equals("bullet3")) {
                    Controller.getPrize = true;
                    Controller.tanks.get(0).setPrize(prize.getName());
                }
            }
            if ((getBounds(pcX, pcY).intersects(new Rectangle((int) prize.getX(), (int) prize.getY(), prize.getWidth(), prize.getHeight())))) {
                if(Controller.tanks.get(1).getPrize().equals("empty")||Controller.tanks.get(1).getPrize().equals("bullet2")||
                        Controller.tanks.get(1).getPrize().equals("bullet3")) {
                    Controller.getPrize = true;
                    Controller.tanks.get(1).setPrize(prize.getName());
                }
            }
        }
        if (keyLEFT)
            rotateAmountTank -= 15;
        if (keyRIGHT)
            rotateAmountTank += 15;
    }

    public void fire(Graphics2D g2d) {
        if (keyM && Controller.tanks.size() > 0) {
            if (rotateAmountBullet >= 360 || rotateAmountBullet <= -360) rotateAmountBullet = 0;

            Bullet bullet = new Bullet(calculateBullet("x"), calculateBullet("y"), rotateAmountBullet, Controller.bulletDamage);
            bullets.add(bullet);
            bulletCollision(bullets.get(0));

            if (Controller.bulletSpeed == 0) Controller.bulletSpeed = 4;
            shotBullet(Controller.bulletSpeed, bullets.get(0));
            if (Controller.laser) g2d.setColor(Color.red);
            else g2d.setColor(Color.black);
            g2d.fillOval(bullets.get(0).x, bullets.get(0).y, 7, 7);

            if (bullets.size() > 7 && Controller.tanks.size() > 0)
                checkTankDestroyed(Controller.tanks.get(0), bullets.get(0));

            if (bulletTime > 60) {

                bullets.get(0).x = calculateBullet("x");
                bullets.get(0).y = calculateBullet("y");
                bullets.get(0).rotateAmountBullet = rotateAmountTank;
                bullets.clear();
                keyM = false;
                bulletTime = 0;

            }
            bulletTime++;
        }
    }

    public int calculateBullet(String xy) {
        int x = locX;
        int y = locY;
        if (rotateAmountTank == 0) {
            if (xy.equals("x")) {
                x += 20;
                return x;
            } else {
                y += 10;
                return y;
            }
        }
        if (rotateAmountTank == 90 || rotateAmountTank == -270) {
            if (xy.equals("x")) {
                x += 9;
                return x;
            } else {
                return y + 20;
            }
        }
        if (rotateAmountTank == -90 || rotateAmountTank == 270) {
            if (xy.equals("x")) {
                x += 9;
                return x;
            } else {
                return y - 20;
            }
        }
        if (rotateAmountTank == 180 || rotateAmountTank == -180) {
            if (xy.equals("x")) {
                return x;
            } else {
                y += 10;
                return y;
            }
        }
        if ((rotateAmountTank > 0 && rotateAmountTank < 90) || (rotateAmountTank < -270 && rotateAmountTank > -360)) {
            x += 10 * Math.sin(rotateAmountTank * Math.PI / 180);
            y += 10 * Math.cos(rotateAmountTank * Math.PI / 180);
            if (xy.equals("x")) return x;
            else return y;
        }
        if ((rotateAmountTank > 180 && rotateAmountTank < 270) || (rotateAmountTank < -90 && rotateAmountTank > -180)) {
            x -= 10 * Math.sin(rotateAmountTank * Math.PI / 180);
            y -= 10 * Math.cos(rotateAmountTank * Math.PI / 180);
            if (xy.equals("x")) return x;
            else return y;

        }
        if ((rotateAmountTank > 90 && rotateAmountTank < 180) || (rotateAmountTank < -180 && rotateAmountTank > -270)) {
            x += 25 * Math.sin(rotateAmountTank * Math.PI / 180);
            y -= 20 * Math.cos(rotateAmountTank * Math.PI / 180);
            if (xy.equals("x")) return x;
            else return y;
        }
        if ((rotateAmountTank > 270 && rotateAmountTank < 360) || (rotateAmountTank < 0 && rotateAmountTank > -90)) {
            x += 20 * Math.cos(rotateAmountTank * Math.PI / 180);
            y -= 25 * Math.sin(rotateAmountTank * Math.PI / 180);
            if (xy.equals("x")) return x;
            else return y;

        }
        return 0;
    }

    public void move(int px) {
        double d;
        if (!isMoveAllowed(px)) px = 0;

        if (rotateAmountTank == 0) locX += px;
        if (rotateAmountTank == 90 || rotateAmountTank == -270) locY += px;
        if (rotateAmountTank == -90 || rotateAmountTank == 270) locY -= px;
        if (rotateAmountTank == 180 || rotateAmountTank == -180) locX -= px;

        if ((rotateAmountTank > 0 && rotateAmountTank < 90) || (rotateAmountTank < -270 && rotateAmountTank > -360)) {
            if (px > 0) {
                locX += px * Math.cos(rotateAmountTank * Math.PI / 180);
                locY += px * Math.sin(rotateAmountTank * Math.PI / 180);
            } else {
                locX += px * Math.cos(rotateAmountTank * Math.PI / 180);
                locY += px * Math.sin(rotateAmountTank * Math.PI / 180);
            }
        }
        if ((rotateAmountTank > 90 && rotateAmountTank < 180) || (rotateAmountTank < -180 && rotateAmountTank > -270)) {
            if (px > 0) {
                d = rotateAmountTank - 90;
                locX -= px * Math.sin(d * Math.PI / 180);
                locY += px * Math.cos(d * Math.PI / 180);
            } else {
                d = rotateAmountTank + 270;
                locX -= px * Math.sin(d * Math.PI / 180);
                locY += px * Math.cos(d * Math.PI / 180);
            }
        }
        if ((rotateAmountTank > 180 && rotateAmountTank < 270) || (rotateAmountTank < -90 && rotateAmountTank > -180)) {

            if (px > 0) {
                d = rotateAmountTank - 180;
                locX -= px * Math.cos(d * Math.PI / 180);
                locY -= px * Math.sin(d * Math.PI / 180);
            } else {
                d = rotateAmountTank + 180;
                locX -= px * Math.cos(d * Math.PI / 180);
                locY -= px * Math.sin(d * Math.PI / 180);
            }
        }
        if ((rotateAmountTank > 270 && rotateAmountTank < 360) || (rotateAmountTank < 0 && rotateAmountTank > -90)) {

            if (px > 0) {
                d = rotateAmountTank - 270;
                locX += px * Math.sin(d * Math.PI / 180);
                locY -= px * Math.cos(d * Math.PI / 180);
            } else {
                d = rotateAmountTank + 90;
                locX += px * Math.sin(d * Math.PI / 180);
                locY -= px * Math.cos(d * Math.PI / 180);
            }
        }
    }

    public void bulletCollision(Bullet bullet) {
        for (Wall wall : Controller.walls) {
            Rectangle rectangle = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            Ellipse2D ellipse2D = new Ellipse2D.Double(bullet.x, bullet.y, 7, 7);
            if (ellipse2D.intersects(rectangle)) {
                new AudioPlayer("sound effects/select.wav", 0);
                if (wall.getWidth() == 5 && wall.getHeight() == 50) {
                    if ((rotateAmountBullet >= 0 && rotateAmountBullet <= 180) || (rotateAmountBullet <= -180 && rotateAmountBullet > -360)) {
                        rotateAmountBullet = -rotateAmountBullet - 180;
                        break;
                    } else {
                        rotateAmountBullet = 180 - rotateAmountBullet;
                        break;
                    }
                } else if (wall.getWidth() == 50 && wall.getHeight() == 5) {
                    rotateAmountBullet = -rotateAmountBullet;
                    break;
                } else if (wall.getWidth() == 5 && wall.getHeight() == 5) {
                    if ((rotateAmountBullet >= 0 && rotateAmountBullet <= 180) || (rotateAmountBullet <= -180 && rotateAmountBullet > -360)) {
                        rotateAmountBullet = 180 - rotateAmountBullet;
                        break;
                    } else {
                        rotateAmountBullet = -rotateAmountBullet;
                        break;
                    }
                }
            }
        }
    }

    public void checkTankDestroyed(Tank tankToCheck, Bullet bullet) {
        Rectangle rectangle = new Rectangle(tankToCheck.getX(), tankToCheck.getY(), 25, 25);
        Ellipse2D ellipse2D = new Ellipse2D.Double(bullet.x, bullet.y, 7, 7);
        if (ellipse2D.intersects(rectangle) && !tankToCheck.bulletEffect) {
            new AudioPlayer("sound effects/enemydestroyed.wav", 0);
            tankToCheck.setHealth(Controller.bulletDamage);
            //Controller.tanks.remove(tankToCheck);
            bullet.alive = false;
            bulletTime = 61;
            System.out.println(tankToCheck.getHealth());
            if (tankToCheck.getHealth() <= 0) {
                tankToCheck.alive = false;
                rotateAmountTank = 0;

            }
        }
    }

    public void shotBullet(int px, Bullet bullet) {

        double d;
        double r = rotateAmountBullet;

        if (r == 0) bullet.x += px;
        if (r == 90 || r == -270) bullet.y += px;
        if (r == -90 || r == 270) bullet.y -= px;
        if (r == 180 || r == -180) bullet.x -= px;

        if ((r > 0 && r < 90) || (r < -270 && r > -360)) {
            if (px > 0) {
                bullet.x += px * Math.cos(r * Math.PI / 180);
                bullet.y += px * Math.sin(r * Math.PI / 180);
            } else {
                bullet.x += px * Math.cos(r * Math.PI / 180);
                bullet.y += px * Math.sin(r * Math.PI / 180);
            }
        }

        if ((r > 90 && r < 180) || (r < -180 && r > -270)) {
            if (px > 0) {
                d = r - 90;
                bullet.x -= px * Math.sin(d * Math.PI / 180);
                bullet.y += px * Math.cos(d * Math.PI / 180);
            } else {
                d = r + 270;
                bullet.x -= px * Math.sin(d * Math.PI / 180);
                bullet.y += px * Math.cos(d * Math.PI / 180);
            }
        }

        if ((r > 180 && r < 270) || (r < -90 && r > -180)) {

            if (px > 0) {
                d = r - 180;
                bullet.x -= px * Math.cos(d * Math.PI / 180);
                bullet.y -= px * Math.sin(d * Math.PI / 180);
            } else {
                d = r + 180;
                bullet.x -= px * Math.cos(d * Math.PI / 180);
                bullet.y -= px * Math.sin(d * Math.PI / 180);
            }
        }

        if ((r > 270 && r < 360) || (r < 0 && r > -90)) {

            if (px > 0) {
                d = r - 270;
                bullet.x += px * Math.sin(d * Math.PI / 180);
                bullet.y -= px * Math.cos(d * Math.PI / 180);
            } else {
                d = r + 90;
                bullet.x += px * Math.sin(d * Math.PI / 180);
                bullet.y -= px * Math.cos(d * Math.PI / 180);
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
                    case KeyEvent.VK_M:
                        rotateAmountBullet = rotateAmountTank;
                        new AudioPlayer("sound effects/cannon.wav", 0);
                        keyM = true;
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
                    case KeyEvent.VK_M:
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


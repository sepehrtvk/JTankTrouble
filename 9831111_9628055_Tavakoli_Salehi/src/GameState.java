/*** In The Name of Allah ***/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class GameState {

    //tank location.
    public int locX, locY, diam;

    //enemy location.
    public int pcX, pcY;

    //check game over.
    public boolean gameOver;

    //keyboard inputs.
    public boolean keyUP, keyDOWN, keyRIGHT, keyLEFT, keyM;

    //use mouse.
    private boolean mousePress;

    //mouse position.
    private int mouseX, mouseY;

    //key handler.
    private KeyHandler keyHandler;

    //mouse handler.
    private MouseHandler mouseHandler;

    //tank rotate amount.
    public double rotateAmountTank;

    //enemy rotate amount.
    public double rotateAmountPC;

    //player numbers.
    private boolean one = false;
    private boolean two = false;
    private boolean three = false;

    //bullet rotate amount.
    public double rotateAmountBullet;

    //bullets.
    public ArrayList<Bullet> bullets;

    //move direction.
    public int move = 0;

    //bullet in game time.
    private int bulletTime = 0;

    //enemy shot bullet.
    private boolean enemyShot;

    //start time of the game.
    private long startTime;

    //score fot the tank.
    private int tankScore = 0;

    //score fot the enemy.
    private int enemyScore = 0;

    //count number of enemy moves
    public boolean firstTimePC = true;
    public boolean firstTime = true;

    //first shot.
    private boolean firstShot = false;


    /**
     * create a new Game State with given number which is used for tank type
     *
     * @param num of tank
     */

    public GameState(int num) {
        //set default value
        diam = 32;
        rotateAmountTank = 0;
        rotateAmountBullet = 0;
        gameOver = false;
        //
        keyUP = false;
        keyDOWN = false;
        keyRIGHT = false;
        keyLEFT = false;
        keyM = false;
        enemyShot = false;
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
        //new bullets.
        bullets = new ArrayList<>();
        startTime = System.currentTimeMillis();
    }

    /**
     * make a rectangle and return it as tank shape.
     *
     * @param locX X of the tank
     * @param locY Y of the tank
     * @return tank
     */
    public Rectangle getBounds(int locX, int locY) {
        return new Rectangle(locX, locY, 25, 25);
    }

    /**
     * the verticalWallCollision method checks the collision between tank and vertical walls.
     *
     * @return the wall which has collision with tank.
     */
    public Wall verticalWallCollision(int x, int y) {
        for (Wall wall : Controller.walls) {
            if (wall.getWidth() == 5 && wall.getHeight() == 50) {
                if ((getBounds(x, y).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 5, 50)))) {
                    return wall;
                }
            }
        }
        return null;
    }

    /**
     * the horizontalWallCollision method checks the collision between tank and horizontal walls.
     *
     * @return the wall which has collision with tank.
     */
    public Wall horizontalWallCollision(int x, int y) {
        for (Wall wall : Controller.walls) {
            if (wall.getWidth() == 50 && wall.getHeight() == 5) {
                if ((getBounds(x, y).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 50, 5)))) {
                    return wall;
                }
            }
        }
        return null;
    }

    /**
     * check if the current move is possible for the tank or not.
     *
     * @param px speed of the move.
     * @return true if the move is possible.
     */
    public boolean isMoveAllowed(int px) {

        if (verticalWallCollision(locX, locY) != null) {
            if (verticalWallCollision(locX, locY).getX() > locX && px > 0) {
                if ((rotateAmountTank <= 90 && rotateAmountTank >= -90) || (rotateAmountTank <= -270 && rotateAmountTank > -360) || (rotateAmountTank >= 270 && rotateAmountTank < 360)) {
                    return false;
                } else return true;

            }
            if (verticalWallCollision(locX, locY).getX() < locX && px < 0) {
                if ((rotateAmountTank <= 90 && rotateAmountTank >= -90) || (rotateAmountTank <= -270 && rotateAmountTank > -360) || (rotateAmountTank >= 270 && rotateAmountTank < 360)) {
                    return false;
                } else return true;
            }
            if (verticalWallCollision(locX, locY).getX() > locX && px < 0) {
                if ((rotateAmountTank >= 90 && rotateAmountTank <= 270) || (rotateAmountTank <= -90 && rotateAmountTank >= -270)) {
                    return false;
                } else return true;

            }
            if (verticalWallCollision(locX, locY).getX() < locX && px > 0) {
                if ((rotateAmountTank >= 90 || rotateAmountTank <= 270) || (rotateAmountTank <= -90 && rotateAmountTank >= -270)) {
                    return false;
                } else return true;
            }

        }
        if (horizontalWallCollision(locX, locY) != null) {
            if (horizontalWallCollision(locX, locY).getY() > locY && px > 0) {
                if ((rotateAmountTank <= 180 && rotateAmountTank >= 0) || (rotateAmountTank <= -180 && rotateAmountTank > -360)) {
                    return false;
                } else return true;
            }
            if (horizontalWallCollision(locX, locY).getY() < locY && px < 0) {
                if ((rotateAmountTank <= 180 && rotateAmountTank >= 0) || (rotateAmountTank <= -180 && rotateAmountTank > -360)) {
                    return false;
                } else return true;
            }
            if (horizontalWallCollision(locX, locY).getY() < locY && px > 0) {
                if ((rotateAmountTank >= -180 && rotateAmountTank <= 0) || (rotateAmountTank >= 180 && rotateAmountTank < 360)) {
                    return false;
                } else return true;
            }
            if (horizontalWallCollision(locX, locY).getY() > locY && px < 0) {
                if ((rotateAmountTank >= -180 && rotateAmountTank <= 0) || (rotateAmountTank >= 180 && rotateAmountTank < 360)) {
                    return false;
                } else return true;
            }

        }
        return true;
    }

    /**
     * check if the current move is possible for the PC tank or not.
     *
     * @return true if the move is possible.
     */
    public boolean isMoveAllowedPC() {

        if (verticalWallCollision(pcX, pcY) != null) {
            if (verticalWallCollision(pcX, pcY).getX() > pcX) {
                if (rotateAmountPC == 0) {
                    return false;
                } else return true;
            }
            if (verticalWallCollision(pcX, pcY).getX() < pcX) {
                if (rotateAmountPC == 180) {
                    return false;
                } else return true;
            }
        }

        if (horizontalWallCollision(pcX, pcY) != null) {
            if (horizontalWallCollision(pcX, pcY).getY() > pcY) {
                if (rotateAmountPC == 90) {
                    return false;
                } else return true;
            }
            if (horizontalWallCollision(pcX, pcY).getY() < pcY) {
                if (rotateAmountPC == 270) {
                    return false;
                } else return true;
            }
        }
        return true;
    }

    /**
     * this method updates the state for the PC tank.
     */
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
    public void update() {
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
        if (keyUP && Controller.engineStarted) {
            calculateBullet("x");
            calculateBullet("y");
            move(+5);
        }
        if (keyDOWN && Controller.engineStarted) {
            calculateBullet("x");
            calculateBullet("y");
            move(-5);
        }

        for (Prize prize : Controller.prizes) {
            if ((getBounds(locX, locY).intersects(new Rectangle((int) prize.getX(), (int) prize.getY(), prize.getWidth(), prize.getHeight())))) {
                if (Controller.tanks.get(0).getPrize().equals("empty") || Controller.tanks.get(0).getPrize().equals("bullet2") ||
                        Controller.tanks.get(0).getPrize().equals("bullet3")) {
                    Controller.getPrize = true;
                    Controller.tanks.get(0).setPrize(prize.getName());
                }
            }
            if ((getBounds(pcX, pcY).intersects(new Rectangle((int) prize.getX(), (int) prize.getY(), prize.getWidth(), prize.getHeight())))) {
                if (Controller.tanks.get(1).getPrize().equals("empty") || Controller.tanks.get(1).getPrize().equals("bullet2") ||
                        Controller.tanks.get(1).getPrize().equals("bullet3")) {
                    Controller.getPrize = true;
                    Controller.tanks.get(1).setPrize(prize.getName());
                }
            }
        }

        if (keyLEFT && Controller.engineStarted)
            rotateAmountTank -= 15;

        if (keyRIGHT && Controller.engineStarted)
            rotateAmountTank += 15;


        locX = Math.max(locX, 40);
        locX = Math.min(locX, 20 + (((Controller.col - 1) / 2) * 50) + (((Controller.col - 1) / 2) + 1) * 5 - 25);

        locY = Math.max(locY, 70);
        locY = Math.min(locY, 50 + ((Controller.row - 1) / 2) * 50 + (((Controller.row - 1) / 2) + 1) * 5 - 25);

    }

    /**
     * this fire method shots a bullet from tanks and draw them on the main frame.
     *
     * @param g2d Graphic2D to draw bullet.
     */
    public void fire(Graphics2D g2d) {
        Random random = new Random();
        int rand = random.nextInt(185) + 1;
        if (rand == 10 && Controller.tanks.get(1).alive && !keyM) {
            if (firstShot) rotateAmountBullet = rotateAmountPC;
            enemyShot = true;
            firstShot = false;
        }

        if (keyM && Controller.tanks.size() > 0 && !enemyShot) {
            if (rotateAmountBullet >= 360 || rotateAmountBullet <= -360) rotateAmountBullet = 0;

            Bullet bullet = new Bullet(calculateBullet("x"), calculateBullet("y"), rotateAmountBullet, Controller.bulletDamage);
            bullets.add(bullet);
            bulletCollision(bullets.get(0));

            if (bullets.size() > 0) {
                if (Controller.bulletSpeed == 0) Controller.bulletSpeed = 4;
                shotBullet(Controller.bulletSpeed, bullets.get(0));
                if (Controller.laser) g2d.setColor(Color.red);
                else g2d.setColor(Color.black);
                g2d.fillOval(bullets.get(0).x, bullets.get(0).y, 7, 7);
            }
            if (bullets.size() > 10 && Controller.tanks.size() > 0) {
                checkTankDestroyed(Controller.tanks.get(1), bullets.get(0));
                checkTankDestroyed(Controller.tanks.get(0), bullets.get(0));
            }

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
        if (enemyShot && Controller.tanks.size() > 0) {
            if (rotateAmountBullet >= 360 || rotateAmountBullet <= -360) rotateAmountBullet = 0;
            Bullet bullet = new Bullet(calculateBulletPC("x"), calculateBulletPC("y"), rotateAmountBullet, Controller.bulletDamage);
            bullets.add(bullet);
            bulletCollision(bullets.get(0));

            if (bullets.size() > 0) {
                if (Controller.bulletSpeed == 0) Controller.bulletSpeed = 4;
                shotBullet(Controller.bulletSpeed, bullets.get(0));
                if (Controller.laser) g2d.setColor(Color.red);
                else g2d.setColor(Color.black);
                g2d.fillOval(bullets.get(0).x, bullets.get(0).y, 7, 7);
            }
            if (bullets.size() > 10 && Controller.tanks.size() > 0) {
                checkTankDestroyed(Controller.tanks.get(1), bullets.get(0));
                checkTankDestroyed(Controller.tanks.get(0), bullets.get(0));
            }

            if (bulletTime > 60) {

                bullets.get(0).x = calculateBulletPC("x");
                bullets.get(0).y = calculateBulletPC("y");
                bullets.get(0).rotateAmountBullet = rotateAmountTank;
                bullets.clear();
                enemyShot = false;
                firstShot = true;
                bulletTime = 0;

            }
            bulletTime++;
        }
    }

    /**
     * this calculateBullet method makes the first X and Y to shot bullet for PC tank.
     *
     * @param xy X or Y of the bullet.
     * @return X or Y of the shot.
     */

    public int calculateBulletPC(String xy) {
        int x = pcX;
        int y = pcY;
        if (rotateAmountPC == 0) {
            if (xy.equals("x")) {
                x += 20;
                return x;
            } else {
                y += 10;
                return y;
            }
        }
        if (rotateAmountPC == 90) {
            if (xy.equals("x")) {
                x += 6;
                return x;
            } else {
                return y + 20;
            }
        }
        if (rotateAmountPC == 270) {
            if (xy.equals("x")) {
                x += 7;
                return x;
            } else {
                return y - 10;
            }
        }
        if (rotateAmountPC == 180) {
            if (xy.equals("x")) {
                return x;
            } else {
                y += 10;
                return y;
            }
        }
        return 0;
    }

    /**
     * this calculateBullet method makes the first X and Y to shot bullet.
     *
     * @param xy X or Y of the bullet.
     * @return X or Y of the shot.
     */
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

    /**
     * moves the tank with the given speed and direction.
     *
     * @param px speed of the tank.
     */
    public void move(int px) {
        double d;

        //check possible move.
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

    /**
     * this bulletCollision method checks collision of the bullet with walls.
     *
     * @param bullet bullet to check collision.
     */
    public void bulletCollision(Bullet bullet) {
        for (Wall wall : Controller.walls) {
            Rectangle rectangle = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            Ellipse2D ellipse2D = new Ellipse2D.Double(bullet.x, bullet.y, 5, 5);
            if (ellipse2D.intersects(rectangle)) {
                if (wall.isDestructiveWall()) {
                    new AudioPlayer("Resources/sound effects/softwall.wav", 0);
                    bulletTime = 61;
                    Controller.walls.remove(wall);
                    break;
                } else {
                    new AudioPlayer("Resources/sound effects/select.wav", 0);
                    if (wall.getWidth() == 5 && wall.getHeight() == 50) {
                        if ((rotateAmountBullet >= 0 && rotateAmountBullet < 180) || (rotateAmountBullet <= -180 && rotateAmountBullet > -360)) {
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
                            rotateAmountBullet = -rotateAmountBullet - 180;
                            break;
                        } else {
                            rotateAmountBullet = -rotateAmountBullet;
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * this checkTankDestroyed method checks collision of the bullet with tanks and destroys them.
     *
     * @param bullet      bullet to check collision.
     * @param tankToCheck tank to check collision.
     */
    public void checkTankDestroyed(Tank tankToCheck, Bullet bullet) {
        Rectangle rectangle = new Rectangle(tankToCheck.getX(), tankToCheck.getY(), 25, 25);
        Ellipse2D ellipse2D = new Ellipse2D.Double(bullet.x, bullet.y, 7, 7);
        if (ellipse2D.intersects(rectangle) && !tankToCheck.bulletEffect) {
            new AudioPlayer("Resources/sound effects/enemydestroyed.wav", 0);

            tankToCheck.setHealth(Controller.bulletDamage);
            //Controller.tanks.remove(tankToCheck);
            bullet.alive = false;
            bulletTime = 61;
            if (tankToCheck.getHealth() <= 0) {
                if (Controller.leagueGame) {
                    if (tankToCheck.equals(Controller.tanks.get(1))) tankScore++;
                    else enemyScore++;

                } else {
                    tankToCheck.alive = false;
                    rotateAmountTank = 0;

                }
                gameFinish(tankToCheck.getX(), tankToCheck.getY());
            }

        }
    }

    /**
     * this method checks end game.
     *
     * @param x tank X
     * @param y tank Y
     */
    public void gameFinish(int x, int y) {
        BufferedImage image = null;
        int index = 0;
        if (!Controller.leagueGame) {
            try {
                if (x == locX && y == locY) {
                    image = ImageIO.read(new File("Resources/pictures/GameOver.png"));
                    index = 5;

                } else {
                    image = ImageIO.read(new File("Resources/pictures/win.png"));
                    index = 4;

                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("file not found.");
            }
        } else {
            if (x == locX && y == locY) {
                index = 5;
            } else index = 4;
        }
        StringBuilder sb = new StringBuilder();
        try {
            Scanner scanner = new Scanner(new FileReader(new File("Resources/Accounts.txt")));
            String[] strings = new String[8];
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                strings = str.split(" ");
                if (strings[0].equals(Controller.userName)) {
                    int a = Integer.valueOf(strings[index]);
                    a++;
                    strings[index] = String.valueOf(a);
                } else {
                    sb.append(str + "\n");
                }

            }
            scanner.close();
            FileWriter fileWriter = new FileWriter(new File("Resources/Accounts.txt"));
            fileWriter.write(sb.toString());
            long temp = Long.valueOf(strings[3]);
            strings[3] = String.valueOf(temp + (System.currentTimeMillis() - startTime) / 1000);
            fileWriter.write(strings[0] + " " + strings[1] + " " + strings[2] + " " + strings[3] + " " + strings[4] + " " + strings[5] + " " + strings[6] + " " + strings[7] + "\n");
            fileWriter.close();
        } catch (Exception ee) {
            ee.printStackTrace();
            System.out.println("file not found!");
        }

        JTextArea jTextArea = new JTextArea("Your Score : " + tankScore + "\nEnemy Score : " + enemyScore);
        jTextArea.setEditable(false);
        jTextArea.setBackground(Color.lightGray);
        jTextArea.setAlignmentX(10);
        jTextArea.setAlignmentY(10);
        JFrame jFrame = new JFrame();
        if (Controller.leagueGame)
            jFrame.setBounds(900, 500, 250, 100);
        else jFrame.setBounds(900, 500, 500, 300);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setLayout(new BorderLayout());
        if (!Controller.leagueGame) {
            JLabel picLabel = new JLabel(new ImageIcon(image));
            jFrame.add(picLabel, BorderLayout.CENTER);
        } else {
            jFrame.add(jTextArea, BorderLayout.CENTER);
        }
        JButton jButton;
        if (!Controller.leagueGame)
            jButton = new JButton("Back To Main Menu");
        else jButton = new JButton("Continue");
        jFrame.add(jButton, BorderLayout.SOUTH);
        jFrame.setFocusable(true);
        jFrame.setVisible(true);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Controller.leagueGame) {
                    jFrame.dispose();
                } else {
                    gameOver = true;
                    jFrame.dispose();
                    new MainFrame();
                }
            }
        });

    }

    /**
     * the shotBullet method shots a bullet with given speed.
     *
     * @param px     speed of the bullet.
     * @param bullet bullet to shot.
     */
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

    /**
     * get the key listener.
     *
     * @return key listener.
     */
    public KeyListener getKeyListener() {
        return keyHandler;
    }

    /**
     * get the mouse listener.
     *
     * @return mouse listener.
     */

    public MouseListener getMouseListener() {
        return mouseHandler;
    }

    /**
     * get the mouseMotion listener.
     *
     * @return mouseMotion listener.
     */

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
                        if (Controller.laser)
                            new AudioPlayer("Resources/sound effects/mashingun.wav", 0);
                        else
                            new AudioPlayer("Resources/sound effects/cannon.wav", 0);
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


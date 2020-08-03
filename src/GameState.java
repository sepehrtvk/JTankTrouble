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

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {

    public int locX, locY, diam;
    public int pcX, pcY;
    public boolean gameOver, keyM;

    public boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private boolean mousePress;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    public double rotateAmountTank;
    public double rotateAmountPC;
    private boolean one = false;
    private boolean two = false;
    private boolean three = false;
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    boolean PermissionUp = true;
    boolean PermissionDown = true;


    public double rotateAmountBullet;
    private int bulletTime = 0;
    private int tankTime = 0;


    public ArrayList<Bullet> bullets;


    public GameState(int num) {
        locX = 100;
        locY = 100;
        diam = 32;
        pcX = 40;
        pcY = 100;
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
        up = true;
        down = true;
        left = true;
        right = true;

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

    public void pcUpdate() {

        for (Wall wall : Controller.walls) {

            if (wall.getWidth() == 5 && wall.getHeight() == 50) {
                if (getBounds(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), (int) wall.getWidth(), (int) wall.getHeight())) && wall.getX() < pcX) {
                    left = false;
                    System.out.println("3");
                    break;
                }
                if (getBounds(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), (int) wall.getWidth(), (int) wall.getHeight())) && wall.getX() > pcX) {
                    right = false;
                    System.out.println("4");
                    break;
                }

            }
            if (wall.getWidth() == 50 && wall.getHeight() == 5) {

                if (getBounds(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), (int) wall.getWidth(), (int) wall.getHeight())) && wall.getY() > pcY) {
                    down = false;
                    System.out.println("2");
                    break;
                }
                if (getBounds(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), (int) wall.getWidth(), (int) wall.getHeight())) && wall.getY() < pcY) {
                    System.out.println("1");
                    up = false;
                    break;
                }

            }
            if (!up && !down && !left && !right) {
                if (getBounds(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), (int) wall.getWidth(), (int) wall.getHeight())) && wall.getX() < pcX) {
                    left = false;
                    System.out.println("33");
                } else {
                    left = true;

                }
                if (getBounds(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), (int) wall.getWidth(), (int) wall.getHeight())) && wall.getX() > pcX) {
                    right = false;
                    System.out.println("44");
                } else
                    right = true;


                if (wall.getWidth() == 50 && wall.getHeight() == 5) {

                    if (getBounds(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), (int) wall.getWidth(), (int) wall.getHeight())) && wall.getY() > pcY) {
                        down = false;
                        System.out.println("22");
                        break;
                    } else
                        down = true;
                    if (getBounds(pcX, pcY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), (int) wall.getWidth(), (int) wall.getHeight())) && wall.getY() < pcY) {
                        System.out.println("11");
                        up = false;
                        break;
                    } else
                        up = true;

                }
            }

        }
        if (up) {
            pcY -= 2;
            rotateAmountPC = 270;
        } else if (down) {
            pcY += 2;
            rotateAmountPC = 90;
        } else if (right) {
            pcX -= 2;
            rotateAmountPC = 180;
        } else if (left) {
            pcX += 2;
            rotateAmountPC = 0;
        }

//        pcX = Math.max(pcX, 40);
//        pcX = Math.min(pcX, 20 + (((Controller.col - 1) / 2) * 50) + (((Controller.col - 1) / 2) + 1) * 5 - 25);
//
//        pcY = Math.max(pcY, 70);
//        pcY = Math.min(pcY, 50 + ((Controller.row - 1) / 2) * 50 + (((Controller.row - 1) / 2) + 1) * 5 - 25);
    }


    /**
     * The method which updates the game state.
     */
    public void update() {
        if (rotateAmountTank >= 360 || rotateAmountTank <= -360) rotateAmountTank = 0;
        if (mousePress) {
            locY = mouseY - diam / 2;
            locX = mouseX - diam / 2;
        }
        if (keyUP) {
            calculateBullet("x");
            calculateBullet("y");

            PermissionUp = true;
            for (Wall wall : Controller.walls) {
                if (wall.getWidth() == 5 && wall.getHeight() == 50) {
                    if ((getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 5, 50)))) {
                        //&&
                        //                            ((rotateAmount == 0 && locX < wall.getX()) || ((rotateAmount == 180 || rotateAmount == -180) && locX > wall.getX()))
                        PermissionUp = false;
                        break;
                    }
                }
                if (wall.getWidth() == 50 && wall.getHeight() == 5) {
                    if ((getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 50, 5)))) {
                        // &&
                        //                            (((rotateAmount == +90 || rotateAmount == -270) && locY < wall.getY()) || (((rotateAmount == -90 || rotateAmount == -270) && locY > wall.getY())))
                        PermissionUp = false;
                        break;
                    }
                }
            }
            if (PermissionUp)
                move(+5);
            for (Wall wall : Controller.walls)
                if (!PermissionDown && (!(getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 50, 5))) ||
                        !(getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 5, 50))))) {
                    move(+5);
                    break;
                }


        }
        if (keyDOWN) {
            calculateBullet("x");
            calculateBullet("y");
            PermissionDown = true;
            for (Wall wall : Controller.walls) {
                if (wall.getWidth() == 5 && wall.getHeight() == 50) {
                    if ((getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 5, 50)))) {
                        //&&
                        //                            ((((rotateAmount == 0) || locX >= wall.getX())) || (((rotateAmount == -180 || rotateAmount == 180) && locX <= wall.getX())))
                        PermissionDown = false;
                        break;
                    }
                }
                if (wall.getWidth() == 50 && wall.getHeight() == 5) {
                    if ((getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 50, 5)))) {
                        // &&
                        //                            (((rotateAmount == -90 || rotateAmount == 270) && locY<wall.getY()))|| (((rotateAmount==90||rotateAmount==-270)&& locY>wall.getY()))
                        PermissionDown = false;
                        break;
                    }
                }
            }
            if (PermissionDown)
                move(-5);
            for (Wall wall : Controller.walls)
                if (!PermissionUp && (!(getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 50, 5))) ||
                        !(getBounds(locX, locY).intersects(new Rectangle((int) wall.getX(), (int) wall.getY(), 5, 50))))) {
                    move(-5);
                    break;
                }


        }
        for (Prize prize : Controller.prizes) {
            if ((getBounds(locX, locY).intersects(new Rectangle((int) prize.getX(), (int) prize.getY(), prize.getWidth(), prize.getHeight())))) {
                Controller.getPrize = true;
                Controller.tanks.get(0).setPrize(prize.getName());
            }
        }

        if (keyLEFT)
            rotateAmountTank -= 15;

        if (keyRIGHT)
            rotateAmountTank += 15;


        locX = Math.max(locX, 40);
        locX = Math.min(locX, 20 + (((Controller.col - 1) / 2) * 50) + (((Controller.col - 1) / 2) + 1) * 5 - 25);

        locY = Math.max(locY, 70);
        locY = Math.min(locY, 50 + ((Controller.row - 1) / 2) * 50 + (((Controller.row - 1) / 2) + 1) * 5 - 25);

    }

    public void fire(Graphics2D g2d) {
        if (keyM && Controller.tanks.size() > 0) {
            if (rotateAmountBullet >= 360 || rotateAmountBullet <= -360) rotateAmountBullet = 0;

            Bullet bullet = new Bullet(calculateBullet("x"), calculateBullet("y"), rotateAmountBullet, 30);
            bullets.add(bullet);
            bulletCollision(bullets.get(0));

            shotBullet(8, bullets.get(0));
            g2d.fillOval(bullets.get(0).x, bullets.get(0).y, 7, 7);

            if (bullets.size() > 5 && Controller.tanks.size() > 0)
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
        if (ellipse2D.intersects(rectangle)) {
            new AudioPlayer("sound effects/enemydestroyed.wav", 0);

            Controller.tanks.remove(tankToCheck);
            tankToCheck.alive = false;
            bullet.alive = false;
            rotateAmountTank = 0;
            bulletTime = 61;

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


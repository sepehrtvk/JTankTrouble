/*** In The Name of Allah ***/

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 * http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 880;// 720p game resolution
    //720
    public static final int GAME_WIDTH = 16 * 720 / 9;  // wide aspect ratio

    //uncomment all /*...*/ in the class for using Tank icon instead of a simple circle
    private BufferedImage image;
    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage image3;
    private BufferedImage image4;
    private BufferedImage image5;
    private BufferedImage background;
    private BufferedImage taken;
    public BufferedImage bullet2;
    public BufferedImage bullet3;
    public BufferedImage life;
    public BufferedImage laser;
    public BufferedImage shield;
    public ArrayList<Integer> prizeLoc;
    public int realMapSize;
    public int row;
    public int col;
    long start = System.currentTimeMillis();
    long startSec = start / 1000;
    public long renderCount = 0;
    public long renderCountLimit = 0;
    int lastX = 120;
    int lastY = 120;
    boolean firstPrize = true;
    boolean getPrize = false;
    public BufferedImage lastPrize;


    private BufferStrategy bufferStrategy;

    private Graphics2D g2d;
    private ArrayList<Prize> prizes;
    private ArrayList<Wall> walls;
    private ArrayList<Tank> tanks;


    public GameFrame(String title) {
        super(title);
        setResizable(false);
        setBackground(Color.white);

        setSize(GAME_WIDTH, GAME_HEIGHT);
        setLayout(new BorderLayout());
        try {
            image = ImageIO.read(new File("tank_dark.png"));
        } catch (IOException e) {
            System.out.println(e);
        }
        walls = new ArrayList<>();
        tanks = new ArrayList<>();
        prizeLoc = new ArrayList<>();
        prizes = new ArrayList<>();


//        tanks.add(new Tank("tank_dark.png", 30, 70, 0));
        Controller.walls = walls;
        Controller.prizes = prizes;
        Controller.getPrize = getPrize;
        Controller.tanks = tanks;
        Controller.g2d = g2d;
        Controller.taken = taken;
        Controller.renderCount=renderCount;
        Controller.renderCountLimit=renderCountLimit;

        try {
            image1 = ImageIO.read(new File("tank_dark.png"));
            image2 = ImageIO.read(new File("tank_blue.png"));
            image3 = ImageIO.read(new File("tank_red.png"));
            image4 = ImageIO.read(new File("tank_green.png"));
            image5 = ImageIO.read(new File("tank_sand.png"));
            //background = ImageIO.read(new File("background.png"));
            bullet2 = ImageIO.read(new File("2B.png"));
            bullet3 = ImageIO.read(new File("3B.png"));
            life = ImageIO.read(new File("life.png"));
            laser = ImageIO.read(new File("laser.png"));
            shield = ImageIO.read(new File("shield.png"));

        } catch (IOException e) {
            System.out.println(e);
        }


    }

    /**
     * This must be called once after the JFrame is shown:
     * frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);

                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }

    public BufferedImage rotate(BufferedImage image, Double degrees) {
        // Calculate the new size of the image based on the angle of rotaion
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

        // Create a new image
        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();
        // Calculate the "anchor" point around which the image will be rotated
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;
        // Transform the origin point around the anchor point
        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);
        // Paint the originl image
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
    }

    /**
     * Rendering all game elements based on the game state.
     */

    private void doRendering(Graphics2D g2d, GameState state) {
        renderCount++;
        // Draw background
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        // Draw ball
        g2d.setColor(Color.BLACK);
        //g2d.fillOval(state.locX, state.locY, state.diam, state.diam);
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0, 720, 1280, 160);
        // g2d.drawImage(image1,state.locX,state.locY,null);
        g2d.setColor(Color.black);
        //g2d.drawImage(rotate(image1, state1.rotateAmount), state1.locX, state1.locY, null);
        setTanks(3, g2d, state);
        setEnemy(g2d, state);
        setName(g2d, "narges", "sara", "bardia");
        setMap(g2d, new File("map3.txt"));
        drawMap(g2d);
        setPrize(g2d);
        for(Tank tank:tanks){
            tank.applyPrize();
        }


        state.fire(g2d);
        if (tanks.size() > 0) {
            tanks.get(0).x = state.locX;
            tanks.get(0).y = state.locY;
            //g2d.drawImage(rotate(tanks.get(0).icon, state.rotateAmountTank), tanks.get(0).x, tanks.get(0).y, null);
        }
    }

    public void setMap(Graphics2D g2D, File map) {
        int row = 1;
        int column = 0;
        try (Scanner scanner = new Scanner(new FileReader(map))) {
            column = scanner.nextLine().length();
            while (scanner.hasNext()) {
                column = scanner.nextLine().length();
                row++;
            }

        } catch (Exception ee) {
            ee.printStackTrace();
        }
        this.row = row;
        this.col = column;
        Controller.row = row;
        Controller.col = col;
    }

    public void setName(Graphics2D g2d, String player1, String player2, String player3) {
        if (player1 != null) {
            g2d.drawImage(image2, 150, 750, null);
            player1 = "narges";
            g2d.drawString(player1, 160, 745);
            g2d.drawString(String.valueOf(tanks.get(0).getHealth()), 160, 850);
        }
//        if (player2 != null) {
//            g2d.drawImage(image3, 450, 750, null);
//            g2d.drawString(player2, 455, 745);
//        }
//
//        if (player3 != null) {
//            g2d.drawImage(image4, 750, 750, null);
//            g2d.drawString(player3, 755, 745);
//        }
//
//        g2d.drawImage(image5, 1050, 750, null);
    }

    public void setEnemy(Graphics2D g2d, GameState state) {
        Tank tank = new Tank("enemy.png",state.pcX,state.pcY,0);
        taken = tank.getIcon();
        tanks.add(tank);
        g2d.drawImage(rotate(tank.getIcon(), state.rotateAmountPC), state.pcX, state.pcY, null);
//        System.out.println(pcState.pcX+" "+pcState.pcY);
    }

    public void setTanks(int numOfPlayer, Graphics2D g2d, GameState state) {
        //g2d.drawImage(background, 0, 0, null);
        if (numOfPlayer > 0) {
            Tank tank = new Tank("tank_blue_RS.png",state.locX,state.locY,0);
            taken = tank.getIcon();
            tanks.add(tank);
            g2d.drawImage(rotate(tank.getIcon(), state.rotateAmountTank), state.locX, state.locY, null);
//            numOfPlayer--;
//
//            if (numOfPlayer > 0) {
//                Tank tank1 = new Tank("tank_green_RS.png");
//                g2d.drawImage(rotate(tank1.getIcon(), state1.rotateAmount), state1.locX, state1.locY, null);
//                numOfPlayer--;
//                if (numOfPlayer > 0) {
//                    Tank tank2 = new Tank("tank_red_RS.png");
//                    g2d.drawImage(rotate(tank2.getIcon(), state2.rotateAmount), state2.locX, state2.locY, null);
//                }
//            }
        }
    }

    public void drawMap(Graphics2D g2d) {
        File accounts = new File("map3.txt");
        try (Scanner scanner = new Scanner(new FileReader(accounts))) {
            int lineCounter = 0;
            int currentX = 30;
            int currentY = 60;
            while (scanner.hasNext()) {
                char[] chars = scanner.next().toCharArray();
                for (int k = 0; k < chars.length; k++) {
                    if (lineCounter % 2 == 0) {
                        if (k % 2 == 0) {
                            if (chars[k] == '1') {
                                Wall wall = new Wall(currentX, currentY, 5, 5, g2d);
                                walls.add(wall);

                            } else if (chars[k] == '2') {
                                g2d.setColor(Color.lightGray);
                                Wall wall = new Wall(currentX, currentY, 5, 5, g2d);
                                walls.add(wall);
                            }
                            g2d.setColor(Color.black);
                            currentX += 5;
                        } else {
                            if (chars[k] == '1') {
                                Wall wall = new Wall(currentX, currentY, 50, 5, g2d);
                                walls.add(wall);
                            } else if (chars[k] == '2') {
                                g2d.setColor(Color.lightGray);
                                Wall wall = new Wall(currentX, currentY, 50, 5, g2d);
                                walls.add(wall);
                            }
                            g2d.setColor(Color.black);
                            currentX += 50;
                        }
                    }
                    if (lineCounter % 2 != 0) {
                        if (k % 2 == 0) {
                            if (chars[k] == '1') {
                                Wall wall = new Wall(currentX, currentY, 5, 50, g2d);
                                walls.add(wall);
                            } else if (chars[k] == '2') {
                                g2d.setColor(Color.lightGray);
                                Wall wall = new Wall(currentX, currentY, 5, 50, g2d);
                                walls.add(wall);
                            }
                            g2d.setColor(Color.black);
                            currentX += 5;
                        } else {
                            if (firstPrize) {
                                prizeLoc.add(currentX + 25);
                                prizeLoc.add(currentY + 25);
                            }
                            currentX += 50;

                        }
                    }
                }
                currentX = 30;
                if (lineCounter % 2 == 0) currentY += 5;
                else currentY += 50;
                lineCounter++;
            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        if (firstPrize) {
            realMapSize = prizeLoc.size();
        }
    }

    /**
     * Draw prize randomly
     *
     * @param g2d graphic2D of game
     * @throws IOException
     */
    public void setPrize(Graphics2D g2d) {
        int randomLoc = (int) (Math.random() * (realMapSize));
        int randomNum = (int) (Math.random() * (5));

        if (randomLoc % 2 != 0)
            randomLoc = randomLoc - 1;
        if (firstPrize) {
            g2d.drawImage(bullet2, prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), null);
            prizes.add(new Prize(prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), 15, 15,"bullet2"));
            firstPrize = false;
            lastX = prizeLoc.get(randomLoc);
            lastY = prizeLoc.get(randomLoc + 1);
            renderCountLimit = renderCount + 300;
            lastPrize = bullet2;
        } else if (renderCount != renderCountLimit && !Controller.getPrize) {
            g2d.drawImage(lastPrize, lastX, lastY, null);
        } else {
            Controller.getPrize = false;
            if (randomNum == 1) {
                g2d.drawImage(shield, prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), null);
                prizes.clear();
                prizes.add(new Prize(prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), 10, 10, "shield"));
                lastPrize = shield;
            }
            if (randomNum == 0) {
                g2d.drawImage(life, prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), null);
                prizes.clear();
                prizes.add(new Prize(prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), 10, 10, "life"));
                lastPrize = life;
            }
            if (randomNum == 3) {
                g2d.drawImage(bullet3, prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), null);
                prizes.clear();
                prizes.add(new Prize(prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), 10, 10, "bullet3"));
                lastPrize = bullet3;
            }
            if (randomNum == 2) {
                g2d.drawImage(laser, prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), null);
                prizes.clear();
                prizes.add(new Prize(prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), 10, 10, "laser"));
                lastPrize = laser;
            }
            if (randomNum == 4) {
                g2d.drawImage(bullet2, prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), null);
                prizes.clear();
                prizes.add(new Prize(prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), 10, 10, "bullet2"));
                lastPrize = bullet2;
            }
            lastX = prizeLoc.get(randomLoc);
            lastY = prizeLoc.get(randomLoc + 1);
            renderCountLimit = renderCount + 300;
        }
    }


}


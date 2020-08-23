
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 880;// 720p game resolution
    //720
    public static final int GAME_WIDTH = 16 * 720 / 9;  // wide aspect ratio

    //tank 1 photo.
    private BufferedImage image;

    //tank 2 photo.
    private BufferedImage image1;

    //tank 3 photo.
    private BufferedImage image2;

    //tank 4 photo.
    private BufferedImage image3;

    //tank 5 photo.
    private BufferedImage image4;

    //tank 6 photo.
    private BufferedImage image5;

    //background photo.
    private BufferedImage background;

    //plus icon.
    private BufferedImage plus;

    //plus2 icon.
    private BufferedImage plusS;

    //2X bullet speed.
    public BufferedImage bullet2;

    //3X bullet speed.
    public BufferedImage bullet3;

    //life prize.
    public BufferedImage life;

    //laser prize.
    public BufferedImage laser;

    //shield prize.
    public BufferedImage shield;

    //prize locations.
    public ArrayList<Integer> prizeLoc;

    //real map size.
    public int realMapSize;

    //row of the map.
    public int row;

    //column of the map.
    public int col;

    //time after the tanks destroyed.
    private int deathTime;

    //render counter.
    public long renderCount = 0;

    //render counter limit.
    public long renderCountLimit = 0;

    //last X position.
    int lastX = 120;

    //last Y position.
    int lastY = 120;

    //first prize in the map.
    boolean firstPrize = true;

    //tank got the tank.
    boolean getPrize = false;

    //last prize imageIcon.
    public BufferedImage lastPrize;

    //using buffer strategy.
    private BufferStrategy bufferStrategy;

    //prizes.
    private ArrayList<Prize> prizes;

    //walls.
    private ArrayList<Wall> walls;

    //tanks.
    private ArrayList<Tank> tanks;

    //maps.
    private ArrayList<File> maps;

    //check if all walls added.
    private boolean allWallsAdded = false;

    //map.
    private File map;

    /**
     * this constructor make a new game frame with current settings.
     *
     * @param title frame title.
     */
    public GameFrame(String title) {

        super(title);
        setResizable(false);
        setBackground(Color.white);

        //make settings.
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setLayout(new BorderLayout());
        walls = new ArrayList<>();
        tanks = new ArrayList<>();
        prizeLoc = new ArrayList<>();
        prizes = new ArrayList<>();
        maps = new ArrayList<>();

        //set Controller.
        Controller.walls = walls;
        Controller.prizes = prizes;
        Controller.getPrize = getPrize;
        Controller.tanks = tanks;
        Controller.realMapSize = realMapSize;
        Controller.prizeLoc = prizeLoc;

        //add pictures.
        try {
            image1 = ImageIO.read(new File("Resources/pictures/tank_dark.png"));
            image2 = ImageIO.read(new File("Resources/pictures/tank_blue.png"));
            image3 = ImageIO.read(new File("Resources/pictures/tank_red.png"));
            image4 = ImageIO.read(new File("Resources/pictures/tank_green.png"));
            image5 = ImageIO.read(new File("Resources/pictures/tank_sand.png"));
            plus = ImageIO.read(new File("Resources/pictures/+.png"));
            plusS = ImageIO.read(new File("Resources/pictures/+s.png"));
            background = ImageIO.read(new File("Resources/pictures/bg.png"));
            bullet2 = ImageIO.read(new File("Resources/pictures/2B.png"));
            bullet3 = ImageIO.read(new File("Resources/pictures/3B.png"));
            life = ImageIO.read(new File("Resources/pictures/life.png"));
            laser = ImageIO.read(new File("Resources/pictures/laser.png"));
            shield = ImageIO.read(new File("Resources/pictures/shield.png"));

        } catch (IOException e) {
            System.out.println(e);
        }

        ImageIcon icon = new ImageIcon("Resources/pictures/Icon.png");
        this.setIconImage(icon.getImage());

        File f = new File("Resources/maps/");
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        Random random = new Random();
        int mapNum = random.nextInt(files.size());
        map = files.get(mapNum);

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

    /**
     * rotate method takes a imageIcon as a tank and rotate it with the given degrees.
     *
     * @param image   image to rotate.
     * @param degrees rotate amount.
     * @return rotated image.
     */
    public BufferedImage rotate(BufferedImage image, Double degrees) {

        // Calculate the new size of the image based on the angle of rotation
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
        // Paint the original image
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
    }

    /**
     * Rendering all game elements based on the game state.
     */

    private void doRendering(Graphics2D g2d, GameState state) {
        //increase render number.
        renderCount++;

        // Draw background
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        g2d.drawImage(background, 0, 0, null);

        //draw map details.
        g2d.setColor(Color.BLACK);
        g2d.setColor(new Color(0xD9AF8856, true));
        g2d.fillRect(0, 720, 1280, 160);
        //
        g2d.drawImage(plusS, 0, 720, null);
        g2d.drawImage(plus, 0, 776, null);
        g2d.drawImage(plus, 0, 832, null);
        //
        g2d.drawImage(plus, 1224, 720, null);
        g2d.drawImage(plus, 1224, 776, null);
        g2d.drawImage(plus, 1224, 832, null);
        //
        g2d.drawImage(plus, 56, 832, null);
        g2d.drawImage(plus, 112, 832, null);
        g2d.drawImage(plusS, 168, 832, null);
        g2d.drawImage(plus, 224, 832, null);
        g2d.drawImage(plus, 280, 832, null);
        //
        g2d.drawImage(plus, 56, 720, null);
        g2d.drawImage(plusS, 112, 720, null);
        g2d.drawImage(plus, 168, 720, null);
        g2d.drawImage(plus, 224, 720, null);
        g2d.drawImage(plus, 280, 720, null);
        //
        g2d.drawImage(plusS, 944, 832, null);
        g2d.drawImage(plus, 1000, 832, null);
        g2d.drawImage(plusS, 1056, 832, null);
        g2d.drawImage(plus, 1112, 832, null);
        g2d.drawImage(plus, 1168, 832, null);
        //
        g2d.drawImage(plus, 944, 720, null);
        g2d.drawImage(plus, 1000, 720, null);
        g2d.drawImage(plusS, 1056, 720, null);
        g2d.drawImage(plus, 1112, 720, null);
        g2d.drawImage(plus, 1168, 720, null);

        g2d.setColor(Color.black);

        //set tanks.
        setTanks(3, g2d, state);

        //set enemy.
        setEnemy(g2d, state);

        //set names.
        setName(g2d, Controller.userName, null, null,state);

        //draw the given map.
        drawMap(g2d);

        //set prizes.
        setPrize(g2d);

        //apply prize to all tanks.
        for (Tank tank : tanks) {
            tank.applyPrize();
        }

        //set bullet fire.
        state.fire(g2d);

        //make default settings.
        if (tanks.size() > 0) {
            tanks.get(0).x = state.locX;
            tanks.get(0).y = state.locY;
            tanks.get(1).x = state.pcX;
            tanks.get(1).y = state.pcY;
        }
    }

    /**
     * set the current map.
     */
    public void setMap() {
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

    /**
     * set information of each tank
     *
     * @param g2d     Graphic 2D
     * @param player1 player 1
     * @param player2 player 2
     * @param player3 player3
     */

    public void setName(Graphics2D g2d, String player1, String player2, String player3,GameState state) {
        //set player info
        player1 = Controller.userName;
        if (Controller.tankFilePath.equals("Resources/pictures/tank_blue_RS.png"))
            g2d.drawImage(image2, 500, 750, null);
        else if (Controller.tankFilePath.equals("Resources/pictures/tank_red_RS.png"))
            g2d.drawImage(image3, 500, 750, null);
        else if (Controller.tankFilePath.equals("Resources/pictures/tank_dark_RS.png"))
            g2d.drawImage(image1, 500, 750, null);
        else if (Controller.tankFilePath.equals("Resources/pictures/tank_green_RS.png"))
            g2d.drawImage(image4, 500, 750, null);

        g2d.drawString(player1, 440, 775);
        g2d.setFont(new Font("Silom",Font.BOLD,10));
        g2d.drawString(String.valueOf(tanks.get(0).getHealth())+"%",state.locX-10,state.locY);
        g2d.drawString(String.valueOf(tanks.get(0).getHealth()), 440, 800);
        g2d.drawString(tanks.get(0).getPrize(), 440, 825);

        //set enemy info.
        player3 = "enemy";
        g2d.drawImage(image5, 700, 750, null);
        g2d.drawString("enemy", 800, 775);
        g2d.drawString(String.valueOf(tanks.get(1).getHealth()), 800, 800);
        g2d.drawString(String.valueOf(tanks.get(1).getHealth())+"%",state.pcX-10,state.pcY);
        g2d.drawString(tanks.get(1).getPrize(), 800, 825);
    }

    /**
     * set enemy tank in game frame
     *
     * @param g2d   Graphic of game
     * @param state of game
     */

    public void setEnemy(Graphics2D g2d, GameState state) {
        Tank tank = new Tank("Resources/pictures/enemy.png", state.pcX, state.pcY, 0);
        tanks.add(tank);
        if (tanks.get(1).alive)
            g2d.drawImage(rotate(tank.getIcon(), state.rotateAmountPC), state.pcX, state.pcY, null);
        else if (deathTime >= 0 && !state.gameOver && !Controller.leagueGame) {
            try {
                if (deathTime < 5) {
                    BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion2.png"));
                    g2d.drawImage(bufferedImage, state.pcX, state.pcY, null);
                } else if (deathTime < 10) {
                    BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion3.png"));
                    g2d.drawImage(bufferedImage, state.pcX, state.pcY, null);
                } else if (deathTime < 15) {
                    BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion4.png"));
                    g2d.drawImage(bufferedImage, state.pcX, state.pcY, null);
                } else if (deathTime < 20) {
                    BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion5.png"));
                    g2d.drawImage(bufferedImage, state.pcX, state.pcY, null);
                } else if (deathTime < 25) {
                    BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion6.png"));
                    g2d.drawImage(bufferedImage, state.pcX, state.pcY, null);
                }
                deathTime++;
            } catch (Exception ee) {
                ee.printStackTrace();
                System.out.println("file not found");
            }

        }
        if (deathTime == 25) {
            deathTime = 0;
        }

    }

    /**
     * set player tanks
     *
     * @param numOfPlayer number of player
     * @param g2d         Graphic 2D
     * @param state       of game
     * @throws IOException reading path
     */

    public void setTanks(int numOfPlayer, Graphics2D g2d, GameState state) {
        if (numOfPlayer > 0) {
            Tank tank = new Tank(Controller.tankFilePath, state.locX, state.locY, 0);
            if (!tanks.contains(tank))
                tanks.add(tank);
            if (tanks.get(0).alive)
                g2d.drawImage(rotate(tanks.get(0).getIcon(), state.rotateAmountTank), state.locX, state.locY, null);
            else if (deathTime >= 0 && !state.gameOver) {
                try {
                    if (deathTime < 5) {
                        BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion2.png"));
                        g2d.drawImage(bufferedImage, state.locX, state.locY, null);
                    } else if (deathTime < 10) {
                        BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion3.png"));
                        g2d.drawImage(bufferedImage, state.locX, state.locY, null);
                    } else if (deathTime < 15) {
                        BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion4.png"));
                        g2d.drawImage(bufferedImage, state.locX, state.locY, null);
                    } else if (deathTime < 20) {
                        BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion5.png"));
                        g2d.drawImage(bufferedImage, state.locX, state.locY, null);
                    } else if (deathTime < 25) {
                        BufferedImage bufferedImage = ImageIO.read(new File("Resources/gifFiles/explosion6.png"));
                        g2d.drawImage(bufferedImage, state.locX, state.locY, null);
                    }
                    deathTime++;
                } catch (Exception ee) {
                    //ee.printStackTrace();
                    System.out.println("file not found!");
                }
            }
            if (deathTime == 25)
                deathTime = 0;
        }
    }

    /**
     * Draw map based of given map.txt file
     *
     * @param g2d Graphic of game
     */

    public void drawMap(Graphics2D g2d) {
        File accounts = map;
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
                                Wall wall = new Wall(currentX, currentY, 5, 5, g2d, false);
                                wall.draw();
                                if (!allWallsAdded)
                                    walls.add(wall);
                            } else if (chars[k] == '2') {
                                Wall wall = new Wall(currentX, currentY, 5, 5, g2d, true);
                                wall.draw();
                                if (!allWallsAdded)
                                    walls.add(wall);
                            }
                            currentX += 5;
                        } else {
                            if (chars[k] == '1') {
                                Wall wall = new Wall(currentX, currentY, 50, 5, g2d, false);
                                wall.draw();
                                if (!allWallsAdded)
                                    walls.add(wall);
                            } else if (chars[k] == '2') {
                                Wall wall = new Wall(currentX, currentY, 50, 5, g2d, true);
                                wall.draw();

                                if (!allWallsAdded)
                                    walls.add(wall);
                            }
                            currentX += 50;
                        }
                    }
                    if (lineCounter % 2 != 0) {
                        if (k % 2 == 0) {
                            if (chars[k] == '1') {
                                Wall wall = new Wall(currentX, currentY, 5, 50, g2d, false);
                                wall.draw();
                                if (!allWallsAdded)
                                    walls.add(wall);
                            } else if (chars[k] == '2') {
                                Wall wall = new Wall(currentX, currentY, 5, 50, g2d, true);
                                wall.draw();
                                if (!allWallsAdded)
                                    walls.add(wall);
                            }
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
            allWallsAdded = true;
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
        //create a random location for prize
        int randomLoc = (int) (Math.random() * (realMapSize));
        //create a random number for prize type
        int randomNum = (int) (Math.random() * (5));
        //check if random location is x or y
        if (randomLoc % 2 != 0)
            randomLoc = randomLoc - 1;
        //if its first prize on map
        if (firstPrize) {
            g2d.drawImage(bullet2, prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), null);
            prizes.add(new Prize(prizeLoc.get(randomLoc), prizeLoc.get(randomLoc + 1), 15, 15, "bullet2"));
            firstPrize = false;
            lastX = prizeLoc.get(randomLoc);
            lastY = prizeLoc.get(randomLoc + 1);
            renderCountLimit = renderCount + 500;
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
            renderCountLimit = renderCount + 500;
        }
    }


}


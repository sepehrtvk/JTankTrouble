import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * this class keeps tank info
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class Tank {

    //x of tank
    int x;
    //y of tank
    int y;
    //rotate amount of tank
    double rotateAmount;
    //icon of tank
    BufferedImage icon;
    GameState state;
    //keep current prize
    String prize;
    //keep  current health value
    int Health;
    //Below fields check expiration of each prize
    int shieldCounter;
    int laserCounter;
    boolean bulletEffect;
    boolean laser;
    boolean bullet2;
    boolean bullet3;
    //check if tank is alive or not
    boolean alive;
    //prize.
    int prizeExpiration=0;


    /**
     * create a new tank with given value
     *
     * @param path         of icon of tank
     * @param x            of tank
     * @param y            of tank
     * @param rotateAmount of tank
     */
    public Tank(String path, int x, int y, double rotateAmount) {
        //set default value
        prize = "empty";
        Health = Controller.tankHealth;
        //set Counter of expiration time of each prize
        shieldCounter = 0;
        laserCounter = 0;
        try {
            icon = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("File not found ");
        }
        alive = true;
        this.x = x;
        this.y = y;
        this.rotateAmount = rotateAmount;

    }

    /**
     * @return icon of tank
     */
    public BufferedImage getIcon() {
        return icon;
    }

    /**
     * @return x of tank
     */

    public int getX() {
        return x;
    }

    /**
     * @return y of tank
     */
    public int getY() {
        return y;
    }

    /**
     * @return state of tank - GameState
     */
    public GameState getState() {
        return state;
    }

    /**
     * @return Health value of tank
     */
    public int getHealth() {
        return Health;
    }

    /**
     * @param healthDamage to revalue health of tank
     */
    public void setHealth(int healthDamage) {
        Health -= healthDamage;
        if (Health <= 0) Health = 0;
    }

    /**
     * @return current prize
     */
    public String getPrize() {
        return prize;
    }

    /**
     * @param prize to set for tank
     */
    public void setPrize(String prize) {
        this.prize = prize;
    }

    /**
     * method apply each prize features
     */
    public void applyPrize() {
        //check if tank has a prize now
        if (!prize.equals("empty")) {
            if (prize.equals("life")) {
                Health += 10;
                if (Health > 100) {
                    Health = 100;
                }
                prize="empty";
            }
            if (prize.equals("shield")) {
                if (shieldCounter < 450) {
                    bulletEffect = true;
                    shieldCounter++;
                } else {
                    shieldCounter = 0;
                    bulletEffect = false;
                    prize="empty";
                }
            }
            if (prize.equals("bullet2")) {
                bullet2 = true;
                Controller.bulletSpeed = 6;
            }
            if (prize.equals("bullet3")) {
                bullet3 = true;
                Controller.bulletSpeed = 9;
            }
            if (prize.equals("laser")) {
                Controller.laser = laser;
                if (laserCounter < 150) {
                    laser = true;
                    Controller.laser = true;
                    Controller.bulletSpeed = 9;
                    laserCounter++;
                } else {
                    laserCounter = 0;
                    Controller.laser = false;
                    Controller.bulletSpeed = 4;
                    prize="empty";
                }
            }
        }
    }
}
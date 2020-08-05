import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tank {

    int x;
    int y;
    double rotateAmount;
    BufferedImage icon;
    Bullet bullet;
    GameState state;
    boolean alive;
    String prize;
    int Health;
    boolean bulletEffect;
    boolean laser;
    boolean bullet2;
    boolean bullet3;
    int shieldCounter;
    int laserCounter;

    public Tank(String path, int x, int y, double rotateAmount) {
        prize = "empty";
        Health = Controller.tankHealth;
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

    public BufferedImage getIcon() {
        return icon;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public GameState getState() {
        return state;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int healthDamage) {
        Health -=  healthDamage;
        if (Health <= 0) Health = 0;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public void applyPrize() {
        if (!prize.equals("empty")) {
            if (prize.equals("life")) {
                Health += 10;
            }
            if (Health > 100) {
                Health = 100;
            }
            if (prize.equals("shield")) {
                if (shieldCounter < 150) {
                    bulletEffect = true;
                    shieldCounter++;
                } else {
                    shieldCounter = 160;
                    bulletEffect = false;
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
                if (laserCounter < 120) {
                    laser = true;
                    Controller.laser = true;
                    Controller.bulletSpeed = 9;
                    laserCounter++;
                } else {
                    laserCounter = 130;
                    Controller.laser = false;
                    Controller.bulletSpeed = 4;
                }
            }
        }
    }
}
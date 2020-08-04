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

    public Tank(String path, int x, int y, double rotateAmount) {
        prize="empty";
        Health = 100;
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
        Health = Health - healthDamage;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public void applyPrize() {
        if(!prize.equals("empty")) {
            if (prize.equals("life"))
                Health += 10;
//        if (Health > 100)
//            Health = 100;
            if (prize.equals("shield")) {
                if (Controller.renderCountLimit - 50 == Controller.renderCount)
                    bulletEffect = true;
                else
                    bulletEffect = false;
            }
            if (prize.equals("bullet2")) {
                if (!bullet2)
                    bullet2 = true;
            }
            if (prize.equals("bullet3")) {
                if (!bullet3)
                    bullet3 = true;
            }
            if (prize.equals("laser")) {
                if (Controller.renderCountLimit - 410 == Controller.renderCount)
                    laser = false;
                else
                    laser = true;
            }
        }
    }
}
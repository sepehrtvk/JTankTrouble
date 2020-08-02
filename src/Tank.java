import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tank {

    int x;
    int y;
    double rotateAmount;
    BufferedImage icon;
    Bullets bullets;
    GameState state;
    String prize;
    int Health;

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public Tank(String path) {
        Health = 100;
        try {
            icon = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("File not found ");
        }

        bullets = new Bullets();
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

    public double getRotateAmount() {
        return rotateAmount;
    }

    public Bullets getBullets() {
        return bullets;
    }

    public int getHealth() {
        return Health;
    }

    public void setHealth(int healthDamage) {
        Health = Health - healthDamage;
    }
}

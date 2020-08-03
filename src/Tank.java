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

    public Tank(String path, int x, int y, double rotateAmount) {
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
}
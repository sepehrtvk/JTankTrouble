import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Bullet {

    int x;
    int y;
    double rotateAmountBullet;
    int damage;
    boolean alive;


    public Bullet(int x, int y, double rotateAmountBullet, int damage) {
        this.x = x;
        this.y = y;
        this.rotateAmountBullet = rotateAmountBullet;
        this.damage = damage;
        alive=true;
    }


}
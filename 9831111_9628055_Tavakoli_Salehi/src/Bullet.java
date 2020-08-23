/**
 * this class keeps bullet info
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class Bullet {
    //x of bullet
    int x;
    //y of bullet
    int y;
    //rotate amount of bullet
    double rotateAmountBullet;
    //damage of bullet
    int damage;
    //existence of bullet
    boolean alive;


    /**
     * create a new bullet with given value
     *
     * @param x                  of bullet
     * @param y                  of bullet
     * @param rotateAmountBullet of bullet
     * @param damage             of bullet
     */
    public Bullet(int x, int y, double rotateAmountBullet, int damage) {
        this.x = x;
        this.y = y;
        this.rotateAmountBullet = rotateAmountBullet;
        this.damage = damage;
        alive = true;
    }
}
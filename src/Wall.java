import java.awt.*;

/**
 * this class keeps wall info
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class Wall {
    //coordinate of wall
    private int x, y, width, height;
    //Graphic2D to draw wall
    private Graphics2D g2d;

    /**
     * constructor of each wall
     * @param x of wall
     * @param y of wall
     * @param width of wall
     * @param height of wall
     * @param g2d to draw wall
     */
    public Wall(int x, int y, int width, int height, Graphics2D g2d) {

        //set value given to constructor
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.g2d = g2d;

        g2d.fillRect(x, y, width, height);
    }

    /**
     * @return x of wall
     */
    public int getX() {
        return x;
    }

    /**
     * @return y of wall
     */
    public int getY() {
        return y;
    }

    /**
     * @return width of wall
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return Height of wall
     */
    public int getHeight() {
        return height;
    }
}

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * this class keeps wall info
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */

public class Wall {

    //coordinate of wall.
    private int x, y, width, height;

    //Graphic2D to draw wall.
    private Graphics2D g2d;

    //check if the wall is destructive.
    private boolean destructiveWall;

    /**
     * constructor of each wall
     *
     * @param x               of wall
     * @param y               of wall
     * @param width           of wall
     * @param height          of wall
     * @param g2d             to draw wall
     * @param destructiveWall destructive Wall
     */
    public Wall(int x, int y, int width, int height, Graphics2D g2d, boolean destructiveWall) {
        //set value given to constructor.
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.g2d = g2d;
        this.destructiveWall = destructiveWall;

    }

    /**
     * this draw method draws the wall.
     */
    public void draw() {
        if(!destructiveWall)
        g2d.setColor(Color.darkGray);
        else g2d.setColor(Color.gray);
        if(height!=width){
            RoundRectangle2D roundRectangle2D = new RoundRectangle2D.Double(x, y, width, height,3,3);
            g2d.draw(roundRectangle2D);
        } else g2d.drawOval(x, y, width, height);

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

    /**
     * check if the wall is destructible.
     *
     * @return
     */
    public boolean isDestructiveWall() {
        return destructiveWall;
    }

}

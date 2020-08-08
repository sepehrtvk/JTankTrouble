import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * keep prize info
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class Prize {
    //x of prize
    int x;
    //y of prize
    int y;
    //width of prize
    int width;
    //height of prize
    int height;
    //name of prize
    String name;

    /**
     * creat a new prize
     * @param x of prize
     * @param y of prize
     * @param width  pof prize
     * @param height of prize
     * @param name of prize
     */
    public Prize(int x, int y, int width, int height, String name) {
        //set given value to constructor as default value
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
    }

    /**
     *
     * @return x of prize
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @return y of tank
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @return width of prize
     */
    public int getWidth() {
        return width;
    }

    /**
     *
     * @return name of prize
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return height of prize
     */
    public int getHeight() {
        return height;
    }

}


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * this class link between classes and their fields
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */

public class Controller {
    //redefine some variable as static
    public static String userName;
    public static Graphics2D g2d;
    public static int row;
    public static int col;
    public static int realMapSize;
    public static ArrayList<Wall> walls;
    public static ArrayList<Prize> prizes;
    public static ArrayList<Tank> tanks;
    public static ArrayList<Integer> prizeLoc;
    public static boolean getPrize;
    public static int bulletSpeed;
    public static boolean laser;
    public static int bulletDamage;
    public static int tankHealth;
    public static int wallsHealth;
    public static boolean engineStarted;
    public static String tankFilePath="Resources/pictures/tank_blue_RS.png";
    public static boolean leagueGame=false;


}

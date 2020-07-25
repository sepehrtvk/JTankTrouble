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

    public Tank(String path){
        try {
            icon = ImageIO.read(new File(path));
        }
        catch (IOException e){
            System.out.println("File not found ");
        }

        bullets=new Bullets();
    }

    public BufferedImage getIcon(){
        return icon;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public GameState getState() {
        return state;
    }
}

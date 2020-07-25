import java.awt.image.BufferedImage;
import java.sql.BatchUpdateException;

public class Bullets {

    int x;
    int y ;
    double angelWithHorizon;
    BufferedImage icon;

    public Bullets(){

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getAngelWithHorizon() {
        return angelWithHorizon;
    }

    public BufferedImage getIcon() {
        return icon;
    }
}

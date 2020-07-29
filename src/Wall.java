import java.awt.*;

public class Wall extends Rectangle {

    private int x, y, width, height;
    private Graphics2D g2d;

    public Wall(int x, int y, int width, int height, Graphics2D g2d) {

        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.g2d = g2d;

        g2d.fillRect(x, y, width, height);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return x+" "+y;
    }
}

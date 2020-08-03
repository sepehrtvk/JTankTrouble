import java.awt.*;

public class Wall {

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

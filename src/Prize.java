import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Prize {
    int x;
    int y;
    int width;
    int height;
    String name;

    public Prize(int x, int y, int width, int height, String name) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }
    //    public static  void setPrize(GameFrame gameFrame, Graphics2D g2d){
//        int randomLoc = (int) (Math.random() * (gameFrame.realMapSize));
//        int randomNum = (int) (Math.random() * (5));
//
//        if (randomLoc % 2 != 0)
//            randomLoc = randomLoc - 1;
//        if (gameFrame.firstPrize) {
//            g2d.drawImage(gameFrame.bullet2, gameFrame.prizeLoc.get(randomLoc), gameFrame.prizeLoc.get(randomLoc + 1), null);
//            gameFrame.firstPrize = false;
//            gameFrame.lastX = gameFrame.prizeLoc.get(randomLoc);
//            gameFrame.lastY = gameFrame.prizeLoc.get(randomLoc + 1);
//            gameFrame.renderCountLimit = gameFrame.renderCount + 90;
//            gameFrame.lastPrize = gameFrame.bullet2;
//        } else if (gameFrame.renderCount != gameFrame.renderCountLimit) {
//            g2d.drawImage(gameFrame.lastPrize, gameFrame.lastX, gameFrame.lastY, null);
//        } else {
//            if(randomNum==1) {
//                g2d.drawImage(gameFrame.shield, gameFrame.prizeLoc.get(randomLoc), gameFrame.prizeLoc.get(randomLoc + 1), null);
//                gameFrame.lastPrize = gameFrame.shield;
//            } if(randomNum==0) {
//                g2d.drawImage(gameFrame.life, gameFrame.prizeLoc.get(randomLoc), gameFrame.prizeLoc.get(randomLoc + 1), null);
//                gameFrame.lastPrize = gameFrame.life;
//            } if(randomNum==3) {
//                g2d.drawImage(gameFrame.bullet3, gameFrame.prizeLoc.get(randomLoc), gameFrame.prizeLoc.get(randomLoc + 1), null);
//                gameFrame.lastPrize = gameFrame.bullet3;
//            } if(randomNum==2) {
//                g2d.drawImage(gameFrame.laser, gameFrame.prizeLoc.get(randomLoc), gameFrame.prizeLoc.get(randomLoc + 1), null);
//                gameFrame.lastPrize = gameFrame.laser;
//            } if(randomNum==4) {
//                g2d.drawImage(gameFrame.bullet2, gameFrame.prizeLoc.get(randomLoc), gameFrame.prizeLoc.get(randomLoc + 1), null);
//                gameFrame.lastPrize = gameFrame.bullet2;
//            }
//            gameFrame.lastX = gameFrame.prizeLoc.get(randomLoc);
//            gameFrame.lastY = gameFrame.prizeLoc.get(randomLoc + 1);
//            gameFrame.renderCountLimit = gameFrame.renderCount + 90;
//        }
//    }
}

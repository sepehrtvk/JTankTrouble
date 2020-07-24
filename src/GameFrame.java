/*** In The Name of Allah ***/

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering,
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 *
 * @author Seyed Mohammad Ghaffarian
 */
public class GameFrame extends JFrame {

    public static final int GAME_HEIGHT = 800;// 720p game resolution
    //720
    public static final int GAME_WIDTH = 16 * 720 / 9;  // wide aspect ratio

    //uncomment all /*...*/ in the class for using Tank icon instead of a simple circle
    private BufferedImage image1;
    private BufferedImage image2;
    private BufferedImage image3;
    private  BufferedImage image4;
    private BufferedImage image5;

    private BufferStrategy bufferStrategy;

    private JPanel PlayerState;

    public GameFrame(String title) {

        super(title);
        setBackground(Color.white);
        setResizable(false);
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setLayout(new BorderLayout());
        PlayerState=new JPanel();
        PlayerState.setBackground(Color.pink);
        add(PlayerState,BorderLayout.SOUTH);

        try{
            image1 = ImageIO.read(new File("Tank_dark.png"));
            image2=ImageIO.read(new File("Tank_blue.png"));
            image3=ImageIO.read(new File("Tank_red.png"));
            image4=ImageIO.read(new File("Tank_green.png"));
            image5=ImageIO.read(new File("Tank_sand.png"));
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    /**
     * This must be called once after the JFrame is shown:
     *    frame.setVisible(true);
     * and before any rendering is started.
     */
    public void initBufferStrategy() {
        // Triple-buffering
        createBufferStrategy(3);
        bufferStrategy = getBufferStrategy();
    }


    /**
     * Game rendering with triple-buffering using BufferStrategy.
     */
    public void render(GameState state) {
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
                try {
                    doRendering(graphics, state);

                } finally {
                    // Dispose the graphics
                    graphics.dispose();
                }
                // Repeat the rendering if the drawing buffer contents were restored
            } while (bufferStrategy.contentsRestored());

            // Display the buffer
            bufferStrategy.show();
            // Tell the system to do the drawing NOW;
            // otherwise it can take a few extra ms and will feel jerky!
            Toolkit.getDefaultToolkit().sync();

            // Repeat the rendering if the drawing buffer was lost
        } while (bufferStrategy.contentsLost());
    }


    public BufferedImage rotate(BufferedImage image, Double degrees) {
        // Calculate the new size of the image based on the angle of rotaion
        double radians = Math.toRadians(degrees);
        double sin = Math.abs(Math.sin(radians));
        double cos = Math.abs(Math.cos(radians));
        int newWidth = (int) Math.round(image.getWidth() * cos + image.getHeight() * sin);
        int newHeight = (int) Math.round(image.getWidth() * sin + image.getHeight() * cos);

        // Create a new image
        BufferedImage rotate = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotate.createGraphics();
        // Calculate the "anchor" point around which the image will be rotated
        int x = (newWidth - image.getWidth()) / 2;
        int y = (newHeight - image.getHeight()) / 2;
        // Transform the origin point around the anchor point
        AffineTransform at = new AffineTransform();
        at.setToRotation(radians, x + (image.getWidth() / 2), y + (image.getHeight() / 2));
        at.translate(x, y);
        g2d.setTransform(at);
        // Paint the originl image
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return rotate;
    }

    /**
     * Rendering all game elements based on the game state.
     */
    private void doRendering(Graphics2D g2d, GameState state) {
        // Draw background
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        // Draw ball
        g2d.setColor(Color.BLACK);
        //g2d.fillOval(state.locX, state.locY, state.diam, state.diam);
        g2d.setColor(Color.lightGray);
        g2d.fillRect(0,640,1280,160);
       // g2d.drawImage(image1,state.locX,state.locY,null);
        g2d.setColor(Color.black);
        g2d.drawImage(rotate(image1,state.rotateAmount),state.locX,state.locY,null);
        File accounts = new File("map.txt");
        setName(g2d,"narges","sara","bardia");

        try (Scanner scanner = new Scanner(new FileReader(accounts))) {
            int lineCounter=0;
            int currentX = 30;
            int currentY = 70;

            while (scanner.hasNext()) {

                char[] chars = scanner.next().toCharArray();

                for (int k = 0; k < chars.length; k++) {

                    if (lineCounter % 2 == 0) {
                        if (k % 2 == 0) {
                            if (chars[k] == '1')
                                g2d.fillRect(currentX, currentY, 5, 5);
                            currentX += 5;
                        } else {
                            if (chars[k] == '1')
                                g2d.fillRect(currentX, currentY, 50, 5);
                            currentX += 50;

                        }
                    }
                    if (lineCounter % 2 != 0) {
                        if (k % 2 == 0) {
                            if (chars[k] == '1')
                                g2d.fillRect(currentX, currentY, 5, 50);
                            currentX += 5;
                        } else {
                            if (chars[k] == '1')
                                g2d.fillRect(currentX, currentY, 50, 50);
                            currentX += 50;

                        }
                    }
                }
                currentX = 30;
                if (lineCounter % 2 == 0) currentY += 5;
                else currentY += 50;
                lineCounter++;

            }
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void  setName(Graphics2D g2d,String player1,String player2,String player3){
        if(player1!=null) {
            g2d.drawImage(image2, 150, 670, null);
            player1="narges";
            g2d.drawString(player1,160,665);
        }
        if(player2!=null) {
            g2d.drawImage(image3, 450, 670, null);
            g2d.drawString(player2,455,665);
        }

        if(player3!=null) {
            g2d.drawImage(image4, 750, 670, null);
            g2d.drawString(player3,755,665);
        }

        g2d.drawImage(image5,1050,670,null);
    }

}

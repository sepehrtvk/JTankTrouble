import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        //new LoginFrame();
        //new MainFrame();
        //new SettingFrame();
        //new OnlineModeFrame();
        //new CreateNewGameFrame("Make");
//        JFrame jFrame = new JFrame();
//        jFrame.setBounds(500,500,700,400);
//        jFrame.add(new Map());
//        jFrame.setVisible(true);


        // Initialize the global thread-pool
        ThreadPool.init();

        // Show the game menu ...

        // After the player clicks 'PLAY' ...
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GameFrame frame = null;
                try {
                    frame = new GameFrame("JTankTrouble");
                } catch (AWTException | IOException e) {
                    e.printStackTrace();
                }
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
            }
        });
    }


}


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * the OfflineModeFrame class extends the JFrame and used to show offline game setting and
 * choose game mode.
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class OfflineModeFrame extends JFrame {

    //deathMatch icon.
    private ImageIcon deathMatchIcon;

    //league icon.
    private ImageIcon leagueIcon;

    //deathMatch button.
    private JButton deathMatchButton;

    //league button.
    private JButton leagueButton;

    /**
     * make the offline mode.
     *
     * @param title title of the game.
     */
    public OfflineModeFrame(String title) {

        super(title);
        setResizable(false);
        setLayout(new FlowLayout());
        setBounds(650, 450, 400, 165);

        //set icons.
        deathMatchIcon = new ImageIcon("Resources/pictures/deathMatch.png");
        leagueIcon = new ImageIcon("Resources/pictures/league.png");

        deathMatchButton = new JButton(deathMatchIcon);
        //add action listener.
        deathMatchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GameFrame frame = new GameFrame("J Tank Trouble");
                frame.setMap();
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
                new EngineStartFrame("Engine Start ");
            }
        });
        //league button.
        leagueButton = new JButton(leagueIcon);
        leagueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Controller.leagueGame=true;
                GameFrame frame = new GameFrame("J Tank Trouble");
                frame.setMap();
                frame.setLocationRelativeTo(null); // put frame at center of screen
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.initBufferStrategy();
                // Create and execute the game-loop
                GameLoop game = new GameLoop(frame);
                game.init();
                ThreadPool.execute(game);
                // and the game starts ...
                new EngineStartFrame("Engine Start ");

            }
        });

        //add items.
        add(deathMatchButton);
        add(leagueButton);

        //show settings.
        setVisible(true);


    }
}

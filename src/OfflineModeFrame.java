import javax.swing.*;
import java.awt.*;

public class OfflineModeFrame extends JFrame {

    private ImageIcon deathMatchIcon;
    private ImageIcon leagueIcon;
    private JButton deathMatchButton;
    private JButton leagueButton;


    public OfflineModeFrame(String title) {

        super(title);
        setResizable(false);
        setLayout(new FlowLayout());
        setBounds(650, 450, 400, 165);

        deathMatchIcon = new ImageIcon("pictures/deathMatch.png");
        leagueIcon = new ImageIcon("pictures/league.png");
        deathMatchButton = new JButton(deathMatchIcon);
        leagueButton = new JButton(leagueIcon);

        add(deathMatchButton);
        add(leagueButton);
        setVisible(true);


    }
}

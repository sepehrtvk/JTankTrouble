import javax.swing.*;
import java.awt.*;

public class OnlineModeFrame extends JFrame {
    private JComboBox serverBox;
    private JPanel serverChoosePanel;
    private JPanel gamesPanel;
    private JButton playButton;
    private JButton createGame;

    public OnlineModeFrame() {
        setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new BorderLayout());
        setBounds(400, 200, 900, 600);
        initServerList();
        initGamesPanel();
        setVisible(true);

    }

}

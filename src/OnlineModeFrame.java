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
    public void initServerList() {
        serverChoosePanel = new JPanel();
        serverChoosePanel.setLayout(new FlowLayout());

        serverBox = new JComboBox();
        serverBox.addItem("Server 1");
        serverBox.addItem("Server 2");
        serverBox.addItem("Server 3");

        playButton = new JButton("Play");
        serverChoosePanel.add(serverBox);
        serverChoosePanel.add(playButton);

        createGame = new JButton("Create New Game");
        createGame.addActionListener(new CreateNewGameAction());
        serverChoosePanel.add(createGame);
        add(serverChoosePanel, BorderLayout.NORTH);

    }

}

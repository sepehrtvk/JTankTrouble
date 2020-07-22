import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

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

    public void initGamesPanel() {
        gamesPanel = new JPanel();
        gamesPanel.setLayout(new GridLayout(4, 2, 3, 3));

        File accounts = new File("Games.txt");
        try (Scanner scanner = new Scanner(new FileReader(accounts))) {

            while (scanner.hasNextLine()) {
                String account = "";
                account += scanner.nextLine() + " ";
                String[] inputs = account.split(" ");
                showGames(gamesPanel, inputs[0], inputs[1], inputs[2], inputs[3], inputs[4], inputs[5], inputs[6], inputs[7]);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        add(gamesPanel, BorderLayout.CENTER);
    }


}

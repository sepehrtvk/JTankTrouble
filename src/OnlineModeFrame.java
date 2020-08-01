import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public void showGames(JPanel panel, String gameName, String gameMode, String finishMode, String limitNumber, String connectedNumber, String tankHealth, String shotDamage, String wallHealth) {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new BorderLayout());
        JCheckBox jCheckBox = new JCheckBox("Join");
        JTextArea jTextArea = new JTextArea();
        jTextArea.setLineWrap(true);
        jTextArea.setEditable(false);
        jTextArea.setFont(new Font("Arial", Font.CENTER_BASELINE, 13));
        jTextArea.append("Game Name : " + gameName);
        jTextArea.append("\nGame Mode : " + gameMode);
        jTextArea.append("\nGame Finish Mode : " + finishMode);
        jTextArea.append("\nLimit Number : " + limitNumber);
        jTextArea.append("\nConnected Number : " + connectedNumber);
        jTextArea.append("\nTank Health Amount : " + tankHealth);
        jTextArea.append("\nShot Damage : " + shotDamage);
        jTextArea.append("\nDestructible Walls Health : " + wallHealth);
        jTextArea.setBackground(Color.YELLOW);
        newPanel.add(jTextArea, BorderLayout.CENTER);
        newPanel.add(jCheckBox, BorderLayout.EAST);
        newPanel.setBackground(Color.yellow);
        newPanel.setOpaque(true);
        newPanel.setBorder(new LineBorder(Color.red, 2));
        panel.add(newPanel);

    }

    class CreateNewGameAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new CreateNewGameFrame("Make A New Game");
        }
    }
}

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class SettingFrame extends JFrame {

    private JPanel settingPanel;
    private JPanel serverPanel;
    private JPanel gameDetailsPanel;
    private JPanel userInformationPanel;
    private JLabel tankLabel;
    private JLabel tankPhoto;
    private ImageIcon tank;


    public SettingFrame() {

        setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        setBounds(400, 200, 900, 600);
        initUserInformationPanel();
        initSettingPanel();

        setVisible(true);
    }
    public void initUserInformationPanel() {

        userInformationPanel = new JPanel();
        userInformationPanel.setBackground(Color.darkGray);
        userInformationPanel.setLayout(new GridLayout(5, 2));


        File accounts = new File("Accounts.txt");
        try (Scanner scanner = new Scanner(new FileReader(accounts))) {
            String account;
            while (scanner.hasNextLine()) {
                account = scanner.nextLine() + "\n";
                String[] strings = account.split(" ");
                if (strings[0].equals(Controller.userName)) {

                    textAndLabel("pictures/user.png", Controller.userName);

                    textAndLabel("pictures/playedTime.png", strings[3]);

                    textAndLabel("pictures/offlineGame.png", "Win = " + strings[4] + "      |     Lost = " + strings[5]);

                    textAndLabel("pictures/onlineGame.png", "Win = " + strings[6] + "      |     Lost = " + strings[7]);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ImageIcon userTankIcon = new ImageIcon("pictures/userTank.png");
        tankLabel = new JLabel(userTankIcon);
        userInformationPanel.add(tankLabel);
        tank = new ImageIcon("pictures/icon.png");
        tankPhoto = new JLabel(tank);
        tankPhoto.setBorder(new LineBorder(Color.GRAY, 2));
        userInformationPanel.add(tankPhoto);

        add(userInformationPanel);

    }
    public void textAndLabel(String pngFile, String textField) {

        ImageIcon icon = new ImageIcon(pngFile);
        JLabel label = new JLabel(icon);
        userInformationPanel.add(label);

        JTextField text = new JTextField(textField);
        text.setEditable(false);
        text.setBackground(Color.darkGray);
        text.setBorder(new LineBorder(Color.GRAY, 2));
        text.setHorizontalAlignment(JTextField.CENTER);
        text.setForeground(Color.WHITE);
        userInformationPanel.add(text);

    }
    public void initSettingPanel() {

        settingPanel = new JPanel();
        gameDetailsPanel = new JPanel();
        serverPanel = new JPanel();
        serverPanel.setBackground(Color.darkGray);
        serverPanel.setLayout(new BorderLayout());
        serverPanel.setBorder(new LineBorder(Color.GRAY, 2));
        gameDetailsPanel.setBackground(Color.darkGray);
        gameDetailsPanel.setLayout(new GridLayout(3, 2));
        settingPanel.setBackground(Color.darkGray);
        settingPanel.setLayout(new BorderLayout());
        sliderAndIcon("pictures/tankHealth.png", 40);
        sliderAndIcon("pictures/shotDamage.png", 80);
        sliderAndIcon("pictures/DestructibleWall.png", 20);
        initServerPanel();
        add(settingPanel);
        settingPanel.add(gameDetailsPanel, BorderLayout.NORTH);
        settingPanel.add(serverPanel, BorderLayout.CENTER);
    }




}

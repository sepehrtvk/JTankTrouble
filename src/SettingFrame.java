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



}

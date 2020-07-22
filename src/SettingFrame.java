import javax.swing.*;
import java.awt.*;

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

}

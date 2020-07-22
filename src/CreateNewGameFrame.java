import javax.swing.*;
import java.awt.*;

public class CreateNewGameFrame extends JFrame {
    private JPanel settingPanel;
    private JPanel gameDetailsPanel;
    private JPanel optionPanel;
    private JTextField gameNameText;
    private JComboBox gameFinishMode;
    private JComboBox playerNumber;
    private JComboBox gameMode;
    private JButton okButton;
    private JSlider tankHealthSlider;
    private JSlider shotDamageSlider;
    private JSlider destructibleWallSlider;
    private String gameDetails = "";

    public CreateNewGameFrame(String title) {

        super(title);
        setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        setBounds(600, 200, 500, 570);
        initSettingPanel();
        initNewGame();

        setVisible(true);

    }

}

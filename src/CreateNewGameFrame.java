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
    public void initSettingPanel() {

        settingPanel = new JPanel();
        gameDetailsPanel = new JPanel();
        gameDetailsPanel.setBackground(Color.white);
        gameDetailsPanel.setLayout(new GridLayout(3, 2));
        settingPanel.setBackground(Color.white);
        settingPanel.setLayout(new BorderLayout());

        tankHealthSlider = new JSlider(10, 100, 40);
        shotDamageSlider = new JSlider(10, 100, 80);
        destructibleWallSlider = new JSlider(10, 100, 20);
        sliderAndIcon(tankHealthSlider, "pictures/tankHealth.png");
        sliderAndIcon(shotDamageSlider, "pictures/shotDamage.png");
        sliderAndIcon(destructibleWallSlider,"pictures/DestructibleWall.png");

        add(settingPanel);
        settingPanel.add(gameDetailsPanel, BorderLayout.CENTER);
    }

}

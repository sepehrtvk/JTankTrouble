import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        sliderAndIcon(destructibleWallSlider, "pictures/DestructibleWall.png");

        add(settingPanel);
        settingPanel.add(gameDetailsPanel, BorderLayout.CENTER);
    }

    public void sliderAndIcon(JSlider slider, String iconName) {

        slider.setMajorTickSpacing(10);
        slider.setBackground(Color.white);
        slider.setBorder(new LineBorder(Color.GRAY, 2));
        slider.setForeground(Color.darkGray);
        slider.setOpaque(true);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        ImageIcon icon = new ImageIcon(iconName);
        JLabel label = new JLabel(icon);
        gameDetailsPanel.add(label);
        gameDetailsPanel.add(slider);


    }

    public void initNewGame() {

        gameNameText = new JTextField("Name");
        gameNameText.setBorder(new TitledBorder("Game Name : "));
        settingPanel.add(gameNameText, BorderLayout.NORTH);

        optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(2, 4));
        optionPanel.setBackground(Color.WHITE);

        gameMode = new JComboBox();
        gameMode.addItem("SinglePlayer");
        gameMode.addItem("MultiPlayer");


        gameFinishMode = new JComboBox();
        gameFinishMode.addItem("DeathMatch");
        gameFinishMode.addItem("League");

        playerNumber = new JComboBox();
        playerNumber.addItem("2");
        playerNumber.addItem("4");
        playerNumber.addItem("6");
        playerNumber.addItem("8");

        okButton = new JButton("OK");
        okButton.addActionListener(new OkButtonAction());

        optionPanel.add(new JLabel("     Game Mode"));
        optionPanel.add(new JLabel("      Finish Mode"));
        optionPanel.add(new JLabel("   Player Number"));
        optionPanel.add(new JLabel(""));
        optionPanel.add(gameMode);
        optionPanel.add(gameFinishMode);
        optionPanel.add(playerNumber);
        optionPanel.add(okButton);

        settingPanel.add(optionPanel, BorderLayout.SOUTH);

    }
    class OkButtonAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            gameDetails = tankHealthSlider.getValue() + " " + shotDamageSlider.getValue() + " " + destructibleWallSlider.getValue();
            String data = gameNameText.getText() + " " + gameMode.getSelectedItem().toString() + " " + gameFinishMode.getSelectedItem().toString() + " " + playerNumber.getSelectedItem().toString() + " 0 " + gameDetails;
            writeToFile(data);
            dispose();
            new OnlineModeFrame();
        }
    }


}

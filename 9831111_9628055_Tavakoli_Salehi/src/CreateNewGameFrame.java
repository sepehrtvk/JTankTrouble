import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

/**
 * this CreateNewGameFrame class which extends JFrame , makes a new online game with the given details.
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class CreateNewGameFrame extends JFrame {

    //setting panel.
    private JPanel settingPanel;

    //game details panel.
    private JPanel gameDetailsPanel;

    //options to choose panel
    private JPanel optionPanel;

    //game name text.
    private JTextField gameNameText;

    //game choose finish mode.
    private JComboBox gameFinishMode;

    //number of the players.
    private JComboBox playerNumber;

    //game mode.
    private JComboBox gameMode;

    //ok button.
    private JButton okButton;

    //tank health slider.
    private JSlider tankHealthSlider;

    //shot damage slider.
    private JSlider shotDamageSlider;

    //walls health.
    private JSlider destructibleWallSlider;

    //game details of the match.
    private String gameDetails = "";

    /**
     * this constructor makes a new online game .
     *
     * @param title name of the game.
     */
    public CreateNewGameFrame(String title) {

        //set frame settings.
        super(title);
        setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        setBounds(600, 200, 500, 570);
        initSettingPanel();
        initNewGame();

        //show frame.
        setVisible(true);

    }

    /**
     * this initSettingPanel makes the setting panel and gets the setting to start the game.
     */
    public void initSettingPanel() {

        //make the panel.
        settingPanel = new JPanel();
        gameDetailsPanel = new JPanel();
        gameDetailsPanel.setBackground(Color.white);
        gameDetailsPanel.setLayout(new GridLayout(3, 2));
        settingPanel.setBackground(Color.white);
        settingPanel.setLayout(new BorderLayout());

        //set items.
        tankHealthSlider = new JSlider(10, 100, 40);
        shotDamageSlider = new JSlider(10, 100, 80);
        destructibleWallSlider = new JSlider(10, 100, 20);
        sliderAndIcon(tankHealthSlider, "Resources/pictures/tankHealth.png");
        sliderAndIcon(shotDamageSlider, "Resources/pictures/shotDamage.png");
        sliderAndIcon(destructibleWallSlider, "Resources/pictures/DestructibleWall.png");

        //add panel.
        add(settingPanel);
        settingPanel.add(gameDetailsPanel, BorderLayout.CENTER);
    }

    /**
     * this sliderAndIcon method sets the sliders.
     *
     * @param slider   slider to set.
     * @param iconName name of the imageIcon.
     */
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

    /**
     * this initNewGame method makes a new online game with given details and settings.
     */
    public void initNewGame() {

        gameNameText = new JTextField("Name");
        gameNameText.setBorder(new TitledBorder("Game Name : "));
        settingPanel.add(gameNameText, BorderLayout.NORTH);

        optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(2, 4));
        optionPanel.setBackground(Color.WHITE);

        //set game mode.
        gameMode = new JComboBox();
        gameMode.addItem("SinglePlayer");
        gameMode.addItem("MultiPlayer");

        //set finish mode.
        gameFinishMode = new JComboBox();
        gameFinishMode.addItem("DeathMatch");
        gameFinishMode.addItem("League");

        //player numbers.
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

    /**
     * ok button action listener to set settings.
     */
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

    /**
     * write details to the file.
     *
     * @param dataToWrite data want to write on file.
     */
    public void writeToFile(String dataToWrite) {
        File file = new File("Resources/  Games.txt");
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(dataToWrite + "\n");
        } catch (Exception ex) {
            System.out.println("No game found ! ");
            ex.printStackTrace();
        }
    }


}

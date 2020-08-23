import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * this SettingFrame extends JFrame and used to change settings for each match.
 * here we can set shot damage , tank health and the walls health.
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class SettingFrame extends JFrame {

    //settings panel.
    private JPanel settingPanel;

    //server panel.
    private JPanel serverPanel;

    //game details panel.
    private JPanel gameDetailsPanel;

    //user information panel.
    private JPanel userInformationPanel;

    //tank label.
    private JLabel tankLabel;

    //tank health slider.
    private JSlider tankHealthSlider;

    //bullet shot damage slider..
    private JSlider shotDamageSlider;

    //walls health slider.
    private JSlider wallsHealthSlider;

    /**
     * this SettingFrame constructor make the setting main frame and add items to it.
     */
    public SettingFrame() {

        //set JFrame .
        setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new GridLayout(1, 2));
        setBounds(400, 200, 900, 600);

        //init user information panel.
        initUserInformationPanel();

        //init settings panel.
        initSettingPanel();

        //show the frame.
        setVisible(true);
    }

    /**
     * this initUserInformationPanel makes the information panel and reads from the accounts file.
     */
    public void initUserInformationPanel() {

        //make the panel.
        userInformationPanel = new JPanel();
        userInformationPanel.setBackground(Color.darkGray);
        userInformationPanel.setLayout(new GridLayout(5, 2));

        //read from file.
        File accounts = new File("Resources/Accounts.txt");
        try (Scanner scanner = new Scanner(new FileReader(accounts))) {
            String account;
            while (scanner.hasNextLine()) {
                account = scanner.nextLine() + "\n";
                String[] strings = account.split(" ");
                if (strings[0].equals(Controller.userName)) {

                    textAndLabel("Resources/pictures/user.png", Controller.userName);

                    textAndLabel("Resources/pictures/playedTime.png", strings[3]);

                    textAndLabel("Resources/pictures/offlineGame.png", "Win = " + strings[4] + "      |     Lost = " + strings[5]);

                    textAndLabel("Resources/pictures/onlineGame.png", "Win = " + strings[6] + "      |     Lost = " + strings[7]);

                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //tank photo.
        ImageIcon userTankIcon = new ImageIcon("Resources/pictures/userTank.png");
        tankLabel = new JLabel(userTankIcon);
        userInformationPanel.add(tankLabel);

        JPanel jPanel = new JPanel();
        jPanel.setBackground(Color.gray);
        jPanel.setBorder(new LineBorder(Color.GRAY, 2));
        jPanel.setLayout(new BorderLayout());
        JComboBox jComboBox = new JComboBox();
        jComboBox.addItem("Blue");
        jComboBox.addItem("Red");
        jComboBox.addItem("Dark");
        jComboBox.addItem("Green");
        jComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getItem().toString().equals("Blue")){
                    Controller.tankFilePath="Resources/pictures/tank_blue_RS.png";
                    ImageIcon icon = new ImageIcon("Resources/pictures/tank_blue.png");
                    JLabel jLabel = new JLabel(icon);
                    if(jPanel.getComponents().length==2)
                        jPanel.remove(1);
                    jPanel.add(jLabel,BorderLayout.CENTER);
                    jPanel.repaint();
                    jPanel.revalidate();
                }
                if(e.getItem().toString().equals("Red")){
                    Controller.tankFilePath="Resources/pictures/tank_red_RS.png";
                    ImageIcon icon = new ImageIcon("Resources/pictures/tank_red.png");
                    JLabel jLabel = new JLabel(icon);
                    jPanel.remove(1);
                    jPanel.add(jLabel,BorderLayout.CENTER);
                    jPanel.repaint();
                    jPanel.revalidate();

                }
                if(e.getItem().toString().equals("Dark")){
                    Controller.tankFilePath="Resources/pictures/tank_dark_RS.png";
                    ImageIcon icon = new ImageIcon("Resources/pictures/tank_dark.png");
                    JLabel jLabel = new JLabel(icon);
                    jPanel.remove(1);
                    jPanel.add(jLabel,BorderLayout.CENTER);
                    jPanel.repaint();
                    jPanel.revalidate();

                }
                if(e.getItem().toString().equals("Green")){
                    Controller.tankFilePath="Resources/pictures/tank_green_RS.png";
                    ImageIcon icon = new ImageIcon("Resources/pictures/tank_green.png");
                    JLabel jLabel = new JLabel(icon);
                    jPanel.remove(1);
                    jPanel.add(jLabel,BorderLayout.CENTER);
                    jPanel.repaint();
                    jPanel.revalidate();

                }
            }
        });
        jPanel.add(jComboBox,BorderLayout.NORTH);
        userInformationPanel.add(jPanel);
        repaint();
        revalidate();


        //add panel.
        add(userInformationPanel);

    }

    /**
     * the textAndLabel method takes the file path and the text , then add them to the setting panel.
     *
     * @param pngFile   the photo file.
     * @param textField text.
     */
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

    /**
     * this initSettingPanel method makes the setting panel and add server , sliders and buttons to it.
     */
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
        tankHealthSlider = new JSlider(10, 100, 40);
        shotDamageSlider = new JSlider(10, 100, 80);
        wallsHealthSlider = new JSlider(10, 100, 20);

        //set sliders.
        sliderAndIcon(tankHealthSlider, "Resources/pictures/tankHealth.png");
        sliderAndIcon(shotDamageSlider, "Resources/pictures/shotDamage.png");
        sliderAndIcon(wallsHealthSlider, "Resources/pictures/DestructibleWall.png");

        //init server box.
        initServerPanel();

        //add items.
        add(settingPanel);
        settingPanel.add(gameDetailsPanel, BorderLayout.NORTH);
        settingPanel.add(serverPanel, BorderLayout.CENTER);
    }

    /**
     * this initServerPanel method makes the server list to choose one from it.
     */
    public void initServerPanel() {
        JComboBox serverBox = new JComboBox();
        serverBox.addItem("Server 1");
        serverBox.addItem("Server 2");
        serverBox.addItem("Server 3");
        JButton okButton = new JButton(" OK ");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Controller.bulletDamage = shotDamageSlider.getValue();
                Controller.tankHealth = tankHealthSlider.getValue();
                Controller.wallsHealth = wallsHealthSlider.getValue();
                dispose();
            }
        });

        //add items.
        serverPanel.add(serverBox, BorderLayout.CENTER);
        serverPanel.add(okButton, BorderLayout.EAST);
    }

    /**
     * the sliderAndIcon method sets the sliders with the given name and JSlider.
     *
     * @param slider   slider to set.
     * @param iconName name of the file.
     */
    public void sliderAndIcon(JSlider slider, String iconName) {

        //set sliders.
        slider.setMajorTickSpacing(10);
        slider.setBackground(Color.darkGray);
        slider.setBorder(new LineBorder(Color.GRAY, 2));
        slider.setForeground(Color.WHITE);
        slider.setOpaque(true);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        ImageIcon icon = new ImageIcon(iconName);
        JLabel label = new JLabel(icon);

        //add sliders.
        gameDetailsPanel.add(label);
        gameDetailsPanel.add(slider);

    }


}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * this MainFrame class extends JFrame and shows the main menu of the j tank trouble game.
 * here we have 4 options to choose , online game , offline game , setting and quit.
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class MainFrame extends JFrame {

    //main picture.
    private ImageIcon mainPhotoIcon;

    //offline mode icon.
    private ImageIcon offlineModeIcon;

    //online mode icon.
    private ImageIcon onlineModeIcon;

    //setting icon.
    private ImageIcon settingIcon;

    //exit icon.
    private ImageIcon exitIcon;

    //offline mode button.
    private JButton offlineModeButton;

    //online mode button.
    private JButton onlineModeButton;

    //setting button.
    private JButton settingButton;

    //exit button.
    private JButton exitButton;

    //main photo label.
    private JLabel mainPhoto;

    //items panel.
    private JPanel items;

    /**
     * this constructor makes the main menu and add items to it.
     */
    public MainFrame() {

        //set main frame.
        setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new BorderLayout());
        setBounds(500, 200, 700, 660);

        //add main picture.
        mainPhotoIcon = new ImageIcon("Resources/gifFiles/tank.gif");
        mainPhoto = new JLabel(mainPhotoIcon);

        //add items.
        items = new JPanel();
        items.setBackground(Color.WHITE);
        items.setLayout(new FlowLayout());

        //add buttons.
        initButtons();

        //add panel.
        add(items, BorderLayout.CENTER);
        add(mainPhoto, BorderLayout.NORTH);
        setVisible(true);
    }

    /**
     * this initButtons method makes the 4 buttons with the given imageIcons .
     */
    public void initButtons() {

        //offline icon.
        offlineModeIcon = new ImageIcon("Resources/pictures/offline.png");
        offlineModeButton = new JButton(offlineModeIcon);
        offlineModeButton.setToolTipText("Offline Game Mode.");
        offlineModeButton.addMouseListener(new MouseAction());
        offlineModeButton.addActionListener(new OfflineModeAction());

        //online icon.
        onlineModeIcon = new ImageIcon("Resources/pictures/online.png");
        onlineModeButton = new JButton(onlineModeIcon);
        onlineModeButton.setToolTipText("Online Game Mode.");
        onlineModeButton.addActionListener(new OnlineModeAction());
        onlineModeButton.addMouseListener(new MouseAction());

        //setting icon.
        settingIcon = new ImageIcon("Resources/pictures/setting.png");
        settingButton = new JButton(settingIcon);
        settingButton.addActionListener(new SettingButtonAction());
        settingButton.addMouseListener(new MouseAction());
        settingButton.setToolTipText("Setting And User Details.");

        //exit icon.
        exitIcon = new ImageIcon("Resources/pictures/exit.png");
        exitButton = new JButton(exitIcon);
        exitButton.setToolTipText("Exit Form Current User Account.");
        exitButton.addActionListener(new ExitButtonAction());
        exitButton.addMouseListener(new MouseAction());

        //add items.
        items.add(offlineModeButton);
        items.add(onlineModeButton);
        items.add(settingButton);
        items.add(exitButton);
    }

    /**
     * ExitButtonAction makes the action after clicking on the button.
     */
    class ExitButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new LoginFrame();
        }
    }

    /**
     * SettingButtonAction makes the action after clicking on the button.
     */
    class SettingButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SettingFrame();
        }
    }

    /**
     * MouseAction makes a green border around the buttons..
     */
    class MouseAction extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            super.mouseEntered(e);
            JButton jButton = (JButton) e.getComponent();
            jButton.setBackground(Color.GREEN);
            jButton.setOpaque(true);
            repaint();
            revalidate();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            super.mouseExited(e);
            JButton jButton = (JButton) e.getComponent();
            jButton.setBackground(Color.WHITE);
            jButton.setOpaque(true);
            repaint();
            revalidate();

        }
    }

    /**
     * OfflineModeAction makes the action after clicking on the button.
     */
    class OfflineModeAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            JFrame dialogFrame = new JFrame("Change settings ");
            dialogFrame.setResizable(false);
            dialogFrame.setLayout(new FlowLayout());
            dialogFrame.setBounds(700, 450, 300, 85);
            JLabel settingLabel = new JLabel("Do you want to play with current setting ?");
            dialogFrame.add(settingLabel);

            JButton yesButton = new JButton("Yes");
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialogFrame.dispose();
                    dispose();
                    new OfflineModeFrame("Choose Your Game Mode : ");
                    //new Game starts here...
                }
            });

            JButton noButton = new JButton("No");
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialogFrame.dispose();
                    new SettingFrame();
                }
            });

            dialogFrame.add(yesButton);
            dialogFrame.add(noButton);
            dialogFrame.setVisible(true);

        }

    }

    /**
     * OnlineModeAction makes the action after clicking on the button.
     */

    class OnlineModeAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            new OnlineModeFrame();
        }
    }


}
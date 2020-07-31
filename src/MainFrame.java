import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends JFrame {

    private ImageIcon mainPhotoIcon;
    private ImageIcon offlineModeIcon;
    private ImageIcon onlineModeIcon;
    private ImageIcon settingIcon;
    private ImageIcon exitIcon;
    private JButton offlineModeButton;
    private JButton onlineModeButton;
    private JButton settingButton;
    private JButton exitButton;
    private JLabel mainPhoto;
    private JPanel items;

    public MainFrame() {

        setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new BorderLayout());
        setBounds(500, 200, 700, 660);

        mainPhotoIcon = new ImageIcon("gifFiles/tank.gif");
        mainPhoto = new JLabel(mainPhotoIcon);

        items = new JPanel();
        items.setBackground(Color.WHITE);
        items.setLayout(new FlowLayout());

        initButtons();

        add(items, BorderLayout.CENTER);
        add(mainPhoto, BorderLayout.NORTH);
        setVisible(true);
    }

    public void initButtons() {

        offlineModeIcon = new ImageIcon("pictures/offline.png");
        offlineModeButton = new JButton(offlineModeIcon);
        offlineModeButton.setToolTipText("Offline Game Mode.");
        offlineModeButton.addMouseListener(new MouseAction());
        offlineModeButton.addActionListener(new OfflineModeAction());
        onlineModeIcon = new ImageIcon("pictures/online.png");
        onlineModeButton = new JButton(onlineModeIcon);
        onlineModeButton.setToolTipText("Online Game Mode.");
        onlineModeButton.addActionListener(new OnlineModeAction());
        onlineModeButton.addMouseListener(new MouseAction());
        settingIcon = new ImageIcon("pictures/setting.png");
        settingButton = new JButton(settingIcon);
        settingButton.addActionListener(new SettingButtonAction());
        settingButton.addMouseListener(new MouseAction());
        settingButton.setToolTipText("Setting And User Details.");
        exitIcon = new ImageIcon("pictures/exit.png");
        exitButton = new JButton(exitIcon);
        exitButton.setToolTipText("Exit Form Current User Account.");
        exitButton.addActionListener(new ExitButtonAction());
        exitButton.addMouseListener(new MouseAction());

        items.add(offlineModeButton);
        items.add(onlineModeButton);
        items.add(settingButton);
        items.add(exitButton);
    }

    class ExitButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new LoginFrame();
        }
    }

    class SettingButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SettingFrame();
        }
    }

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
    class OfflineModeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            JFrame dialogFrame = new JFrame("Change settings ");
            dialogFrame.setResizable(false);
            dialogFrame.setLayout(new FlowLayout());
            dialogFrame.setBounds(700,450,300,85);
            JLabel settingLabel = new JLabel("Do you want to play with current setting ?");
            dialogFrame.add(settingLabel);

            JButton yesButton = new JButton("Yes");
            yesButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialogFrame.dispose();
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
    class OnlineModeAction implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            new OnlineModeFrame();
        }
    }


}

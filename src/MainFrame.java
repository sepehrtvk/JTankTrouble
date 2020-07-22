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

        //initButtons();

        add(items, BorderLayout.CENTER);
        add(mainPhoto, BorderLayout.NORTH);
        setVisible(true);
    }
}
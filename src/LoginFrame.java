import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class LoginFrame extends JFrame {

    private JTextField userName;
    private JPasswordField passwordField;
    private JCheckBox remindMe;
    private JPanel informationPanel;
    private JPanel buttonPanel;
    private JButton signIn;
    private JButton signUp;
    private ImageIcon tankPhoto;


    public LoginFrame() {

        setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new BorderLayout());
        setBounds(700, 250, 320, 515);

//        initLoginFrame();
//        initRemindMe();
//        initInformationPanel();
        setVisible(true);

    }
}

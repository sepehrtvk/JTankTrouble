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

       initLoginFrame();
//        initRemindMe();
//        initInformationPanel();
        setVisible(true);

    }
    public void initLoginFrame() {

        tankPhoto = new ImageIcon("gifFiles/gif.gif");
        JLabel tankPhotoLabel = new JLabel(tankPhoto);
        add(tankPhotoLabel, BorderLayout.NORTH);

        userName = new JTextField();
        userName.setBorder(new TitledBorder("Username :"));
        userName.setBackground(Color.lightGray);
        passwordField = new JPasswordField("");
        passwordField.setBorder(new TitledBorder("Password :"));
        passwordField.setBackground(Color.lightGray);
        remindMe = new JCheckBox("Remind Me");
        remindMe.setBackground(Color.lightGray);

        buttonPanel = new JPanel();
        buttonPanel.setBorder(new LineBorder(Color.DARK_GRAY));
        signIn = new JButton(" Sign in ");
        signUp = new JButton(" Sign up ");
        signIn.setFont(new Font("Marker Felt", Font.BOLD, 15));
        signUp.setFont(new Font("Marker Felt", Font.BOLD, 15));
        buttonPanel.add(signIn);
        buttonPanel.add(signUp);

        add(buttonPanel, BorderLayout.SOUTH);

//        signUp.addActionListener(new SignUpAction());
//        signIn.addActionListener(new SignInAction());


    }
    public void signUpUser(File accounts) {
        try (FileWriter fileWriter = new FileWriter(accounts, true)) {
            fileWriter.write(userName.getText() + " " + passwordField.getText() + " " + remindMe.isSelected() + " 0 0 0 0 0 " + "\n");
            JFrame signUpMessage = new JFrame();
            ImageIcon icon = new ImageIcon("pictures/logo.png");
            JOptionPane.showMessageDialog(signUpMessage, "Successfully registered.", "", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (Exception ex) {
            System.out.println("No account found ! ");
            ex.printStackTrace();
        }

    }


}

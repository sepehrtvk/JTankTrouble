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

/**
 * this LoginFrame class which extends the JFrame shows the login form to get username and password from the player.
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class LoginFrame extends JFrame {

    //username text.
    private JTextField userName;

    //password text.
    private JPasswordField passwordField;

    //remindMe check box.
    private JCheckBox remindMe;

    //information panel.
    private JPanel informationPanel;

    //button panel.
    private JPanel buttonPanel;

    //sign in button.
    private JButton signIn;

    //sign up button.
    private JButton signUp;

    //photo if the tank.
    private ImageIcon tankPhoto;

    /**
     * this constructor makes the login form.
     */
    public LoginFrame() {

        setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new BorderLayout());
        setBounds(700, 250, 320, 515);

        initLoginFrame();
        initRemindMe();
        initInformationPanel();
        setVisible(true);

    }

    /**
     * make the information panel.
     */
    public void initInformationPanel() {

        informationPanel = new JPanel();
        informationPanel.setOpaque(true);
        informationPanel.setBorder(new LineBorder(Color.DARK_GRAY));
        informationPanel.setLayout(new GridLayout(3, 1));
        informationPanel.add(userName);
        informationPanel.add(passwordField);
        informationPanel.add(remindMe);

        add(informationPanel, BorderLayout.CENTER);

    }

    /**
     * make the login panel with gif file..
     */

    public void initLoginFrame() {

        tankPhoto = new ImageIcon("Resources/gifFiles/gif.gif");
        JLabel tankPhotoLabel = new JLabel(tankPhoto);
        add(tankPhotoLabel, BorderLayout.NORTH);

        //username.
        userName = new JTextField();
        userName.setBorder(new TitledBorder("Username :"));
        userName.setBackground(Color.lightGray);

        //password.
        passwordField = new JPasswordField("");
        passwordField.setBorder(new TitledBorder("Password :"));
        passwordField.setBackground(Color.lightGray);

        //remind me.
        remindMe = new JCheckBox("Remind Me");
        remindMe.setBackground(Color.lightGray);

        //button panel.
        buttonPanel = new JPanel();
        buttonPanel.setBorder(new LineBorder(Color.DARK_GRAY));
        signIn = new JButton(" Sign in ");
        signUp = new JButton(" Sign up ");
        signIn.setFont(new Font("Marker Felt", Font.BOLD, 15));
        signUp.setFont(new Font("Marker Felt", Font.BOLD, 15));
        buttonPanel.add(signIn);
        buttonPanel.add(signUp);

        add(buttonPanel, BorderLayout.SOUTH);

        signUp.addActionListener(new SignUpAction());
        signIn.addActionListener(new SignInAction());


    }

    /**
     * make the remind me check box to save user details.
     */
    public void initRemindMe() {
        File accounts = new File("Resources/Accounts.txt");
        try (Scanner scanner = new Scanner(new FileReader(accounts))) {
            String account;
            while (scanner.hasNextLine()) {
                account = scanner.nextLine() + "\n";
                String[] strings = account.split(" ");
                if (account.contains("true")) {
                    remindMe.setSelected(true);
                    userName.setText(strings[0]);
                    passwordField.setText(strings[1]);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * sign up a new user with the given name and password.
     *
     * @param accounts file of the accounts.
     */
    public void signUpUser(File accounts) {
        try (FileWriter fileWriter = new FileWriter(accounts, true)) {
            fileWriter.write(userName.getText() + " " + passwordField.getText() + " " + remindMe.isSelected() + " 0 0 0 0 0 " + "\n");
            JFrame signUpMessage = new JFrame();
            ImageIcon icon = new ImageIcon("Resources/pictures/logo.png");
            JOptionPane.showMessageDialog(signUpMessage, "Successfully registered.", "", JOptionPane.INFORMATION_MESSAGE, icon);
        } catch (Exception ex) {
            System.out.println("No account found ! ");
            ex.printStackTrace();
        }

    }

    /**
     * sign in with the correct user name and password.
     */
    public void signInUser() {
        File accounts = new File("Resources/Accounts.txt");
        try (Scanner scanner = new Scanner(new FileReader(accounts))) {
            String account = "";
            String inputs = "";
            while (scanner.hasNext()) {
                inputs = userName.getText() + " " + passwordField.getText();
                Controller.userName = userName.getText();
                account += scanner.next() + " ";
            }
            if (account.contains(inputs) && accounts.length() != 0) {
                dispose();
                JFrame signInMessage = new JFrame();
                String str3 = "Welcome to J Tank Trouble game ! \nUser : " + userName.getText();
                ImageIcon icon = new ImageIcon("Resources/pictures/logo.png");
                JOptionPane.showMessageDialog(signInMessage, str3, "", JOptionPane.INFORMATION_MESSAGE, icon);
                new MainFrame();
            } else {
                JFrame incorrectMessage = new JFrame();
                JOptionPane.showMessageDialog(incorrectMessage, "Incorrect Username or Password ! \nPlease try again... ", "", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * sign in action to save username and password.
     */
    class SignInAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            signInUser();
        }
    }

    /**
     * sign up action to make a new user.
     */
    class SignUpAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File accounts = new File("Resources/Accounts.txt");
            try (Scanner scanner = new Scanner(new FileReader(accounts))) {
                String account = "";
                while (scanner.hasNextLine()) {
                    account = scanner.nextLine() + "\n";
                }
                if (account.contains(userName.getText())) {
                    JFrame userFound = new JFrame();
                    JOptionPane.showMessageDialog(userFound, "This User already exists ! ", "", JOptionPane.ERROR_MESSAGE);
                } else {
                    signUpUser(accounts);
                }

            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
    }


}

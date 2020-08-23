import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * the EngineStartFrame class extends JFrame and shows a starter for the tank.
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class EngineStartFrame extends JFrame {

    //text of the start engine.
    private JTextField text;

    //icon of the starter.
    private ImageIcon icon;

    /**
     * this constructor makes the engine starter.,
     *
     * @param title frame title
     */
    public EngineStartFrame(String title) {
        super(title);
        setBackground(Color.LIGHT_GRAY);
        setResizable(false);
        setLocationRelativeTo(null); // put frame at center of screen

        setLayout(new BorderLayout());
        setBounds(1160, 120, 300, 270);

        //init button.
        initEngine();

        //init start progress.
        initStartProgress();

        setVisible(true);
    }

    /**
     * this initEngine method makes the text field.
     */
    public void initEngine() {
        text = new JTextField("Press Button To Start Engine ");
        text.setHorizontalAlignment(0);
        text.setEditable(false);
        text.setBackground(Color.BLACK);
        text.setForeground(Color.red);
        add(text, BorderLayout.NORTH);

    }

    /**
     * this initStartProgress makes the start progress.
     */
    public void initStartProgress() {
        icon = new ImageIcon("Resources/pictures/engineStarter.png");
        JLabel jLabel = new JLabel(icon);
        add(jLabel, BorderLayout.CENTER);

        jLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                remove(jLabel);
                repaint();
                revalidate();
                new AudioPlayer("Resources/sound effects/carstart.wav", 0);

                ImageIcon imageIcon = new ImageIcon("Resources/gifFiles/start.gif");
                JLabel jLabel1 = new JLabel(imageIcon);
                add(jLabel1, BorderLayout.CENTER);
                repaint();
                revalidate();


            }
        });

        //add action listener.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                Controller.engineStarted = true;
                dispose();
            }
        });
    }
}

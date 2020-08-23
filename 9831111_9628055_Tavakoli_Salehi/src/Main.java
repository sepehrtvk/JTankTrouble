
import java.awt.*;
import javax.swing.*;

/**
 * the Main class to start running the program.
 *
 * @author narges salehi & sepehr tavakoli
 * @version 1.1
 * @since July 21 2020
 */
public class Main {

    public static void main(String[] args) {
        // Initialize the global thread-pool
        ThreadPool.init();
        // Show the game menu ...
        new LoginFrame();

    }
}

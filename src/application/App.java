package application;

import java.io.IOException;
import javax.swing.JOptionPane;

public class App {

    public static void main(String[] args) throws IOException {

        try {
            Login_Panel lp = new Login_Panel();
            lp.setSize(800, 600);
            lp.setVisible(true);
            lp.setLocationRelativeTo(null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
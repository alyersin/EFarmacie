package application;

import java.io.IOException;
import javax.swing.JOptionPane;

public class App {

    public static void main(String[] args) throws IOException {

        try { 
            // Creaza o instanta dupa Login_Panel
            Login_Panel lp = new Login_Panel();
            lp.setSize(800, 600); // Seteaza dimensiunea
            lp.setVisible(true); // Face panoul vizibil
            lp.setLocationRelativeTo(null); // Pozitioneaza panoul in centru
        } catch (Exception e) {
            // Afiseaza un mesaj de eroare daca apare o exceptie
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}

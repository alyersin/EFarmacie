package application;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Login_Panel extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    JButton b1;
    JLabel userLabel, passLabel;
    final JTextField textField_1;
    final JPasswordField textField_2;
    List<String> listaUsers;
    List<String> listaPasswords;

    Login_Panel() {
        // Incarca conturile din fisier
        loadAccounts("accounts.txt");

        getContentPane().setLayout(null);  // Setam layout (absolute) in swing pentru a folosi x, y si w, h

        userLabel = new JLabel("User");
        userLabel.setBounds(100, 100, 80, 30);
        textField_1 = new JTextField(15);
        textField_1.setBounds(200, 100, 200, 30);

        passLabel = new JLabel("Parola");
        passLabel.setBounds(100, 150, 80, 30);
        textField_2 = new JPasswordField(15);
        textField_2.setBounds(200, 150, 200, 30);

        b1 = new JButton("Login");
        b1.setBounds(200, 200, 100, 30);

        getContentPane().add(userLabel);
        getContentPane().add(textField_1);
        getContentPane().add(passLabel);
        getContentPane().add(textField_2);
        getContentPane().add(b1);

        b1.addActionListener(this);
        setTitle("LOGIN");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EnterKeyListener(textField_1);
        EnterKeyListener(textField_2);
    }

    // Listener pentru tasta Enter
    private void EnterKeyListener(JComponent comp) {
        comp.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    b1.doClick();
                }
            }
        });
    }

    // Gestionare autentificare
    public void actionPerformed(ActionEvent ae) {
        String user = textField_1.getText();
        String pass = new String(textField_2.getPassword());

        if (autentificare(user, pass)) {
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
            setVisible(false);
        } else {
            showError("user / parola incorecte");
        }
    }

    // Incarca conturile din accounts.txt
    private void loadAccounts(String filePath) {
        listaUsers = new ArrayList<>(); // Initializare array pentru username-uri
        listaPasswords = new ArrayList<>(); // Initializare array pentru parole
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { // Deschidere fisier pentru citire
            String linie; // Declarare variabila unde vom stoca toate liniile citite din fiser
            while ((linie = br.readLine()) != null) {  // Citire din fisier si transformare in String pana cand = null (nu mai sunt linii cu text)
                String[] temp = linie.split(":"); // Stocare in temp array fara delimitator ":"
                if (temp.length == 2) {  // Verificare daca sunt 2 elemente
                    listaUsers.add(temp[0]); // Adaugare username in lista
                    listaPasswords.add(temp[1]); // Adaugare parola in lista
                }
            }
        } catch (IOException e) {
            showError("Eroare la incarcare: " + e.getMessage());
        }
    }

    // Autentifica utilizatorul
    private boolean autentificare(String username, String password) {
        for (int i = 0; i < listaUsers.size(); i++) { // Iterare prin lista de username-uri
            if (listaUsers.get(i).equals(username) && listaPasswords.get(i).equals(password)) { // Verifica daca username-ul si parola se potrivesc
                return true;
            }
        }
        return false; // Daca nu gaseste nicio potrivire, returneaza false
    }

    // Afiseaza mesaj de eroare
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}

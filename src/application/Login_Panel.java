package application;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Login_Panel extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    JButton b1; // Buton pentru autentificare
    JLabel userLabel, passLabel; // Etichete pentru campurile de text
    final JTextField textField_1; // Camp text pentru utilizator
    final JPasswordField textField_2; // Camp text pentru parola
    List<String> listaUsers; // Lista pentru username-uri
    List<String> listaPasswords; // Lista pentru parole

    Login_Panel() {
        // Incarca conturile din fisier
        loadAccounts("accounts.txt"); // Apelare metoda pentru incarcarea conturilor din fisier

        getContentPane().setLayout(null);  // Setare layout nul pentru utilizarea coordonatelor absolute

        userLabel = new JLabel("User"); // Creare eticheta pentru utilizator
        userLabel.setBounds(100, 100, 80, 30); // Setare pozitie si dimensiune eticheta
        textField_1 = new JTextField(15); // Creare camp text pentru utilizator cu lungime maxima de 15 caractere
        textField_1.setBounds(200, 100, 200, 30); // Setare pozitie si dimensiune camp text

        passLabel = new JLabel("Parola"); // Creare eticheta pentru parola
        passLabel.setBounds(100, 150, 80, 30); // Setare pozitie si dimensiune eticheta
        textField_2 = new JPasswordField(15); // Creare camp text pentru parola cu lungime maxima de 15 caractere
        textField_2.setBounds(200, 150, 200, 30); // Setare pozitie si dimensiune camp text

        b1 = new JButton("Login"); // Creare buton pentru autentificare
        b1.setBounds(200, 200, 100, 30); // Setare pozitie si dimensiune buton

        getContentPane().add(userLabel); // Adaugare eticheta utilizator in panou
        getContentPane().add(textField_1); // Adaugare camp text utilizator in panou
        getContentPane().add(passLabel); // Adaugare eticheta parola in panou
        getContentPane().add(textField_2); // Adaugare camp text parola in panou
        getContentPane().add(b1); // Adaugare buton in panou

        b1.addActionListener(this); // Adaugare ascultator de actiuni pentru buton
        setTitle("LOGIN"); // Setare titlu fereastra
        setSize(400, 300); // Setare dimensiune fereastra
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setare actiune inchidere fereastra

        EnterKeyListener(textField_1); // Adaugare listener pentru tasta Enter la campul text utilizator
        EnterKeyListener(textField_2); // Adaugare listener pentru tasta Enter la campul text parola
    }

    // Listener pentru tasta Enter
    private void EnterKeyListener(JComponent comp) {
        comp.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    b1.doClick(); // Simuleaza un click pe butonul de autentificare
                }
            }
        });
    }

    // Gestionare autentificare
    public void actionPerformed(ActionEvent ae) {
        String user = textField_1.getText(); // Obtine textul din campul utilizator
        String pass = new String(textField_2.getPassword()); // Obtine textul din campul parola

        if (autentificare(user, pass)) { // Verifica autentificarea
            MainFrame mf = new MainFrame(); // Creare instanta MainFrame
            mf.setVisible(true); // Setare vizibilitate fereastra principala
            setVisible(false); // Ascundere fereastra de login
        } else {
            showError("user / parola incorecte"); // Afisare mesaj de eroare pentru autentificare esuata
        }
    }

    // Incarca conturile din accounts.txt
    private void loadAccounts(String filePath) {
        listaUsers = new ArrayList<>(); // Initializare lista pentru username-uri
        listaPasswords = new ArrayList<>(); // Initializare lista pentru parole
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { // Deschidere fisier pentru citire
            String linie; // Declarare variabila pentru stocarea liniilor citite din fisier
            while ((linie = br.readLine()) != null) { // Citire linii din fisier pana la sfarsit
                String[] temp = linie.split(":"); // Impartire linie in username si parola folosind ":" ca delimitator
                if (temp.length == 2) { // Verificare daca linia contine exact 2 elemente
                    listaUsers.add(temp[0]); // Adaugare username in lista
                    listaPasswords.add(temp[1]); // Adaugare parola in lista
                }
            }
        } catch (IOException e) {
            showError("Eroare la incarcare: " + e.getMessage()); // Afisare mesaj de eroare daca apare o exceptie la citirea fisierului
        }
    }

    // Autentifica utilizatorul
    private boolean autentificare(String username, String password) {
        for (int i = 0; i < listaUsers.size(); i++) { // Iterare prin lista de username-uri
            if (listaUsers.get(i).equals(username) && listaPasswords.get(i).equals(password)) { // Verifica daca username-ul si parola se potrivesc
                return true; // Returneaza true daca autentificarea este reusita
            }
        }
        return false; // Returneaza false daca nu gaseste nicio potrivire
    }

    // Afiseaza mesaj de eroare
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message); // Afisare mesaj de eroare intr-o fereastra de dialog
    }
}

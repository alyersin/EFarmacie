package application;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Login_Panel extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

    public JButton loginBtn; // Buton pentru autentificare
    public JLabel userLabel, passLabel; // Etichete pentru campurile de text
    public JTextField userField; // Camp text pentru utilizator
    public JPasswordField passField; // Camp text pentru parola
    public List<String> usersArray; // Lista pentru username-uri
    public List<String> passwordsArray; // Lista pentru parole

    Login_Panel() {
    	
        incarcaConturi("accounts.txt"); // Apelare metoda pentru incarcarea conturilor din fisier
    	
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Setare actiune inchidere fereastra
        setTitle("AUTENTIFICARE"); // Setare titlu fereastra
        setSize(400, 300); // Setare dimensiune fereastra
        
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane().setLayout(null);  // Setare layout nul pentru utilizarea coordonatelor absolute

        userLabel = new JLabel("User"); // Creare eticheta pentru utilizator
        userLabel.setBounds(240, 170, 80, 30); // Setare pozitie si dimensiune eticheta
        userField = new JTextField(15); // Creare camp text pentru utilizator cu lungime maxima de 15 caractere
        userField.setBounds(300, 170, 200, 30); // Setare pozitie si dimensiune camp text

        passLabel = new JLabel("Parola"); // Creare eticheta pentru parola
        passLabel.setBounds(240, 220, 80, 30); // Setare pozitie si dimensiune eticheta
        passField = new JPasswordField(15); // Creare camp text pentru parola cu lungime maxima de 15 caractere
        passField.setBounds(300, 220, 200, 30); // Setare pozitie si dimensiune camp text

        loginBtn = new JButton("Login"); // Creare buton pentru autentificare
        loginBtn.setBounds(350, 270, 100, 30); // Setare pozitie si dimensiune buton

        getContentPane().add(userLabel); // Adaugare eticheta utilizator in panou
        getContentPane().add(userField); // Adaugare camp text utilizator in panou
        getContentPane().add(passLabel); // Adaugare eticheta parola in panou 
        getContentPane().add(passField); // Adaugare camp text parola in panou
        getContentPane().add(loginBtn); // Adaugare buton in panou

        loginBtn.addActionListener(this); // Adaugare ascultator de actiuni pentru buton

        enterKeyListener(userField); // Adaugare listener pentru tasta Enter la campul text utilizator
        enterKeyListener(passField); // Adaugare listener pentru tasta Enter la campul text parola
    }

    // Listener pentru tasta Enter
    private void enterKeyListener(JComponent comp) {
        comp.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginBtn.doClick(); // Simuleaza un click pe butonul de autentificare
                }
            }
        });
    }

    // Gestionare autentificare
    public void actionPerformed(ActionEvent ae) {
        String username = userField.getText(); // Obtine textul din campul utilizator
        String password = new String(passField.getPassword()); // Obtine textul din campul parola

        if (autentificare(username, password)) { // Verifica autentificarea
            MainFrame mf = new MainFrame(); // Creare instanta MainFrame
            mf.setVisible(true); // Setare vizibilitate fereastra principala
            setVisible(false); // Ascundere fereastra de login
        } else {
            showError("Username / Parola incorecta"); // Afisare mesaj de eroare pentru autentificare esuata
        }
    }

    // Incarca conturile din accounts.txt
    private void incarcaConturi(String filePath) {
        usersArray = new ArrayList<>(); // Initializare lista pentru username-uri
        passwordsArray = new ArrayList<>(); // Initializare lista pentru parole
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) { // Deschidere fisier pentru citire
            String lineRead; // Declarare variabila pentru stocarea liniilor citite din fisier
            while ((lineRead = br.readLine()) != null) { // Citire linii din fisier pana la sfarsit
                String[] temp = lineRead.split(":"); // Impartire lineRead in username si parola folosind ":" ca delimitator
                if (temp.length == 2) { // Verificare daca linia contine exact 2 elemente
                    usersArray.add(temp[0]); // Adaugare username in usersArray[]
                    passwordsArray.add(temp[1]); // Adaugare parola in passwordsArray[]
                }
            }
        } catch (IOException e) {
            showError("Eroare la incarcare: " + e.getMessage()); // Afisare mesaj de eroare daca apare o exceptie la citirea fisierului
        }
    }

    // Autentifica utilizatorul
    private boolean autentificare(String username, String password) {
        for (int i = 0; i < usersArray.size(); i++) { // Iterare prin lista de username-uri
            if (usersArray.get(i).equals(username) && passwordsArray.get(i).equals(password)) { // Verifica daca username-ul si parola se potrivesc
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

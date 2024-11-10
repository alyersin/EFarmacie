package application;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Login_Panel extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    public JButton loginBtn;
    public JLabel userLabel, passLabel;
    public JTextField userField;
    public JPasswordField passField;
    private List<String> usersArray;
    private List<String> passwordsArray;

    Login_Panel() {
        incarcaConturi("accounts.txt");
    	
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("AUTENTIFICARE");
        setSize(400, 300);
        
        getContentPane().setBackground(Color.LIGHT_GRAY);
        getContentPane().setLayout(null);

        userLabel = new JLabel("User");
        userLabel.setBounds(240, 170, 80, 30);
        userField = new JTextField(15);
        userField.setBounds(300, 170, 200, 30);

        passLabel = new JLabel("Parola");
        passLabel.setBounds(240, 220, 80, 30);
        passField = new JPasswordField(15);
        passField.setBounds(300, 220, 200, 30);

        loginBtn = new JButton("Login");
        loginBtn.setBounds(350, 270, 100, 30);

        getContentPane().add(userLabel);
        getContentPane().add(userField);
        getContentPane().add(passLabel);
        getContentPane().add(passField);
        getContentPane().add(loginBtn);

        loginBtn.addActionListener(this);

        enterKeyListener(userField);
        enterKeyListener(passField);
    }

    // Adauga un listener pentru tasta Enter pe un component
    private void enterKeyListener(JComponent comp) {
        comp.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginBtn.doClick();
                }
            }
        });
    }

    private void incarcaConturi(String filePath) {
        usersArray = new ArrayList<>();
        passwordsArray = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String lineRead;
            while ((lineRead = br.readLine()) != null) {
                String[] temp = lineRead.split(":");
                if (temp.length == 2) {
                    usersArray.add(temp[0]);
                    passwordsArray.add(temp[1]);
                }
            }
        } catch (IOException e) {
            showError("Eroare la incarcare: " + e.getMessage());
        }
    }

    // Gestioneaza butonul Login
    public void actionPerformed(ActionEvent ae) {
        String username = userField.getText();
        String password = new String(passField.getPassword());

        if (autentificare(username, password)) {
            MainFrame mf = new MainFrame();
            mf.setVisible(true);
            setVisible(false);
        } else {
            showError("Username / Parola incorecta");
        }
    }

    // Autentificare user / parola
    private boolean autentificare(String username, String password) {
        for (int i = 0; i < usersArray.size(); i++) {
            if (usersArray.get(i).equals(username) && passwordsArray.get(i).equals(password)) {
                return true;
            }
        }
        return false;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
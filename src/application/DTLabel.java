package application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DTLabel {
    private JLabel labelDataOra; // Variabilă de instanță pentru eticheta JLabel
    private Timer timer; // Variabilă de instanță pentru temporizator

    public DTLabel(JLabel labelDataOra) { // Constructorul clasei, care primește un obiect JLabel ca parametru
        this.labelDataOra = labelDataOra; // Atribuie parametrul labelDataOra variabilei de instanță labelDataOra
    }

    public void start() { // Definirea metodei `start` care inițializează și pornește temporizatorul
        timer = new Timer(1000, new ActionListener() { // Inițializează temporizatorul pentru a declanșa evenimente la fiecare secundă (1000 de milisecunde)
            @Override
            public void actionPerformed(ActionEvent e) { // Metoda `actionPerformed` este apelată de fiecare dată când temporizatorul declanșează un eveniment
            	getDateTime(); // Apelarea metodei `updateDateTime` pentru a actualiza data și ora la fiecare secundă
            }
        });
        timer.start(); // Pornește temporizatorul
    }

    private void getDateTime() { // Definirea metodei private `getDateTime` pentru a actualiza data și ora
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // Creează un obiect SimpleDateFormat pentru a formata data și ora curente
        String oraActuala = sdf.format(new Date()); // Obține data și ora curente ca string formatat folosind SimpleDateFormat
        labelDataOra.setText(oraActuala); // Setează textul etichetei JLabel `labelDataOra` la data și ora curente
    }
}

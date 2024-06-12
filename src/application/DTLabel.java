package application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DTLabel {
    private JLabel labelDataOra; // Variabila de instanta pentru eticheta JLabel
    private Timer timer; // Variabila de instanta pentru temporizator

    public DTLabel(JLabel labelDataOra) { // Constructorul clasei, care primeste un obiect JLabel ca parametru
        this.labelDataOra = labelDataOra; // Atribuie parametrul labelDataOra variabilei de instanta labelDataOra
    }

    public void start() { // Definirea metodei `start` care initializeaza si porneste temporizatorul
        timer = new Timer(1000, new ActionListener() { // Initializeaza temporizatorul pentru a declansa evenimente la fiecare secunda (1000 de milisecunde)
            @Override
            public void actionPerformed(ActionEvent e) { // Metoda `actionPerformed` este apelata de fiecare data cand temporizatorul declanseaza un eveniment
                getDateTime(); // Apelarea metodei `updateDateTime` pentru a actualiza data si ora la fiecare secunda
            }
        });
        timer.start(); // Porneste temporizatorul
    }

    private void getDateTime() { // Definirea metodei private `getDateTime` pentru a actualiza data si ora
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); // Creeaza un obiect SimpleDateFormat pentru a formata data si ora curente
        String oraActuala = sdf.format(new Date()); // Obtine data si ora curente ca string formatat folosind SimpleDateFormat
        labelDataOra.setText(oraActuala); // Seteaza textul etichetei JLabel `labelDataOra` la data si ora curente
    }
}

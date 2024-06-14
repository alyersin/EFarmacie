package application;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.Color;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel modelTabelDefault; // Modelul tabelului pentru stoc
    private JTable tabelStoc; // Tabelul pentru stoc
    private List<String[]> cosProduse; // Lista de produse in cos
    private Search_Panel searchPanel; // Panoul de cautare
    private Medic_Panel sidePanel; // Panoul lateral pentru medic
    private DTLabel updateDTLabel; // Eticheta pentru actualizarea datei si orei
    private JPopupMenu popupStergeProdus; // Meniul popup pentru stergerea produsului
    private Cos_Cumparaturi cosCumparaturi; // Obiect pentru gestionarea cosului de cumparaturi

    public MainFrame() {
    	setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Setare actiune inchidere fereastra
        setTitle("EFarmacie"); // Setare titlu fereastra
        setSize(1160, 800); // Setare dimensiune fereastra
        setLocationRelativeTo(null); // Setare locatie fereastra in centru
        setLayout(null); // Setare layout nul
        getContentPane().setBackground(Color.LIGHT_GRAY);

        cosProduse = new ArrayList<>(); // Initializare lista de produse in cos
        cosCumparaturi = new Cos_Cumparaturi(cosProduse); // Initializare obiect Cos_Cumparaturi

        configurareTabel(); // Configurare tabel pentru stoc
        initializarePanels(); // Initializare panouri
        new Stoc_Farmacie().incarcaStoc(modelTabelDefault); // Incarcare stoc in tabel
        new Table_Listeners(this).addListeners(); // Adaugare ascultatori pentru evenimente de tabel
        createPopupSterge(); // Creare popup pentru stergerea produsului
    }

    private void configurareTabel() {
        modelTabelDefault = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Setare celulele tabelului ca needitabile
            }
        };
        modelTabelDefault.addColumn("Nume"); // Adaugare coloana "Nume"
        modelTabelDefault.addColumn("Compozitie"); // Adaugare coloana "Compozitie"
        modelTabelDefault.addColumn("Indicatii"); // Adaugare coloana "Indicatii"
        modelTabelDefault.addColumn("Contraindicatii"); // Adaugare coloana "Contraindicatii"
        modelTabelDefault.addColumn("Mod de administrare"); // Adaugare coloana "Mod de administrare"
        modelTabelDefault.addColumn("Stoc"); // Adaugare coloana "Stoc"
        modelTabelDefault.addColumn("Pret"); // Adaugare coloana "Pret"
        tabelStoc = new JTable(modelTabelDefault); // Creare tabel cu modelul specificat
        tabelStoc.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); // Setare mod de redimensionare automata pentru ultima coloana
    }

    private void initializarePanels() {
        searchPanel = new Search_Panel(this); // Creare panou de cautare
        sidePanel = new Medic_Panel(this, cosCumparaturi); // Creare panou lateral pentru medic

        searchPanel.setBounds(0, 0, 800, 800); // Setare pozitie si dimensiune panou cautare
        sidePanel.setBounds(810, 0, 340, 800); // Setare pozitie si dimensiune panou lateral

        add(searchPanel); // Adaugare panou cautare in fereastra principala
        add(sidePanel); // Adaugare panou lateral in fereastra principala
    }

    public DefaultTableModel getModelTabelDefault() {
        return modelTabelDefault; // Returnare modelTabelDefault tabel
    }

    public JTable getTabelStoc() {
        return tabelStoc; // Returnare tabel stoc
    }

    public List<String[]> getCosProduse() {
        return cosProduse; // Returnare lista de produse in cos
    }

    public JPopupMenu getPopupStergeProdus() {
        return popupStergeProdus; // Returnare meniul popup pentru stergerea produsului
    }

    public JTextField getSearchField() {
        return searchPanel.getSearchField(); // Returnare camp text pentru cautare
    }

    public void updateDateTimeLabel(JLabel DTLabel) {
        updateDTLabel = new DTLabel(DTLabel); // Creare instanta DTLabel
        updateDTLabel.start(); // Pornire actualizare data si ora
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Eroare", JOptionPane.ERROR_MESSAGE); // Afisare mesaj de eroare
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Informatie", JOptionPane.INFORMATION_MESSAGE); // Afisare mesaj informativ
    }

    public Cos_Cumparaturi getCosCumparaturi() {
        return cosCumparaturi; // Returnare obiect Cos_Cumparaturi
    }
    
    private void createPopupSterge() {
        popupStergeProdus = new Table_Listeners(this).createPopup(); // Creare popup pentru stergerea produsului
    }
}

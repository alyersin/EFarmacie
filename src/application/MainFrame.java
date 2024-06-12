package application;

import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L; // Declaratie pentru serialVersionUID
    private DefaultTableModel model; // Modelul de tabel
    private JTable tabelStoc; // Tabelul de stoc
    private List<String[]> cosProduse; // Lista de produse din cos
    private Search_Panel searchPanel; // Panoul de cautare
    private Medic_Panel sidePanel; // Panoul lateral
    private DTLabel updateDTLabel; // Label pentru actualizarea datei si orei
    private JPopupMenu popupStergeProdus; // Meniul popup pentru stergerea produselor

    public MainFrame() { // Constructorul clasei MainFrame
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); // Seteaza operatia de inchidere a ferestrei
        setTitle("EFarmacie"); // Seteaza titlul ferestrei
        setSize(1160, 800); // Seteaza dimensiunea ferestrei
        setLocationRelativeTo(null); // Centreaza fereastra pe ecran
        setLayout(null); // Seteaza layout-ul ferestrei ca null

        cosProduse = new ArrayList<>(); // Initializeaza lista de produse din cos
        configurareTabel(); // Configureaza tabelul
        initializePanels(); // Initializeaza panourile
        new Stoc_Farmacie().incarcaStoc(model); // Incarca stocul in modelul de tabel
        new Table_Listeners(this).addListeners(); // Adauga ascultatori de evenimente pentru tabel
        createpopupStergeProdus(); // Creeaza meniul popup pentru stergerea produselor
    }

    private void configurareTabel() { // Metoda pentru configurarea tabelului
        model = new DefaultTableModel() { // Initializeaza modelul de tabel
            private static final long serialVersionUID = 1L; // Declaratie pentru serialVersionUID
            @Override
            public boolean isCellEditable(int row, int column) { // Suprascrie metoda isCellEditable pentru a face celulele ne-editabile
                return false; // Returneaza false pentru a face celulele ne-editabile
            }
        };
        model.addColumn("Nume"); // Adauga coloana "Nume" in modelul de tabel
        model.addColumn("Compozitie"); // Adauga coloana "Compozitie" in modelul de tabel
        model.addColumn("Indicatii"); // Adauga coloana "Indicatii" in modelul de tabel
        model.addColumn("Contraindicatii"); // Adauga coloana "Contraindicatii" in modelul de tabel
        model.addColumn("Mod de administrare"); // Adauga coloana "Mod de administrare" in modelul de tabel
        model.addColumn("Stoc"); // Adauga coloana "Stoc" in modelul de tabel
        model.addColumn("Pret"); // Adauga coloana "Pret" in modelul de tabel
        tabelStoc = new JTable(model); // Initializeaza tabelul cu modelul de tabel
        tabelStoc.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN); // Seteaza modul de redimensionare automata a tabelului
    }

    private void initializePanels() { // Metoda pentru initializarea panourilor
        searchPanel = new Search_Panel(this); // Initializeaza panoul de cautare
        sidePanel = new Medic_Panel(this); // Initializeaza panoul lateral

        searchPanel.setBounds(0, 0, 800, 800); // Seteaza pozitia si dimensiunea panoului de cautare
        sidePanel.setBounds(810, 0, 340, 800); // Seteaza pozitia si dimensiunea panoului lateral

        add(searchPanel); // Adauga panoul de cautare in fereastra
        add(sidePanel); // Adauga panoul lateral in fereastra
    }

    public DefaultTableModel getModel() { // Metoda getter pentru modelul de tabel
        return model; // Returneaza modelul de tabel
    }

    public JTable getTabelStoc() { // Metoda getter pentru tabelul de stoc
        return tabelStoc; // Returneaza tabelul de stoc
    }

    public List<String[]> getCosProduse() { // Metoda getter pentru lista de produse din cos
        return cosProduse; // Returneaza lista de produse din cos
    }

    public JPopupMenu getPopupStergeProdus() { // Metoda getter pentru meniul popup de stergere a produselor
        return popupStergeProdus; // Returneaza meniul popup de stergere a produselor
    }

    public JTextField getSearchField() { // Metoda getter pentru campul de cautare
        return searchPanel.getSearchField(); // Returneaza campul de cautare din panoul de cautare
    }

    public void updateDateTimeLabel(JLabel dtLabel) { // Metoda pentru actualizarea label-ului de data si ora
        updateDTLabel = new DTLabel(dtLabel); // Initializeaza updateDTLabel cu un nou obiect DTLabel
        updateDTLabel.start(); // Porneste actualizarea label-ului de data si ora
    }

    private void createpopupStergeProdus() { // Metoda pentru crearea meniului popup de stergere a produselor
        popupStergeProdus = new Table_Listeners(this).createPopup(); // Creeaza meniul popup folosind metoda createPopup din Table_Listeners
    }

    public void showError(String message) { // Metoda pentru afisarea mesajelor de eroare
        JOptionPane.showMessageDialog(this, message, "Eroare", JOptionPane.ERROR_MESSAGE); // Afiseaza un dialog cu mesajul de eroare
    }

    public void showMessage(String message) { // Metoda pentru afisarea mesajelor de informare
        JOptionPane.showMessageDialog(this, message, "Informatie", JOptionPane.INFORMATION_MESSAGE); // Afiseaza un dialog cu mesajul de informare
    }
}

package application;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Produs_Panel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField numeField, compozitieField, indicatiiField, contraindicatiiField, modField, stocField, pretField; // Declarare campuri pentru detaliile produsului
    private MainFrame frame; // Referinta la obiectul MainFrame

    // Constructor pentru initializarea panoului de produs cu referinta la MainFrame
    public Produs_Panel(MainFrame frame) {
        this.frame = frame; // Initializare referinta frame
        setupPanel(); // Configurarea panoului
    }

    // Constructor pentru initializarea panoului de produs cu detalii predefinite si referinta la MainFrame
    public Produs_Panel(MainFrame frame, String nume, String compozitie, String indicatii, String contraindicatii, String mod, String stoc, String pret) {
        this.frame = frame; // Initializare referinta frame
        setupPanel(); // Configurarea panoului
        numeField.setText(nume); // Setare text pentru campul nume
        compozitieField.setText(compozitie); // Setare text pentru campul compozitie
        indicatiiField.setText(indicatii); // Setare text pentru campul indicatii
        contraindicatiiField.setText(contraindicatii); // Setare text pentru campul contraindicatii
        modField.setText(mod); // Setare text pentru campul mod de administrare
        stocField.setText(stoc); // Setare text pentru campul stoc
        pretField.setText(pret); // Setare text pentru campul pret
    }

    // Metoda pentru configurarea panoului
    private void setupPanel() {
        setLayout(null); // Setare layout nul pentru pozitionare manuala a componentelor
        setPreferredSize(new Dimension(400, 350)); // Setare dimensiune preferata a panoului

        // Creare si adaugare componente la panou
        JLabel numeLabel = new JLabel("Nume:"); // Creare eticheta pentru nume
        numeLabel.setBounds(10, 10, 100, 30); // Setare pozitie si dimensiune
        numeField = new JTextField(); // Creare camp text pentru nume
        numeField.setBounds(120, 10, 200, 30); // Setare pozitie si dimensiune

        JLabel compozitieLabel = new JLabel("Compozitie:"); // Creare eticheta pentru compozitie
        compozitieLabel.setBounds(10, 50, 100, 30); // Setare pozitie si dimensiune
        compozitieField = new JTextField(); // Creare camp text pentru compozitie
        compozitieField.setBounds(120, 50, 200, 30); // Setare pozitie si dimensiune

        JLabel indicatiiLabel = new JLabel("Indicatii:"); // Creare eticheta pentru indicatii
        indicatiiLabel.setBounds(10, 90, 100, 30); // Setare pozitie si dimensiune
        indicatiiField = new JTextField(); // Creare camp text pentru indicatii
        indicatiiField.setBounds(120, 90, 200, 30); // Setare pozitie si dimensiune

        JLabel contraindicatiiLabel = new JLabel("Contraindicatii:"); // Creare eticheta pentru contraindicatii
        contraindicatiiLabel.setBounds(10, 130, 100, 30); // Setare pozitie si dimensiune
        contraindicatiiField = new JTextField(); // Creare camp text pentru contraindicatii
        contraindicatiiField.setBounds(120, 130, 200, 30); // Setare pozitie si dimensiune

        JLabel modLabel = new JLabel("Mod de administrare:"); // Creare eticheta pentru mod de administrare
        modLabel.setBounds(10, 170, 140, 30); // Setare pozitie si dimensiune
        modField = new JTextField(); // Creare camp text pentru mod de administrare
        modField.setBounds(160, 170, 200, 30); // Setare pozitie si dimensiune

        JLabel stocLabel = new JLabel("Stoc:"); // Creare eticheta pentru stoc
        stocLabel.setBounds(10, 210, 100, 30); // Setare pozitie si dimensiune
        stocField = new JTextField(); // Creare camp text pentru stoc
        stocField.setBounds(120, 210, 200, 30); // Setare pozitie si dimensiune

        JLabel pretLabel = new JLabel("Pret:"); // Creare eticheta pentru pret
        pretLabel.setBounds(10, 250, 100, 30); // Setare pozitie si dimensiune
        pretField = new JTextField(); // Creare camp text pentru pret
        pretField.setBounds(120, 250, 200, 30); // Setare pozitie si dimensiune

        JButton adaugaInCosButton = new JButton("Adauga produs in cos"); // Creare buton pentru adaugarea produsului in cos
        adaugaInCosButton.setBounds(120, 290, 200, 30); // Setare pozitie si dimensiune
        adaugaInCosButton.addActionListener(e -> adaugaProdusInCos()); // Adaugare ascultator pentru buton

        add(numeLabel); // Adaugare eticheta nume la panou
        add(numeField); // Adaugare camp text nume la panou
        add(compozitieLabel); // Adaugare eticheta compozitie la panou
        add(compozitieField); // Adaugare camp text compozitie la panou
        add(indicatiiLabel); // Adaugare eticheta indicatii la panou
        add(indicatiiField); // Adaugare camp text indicatii la panou
        add(contraindicatiiLabel); // Adaugare eticheta contraindicatii la panou
        add(contraindicatiiField); // Adaugare camp text contraindicatii la panou
        add(modLabel); // Adaugare eticheta mod de administrare la panou
        add(modField); // Adaugare camp text mod de administrare la panou
        add(stocLabel); // Adaugare eticheta stoc la panou
        add(stocField); // Adaugare camp text stoc la panou
        add(pretLabel); // Adaugare eticheta pret la panou
        add(pretField); // Adaugare camp text pret la panou
        add(adaugaInCosButton); // Adaugare buton adauga in cos la panou
    }

    // Getteri pentru campurile de text
    public JTextField getNumeField() {
        return numeField;
    }

    public JTextField getCompozitieField() {
        return compozitieField;
    }

    public JTextField getIndicatiiField() {
        return indicatiiField;
    }

    public JTextField getContraindicatiiField() {
        return contraindicatiiField;
    }

    public JTextField getModField() {
        return modField;
    }

    public JTextField getStocField() {
        return stocField;
    }

    public JTextField getPretField() {
        return pretField;
    }

    // Metoda pentru adaugarea unui produs nou
    public void adaugaProdus() {
        Produs_Panel produsPanel = new Produs_Panel(frame); // Creare instanta Produs_Panel
        int result = JOptionPane.showConfirmDialog(null, produsPanel, "Adauga Produs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE); // Afisare dialog pentru adaugarea produsului

        if (result == JOptionPane.OK_OPTION) { // Daca utilizatorul a apasat OK
            handleAdaugaProdus(produsPanel); // Apeleaza metoda de gestionare a adaugarii produsului
        }
    }

    // Metoda pentru gestionarea adaugarii unui produs
    private void handleAdaugaProdus(Produs_Panel produsPanel) {
        String calculeazaTVA = calculeazaTVA(produsPanel.getPretField().getText()); // Calculare pret cu TVA

        frame.getModel().addRow(new Object[]{
                produsPanel.getNumeField().getText(),
                produsPanel.getCompozitieField().getText(),
                produsPanel.getIndicatiiField().getText(),
                produsPanel.getContraindicatiiField().getText(),
                produsPanel.getModField().getText(),
                produsPanel.getStocField().getText(),
                calculeazaTVA
        }); // Adaugare rand nou in modelul tabelului

        salveazaProdusFisier(produsPanel.getNumeField().getText(), produsPanel.getCompozitieField().getText(),
                produsPanel.getIndicatiiField().getText(), produsPanel.getContraindicatiiField().getText(),
                produsPanel.getModField().getText(), produsPanel.getStocField().getText(), calculeazaTVA); // Salvare produs in fisier
    }

    // Metoda pentru calcularea TVA-ului
    private String calculeazaTVA(String price) {
        try {
            double pretInitial = Double.parseDouble(price); // Convertire pret initial la double
            double pretCuTVA = pretInitial * 1.10; // Calculare pret cu TVA
            return String.format("%.2f", pretCuTVA); // Returnare pret cu TVA formatat
        } catch (NumberFormatException e) {
            frame.showError("Format pret invalid"); // Afisare mesaj de eroare pentru format pret invalid
            return price; // Returnare pret initial
        }
    }

    // Metoda pentru salvarea produsului in fisier
    private void salveazaProdusFisier(String nume, String compozitie, String indicatii,
                                      String contraindicatii, String mod, String stoc, String pret) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("stoc.txt", true))) {
            writer.write(nume + ":" + compozitie + ":" + indicatii + ":" + contraindicatii + ":" + mod + ":" + stoc + ":" + pret); // Scriere produs in fisier
            writer.newLine(); // Linie noua
        } catch (IOException e) {
            frame.showError("Eroare: " + e.getMessage()); // Afisare mesaj de eroare
        }
    }

    // Metoda pentru deschiderea panoului de detalii produs pentru editare
    public void openDetailPanel(MainFrame frame, int row) {
        Produs_Panel detailPanel = new Produs_Panel(frame,
                (String) frame.getModel().getValueAt(row, 0),
                (String) frame.getModel().getValueAt(row, 1),
                (String) frame.getModel().getValueAt(row, 2),
                (String) frame.getModel().getValueAt(row, 3),
                (String) frame.getModel().getValueAt(row, 4),
                (String) frame.getModel().getValueAt(row, 5),
                (String) frame.getModel().getValueAt(row, 6)
        ); // Creare instanta Produs_Panel cu detalii produs

        int result = JOptionPane.showConfirmDialog(null, detailPanel, "Editare Produs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE); // Afisare dialog pentru editarea produsului

        if (result == JOptionPane.OK_OPTION) { // Daca utilizatorul a apasat OK
            handleEditProduct(detailPanel, row); // Apeleaza metoda de gestionare a editarii produsului
        }
    }

    // Metoda pentru gestionarea editarii unui produs
    private void handleEditProduct(Produs_Panel detailPanel, int row) {
        String oldPrice = (String) frame.getModel().getValueAt(row, 6); // Obtine pretul vechi
        String newPrice = detailPanel.getPretField().getText(); // Obtine noul pret
        String finalPrice = oldPrice.equals(newPrice) ? oldPrice : calculeazaTVA(newPrice); // Daca pretul a fost schimbat, calculeaza pretul cu TVA

        frame.getModel().setValueAt(detailPanel.getNumeField().getText(), row, 0); // Seteaza noua valoare pentru nume
        frame.getModel().setValueAt(detailPanel.getCompozitieField().getText(), row, 1); // Seteaza noua valoare pentru compozitie
        frame.getModel().setValueAt(detailPanel.getIndicatiiField().getText(), row, 2); // Seteaza noua valoare pentru indicatii
        frame.getModel().setValueAt(detailPanel.getContraindicatiiField().getText(), row, 3); // Seteaza noua valoare pentru contraindicatii
        frame.getModel().setValueAt(detailPanel.getModField().getText(), row, 4); // Seteaza noua valoare pentru mod de administrare
        frame.getModel().setValueAt(detailPanel.getStocField().getText(), row, 5); // Seteaza noua valoare pentru stoc
        frame.getModel().setValueAt(finalPrice, row, 6); // Seteaza noua valoare pentru pret

        new Stoc_Farmacie().saveAllDataToFile(frame.getModel()); // Salvare toate datele in fisier
    }

    // Metoda pentru adaugarea unui produs in cos
    private void adaugaProdusInCos() {
        String[] produs = new String[]{
                numeField.getText(), // Obtine numele produsului
                compozitieField.getText(), // Obtine compozitia produsului
                indicatiiField.getText(), // Obtine indicatiile produsului
                contraindicatiiField.getText(), // Obtine contraindicatiile produsului
                modField.getText(), // Obtine modul de administrare al produsului
                stocField.getText(), // Obtine stocul produsului
                pretField.getText() // Obtine pretul produsului
        };
        frame.getCosProduse().add(produs); // Adaugare produs in cos
        frame.showMessage("Produsul a fost adaugat in cos."); // Afisare mesaj de confirmare
    }
}

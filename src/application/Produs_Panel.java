package application;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Produs_Panel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField numeField, compozitieField, indicatiiField, contraindicatiiField, modAdminField, stocField, pretField; // Declarare campuri pentru detaliile produsului
    private MainFrame mainFrame; // Referinta la obiectul MainFrame

    // Constructor pentru initializarea panoului de produs (NOU) cu referinta la MainFrame
    public Produs_Panel(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Initializare referinta mainFrame
        setBackground(Color.LIGHT_GRAY);
        setupPanel(); // Configurarea panoului
    }

    // Constructor pentru initializarea panoului de produs (CARE EXISTA) cu detalii predefinite si referinta la MainFrame
    public Produs_Panel(MainFrame mainFrame, String nume, String compozitie, String indicatii, String contraindicatii, String mod, String stoc, String pret) {
        this.mainFrame = mainFrame; // Initializare referinta mainFrame
        setBackground(Color.LIGHT_GRAY);
        setupPanel(); // Configurarea panoului
        numeField.setText(nume); // Setare text pentru campul nume
        compozitieField.setText(compozitie); // Setare text pentru campul compozitie
        indicatiiField.setText(indicatii); // Setare text pentru campul indicatii
        contraindicatiiField.setText(contraindicatii); // Setare text pentru campul contraindicatii
        modAdminField.setText(mod); // Setare text pentru campul mod de administrare
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
        modAdminField = new JTextField(); // Creare camp text pentru mod de administrare
        modAdminField.setBounds(160, 170, 200, 30); // Setare pozitie si dimensiune

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
        add(modAdminField); // Adaugare camp text mod de administrare la panou
        add(stocLabel); // Adaugare eticheta stoc la panou
        add(stocField); // Adaugare camp text stoc la panou
        add(pretLabel); // Adaugare eticheta pret la panou
        add(pretField); // Adaugare camp text pret la panou
        add(adaugaInCosButton); // Adaugare buton adauga in cos la panou
    }

    // Getteri pentru campurile de text
 // Metoda pentru obtinerea campului de text pentru nume
    public JTextField getNumeField() {
        return numeField; // Returneaza campul de text pentru nume
    }
    // Metoda pentru obtinerea campului de text pentru compozitie
    public JTextField getCompozitieField() {
        return compozitieField; // Returneaza campul de text pentru compozitie
    }
    // Metoda pentru obtinerea campului de text pentru indicatii
    public JTextField getIndicatiiField() {
        return indicatiiField; // Returneaza campul de text pentru indicatii
    }
    // Metoda pentru obtinerea campului de text pentru contraindicatii
    public JTextField getContraindicatiiField() {
        return contraindicatiiField; // Returneaza campul de text pentru contraindicatii
    }
    // Metoda pentru obtinerea campului de text pentru modul de administrare
    public JTextField getModField() {
        return modAdminField; // Returneaza campul de text pentru modul de administrare
    }
    // Metoda pentru obtinerea campului de text pentru stoc
    public JTextField getStocField() {
        return stocField; // Returneaza campul de text pentru stoc
    }
    // Metoda pentru obtinerea campului de text pentru pret
    public JTextField getPretField() {
        return pretField; // Returneaza campul de text pentru pret
    }

    // Metoda pentru adaugarea unui produs nou
    public void adaugaProdusStoc() {
        Produs_Panel produsPanel = new Produs_Panel(mainFrame); // Creare instanta Produs_Panel
        int result = JOptionPane.showConfirmDialog(null, produsPanel, "Adauga Produs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE); // Afisare dialog pentru adaugarea produsului

        if (result == JOptionPane.OK_OPTION) { // Daca utilizatorul a apasat OK
            handleAdaugaProdus(produsPanel); // Apeleaza metoda de gestionare a adaugarii produsului
        }
    }

    // Metoda pentru gestionarea adaugarii unui produs
    private void handleAdaugaProdus(Produs_Panel produsPanel) {
        String calculeazaTVA = calculeazaTVA(produsPanel.getPretField().getText()); // Calculare pret cu TVA

        mainFrame.getModelTabelDefault().addRow(new Object[]{
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
    private String calculeazaTVA(String pret) {
        try {
            double pretInitial = Double.parseDouble(pret); // Convertire pret initial la double
            double pretCuTVA = pretInitial * 1.10; // Calculare pret cu TVA
            return String.format("%.2f", pretCuTVA); // Returnare pret cu TVA formatat
        } catch (NumberFormatException e) {
            mainFrame.showError("Format pret invalid"); // Afisare mesaj de eroare pentru format pret invalid
            return pret; // Returnare pret initial
        }
    }

    // Metoda pentru salvarea produsului in fisier
    private void salveazaProdusFisier(String nume, String compozitie, String indicatii,
                                      String contraindicatii, String mod, String stoc, String pret) {
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter("stoc.txt", true))) { //Am folosit bwriter si fwriter pentru eficienta (se putea folosi si doar 1 din ele)
            bWriter.write(nume + ":" + compozitie + ":" + indicatii + ":" + contraindicatii + ":" + mod + ":" + stoc + ":" + pret); // Scriere produs in fisier
            bWriter.newLine(); // Linie noua
        } catch (IOException e) {
            mainFrame.showError("Eroare: " + e.getMessage()); // Afisare mesaj de eroare
        }
    }

    // Metoda pentru deschiderea panoului de detalii produs pentru editare
    public void deschideDetailPanel(MainFrame mainFrame, int row) {
        Produs_Panel detailPanel = new Produs_Panel(mainFrame,
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 0),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 1),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 2),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 3),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 4),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 5),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 6)
        ); // Creare instanta Produs_Panel cu detalii produs

        int clicked = JOptionPane.showConfirmDialog(null, detailPanel, "Editare Produs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE); // Afisare dialog pentru editarea produsului

        if (clicked == JOptionPane.OK_OPTION) { // Daca utilizatorul a apasat OK
            editeazaProdus(detailPanel, row); // Apeleaza metoda de gestionare a editarii produsului
        }
    }

    // Metoda pentru gestionarea editarii unui produs
    private void editeazaProdus(Produs_Panel detailPanel, int row) {
        String pretInitial = (String) mainFrame.getModelTabelDefault().getValueAt(row, 6); // Arata pretul vechi din tabel
        String pretNou = detailPanel.getPretField().getText(); //Arata pretul din fereastra de editare
        String finalPrice = pretInitial.equals(pretNou) ? pretInitial : calculeazaTVA(pretNou); // Daca pretul a fost schimbat, calculeaza pretul cu TVA

        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getNumeField().getText(), row, 0); // Seteaza noua valoare pentru nume
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getCompozitieField().getText(), row, 1); // Seteaza noua valoare pentru compozitie
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getIndicatiiField().getText(), row, 2); // Seteaza noua valoare pentru indicatii
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getContraindicatiiField().getText(), row, 3); // Seteaza noua valoare pentru contraindicatii
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getModField().getText(), row, 4); // Seteaza noua valoare pentru mod de administrare
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getStocField().getText(), row, 5); // Seteaza noua valoare pentru stoc
        mainFrame.getModelTabelDefault().setValueAt(finalPrice, row, 6); // Seteaza noua valoare pentru pret

        new Stoc_Farmacie().salveazaStocInFisier(mainFrame.getModelTabelDefault()); // Salvare toate datele in fisier
    }

    // Metoda pentru adaugarea unui produs in cos
    private void adaugaProdusInCos() {
        String[] produs = new String[]{
                numeField.getText(), // Obtine numele produsului
                compozitieField.getText(), // Obtine compozitia produsului
                indicatiiField.getText(), // Obtine indicatiile produsului
                contraindicatiiField.getText(), // Obtine contraindicatiile produsului
                modAdminField.getText(), // Obtine modul de administrare al produsului
                stocField.getText(), // Obtine stocul produsului
                pretField.getText() // Obtine pretul produsului
        };
        mainFrame.getCosProduse().add(produs); // Adaugare produs in cos
        mainFrame.showMessage("Produs adagat in cos"); // Afisare mesaj de confirmare
    }
}

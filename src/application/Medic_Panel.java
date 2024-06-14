package application;

import javax.swing.*;

import java.awt.Color;
import java.io.*;

public class Medic_Panel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField nrRetetaField, numeMedicField, prenumeMedicField, codMedicField; // Campuri de text pentru detalii reteta si medic
    private MainFrame mainFrame; // Referinta la obiectul MainFrame
    
    public Medic_Panel(MainFrame mainFrame, Cos_Cumparaturi cosCumparaturi) {
        this.mainFrame = mainFrame; // Initializare referinta mainFrame
        setLayout(null); // Setare layout nul
        displayMedicComponents(); // Configurarea componentelor
        setBackground(Color.LIGHT_GRAY);
    }

    private void displayMedicComponents() {
        JLabel nrRetetaLabel = generatorLabel("Nr. reteta:", 20, 20); // Creare eticheta pentru nr. reteta
        nrRetetaField = generatorTextField(90, 20); // Creare camp text pentru nr. reteta

        JLabel numeLabel = generatorLabel("Nume:", 20, 60); // Creare eticheta pentru nume
        numeMedicField = generatorTextField(90, 60); // Creare camp text pentru nume

        JLabel prenumeLabel = generatorLabel("Prenume:", 20, 100); // Creare eticheta pentru prenume
        prenumeMedicField = generatorTextField(90, 100); // Creare camp text pentru prenume

        JLabel codMedicLabel = generatorLabel("Cod Medic:", 20, 140); // Creare eticheta pentru cod medic
        codMedicField = generatorTextField(90, 140); // Creare camp text pentru cod medic

        JButton submitButton = new JButton("Submit"); // Creare buton pentru submit
        submitButton.setBounds(90, 180, 100, 30); // Setare pozitie si dimensiune buton
        submitButton.addActionListener(e -> saveInputsToFile()); // Adaugare ascultator pentru buton
        
        
        ImageIcon logo = new ImageIcon("ePharmacy_logo1.png"); // Load the image
        JLabel logoLabel = new JLabel(logo); // Create a JLabel with the image
        logoLabel.setBounds(40, 340, 240, 240); // Set position and size (adjust as needed)

        add(nrRetetaLabel); // Adaugare eticheta nr. reteta in panou
        add(nrRetetaField); // Adaugare camp text nr. reteta in panou
        add(numeLabel); // Adaugare eticheta nume in panou
        add(numeMedicField); // Adaugare camp text nume in panou
        add(prenumeLabel); // Adaugare eticheta prenume in panou
        add(prenumeMedicField); // Adaugare camp text prenume in panou
        add(codMedicLabel); // Adaugare eticheta cod medic in panou
        add(codMedicField); // Adaugare camp text cod medic in panou
        add(submitButton); // Adaugare buton in panou
        add(logoLabel); // Add logo to the panel
    }

    private void saveInputsToFile() {
        String nrReteta = nrRetetaField.getText(); // Obtine textul din campul nr. reteta
        String nume = numeMedicField.getText(); // Obtine textul din campul nume
        String prenume = prenumeMedicField.getText(); // Obtine textul din campul prenume
        String codMedic = codMedicField.getText(); // Obtine textul din campul cod medic

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("retete.txt", true))) { // Deschidere fisier pentru scriere in mod append
            bw.write(nrReteta + ":" + nume + ":" + prenume + ":" + codMedic); // Scriere detalii reteta in fisier
            bw.newLine(); // Linie noua
            mainFrame.showMessage("Reteta adaugata"); // Afisare mesaj de succes
            clearTextFields(nrRetetaField, numeMedicField, prenumeMedicField, codMedicField); // Stergere text din campuri
        } catch (IOException e) {
            mainFrame.showError("Eroare la salvarea retetei: " + e.getMessage()); // Afisare mesaj de eroare daca apare o exceptie la scrierea fisierului
        }
    }
    // VARARGS = variable argument (accepts 0 or multiple arguments)
    private void clearTextFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText(""); // Stergere text din fiecare camp
        }
    }
    
    //Generator Labels 
    private JLabel generatorLabel(String text, int x, int y) {
        JLabel label = new JLabel(text); // Creare eticheta cu textul specificat
        label.setBounds(x, y, 100, 30); // Setare pozitie si dimensiune eticheta
        return label; // Returnare eticheta
    }
    //Generator TextFields
    private JTextField generatorTextField(int x, int y) {
        JTextField textField = new JTextField(15); // Creare camp text cu lungime maxima de 15 caractere
        textField.setBounds(x, y, 200, 30); // Setare pozitie si dimensiune camp text
        return textField; // Returnare camp text
    }
}

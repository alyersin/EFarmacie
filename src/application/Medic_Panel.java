package application;

import javax.swing.*;
import java.io.*;

public class Medic_Panel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JTextField nrRetetaField, numeMedicField, prenumeMedicField, codMedicField; // Campuri de text pentru detalii reteta si medic
    private MainFrame frame; // Referinta la obiectul MainFrame
    public Medic_Panel(MainFrame frame, Cos_Cumparaturi cosCumparaturi) {
        this.frame = frame; // Initializare referinta frame
        setLayout(null); // Setare layout nul
        setupComponents(); // Configurarea componentelor
    }

    private void setupComponents() {
        JLabel nrRetetaLabel = createLabel("Nr. reteta:", 20, 20); // Creare eticheta pentru nr. reteta
        nrRetetaField = createTextField(120, 20); // Creare camp text pentru nr. reteta

        JLabel numeLabel = createLabel("Nume:", 20, 60); // Creare eticheta pentru nume
        numeMedicField = createTextField(120, 60); // Creare camp text pentru nume

        JLabel prenumeLabel = createLabel("Prenume:", 20, 100); // Creare eticheta pentru prenume
        prenumeMedicField = createTextField(120, 100); // Creare camp text pentru prenume

        JLabel codMedicLabel = createLabel("Cod Medic:", 20, 140); // Creare eticheta pentru cod medic
        codMedicField = createTextField(120, 140); // Creare camp text pentru cod medic

        JButton submitButton = new JButton("Submit"); // Creare buton pentru submit
        submitButton.setBounds(120, 180, 100, 30); // Setare pozitie si dimensiune buton
        submitButton.addActionListener(e -> saveInputsToFile()); // Adaugare ascultator pentru buton

        add(nrRetetaLabel); // Adaugare eticheta nr. reteta in panou
        add(nrRetetaField); // Adaugare camp text nr. reteta in panou
        add(numeLabel); // Adaugare eticheta nume in panou
        add(numeMedicField); // Adaugare camp text nume in panou
        add(prenumeLabel); // Adaugare eticheta prenume in panou
        add(prenumeMedicField); // Adaugare camp text prenume in panou
        add(codMedicLabel); // Adaugare eticheta cod medic in panou
        add(codMedicField); // Adaugare camp text cod medic in panou
        add(submitButton); // Adaugare buton in panou
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text); // Creare eticheta cu textul specificat
        label.setBounds(x, y, 100, 30); // Setare pozitie si dimensiune eticheta
        return label; // Returnare eticheta
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField(15); // Creare camp text cu lungime maxima de 15 caractere
        textField.setBounds(x, y, 200, 30); // Setare pozitie si dimensiune camp text
        return textField; // Returnare camp text
    }

    private void saveInputsToFile() {
        String nrReteta = nrRetetaField.getText(); // Obtine textul din campul nr. reteta
        String nume = numeMedicField.getText(); // Obtine textul din campul nume
        String prenume = prenumeMedicField.getText(); // Obtine textul din campul prenume
        String codMedic = codMedicField.getText(); // Obtine textul din campul cod medic

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("retete.txt", true))) { // Deschidere fisier pentru scriere in mod append
            writer.write(nrReteta + ":" + nume + ":" + prenume + ":" + codMedic); // Scriere detalii reteta in fisier
            writer.newLine(); // Linie noua
            frame.showMessage("Reteta salvata cu succes."); // Afisare mesaj de succes
            clearTextFields(nrRetetaField, numeMedicField, prenumeMedicField, codMedicField); // Stergere text din campuri
        } catch (IOException e) {
            frame.showError("Eroare la salvarea retetei: " + e.getMessage()); // Afisare mesaj de eroare daca apare o exceptie la scrierea fisierului
        }
    }

    private void clearTextFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText(""); // Stergere text din fiecare camp
        }
    }
}

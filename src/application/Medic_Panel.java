package application;

import javax.swing.*;
import java.awt.Color;
import java.io.*;

public class Medic_Panel extends JPanel {
    private static final long serialVersionUID = 1L;
    
    private JTextField nrRetetaField, numeMedicField, prenumeMedicField, codMedicField;
    private MainFrame mainFrame;
    
    public Medic_Panel(MainFrame mainFrame, Cos_Cumparaturi cosCumparaturi) {
        this.mainFrame = mainFrame;
        setLayout(null);
        displayMedicComponents();
        setBackground(Color.LIGHT_GRAY);
    }

    private void displayMedicComponents() {
        JLabel nrRetetaLabel = generatorLabel("Nr. reteta:", 5, 20);
        nrRetetaField = generatorTextField(75, 20);

        JLabel numeLabel = generatorLabel("Nume:", 5, 60);
        numeMedicField = generatorTextField(75, 60);

        JLabel prenumeLabel = generatorLabel("Prenume:", 5, 100);
        prenumeMedicField = generatorTextField(75, 100);

        JLabel codMedicLabel = generatorLabel("Cod Medic:", 5, 140);
        codMedicField = generatorTextField(75, 140);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(75, 180, 100, 30);
        submitButton.addActionListener(e -> salveazaRetetaInFisier());
        
        ImageIcon logo = new ImageIcon("ePharmacy_logo1.png");
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds(30, 340, 240, 240);

        add(nrRetetaLabel);
        add(nrRetetaField);
        add(numeLabel);
        add(numeMedicField);
        add(prenumeLabel);
        add(prenumeMedicField);
        add(codMedicLabel);
        add(codMedicField);
        add(submitButton);
        add(logoLabel);
    }

    private void salveazaRetetaInFisier() {
        String nrReteta = nrRetetaField.getText();
        String nume = numeMedicField.getText();
        String prenume = prenumeMedicField.getText();
        String codMedic = codMedicField.getText();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("retete.txt", true))) {
            bw.write(nrReteta + ":" + nume + ":" + prenume + ":" + codMedic);
            bw.newLine();
            mainFrame.showMessage("Reteta adaugata");
            clearTextFields(nrRetetaField, numeMedicField, prenumeMedicField, codMedicField);
        } catch (IOException e) {
            mainFrame.showError("Eroare la salvarea retetei: " + e.getMessage());
        }
    }

    private void clearTextFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
    
    private JLabel generatorLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 100, 30);
        return label;
    }

    private JTextField generatorTextField(int x, int y) {
        JTextField textField = new JTextField(15);
        textField.setBounds(x, y, 200, 30);
        return textField;
    }
}

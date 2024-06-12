package application;

import javax.swing.*;
import java.io.*;

public class Medic_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField nrRetetaField, numeMedicField, prenumeMedicField, codMedicField;

    public Medic_Panel(MainFrame frame) {
        setLayout(null);
        setupComponents(frame);
    }

    private void setupComponents(MainFrame frame) {
        JLabel nrRetetaLabel = createLabel("Nr. reteta:", 20, 20);
        nrRetetaField = createTextField(120, 20);

        JLabel numeLabel = createLabel("Nume:", 20, 60);
        numeMedicField = createTextField(120, 60);

        JLabel prenumeLabel = createLabel("Prenume:", 20, 100);
        prenumeMedicField = createTextField(120, 100);

        JLabel codMedicLabel = createLabel("Cod Medic:", 20, 140);
        codMedicField = createTextField(120, 140);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(120, 180, 100, 30);
        submitButton.addActionListener(e -> saveInputsToFile(frame));

        add(nrRetetaLabel);
        add(nrRetetaField);
        add(numeLabel);
        add(numeMedicField);
        add(prenumeLabel);
        add(prenumeMedicField);
        add(codMedicLabel);
        add(codMedicField);
        add(submitButton);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 100, 30);
        return label;
    }

    private JTextField createTextField(int x, int y) {
        JTextField textField = new JTextField(15);
        textField.setBounds(x, y, 200, 30);
        return textField;
    }

    private void saveInputsToFile(MainFrame frame) {
        String nrReteta = nrRetetaField.getText();
        String nume = numeMedicField.getText();
        String prenume = prenumeMedicField.getText();
        String codMedic = codMedicField.getText();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("retete.txt", true))) {
            writer.write(nrReteta + ":" + nume + ":" + prenume + ":" + codMedic);
            writer.newLine();
            frame.showMessage("Reteta salvata cu succes.");
            clearTextFields(nrRetetaField, numeMedicField, prenumeMedicField, codMedicField);
        } catch (IOException e) {
            frame.showError("Eroare la salvarea retetei: " + e.getMessage());
        }
    }

    private void clearTextFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
}

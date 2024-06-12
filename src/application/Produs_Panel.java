package application;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Produs_Panel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField numeField, compozitieField, indicatiiField, contraindicatiiField, modField, stocField, pretField;
    private MainFrame frame;

    public Produs_Panel() {
        setupPanel();
    }

    public Produs_Panel(MainFrame frame) {
        this.frame = frame;
        setupPanel();
    }

    public Produs_Panel(String nume, String compozitie, String indicatii, String contraindicatii, String mod, String stoc, String pret) {
        setupPanel();
        numeField.setText(nume);
        compozitieField.setText(compozitie);
        indicatiiField.setText(indicatii);
        contraindicatiiField.setText(contraindicatii);
        modField.setText(mod);
        stocField.setText(stoc);
        pretField.setText(pret);
    }

    private void setupPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(400, 320));

        JLabel numeLabel = new JLabel("Nume:");
        numeLabel.setBounds(10, 10, 100, 30);
        numeField = new JTextField();
        numeField.setBounds(120, 10, 200, 30);

        JLabel compozitieLabel = new JLabel("Compozitie:");
        compozitieLabel.setBounds(10, 50, 100, 30);
        compozitieField = new JTextField();
        compozitieField.setBounds(120, 50, 200, 30);

        JLabel indicatiiLabel = new JLabel("Indicatii:");
        indicatiiLabel.setBounds(10, 90, 100, 30);
        indicatiiField = new JTextField();
        indicatiiField.setBounds(120, 90, 200, 30);

        JLabel contraindicatiiLabel = new JLabel("Contraindicatii:");
        contraindicatiiLabel.setBounds(10, 130, 100, 30);
        contraindicatiiField = new JTextField();
        contraindicatiiField.setBounds(120, 130, 200, 30);

        JLabel modLabel = new JLabel("Mod de administrare:");
        modLabel.setBounds(10, 170, 140, 30);
        modField = new JTextField();
        modField.setBounds(160, 170, 200, 30);

        JLabel stocLabel = new JLabel("Stoc:");
        stocLabel.setBounds(10, 210, 100, 30);
        stocField = new JTextField();
        stocField.setBounds(120, 210, 200, 30);

        JLabel pretLabel = new JLabel("Pret:");
        pretLabel.setBounds(10, 250, 100, 30);
        pretField = new JTextField();
        pretField.setBounds(120, 250, 200, 30);

        add(numeLabel);
        add(numeField);
        add(compozitieLabel);
        add(compozitieField);
        add(indicatiiLabel);
        add(indicatiiField);
        add(contraindicatiiLabel);
        add(contraindicatiiField);
        add(modLabel);
        add(modField);
        add(stocLabel);
        add(stocField);
        add(pretLabel);
        add(pretField);
    }

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

    public void adaugaProdus() {
        Produs_Panel produsPanel = new Produs_Panel();
        int result = JOptionPane.showConfirmDialog(null, produsPanel, "Adauga Produs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            handleAdaugaProdus(produsPanel);
        }
    }

    private void handleAdaugaProdus(Produs_Panel produsPanel) {
        String calculeazaTVA = calculeazaTVA(produsPanel.getPretField().getText());

        frame.getModel().addRow(new Object[]{
                produsPanel.getNumeField().getText(),
                produsPanel.getCompozitieField().getText(),
                produsPanel.getIndicatiiField().getText(),
                produsPanel.getContraindicatiiField().getText(),
                produsPanel.getModField().getText(),
                produsPanel.getStocField().getText(),
                calculeazaTVA
        });

        salveazaProdusFisier(produsPanel.getNumeField().getText(), produsPanel.getCompozitieField().getText(),
                produsPanel.getIndicatiiField().getText(), produsPanel.getContraindicatiiField().getText(),
                produsPanel.getModField().getText(), produsPanel.getStocField().getText(), calculeazaTVA);
    }

    private String calculeazaTVA(String price) {
        try {
            double pretInitial = Double.parseDouble(price);
            double pretCuTVA = pretInitial * 1.10;
            return String.format("%.2f", pretCuTVA);
        } catch (NumberFormatException e) {
            frame.showError("Format pret invalid");
            return price;
        }
    }

    private void salveazaProdusFisier(String nume, String compozitie, String indicatii,
                                      String contraindicatii, String mod, String stoc, String pret) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("stoc.txt", true))) {
            writer.write(nume + ":" + compozitie + ":" + indicatii + ":" + contraindicatii + ":" + mod + ":" + stoc + ":" + pret);
            writer.newLine();
        } catch (IOException e) {
            frame.showError("Eroare: " + e.getMessage());
        }
    }

    public void openDetailPanel(int row) {
        Produs_Panel detailPanel = new Produs_Panel(
                (String) frame.getModel().getValueAt(row, 0),
                (String) frame.getModel().getValueAt(row, 1),
                (String) frame.getModel().getValueAt(row, 2),
                (String) frame.getModel().getValueAt(row, 3),
                (String) frame.getModel().getValueAt(row, 4),
                (String) frame.getModel().getValueAt(row, 5),
                (String) frame.getModel().getValueAt(row, 6)
        );

        int result = JOptionPane.showConfirmDialog(null, detailPanel, "Editare Produs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            handleEditProduct(detailPanel, row);
        }
    }

    private void handleEditProduct(Produs_Panel detailPanel, int row) {
        String oldPrice = (String) frame.getModel().getValueAt(row, 6);
        String newPrice = detailPanel.getPretField().getText();
        String finalPrice = oldPrice.equals(newPrice) ? oldPrice : calculeazaTVA(newPrice);

        frame.getModel().setValueAt(detailPanel.getNumeField().getText(), row, 0);
        frame.getModel().setValueAt(detailPanel.getCompozitieField().getText(), row, 1);
        frame.getModel().setValueAt(detailPanel.getIndicatiiField().getText(), row, 2);
        frame.getModel().setValueAt(detailPanel.getContraindicatiiField().getText(), row, 3);
        frame.getModel().setValueAt(detailPanel.getModField().getText(), row, 4);
        frame.getModel().setValueAt(detailPanel.getStocField().getText(), row, 5);
        frame.getModel().setValueAt(finalPrice, row, 6);

        new Stoc_Farmacie().saveAllDataToFile(frame.getModel());
    }
}

package application;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Produs_Panel extends JPanel {
    private static final long serialVersionUID = 1L;

    private JTextField numeField, compozitieField, indicatiiField, contraindicatiiField, modAdminField, stocField, pretField;
    private MainFrame mainFrame;

    public Produs_Panel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setBackground(Color.LIGHT_GRAY);
        displayProdusPanel();
    }

    public Produs_Panel(MainFrame mainFrame, String nume, String compozitie, String indicatii, String contraindicatii, String modAdmin, String stoc, String pret) {
        this.mainFrame = mainFrame;
        setBackground(Color.LIGHT_GRAY);
        displayProdusPanel();
        numeField.setText(nume);
        compozitieField.setText(compozitie);
        indicatiiField.setText(indicatii);
        contraindicatiiField.setText(contraindicatii);
        modAdminField.setText(modAdmin);
        stocField.setText(stoc);
        pretField.setText(pret);
    }

    private void displayProdusPanel() {
        setLayout(null);
        setPreferredSize(new Dimension(400, 350));

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
        modAdminField = new JTextField();
        modAdminField.setBounds(160, 170, 200, 30);

        JLabel stocLabel = new JLabel("Stoc:");
        stocLabel.setBounds(10, 210, 100, 30);
        stocField = new JTextField();
        stocField.setBounds(120, 210, 200, 30);

        JLabel pretLabel = new JLabel("Pret:");
        pretLabel.setBounds(10, 250, 100, 30);
        pretField = new JTextField();
        pretField.setBounds(120, 250, 200, 30);

        JButton adaugaInCosButton = new JButton("Adauga in cos");
        adaugaInCosButton.setBounds(120, 290, 200, 30);
        adaugaInCosButton.addActionListener(e -> adaugaProdusInCos());

        add(numeLabel);
        add(numeField);
        add(compozitieLabel);
        add(compozitieField);
        add(indicatiiLabel);
        add(indicatiiField);
        add(contraindicatiiLabel);
        add(contraindicatiiField);
        add(modLabel);
        add(modAdminField);
        add(stocLabel);
        add(stocField);
        add(pretLabel);
        add(pretField);
        add(adaugaInCosButton);
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

    public JTextField getModAdminField() {
        return modAdminField;
    }

    public JTextField getStocField() {
        return stocField;
    }

    public JTextField getPretField() {
        return pretField;
    }

    // Adauga produs in stoc
    public void adaugaProdusStoc() {
        Produs_Panel produsPanel = new Produs_Panel(mainFrame);
        int result = JOptionPane.showConfirmDialog(null, produsPanel, "Adauga Produs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            gestioneazaAdaugaProdus(produsPanel);
        }
    }

    // Gestionarea adaugarii produsului in stoc
    private void gestioneazaAdaugaProdus(Produs_Panel produsPanel) {
        String calculeazaTVA = calculeazaTVA(produsPanel.getPretField().getText());

        mainFrame.getModelTabelDefault().addRow(new Object[]{
                produsPanel.getNumeField().getText(),
                produsPanel.getCompozitieField().getText(),
                produsPanel.getIndicatiiField().getText(),
                produsPanel.getContraindicatiiField().getText(),
                produsPanel.getModAdminField().getText(),
                produsPanel.getStocField().getText(),
                calculeazaTVA
        });

        salveazaProdusFisier(produsPanel.getNumeField().getText(), produsPanel.getCompozitieField().getText(),
                produsPanel.getIndicatiiField().getText(), produsPanel.getContraindicatiiField().getText(),
                produsPanel.getModAdminField().getText(), produsPanel.getStocField().getText(), calculeazaTVA);
    }

    // Calculeaza TVA
    private String calculeazaTVA(String pret) {
        try {
            double pretInitial = Double.parseDouble(pret);
            double pretCuTVA = pretInitial * 1.10;
            return String.format("%.2f", pretCuTVA);
        } catch (NumberFormatException e) {
            mainFrame.showError("Format pret invalid");
            return pret;
        }
    }

    // Salveaza in fisierul stoc
    private void salveazaProdusFisier(String nume, String compozitie, String indicatii, String contraindicatii, String modAdmin, String stoc, String pret) {
        try (BufferedWriter bWriter = new BufferedWriter(new FileWriter("stoc.txt", true))) {
            bWriter.write(nume + ":" + compozitie + ":" + indicatii + ":" + contraindicatii + ":" + modAdmin + ":" + stoc + ":" + pret);
            bWriter.newLine();
        } catch (IOException e) {
            mainFrame.showError("Eroare: " + e.getMessage());
        }
    }

    // Deschide panou pentru un produs
    public void deschideDetailPanel(MainFrame mainFrame, int row) {
        Produs_Panel detailPanel = new Produs_Panel(mainFrame,
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 0),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 1),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 2),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 3),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 4),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 5),
                (String) mainFrame.getModelTabelDefault().getValueAt(row, 6)
        );

        int clicked = JOptionPane.showConfirmDialog(null, detailPanel, "Editare Produs",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (clicked == JOptionPane.OK_OPTION) {
            editeazaProdus(detailPanel, row);
        }
    }

    // Editare produs din stoc
    private void editeazaProdus(Produs_Panel detailPanel, int row) {
        String pretInitial = (String) mainFrame.getModelTabelDefault().getValueAt(row, 6);
        String pretNou = detailPanel.getPretField().getText();
        String pretFinal = pretInitial.equals(pretNou) ? pretInitial : calculeazaTVA(pretNou);

        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getNumeField().getText(), row, 0);
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getCompozitieField().getText(), row, 1);
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getIndicatiiField().getText(), row, 2);
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getContraindicatiiField().getText(), row, 3);
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getModAdminField().getText(), row, 4);
        mainFrame.getModelTabelDefault().setValueAt(detailPanel.getStocField().getText(), row, 5);
        mainFrame.getModelTabelDefault().setValueAt(pretFinal, row, 6);

        new Stoc_Farmacie().salveazaStocInFisier(mainFrame.getModelTabelDefault());
    }

    // Adauga produs in cos
    private void adaugaProdusInCos() {
        String[] produs = new String[]{
                numeField.getText(),
                compozitieField.getText(),
                indicatiiField.getText(),
                contraindicatiiField.getText(),
                modAdminField.getText(),
                stocField.getText(),
                pretField.getText()
        };
        mainFrame.getCosProduse().add(produs);
        mainFrame.showMessage("Adaugat in cos");
    }
}

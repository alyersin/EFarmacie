package application;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.text.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.ArrayList;
import java.util.List;

public class Cos_Cumparaturi {
    private List<String[]> cosProduse;
    private JPanel retetaPanel;
    private DefaultTableModel retetaTabelModel;

    public Cos_Cumparaturi(List<String[]> cosProduse) {
        this.cosProduse = cosProduse;
        this.retetaPanel = new JPanel();
        retetaPanel.setLayout(new BoxLayout(retetaPanel, BoxLayout.Y_AXIS));
        initPrescriptieTabelModel();
    }

    private void initPrescriptieTabelModel() {
        retetaTabelModel = new DefaultTableModel(new Object[]{"Nr. Reteta", "Nume", "Prenume", "Cod Medic"}, 0);
    }

    public void displayCos(Component parentComponent) {
        JPanel cosPanel = new JPanel(null);
        cosPanel.setPreferredSize(new Dimension(800, 600));
        cosPanel.setBackground(Color.LIGHT_GRAY);

        DefaultTableModel tabelCosProduse = new DefaultTableModel(new Object[]{"Nume", "Indicatii", "Pret", "Cantitate", "Total"}, 0);
        JTable tabelCos = new JTable(tabelCosProduse);
        incarcaProduseTabel(tabelCosProduse);

        JScrollPane scrollPane = new JScrollPane(tabelCos);
        scrollPane.setBounds(10, 10, 780, 300);
        cosPanel.add(scrollPane);

        addTotalRow(tabelCosProduse);

        JTable retetaTabel = new JTable(retetaTabelModel);
        JScrollPane retetaScrollPane = new JScrollPane(retetaTabel);
        retetaScrollPane.setBounds(10, 320, 780, 100);
        cosPanel.add(retetaScrollPane);

        JPanel btnsPanel = cosBtnsPanel(tabelCosProduse, tabelCos);
        btnsPanel.setBounds(10, 430, 780, 50);
        cosPanel.add(btnsPanel);

        incarcaReteteTabel();

        JOptionPane.showMessageDialog(parentComponent, cosPanel, "Cos", JOptionPane.PLAIN_MESSAGE);
    }

    private void incarcaReteteTabel() {
        retetaTabelModel.setRowCount(0);
        String lastLine = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("retete.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (lastLine != null) {
            String[] data = lastLine.split(":");
            if (data.length == 4) {
                retetaTabelModel.addRow(data);
            }
        }
    }

    private void incarcaProduseTabel(DefaultTableModel tabelCosProduse) {
        List<String[]> uniqueItemsArray = new ArrayList<>();
        List<Integer> cantitatiArray = new ArrayList<>();

        for (String[] produs : cosProduse) {
            int index = cautaIndexProdus(uniqueItemsArray, produs[0]);
            if (index != -1) {
                cantitatiArray.set(index, cantitatiArray.get(index) + 1);
            } else {
                uniqueItemsArray.add(produs);
                cantitatiArray.add(1);
            }
        }

        for (int i = 0; i < uniqueItemsArray.size(); i++) {
            String[] produs = uniqueItemsArray.get(i);
            int numarBucati = cantitatiArray.get(i);
            double pretPerItem = Double.parseDouble(produs[6]);
            double pretTotal = pretPerItem * numarBucati;
            tabelCosProduse.addRow(new Object[]{produs[0], produs[2], String.format("%.2f", pretPerItem), numarBucati, String.format("%.2f", pretTotal)});
        }
    }

    private int cautaIndexProdus(List<String[]> uniqueItemsArray, String produsNume) {
        for (int i = 0; i < uniqueItemsArray.size(); i++) {
            if (uniqueItemsArray.get(i)[0].equals(produsNume)) {
                return i;
            }
        }
        return -1;
    }

    private void addTotalRow(DefaultTableModel tabelCosProduse) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelCosProduse.getRowCount(); i++) {
            totalFinal += Double.parseDouble((String) tabelCosProduse.getValueAt(i, 4));
        }

        tabelCosProduse.addRow(new Object[]{"", "", "", "TOTAL", String.format("%.2f", totalFinal)});
    }

    private JPanel cosBtnsPanel(DefaultTableModel tabelCosProduse, JTable tabelCos) {
        JButton stergeBtn = generatorButoane("Sterge", e -> stergeProdusDinCos(tabelCos, tabelCosProduse));
        JButton printBtn = generatorButoane("Printeaza Factura", e -> printFactura(tabelCos));
        JButton discountBtn = generatorButoane("Compensat", e -> aplicaReducere(tabelCosProduse));
        JButton stergeRetetaBtn = generatorButoane("Sterge Reteta", e -> stergeRandDinPrescriptie());

        JPanel btnsPanel = new JPanel(null);
        btnsPanel.setPreferredSize(new Dimension(780, 50));

        stergeBtn.setBounds(10, 10, 120, 30);
        btnsPanel.add(stergeBtn);
        printBtn.setBounds(140, 10, 150, 30);
        btnsPanel.add(printBtn);
        discountBtn.setBounds(300, 10, 120, 30);
        btnsPanel.add(discountBtn);
        stergeRetetaBtn.setBounds(430, 10, 180, 30);
        btnsPanel.add(stergeRetetaBtn);

        return btnsPanel;
    }

    private void stergeProdusDinCos(JTable tabelCos, DefaultTableModel tabelCosProduse) {
        int randSelectat = tabelCos.getSelectedRow();
        if (randSelectat != -1) {
            String numeSelectat = (String) tabelCosProduse.getValueAt(randSelectat, 0);
            tabelCosProduse.removeRow(randSelectat);

            for (int i = 0; i < cosProduse.size(); i++) {
                if (cosProduse.get(i)[0].equals(numeSelectat)) {
                    cosProduse.remove(i);
                    break;
                }
            }
            recalculeazaTotal(tabelCosProduse);
        }
    }

    private void stergeRandDinPrescriptie() {
        int randSelectat = retetaTabelModel.getRowCount() - 1;
        if (randSelectat >= 0) {
            retetaTabelModel.removeRow(randSelectat);
        }
    }

    private void printFactura(JTable tabelCos) {
        try {
            tabelCos.print(JTable.PrintMode.FIT_WIDTH,
                new MessageFormat("Factura - " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date())),
                new MessageFormat("Page {0}"));
            retetaPanel.removeAll();
            retetaPanel.revalidate();
            retetaPanel.repaint();
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null, "Eroare la printare: " + pe.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aplicaReducere(DefaultTableModel tabelCosProduse) {
        for (int i = 0; i < tabelCosProduse.getRowCount(); i++) {
            String indicatii = (String) tabelCosProduse.getValueAt(i, 1);
            if (indicatii != null) {
                double pretCuDiscount = getPretRedus(indicatii.toLowerCase(), (String) tabelCosProduse.getValueAt(i, 2));
                tabelCosProduse.setValueAt(String.format("%.2f", pretCuDiscount), i, 2);

                int numarBucati = (int) tabelCosProduse.getValueAt(i, 3);
                double pretTotal = pretCuDiscount * numarBucati;
                tabelCosProduse.setValueAt(String.format("%.2f", pretTotal), i, 4);
            }
        }
        actualizareTotalLine(tabelCosProduse);
    }

    private double getPretRedus(String indicatii, String originalPrice) {
        double pret = Double.parseDouble(originalPrice);
        if (indicatii.contains("cancer")) {
            return pret * 0.10;
        } else if (indicatii.contains("mintale") || indicatii.contains("mintala") || indicatii.contains("schizofrenie") || indicatii.contains("psihoza")) {
            return 0.0;
        }
        return pret;
    }

    private void recalculeazaTotal(DefaultTableModel tabelCosProduse) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelCosProduse.getRowCount(); i++) {
            totalFinal += Double.parseDouble((String) tabelCosProduse.getValueAt(i, 4));
        }

        tabelCosProduse.setValueAt(String.format("%.2f", totalFinal), tabelCosProduse.getRowCount() - 1, 4);
    }

    private void actualizareTotalLine(DefaultTableModel tabelCosProduse) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelCosProduse.getRowCount() - 1; i++) {
            totalFinal += Double.parseDouble((String) tabelCosProduse.getValueAt(i, 4));
        }
        tabelCosProduse.setValueAt(String.format("%.2f", totalFinal), tabelCosProduse.getRowCount() - 1, 4);
    }

    private JButton generatorButoane(String text, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.addActionListener(listener);
        return btn;
    }
}

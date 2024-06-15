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
        initializareRetetaTabel();
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

        JPanel buttonsPanel = cosBtnsPanel(tabelCosProduse, tabelCos);
        buttonsPanel.setBounds(10, 430, 780, 50);
        cosPanel.add(buttonsPanel);

        incarcaReteteTabel();

        JOptionPane.showMessageDialog(parentComponent, cosPanel, "Cos", JOptionPane.PLAIN_MESSAGE);
    }

    // Initializare tabel reteta
    private void initializareRetetaTabel() {
        retetaTabelModel = new DefaultTableModel(new Object[]{"Nr. Reteta", "Nume", "Prenume", "Cod Medic"}, 0);
    }

    // Incarca datele in tabelul reteta din fisierul "retete.txt"
    private void incarcaReteteTabel() {
        retetaTabelModel.setRowCount(0);
        String ultimaLinie = null;
        try (BufferedReader br = new BufferedReader(new FileReader("retete.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                ultimaLinie = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (ultimaLinie != null) {
            String[] data = ultimaLinie.split(":");
            if (data.length == 4) {
                retetaTabelModel.addRow(data);
            }
        }
    }

    // Incarca produsele din cos in tabelul de produse
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

    // Cauta indexul unui produs in lista de produse unice
    private int cautaIndexProdus(List<String[]> uniqueItemsArray, String produsNume) {
        for (int i = 0; i < uniqueItemsArray.size(); i++) {
            if (uniqueItemsArray.get(i)[0].equals(produsNume)) {
                return i;
            }
        }
        return -1;
    }

    // Adauga randul total in tabelul din cos
    private void addTotalRow(DefaultTableModel tabelCosProduse) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelCosProduse.getRowCount(); i++) {
            totalFinal += Double.parseDouble((String) tabelCosProduse.getValueAt(i, 4));
        }

        tabelCosProduse.addRow(new Object[]{"", "", "", "TOTAL", String.format("%.2f", totalFinal)});
    }

    // Creaza panoul cu butoane din Cos
    private JPanel cosBtnsPanel(DefaultTableModel tabelCosProduse, JTable tabelCos) {
        JButton stergeBtn = generatorButoane("Sterge", e -> stergeProdusDinCos(tabelCos, tabelCosProduse));
        JButton printBtn = generatorButoane("Printeaza Factura", e -> printFactura(tabelCos));
        JButton discountBtn = generatorButoane("Compensat", e -> aplicaReducere(tabelCosProduse));
        JButton stergeRetetaBtn = generatorButoane("Sterge Reteta", e -> stergeRandDinPrescriptie());

        JPanel buttonsPanel = new JPanel(null);
        buttonsPanel.setPreferredSize(new Dimension(780, 50));

        stergeBtn.setBounds(10, 10, 120, 30);
        buttonsPanel.add(stergeBtn);
        printBtn.setBounds(140, 10, 150, 30);
        buttonsPanel.add(printBtn);
        discountBtn.setBounds(300, 10, 120, 30);
        buttonsPanel.add(discountBtn);
        stergeRetetaBtn.setBounds(430, 10, 180, 30);
        buttonsPanel.add(stergeRetetaBtn);

        return buttonsPanel;
    }

    // Sterge un produs din cos
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

    // Sterge randul din tabelul de prescriptie
    private void stergeRandDinPrescriptie() {
        int randSelectat = retetaTabelModel.getRowCount() - 1;
        if (randSelectat >= 0) {
            retetaTabelModel.removeRow(randSelectat);
        }
    }

    // Printeaza factura
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

    // Aplica o reducere la produsele din cos
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

    // Calculeaza pretul redus in functie de indicatiile produsului
    private double getPretRedus(String indicatii, String originalPrice) {
        double pret = Double.parseDouble(originalPrice);
        if (indicatii.contains("cancer")) {
            return pret * 0.10;
        } else if (indicatii.contains("mintale") || indicatii.contains("mintala") || indicatii.contains("schizofrenie") || indicatii.contains("psihoza")) {
            return 0.0;
        }
        return pret;
    }

    // Recalculeaza totalul dupa stergerea unui produs din cos
    private void recalculeazaTotal(DefaultTableModel tabelCosProduse) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelCosProduse.getRowCount(); i++) {
            totalFinal += Double.parseDouble((String) tabelCosProduse.getValueAt(i, 4));
        }

        tabelCosProduse.setValueAt(String.format("%.2f", totalFinal), tabelCosProduse.getRowCount() - 1, 4);
    }

    // Actualizeaza linia totala dupa aplicarea reducerilor
    private void actualizareTotalLine(DefaultTableModel tabelCosProduse) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelCosProduse.getRowCount() - 1; i++) {
            totalFinal += Double.parseDouble((String) tabelCosProduse.getValueAt(i, 4));
        }
        tabelCosProduse.setValueAt(String.format("%.2f", totalFinal), tabelCosProduse.getRowCount() - 1, 4);
    }

    // Generator butoane cu listeners
    private JButton generatorButoane(String text, ActionListener listener) {
        JButton btn = new JButton(text);
        btn.addActionListener(listener);
        return btn;
    }
}

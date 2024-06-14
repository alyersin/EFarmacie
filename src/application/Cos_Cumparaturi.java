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
    private List<String[]> cosProduse; // Lista pentru produsele din cos
    private JPanel retetaPanel; // Panou pentru prescriptie
    private DefaultTableModel retetaTabelModel; // Model de tabel pentru prescriptie

    public Cos_Cumparaturi(List<String[]> cosProduse) {
        this.cosProduse = cosProduse; // Initializare lista produse din cos
        this.retetaPanel = new JPanel(); // Creare panou prescriptie
        retetaPanel.setLayout(new BoxLayout(retetaPanel, BoxLayout.Y_AXIS)); // Setare layout vertical
        initPrescriptieTabelModel(); // Initializare model tabel prescriptie
    }

    private void initPrescriptieTabelModel() {
        retetaTabelModel = new DefaultTableModel(new Object[]{"Nr. Reteta", "Nume", "Prenume", "Cod Medic"}, 0); // Creare model tabel cu coloane specifice
    }

    public void displayCos(Component parentComponent) {
        JPanel cosPanel = new JPanel(null); // Creare panou pentru cos cu layout nul
        cosPanel.setPreferredSize(new Dimension(800, 600)); // Setare dimensiune preferata a panoului
        cosPanel.setBackground(Color.LIGHT_GRAY); // Setare culoare fundal

        DefaultTableModel tabelCosProduse = new DefaultTableModel(new Object[]{"Nume", "Indicatii", "Pret", "Cantitate", "Total"}, 0); // Creare model tabel pentru cos cu 0 rows
        JTable tabelCos = new JTable(tabelCosProduse); // Creare tabel pentru cos (dupa modelul default)
        incarcaProduseTabel(tabelCosProduse); // Incarcare produse in tabel

        JScrollPane scrollPane = new JScrollPane(tabelCos); // Creare panou de derulare pentru tabel
        scrollPane.setBounds(10, 10, 780, 300); // Setare pozitie si dimensiune panou de derulare
        cosPanel.add(scrollPane); // Adaugare panou de derulare in panoul cos

        addTotalRow(tabelCosProduse); // Adaugare rand total in tabel

        JTable retetaTabel = new JTable(retetaTabelModel); // Creare tabel pentru prescriptie
        JScrollPane retetaScrollPane = new JScrollPane(retetaTabel); // Creare panou de derulare pentru tabel prescriptie
        retetaScrollPane.setBounds(10, 320, 780, 100); // Setare pozitie si dimensiune panou de derulare prescriptie
        cosPanel.add(retetaScrollPane); // Adaugare panou de derulare prescriptie in panoul cos

        JPanel btnsPanel = cosBtnsPanel(tabelCosProduse, tabelCos); // Creare panou butoane pentru cos
        btnsPanel.setBounds(10, 430, 780, 50); // Setare pozitie si dimensiune panou butoane
        cosPanel.add(btnsPanel); // Adaugare panou butoane in panoul cos

        incarcaReteteTabel(); // Incarcare date prescriptie din fisier

        JOptionPane.showMessageDialog(parentComponent, cosPanel, "Cos", JOptionPane.PLAIN_MESSAGE); // Afisare dialog pentru cos
    }

    private void incarcaReteteTabel() {
        retetaTabelModel.setRowCount(0); // Sterge randurile existente din tabel prescriptie
        String lastLine = null;
        try (BufferedReader reader = new BufferedReader(new FileReader("retete.txt"))) { // Deschidere fisier pentru citire
            String line;
            while ((line = reader.readLine()) != null) { // Citire linii din fisier
                lastLine = line; // Pastrare ultima linie citita
            }
        } catch (IOException e) {
            e.printStackTrace(); // Tratament exceptii la citirea fisierului
        }

        if (lastLine != null) {
            String[] data = lastLine.split(":"); // Impartire linie in componente
            if (data.length == 4) {
                retetaTabelModel.addRow(data); // Adaugare rand in tabel prescriptie
            }
        }
    }

    private void incarcaProduseTabel(DefaultTableModel tabelCosProduse) {
        List<String[]> uniqueItemsArray = new ArrayList<>(); // Lista pentru produse unice
        List<Integer> cantitatiArray = new ArrayList<>(); // Lista pentru cantitatiArray

        for (String[] produs : cosProduse) {
            int index = cautaIndexProdus(uniqueItemsArray, produs[0]); // Cautare index produs
            if (index != -1) {
                cantitatiArray.set(index, cantitatiArray.get(index) + 1); // Actualizare cantitate pentru produs existent
            } else {
                uniqueItemsArray.add(produs); // Adaugare produs nou
                cantitatiArray.add(1); // Adaugare cantitate initiala
            }
        }

        for (int i = 0; i < uniqueItemsArray.size(); i++) {
            String[] produs = uniqueItemsArray.get(i);
            int numarBucati = cantitatiArray.get(i);
            double pretPerItem = Double.parseDouble(produs[6]); // Conversie pret per produs la double
            double pretTotal = pretPerItem * numarBucati; // Calculare pret total
            tabelCosProduse.addRow(new Object[]{produs[0], produs[2], String.format("%.2f", pretPerItem), numarBucati, String.format("%.2f", pretTotal)}); // Adaugare rand in tabel
        }
    }

    private int cautaIndexProdus(List<String[]> uniqueItemsArray, String produsNume) {
        for (int i = 0; i < uniqueItemsArray.size(); i++) {
            if (uniqueItemsArray.get(i)[0].equals(produsNume)) { // Comparare nume produs
                return i; // Returnare index daca produsul este gasit
            }
        }
        return -1; // Returnare -1 daca produsul nu este gasit
    }

    private void addTotalRow(DefaultTableModel tabelCosProduse) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelCosProduse.getRowCount(); i++) {
            totalFinal += Double.parseDouble((String) tabelCosProduse.getValueAt(i, 4)); // Calculare total final
        }

        tabelCosProduse.addRow(new Object[]{"", "", "", "TOTAL", String.format("%.2f", totalFinal)}); // Adaugare rand total in tabel
    }

    private JPanel cosBtnsPanel(DefaultTableModel tabelCosProduse, JTable tabelCos) {
        JButton stergeBtn = generatorButoane("Sterge", e -> stergeProdusDinCos(tabelCos, tabelCosProduse)); // Creare buton pentru stergere produs
        JButton printBtn = generatorButoane("Printeaza Factura", e -> printFactura(tabelCos)); // Creare buton pentru printare factura
        JButton discountBtn = generatorButoane("Compensat", e -> aplicaReducere(tabelCosProduse)); // Creare buton pentru aplicare reducere
        JButton stergeRetetaBtn = generatorButoane("Sterge Reteta", e -> stergeRandDinPrescriptie()); // Creare buton pentru stergere prescriptie

        JPanel btnsPanel = new JPanel(null); // Creare panou pentru butoane cu layout nul
        btnsPanel.setPreferredSize(new Dimension(780, 50)); // Setare dimensiune preferata panou butoane

        stergeBtn.setBounds(10, 10, 120, 30); // Setare pozitie si dimensiune buton stergere produs
        btnsPanel.add(stergeBtn); // Adaugare buton stergere produs in panou
        printBtn.setBounds(140, 10, 150, 30); // Setare pozitie si dimensiune buton printare factura
        btnsPanel.add(printBtn); // Adaugare buton printare factura in panou
        discountBtn.setBounds(300, 10, 120, 30); // Setare pozitie si dimensiune buton aplicare reducere
        btnsPanel.add(discountBtn); // Adaugare buton aplicare reducere in panou
        stergeRetetaBtn.setBounds(430, 10, 180, 30); // Setare pozitie si dimensiune buton stergere prescriptie
        btnsPanel.add(stergeRetetaBtn); // Adaugare buton stergere prescriptie in panou

        return btnsPanel; // Returnare panou butoane
    }

    private void stergeProdusDinCos(JTable tabelCos, DefaultTableModel tabelCosProduse) {
        int randSelectat = tabelCos.getSelectedRow(); // Obtine randul selectat din tabel
        if (randSelectat != -1) {
            String numeSelectat = (String) tabelCosProduse.getValueAt(randSelectat, 0); // Obtine numele produsului selectat
            tabelCosProduse.removeRow(randSelectat); // Sterge randul selectat din tabel

            for (int i = 0; i < cosProduse.size(); i++) {
                if (cosProduse.get(i)[0].equals(numeSelectat)) {
                    cosProduse.remove(i); // Sterge produsul din lista
                    break;
                }
            }
            recalculeazaTotal(tabelCosProduse); // Recalculeaza totalul
        }
    }

    private void stergeRandDinPrescriptie() {
        int randSelectat = retetaTabelModel.getRowCount() - 1; // Obtine indexul ultimului rand din tabel prescriptie
        if (randSelectat >= 0) {
            retetaTabelModel.removeRow(randSelectat); // Sterge ultimul rand din tabel prescriptie
        }
    }

    private void printFactura(JTable tabelCos) {
        try {
            tabelCos.print(JTable.PrintMode.FIT_WIDTH,
                new MessageFormat("Factura - " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date())), // Format pentru antet factura
                new MessageFormat("Page {0}")); // Format pentru pagina
            retetaPanel.removeAll(); // Sterge toate componentele din panou prescriptie
            retetaPanel.revalidate(); // Revalideaza panoul
            retetaPanel.repaint(); // Reface panoul

        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null, "Eroare la printare: " + pe.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE); // Afisare mesaj de eroare la printare
        }
    }

    private void aplicaReducere(DefaultTableModel tabelCosProduse) {
        for (int i = 0; i < tabelCosProduse.getRowCount(); i++) {
            String indicatii = (String) tabelCosProduse.getValueAt(i, 1);
            if (indicatii != null) {
                double pretCuDiscount = getPretRedus(indicatii.toLowerCase(), (String) tabelCosProduse.getValueAt(i, 2)); // Calculare pret cu reducere
                tabelCosProduse.setValueAt(String.format("%.2f", pretCuDiscount), i, 2); // Setare pret cu reducere in tabel

                int numarBucati = (int) tabelCosProduse.getValueAt(i, 3); // Obtine cantitatea
                double pretTotal = pretCuDiscount * numarBucati; // Calculare pret total cu reducere
                tabelCosProduse.setValueAt(String.format("%.2f", pretTotal), i, 4); // Setare pret total cu reducere in tabel
            }
        }
        actualizareTotalLine(tabelCosProduse); // Actualizare linie total
    }

    private double getPretRedus(String indicatii, String originalPrice) {
        double pret = Double.parseDouble(originalPrice); // Conversie pret original la double
        if (indicatii.contains("cancer")) {
            return pret * 0.10; // Reducere 90% pentru cancer
        } else if (indicatii.contains("mintale") || indicatii.contains("mintala") || indicatii.contains("schizofrenie") || indicatii.contains("psihoza")) {
            return 0.0; // Reducere 100% pentru afectiuni mintale
        }
        return pret; // Returnare pret original daca nu se aplica reducere
    }

    private void recalculeazaTotal(DefaultTableModel tabelCosProduse) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelCosProduse.getRowCount(); i++) {
            totalFinal += Double.parseDouble((String) tabelCosProduse.getValueAt(i, 4)); // Calculare total final
        }

        tabelCosProduse.setValueAt(String.format("%.2f", totalFinal), tabelCosProduse.getRowCount() - 1, 4); // Setare total final in tabel
    }

    private void actualizareTotalLine(DefaultTableModel tabelCosProduse) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelCosProduse.getRowCount() - 1; i++) {
            totalFinal += Double.parseDouble((String) tabelCosProduse.getValueAt(i, 4)); // Calculare total final
        }
        tabelCosProduse.setValueAt(String.format("%.2f", totalFinal), tabelCosProduse.getRowCount() - 1, 4); // Setare total final in tabel
    }

    private JButton generatorButoane(String text, ActionListener listener) {
        JButton btn = new JButton(text); // Creare buton cu textul specificat
        btn.addActionListener(listener); // Adaugare ascultator pentru buton
        return btn; // Returnare buton
    }
}

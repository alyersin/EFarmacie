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
    private JPanel prescriptiePanel; // Panou pentru prescriptie
    private DefaultTableModel retetaTableModel; // Model de tabel pentru prescriptie

    public Cos_Cumparaturi(List<String[]> cosProduse) {
        this.cosProduse = cosProduse; // Initializare lista produse din cos
        this.prescriptiePanel = new JPanel(); // Creare panou prescriptie
        prescriptiePanel.setLayout(new BoxLayout(prescriptiePanel, BoxLayout.Y_AXIS)); // Setare layout vertical
        initPrescriptieTableModel(); // Initializare model tabel prescriptie
    }

    private void initPrescriptieTableModel() {
        retetaTableModel = new DefaultTableModel(new Object[]{"Nr. Reteta", "Nume", "Prenume", "Cod Medic"}, 0); // Creare model tabel cu coloane specifice
    }

    public void displayCos(Component parentComponent) {
        JPanel cosPanel = new JPanel(null); // Creare panou pentru cos cu layout nul
        cosPanel.setPreferredSize(new Dimension(800, 600)); // Setare dimensiune preferata a panoului

        DefaultTableModel tabelDefault = new DefaultTableModel(new Object[]{"Nume", "Indicatii", "Pret", "Cantitate", "Total"}, 0); // Creare model tabel pentru cos
        JTable tabelCos = new JTable(tabelDefault); // Creare tabel pentru cos cu modelul creat
        incarcaProduseTabel(tabelDefault); // Incarcare produse in tabel

        JScrollPane scrollPane = new JScrollPane(tabelCos); // Creare panou de derulare pentru tabel
        scrollPane.setBounds(10, 10, 780, 300); // Setare pozitie si dimensiune panou de derulare
        cosPanel.add(scrollPane); // Adaugare panou de derulare in panoul cos

        addTotalRow(tabelDefault); // Adaugare rand total in tabel

        JTable prescriptieTable = new JTable(retetaTableModel); // Creare tabel pentru prescriptie
        JScrollPane prescriptieScrollPane = new JScrollPane(prescriptieTable); // Creare panou de derulare pentru tabel prescriptie
        prescriptieScrollPane.setBounds(10, 320, 780, 100); // Setare pozitie si dimensiune panou de derulare prescriptie
        cosPanel.add(prescriptieScrollPane); // Adaugare panou de derulare prescriptie in panoul cos

        JPanel btnPanel = cosBtnPanel(tabelDefault, tabelCos); // Creare panou butoane pentru cos
        btnPanel.setBounds(10, 430, 780, 50); // Setare pozitie si dimensiune panou butoane
        cosPanel.add(btnPanel); // Adaugare panou butoane in panoul cos

        incarcaReteteTabel(); // Incarcare date prescriptie din fisier

        JOptionPane.showMessageDialog(parentComponent, cosPanel, "Cos", JOptionPane.PLAIN_MESSAGE); // Afisare dialog pentru cos
    }

    private void incarcaReteteTabel() {
        retetaTableModel.setRowCount(0); // Sterge randurile existente din tabel prescriptie
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
                retetaTableModel.addRow(data); // Adaugare rand in tabel prescriptie
            }
        }
    }

    private void incarcaProduseTabel(DefaultTableModel tabelDefault) {
        List<String[]> uniqueItems = new ArrayList<>(); // Lista pentru produse unice
        List<Integer> cantitati = new ArrayList<>(); // Lista pentru cantitati

        for (String[] item : cosProduse) {
            int index = cautaIndexProdus(uniqueItems, item[0]); // Cautare index produs
            if (index != -1) {
                cantitati.set(index, cantitati.get(index) + 1); // Actualizare cantitate pentru produs existent
            } else {
                uniqueItems.add(item); // Adaugare produs nou
                cantitati.add(1); // Adaugare cantitate initiala
            }
        }

        for (int i = 0; i < uniqueItems.size(); i++) {
            String[] item = uniqueItems.get(i);
            int quantity = cantitati.get(i);
            double pricePerItem = Double.parseDouble(item[6]); // Conversie pret per produs la double
            double totalPrice = pricePerItem * quantity; // Calculare pret total
            tabelDefault.addRow(new Object[]{item[0], item[2], String.format("%.2f", pricePerItem), quantity, String.format("%.2f", totalPrice)}); // Adaugare rand in tabel
        }
    }

    private int cautaIndexProdus(List<String[]> uniqueItems, String productName) {
        for (int i = 0; i < uniqueItems.size(); i++) {
            if (uniqueItems.get(i)[0].equals(productName)) { // Comparare nume produs
                return i; // Returnare index daca produsul este gasit
            }
        }
        return -1; // Returnare -1 daca produsul nu este gasit
    }

    private void addTotalRow(DefaultTableModel tabelDefault) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelDefault.getRowCount(); i++) {
            totalFinal += Double.parseDouble((String) tabelDefault.getValueAt(i, 4)); // Calculare total final
        }

        tabelDefault.addRow(new Object[]{"", "", "", "TOTAL", String.format("%.2f", totalFinal)}); // Adaugare rand total in tabel
    }

    private JPanel cosBtnPanel(DefaultTableModel tabelDefault, JTable tabelCos) {
        JButton stergeBtn = generatorButoane("Sterge", e -> stergeProdusDinCos(tabelCos, tabelDefault)); // Creare buton pentru stergere produs
        JButton printBtn = generatorButoane("Printeaza Factura", e -> printFactura(tabelCos)); // Creare buton pentru printare factura
        JButton discountBtn = generatorButoane("Compensat", e -> aplicaReducere(tabelDefault)); // Creare buton pentru aplicare reducere
        JButton stergeRetetaBtn = generatorButoane("Sterge Reteta", e -> stergeRandDinPrescriptie()); // Creare buton pentru stergere prescriptie

        JPanel btnPanel = new JPanel(null); // Creare panou pentru butoane cu layout nul
        btnPanel.setPreferredSize(new Dimension(780, 50)); // Setare dimensiune preferata panou butoane

        stergeBtn.setBounds(10, 10, 120, 30); // Setare pozitie si dimensiune buton stergere produs
        btnPanel.add(stergeBtn); // Adaugare buton stergere produs in panou
        printBtn.setBounds(140, 10, 150, 30); // Setare pozitie si dimensiune buton printare factura
        btnPanel.add(printBtn); // Adaugare buton printare factura in panou
        discountBtn.setBounds(300, 10, 120, 30); // Setare pozitie si dimensiune buton aplicare reducere
        btnPanel.add(discountBtn); // Adaugare buton aplicare reducere in panou
        stergeRetetaBtn.setBounds(430, 10, 180, 30); // Setare pozitie si dimensiune buton stergere prescriptie
        btnPanel.add(stergeRetetaBtn); // Adaugare buton stergere prescriptie in panou

        return btnPanel; // Returnare panou butoane
    }

    private void stergeProdusDinCos(JTable tabelCos, DefaultTableModel tabelDefault) {
        int randSelectat = tabelCos.getSelectedRow(); // Obtine randul selectat din tabel
        if (randSelectat != -1) {
            String numeSelectat = (String) tabelDefault.getValueAt(randSelectat, 0); // Obtine numele produsului selectat
            tabelDefault.removeRow(randSelectat); // Sterge randul selectat din tabel

            for (int i = 0; i < cosProduse.size(); i++) {
                if (cosProduse.get(i)[0].equals(numeSelectat)) {
                    cosProduse.remove(i); // Sterge produsul din lista
                    break;
                }
            }
            recalculeazaTotal(tabelDefault); // Recalculeaza totalul
        }
    }

    private void stergeRandDinPrescriptie() {
        int randSelectat = retetaTableModel.getRowCount() - 1; // Obtine indexul ultimului rand din tabel prescriptie
        if (randSelectat >= 0) {
            retetaTableModel.removeRow(randSelectat); // Sterge ultimul rand din tabel prescriptie
        }
    }

    private void printFactura(JTable tabelCos) {
        try {
            tabelCos.print(JTable.PrintMode.FIT_WIDTH,
                new MessageFormat("Factura - " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date())), // Format pentru antet factura
                new MessageFormat("Page {0}")); // Format pentru pagina
            prescriptiePanel.removeAll(); // Sterge toate componentele din panou prescriptie
            prescriptiePanel.revalidate(); // Revalideaza panoul
            prescriptiePanel.repaint(); // Reface panoul

            scadeStoc(tabelCos); // Scade stocul dupa printare
        } catch (PrinterException pe) {
            JOptionPane.showMessageDialog(null, "Eroare la printare: " + pe.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE); // Afisare mesaj de eroare la printare
        }
    }

    private void scadeStoc(JTable tabelCos) {
        List<String> vanzariProduse = new ArrayList<>(); // Creare lista pentru vanzari produse
        List<Integer> cantitatiVandute = new ArrayList<>(); // Creare lista pentru cantitati vandute

        for (int i = 0; i < tabelCos.getRowCount(); i++) {
            String numeProdus = (String) tabelCos.getValueAt(i, 0); // Obtine numele produsului din tabel
            int cantitateVanduta = (int) tabelCos.getValueAt(i, 3); // Obtine cantitatea vanduta din tabel
            int index = vanzariProduse.indexOf(numeProdus); // Verifica daca produsul este deja in lista
            if (index == -1) {
                vanzariProduse.add(numeProdus); // Adauga produsul in lista
                cantitatiVandute.add(cantitateVanduta); // Adauga cantitatea vanduta in lista
            } else {
                int cantitateExistenta = cantitatiVandute.get(index); // Obtine cantitatea existenta
                cantitatiVandute.set(index, cantitateExistenta + cantitateVanduta); // Actualizeaza cantitatea
            }
        }

        List<String[]> stocProduse = new ArrayList<>(); // Lista pentru stoc produse
        try (BufferedReader reader = new BufferedReader(new FileReader("stoc.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":"); // Impartire linie in componente
                if (data.length == 7) {
                    String numeProdus = data[0];
                    int stocCurent = Integer.parseInt(data[5]); // Conversie stoc curent la int
                    int index = vanzariProduse.indexOf(numeProdus);
                    if (index != -1) {
                        int cantitateVanduta = cantitatiVandute.get(index);
                        stocCurent -= cantitateVanduta; // Scade cantitatea vanduta din stoc
                        data[5] = String.valueOf(stocCurent); // Actualizare stoc in date
                    }
                    stocProduse.add(data); // Adaugare produs in lista stoc
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Tratament exceptii la citirea fisierului
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("stoc.txt"))) {
            for (String[] data : stocProduse) {
                writer.write(String.join(":", data)); // Scriere date in fisier
                writer.newLine(); // Linie noua
            }
        } catch (IOException e) {
            e.printStackTrace(); // Tratament exceptii la scrierea fisierului
        }
    }

    private void aplicaReducere(DefaultTableModel tabelDefault) {
        for (int i = 0; i < tabelDefault.getRowCount(); i++) {
            String indicatii = (String) tabelDefault.getValueAt(i, 1);
            if (indicatii != null) {
                double pretCuDiscount = getPretRedus(indicatii.toLowerCase(), (String) tabelDefault.getValueAt(i, 2)); // Calculare pret cu reducere
                tabelDefault.setValueAt(String.format("%.2f", pretCuDiscount), i, 2); // Setare pret cu reducere in tabel

                int quantity = (int) tabelDefault.getValueAt(i, 3); // Obtine cantitatea
                double totalPrice = pretCuDiscount * quantity; // Calculare pret total cu reducere
                tabelDefault.setValueAt(String.format("%.2f", totalPrice), i, 4); // Setare pret total cu reducere in tabel
            }
        }
        actualizareTotalLine(tabelDefault); // Actualizare linie total
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

    private void recalculeazaTotal(DefaultTableModel tabelDefault) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelDefault.getRowCount(); i++) {
            totalFinal += Double.parseDouble((String) tabelDefault.getValueAt(i, 4)); // Calculare total final
        }

        tabelDefault.setValueAt(String.format("%.2f", totalFinal), tabelDefault.getRowCount() - 1, 4); // Setare total final in tabel
    }

    private void actualizareTotalLine(DefaultTableModel tabelDefault) {
        double totalFinal = 0.0;

        for (int i = 0; i < tabelDefault.getRowCount() - 1; i++) {
            totalFinal += Double.parseDouble((String) tabelDefault.getValueAt(i, 4)); // Calculare total final
        }
        tabelDefault.setValueAt(String.format("%.2f", totalFinal), tabelDefault.getRowCount() - 1, 4); // Setare total final in tabel
    }

    private JButton generatorButoane(String text, ActionListener listener) {
        JButton button = new JButton(text); // Creare buton cu textul specificat
        button.addActionListener(listener); // Adaugare ascultator pentru buton
        return button; // Returnare buton
    }
}

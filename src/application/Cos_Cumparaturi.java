package application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Cos_Cumparaturi {
    private List<String[]> cosProduse;

    public Cos_Cumparaturi(List<String[]> cosProduse) {
        this.cosProduse = cosProduse;
    }

    public void displayCos(Component parentComponent) {
        // Create main panel with null layout for absolute positioning
        JPanel cosPanel = new JPanel(null);
        cosPanel.setPreferredSize(new Dimension(800, 600)); // Set preferred size for the panel

        // Create table model and table
        DefaultTableModel tabelDefault = new DefaultTableModel(new Object[]{"Nume", "Indicatii", "Pret", "Cantitate", "Total"}, 0);
        JTable tabelCos = new JTable(tabelDefault);

        // Populate table model with cart items
        incarcaProduseTabel(tabelDefault);

        // Create scroll pane for the table and set its bounds
        JScrollPane scrollPane = new JScrollPane(tabelCos);
        scrollPane.setBounds(10, 10, 780, 400); // Adjust the size and position as needed
        cosPanel.add(scrollPane);

        // Add total row to the table model
        addTotalRow(tabelDefault);

        // Crearea panelului cu butoane folosind metoda cosBtnPanel
        JPanel buttonPanel = cosBtnPanel(tabelDefault, tabelCos);
        buttonPanel.setBounds(10, 420, 780, 50); // Ajusteaza dimensiunea si pozitia dupa necesitati
        cosPanel.add(buttonPanel);

        // Show dialog with the cart panel
        JOptionPane.showMessageDialog(parentComponent, cosPanel, "Cos", JOptionPane.PLAIN_MESSAGE);
    }

    private void incarcaProduseTabel(DefaultTableModel tabelDefault) {
        List<String[]> uniqueItems = new ArrayList<>(); // Creeaza o lista pentru a stoca produsele unice
        List<Integer> quantities = new ArrayList<>(); // Creeaza o lista pentru a stoca cantitatile corespunzatoare produselor unice

        for (String[] item : cosProduse) { // Parcurge toate produsele din cos
            int index = cautaIndexProdus(uniqueItems, item[0]); // Gaseste indexul produsului in lista de produse unice
            if (index != -1) { // Daca produsul exista in lista de produse unice
                quantities.set(index, quantities.get(index) + 1); // Actualizeaza cantitatea produsului
            } else { // Daca produsul nu exista in lista de produse unice
                uniqueItems.add(item); // Adauga produsul in lista de produse unice
                quantities.add(1); // Adauga cantitatea initiala de 1 pentru acest produs
            }
        }

        for (int i = 0; i < uniqueItems.size(); i++) { // Parcurge lista de produse unice
            String[] item = uniqueItems.get(i); // Obtine produsul curent
            int quantity = quantities.get(i); // Obtine cantitatea corespunzatoare produsului curent
            double pricePerItem = Double.parseDouble(item[6]); // Convertește pretul produsului din string in double
            double totalPrice = pricePerItem * quantity; // Calculeaza pretul total pentru produsul curent
            tabelDefault.addRow(new Object[]{item[0], item[2], String.format("%.2f", pricePerItem), quantity, String.format("%.2f", totalPrice)}); // Adauga un rand in tabel cu informatiile produsului curent
        }
    }

    private int cautaIndexProdus(List<String[]> uniqueItems, String productName) {
        for (int i = 0; i < uniqueItems.size(); i++) {
            if (uniqueItems.get(i)[0].equals(productName)) {
                return i; // Returneaza indexul produsului daca este gasit
            }
        }
        return -1; // Returneaza -1 daca produsul nu este gasit
    }

    private void addTotalRow(DefaultTableModel tabelDefault) { // Definirea metodei `addTotalRow` care primeste ca parametru un `DefaultTableModel` numit `tabelDefault`
        double totalFinal = 0.0; // Initializarea variabilei `totalFinal` cu valoarea 0.0

        for (int i = 0; i < tabelDefault.getRowCount(); i++) { // Parcurge toate randurile din `tabelDefault`
            totalFinal += Double.parseDouble((String) tabelDefault.getValueAt(i, 4)); // Aduna valoarea totala a fiecarui produs la `totalFinal`
        }

        tabelDefault.addRow(new Object[]{"", "", "", "TOTAL", String.format("%.2f", totalFinal)}); // Adauga un nou rand in `tabelDefault` care contine textul "TOTAL" in coloana a patra si valoarea totala formata in coloana a cincea
    }

    private JPanel cosBtnPanel(DefaultTableModel tabelDefault, JTable tabelCos) {
        JButton removeBtn = generatorButoane("Remove", e -> stergeProdusDinCos(tabelCos, tabelDefault));
        JButton printBtn = generatorButoane("Printeaza Factura", e -> printFactura(tabelCos));
        JButton discountBtn = generatorButoane("Compensat", e -> aplicaReducere(tabelDefault));

        JPanel buttonPanel = new JPanel(null); // Use null layout for absolute positioning
        buttonPanel.setPreferredSize(new Dimension(780, 50)); // Set preferred size for the button panel

        // Set bounds for buttons and add them to the button panel
        removeBtn.setBounds(10, 10, 120, 30);
        buttonPanel.add(removeBtn);
        printBtn.setBounds(140, 10, 150, 30);
        buttonPanel.add(printBtn);
        discountBtn.setBounds(300, 10, 120, 30);
        buttonPanel.add(discountBtn);

        return buttonPanel;
    }

    private void stergeProdusDinCos(JTable tabelCos, DefaultTableModel tabelDefault) { // Definirea metodei `stergeProdusDinCos` care primeste ca parametri un `JTable` numit `tabelCos` si un `DefaultTableModel` numit `tabelDefault`
        int randSelectat = tabelCos.getSelectedRow(); // Obtine randul selectat din tabelul `tabelCos`
        if (randSelectat != -1) { // Verifica daca exista un rand selectat
            String numeSelectat = (String) tabelDefault.getValueAt(randSelectat, 0); // Obtine numele produsului din randul selectat, coloana 0
            tabelDefault.removeRow(randSelectat); // Elimina randul selectat din modelul de tabel `tabelDefault`

            for (int i = 0; i < cosProduse.size(); i++) { // Parcurge lista de produse `cosProduse`
                if (cosProduse.get(i)[0].equals(numeSelectat)) { // Verifica daca numele produsului corespunde cu cel selectat
                    cosProduse.remove(i); // Elimina produsul din lista `cosProduse`
                    break; // Iese din bucla odata ce produsul a fost gasit si eliminat
                }
            }
            recalculeazaTotal(tabelDefault); // Recalculeaza totalul general dupa eliminarea produsului
        }
    }
    
    private void printFactura(JTable tabelCos) { // Definirea metodei `printFactura` care primeste ca parametru un `JTable` numit `tabelCos`
        try {
            tabelCos.print(JTable.PrintMode.FIT_WIDTH, // Printeaza tabelul `tabelCos` folosind modul de printare `FIT_WIDTH`
                new MessageFormat("Factura - " + new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date())), // Seteaza antetul paginii cu textul "Factura - " urmat de data si ora curenta
                new MessageFormat("Page {0}")); // Seteaza subsolul paginii cu textul "Page {0}"
        } catch (PrinterException pe) { // Prinde orice exceptie de tip `PrinterException` care ar putea aparea in timpul printarii
            JOptionPane.showMessageDialog(null, "Eroare la printare: " + pe.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE); // Afiseaza un mesaj de eroare daca apare o exceptie de printare
        }
    }

    private void aplicaReducere(DefaultTableModel tabelDefault) { // Definirea metodei `aplicaReducere` care primeste ca parametru un `DefaultTableModel` numit `tabelDefault`
        for (int i = 0; i < tabelDefault.getRowCount(); i++) { // Parcurge toate randurile din `tabelDefault`
            String indicatii = (String) tabelDefault.getValueAt(i, 1); // Obtine valoarea din coloana "Indicatii" pentru randul curent
            if (indicatii != null) { // Verifica daca valoarea "Indicatii" nu este nula
                double pretCuDiscount = getPretRedus(indicatii.toLowerCase(), (String) tabelDefault.getValueAt(i, 2)); // Calculeaza pretul redus in functie de indicatii si pretul original
                tabelDefault.setValueAt(String.format("%.2f", pretCuDiscount), i, 2); // Actualizeaza pretul redus in coloana a treia

                int quantity = (int) tabelDefault.getValueAt(i, 3); // Obtine cantitatea din coloana a patra
                double totalPrice = pretCuDiscount * quantity; // Calculeaza pretul total pentru randul curent
                tabelDefault.setValueAt(String.format("%.2f", totalPrice), i, 4); // Actualizeaza pretul total in coloana a cincea
            }
        }
        actualizareTotalLine(tabelDefault); // Actualizeaza randul total dupa aplicarea reducerilor
    }

    private double getPretRedus(String indicatii, String originalPrice) { // Definirea metodei `getPretRedus` care primeste ca parametri un `String` numit `indicatii` si un `String` numit `originalPrice`
        double pret = Double.parseDouble(originalPrice); // Convertește `originalPrice` din `String` in `double` si il stocheaza in variabila `pret`
        if (indicatii.contains("cancer")) { // Verifica daca `indicatii` contine cuvantul "cancer"
            return pret * 0.10; // Daca `indicatii` contine "cancer", returneaza 10% din `pret`
        } else if (indicatii.contains("mintale") || indicatii.contains("mintala") || indicatii.contains("schizofrenie") || indicatii.contains("psihoza")) { // Verifica daca `indicatii` contine unul dintre cuvintele "mintale", "mintala", "schizofrenie" sau "psihoza"
            return 0.0; // Daca `indicatii` contine oricare dintre aceste cuvinte, returneaza 0.0 (pret gratuit)
        }
        return pret; // Daca niciuna dintre conditiile de mai sus nu este indeplinita, returneaza `pret` (pretul original)
    }

    private void recalculeazaTotal(DefaultTableModel tabelDefault) { // Definirea metodei `recalculeazaTotal` care primeste ca parametru un `DefaultTableModel` numit `tabelDefault`
        double totalFinal = 0.0; // Initializarea variabilei `totalFinal` cu valoarea 0.0

        for (int i = 0; i < tabelDefault.getRowCount(); i++) { // Parcurge toate randurile din `tabelDefault`
            totalFinal += Double.parseDouble((String) tabelDefault.getValueAt(i, 4)); // Aduna valoarea totala a fiecarui produs la `totalFinal`
        }

        tabelDefault.setValueAt(String.format("%.2f", totalFinal), tabelDefault.getRowCount() - 1, 4); // Actualizeaza valoarea totala in ultima celula a coloanei "Total"
    }

    private void actualizareTotalLine(DefaultTableModel tabelDefault) { // Definirea metodei `actualizareTotalLine` care primeste ca parametru un `DefaultTableModel` numit `tabelDefault`
        double totalFinal = 0.0; // Initializarea variabilei `totalFinal` cu valoarea 0.0

        for (int i = 0; i < tabelDefault.getRowCount() - 1; i++) { // Parcurge toate randurile din `tabelDefault`, cu exceptia ultimului rand
            totalFinal += Double.parseDouble((String) tabelDefault.getValueAt(i, 4)); // Aduna valoarea totala a fiecarui produs la `totalFinal`
        }
        tabelDefault.setValueAt(String.format("%.2f", totalFinal), tabelDefault.getRowCount() - 1, 4); // Actualizeaza valoarea totala in ultima celula a coloanei "Total"
    }

    private JButton generatorButoane(String text, ActionListener listener) {  //Generator de butoane cu action listeners
        JButton button = new JButton(text); // Creeaza un nou obiect `JButton` cu textul specificat
        button.addActionListener(listener); // Adauga un `ActionListener` la buton
        return button; // Returneaza obiectul `JButton` creat
    }
}

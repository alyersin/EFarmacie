package application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class Search_Panel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField cautaField; // Camp text pentru cautare
    private JButton adaugaProdusStoc, cosBtn, cautaBtn; // Butoane pentru adaugare produs, vizualizare cos si cautare
    private JLabel DTLabel; // Eticheta pentru afisarea datei si orei
    private MainFrame mainFrame; // Referinta la obiectul MainFrame

    public Search_Panel(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Initializare referinta mainFrame
        setLayout(null); // Setare layout nul
        setBackground(Color.LIGHT_GRAY);
        displaySearch(mainFrame); // Configurare componente
    }

    private void displaySearch(MainFrame mainFrame) {
        cautaField = new JTextField(); // Creare camp text pentru cautare
        cautaField.setBounds(10, 10, 300, 25); // Setare pozitie si dimensiune
        cautaField.addActionListener(e -> cautaProduse()); // Adaugare ascultator pentru evenimentul de cautare

        cautaBtn = new JButton("Cauta Medicament"); // Creare buton de cautare
        cautaBtn.setBounds(320, 10, 150, 25); // Setare pozitie si dimensiune
        cautaBtn.addActionListener(e -> cautaProduse()); // Adaugare ascultator pentru evenimentul de cautare

        adaugaProdusStoc = new JButton("Adauga Produs"); // Creare buton de adaugare produs
        adaugaProdusStoc.setBounds(10, 50, 150, 30); // Setare pozitie si dimensiune
        adaugaProdusStoc.addActionListener(e -> new Produs_Panel(mainFrame).adaugaProdusStoc()); // Adaugare ascultator pentru evenimentul de adaugare produs

        cosBtn = new JButton("Cos"); // Creare buton pentru vizualizarea cosului
        cosBtn.setBounds(170, 50, 150, 30); // Setare pozitie si dimensiune
        cosBtn.addActionListener(e -> new Cos_Cumparaturi(mainFrame.getCosProduse()).displayCos(mainFrame)); // Adaugare ascultator pentru evenimentul de vizualizare cos

        DTLabel = new JLabel(); // Creare eticheta pentru afisarea datei si orei
        DTLabel.setBounds(530, 10, 200, 30); // Setare pozitie si dimensiune
        mainFrame.updateDateTimeLabel(DTLabel); // Actualizare eticheta pentru data si ora

        JScrollPane scrollPane = new JScrollPane(mainFrame.getTabelStoc()); // Creare componenta scroll pentru tabelul stoc
        scrollPane.setBounds(10, 90, 780, 600); // Setare pozitie si dimensiune

        TableRowSorter<DefaultTableModel> sorteazaTabel = new TableRowSorter<>(mainFrame.getModelTabelDefault()); // Creare sorteazaTabel pentru tabel
        mainFrame.getTabelStoc().setRowSorter(sorteazaTabel); // Setare sorteazaTabel pentru tabel

        add(scrollPane); // Adaugare componenta scroll la panou
        add(cautaField); // Adaugare camp text cautare la panou
        add(cautaBtn); // Adaugare buton cautare la panou
        add(adaugaProdusStoc); // Adaugare buton adaugare produs la panou
        add(cosBtn); // Adaugare buton vizualizare cos la panou
        add(DTLabel); // Adaugare eticheta data si ora la panou
    }

    public JTextField getSearchField() {
        return cautaField; // Returnare camp text pentru cautare
    }

    // Metoda pentru cautarea unui produs
    public void cautaProduse() {
        String elementCautat = cautaField.getText().trim().toLowerCase(); // Obtine termenul de cautare din campul de cautare si il converteste la litere mici
        int ultimaLinieGasita = 0; // Indexul ultimei linii gasite

        if (!elementCautat.isEmpty()) { // Verifica daca termenul de cautare nu este gol
            boolean gasit = false; // Flag pentru a indica daca produsul a fost gasit
            for (int rowIdx = ultimaLinieGasita; rowIdx < mainFrame.getModelTabelDefault().getRowCount(); rowIdx++) { // Parcurge randurile tabelului incepand de la randul ultimaLinieGasita
                for (int colIdx = 0; colIdx < mainFrame.getModelTabelDefault().getColumnCount(); colIdx++) { // Parcurge coloanele randului curent
                    String valoareaDinCelula = (String) mainFrame.getModelTabelDefault().getValueAt(rowIdx, colIdx); // Obtine valoarea celulei la randul si coloana curenta
                    if (valoareaDinCelula != null && valoareaDinCelula.toLowerCase().contains(elementCautat)) { // Verifica daca valoarea celulei nu este null si contine termenul de cautare
                        mainFrame.getTabelStoc().setRowSelectionInterval(rowIdx, rowIdx); // Selecteaza randul gasit in tabel
                        mainFrame.getTabelStoc().scrollRectToVisible(mainFrame.getTabelStoc().getCellRect(rowIdx, colIdx, true)); // Deruleaza pentru a face vizibila celula selectata
                        mainFrame.getTabelStoc().setSelectionBackground(Color.BLUE); // Seteaza fundalul selectiei la culoarea albastra
                        ultimaLinieGasita = rowIdx + 1; // Actualizeaza ultimaLinieGasita pentru a continua cautarea de la randul urmator
                        gasit = true; // Seteaza flag-ul gasit la true
                        break; // Iese din bucla coloanelor
                    }
                }
                if (gasit) { // Daca produsul a fost gasit
                    break; // Iese din bucla randurilor
                }
            }
            if (!gasit) { // Daca produsul nu a fost gasit
                ultimaLinieGasita = 0; // Reseteaza ultimaLinieGasita la 0
                mainFrame.showError("Produsul nu exista"); // Afiseaza un mesaj de eroare
            }
        }
    }
}

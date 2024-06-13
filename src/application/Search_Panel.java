package application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class Search_Panel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField searchField; // Camp text pentru cautare
    private JButton adaugaProdusStoc, btnCos, searchButton; // Butoane pentru adaugare produs, vizualizare cos si cautare
    private JLabel DTLabel; // Eticheta pentru afisarea datei si orei
    private MainFrame mainFrame; // Referinta la obiectul MainFrame
    private int ultimaLinieGasita = -1; // Indexul ultimei linii gasite

    public Search_Panel(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Initializare referinta mainFrame
        setLayout(null); // Setare layout nul
        setupComponents(mainFrame); // Configurare componente
    }

    private void setupComponents(MainFrame mainFrame) {
        searchField = new JTextField(); // Creare camp text pentru cautare
        searchField.setBounds(10, 10, 300, 25); // Setare pozitie si dimensiune
        searchField.addActionListener(e -> cautaProduse()); // Adaugare ascultator pentru evenimentul de cautare

        searchButton = new JButton("Search"); // Creare buton de cautare
        searchButton.setBounds(320, 10, 100, 25); // Setare pozitie si dimensiune
        searchButton.addActionListener(e -> cautaProduse()); // Adaugare ascultator pentru evenimentul de cautare

        adaugaProdusStoc = new JButton("Adauga Produs"); // Creare buton de adaugare produs
        adaugaProdusStoc.setBounds(10, 50, 150, 30); // Setare pozitie si dimensiune
        adaugaProdusStoc.addActionListener(e -> new Produs_Panel(mainFrame).adaugaProdusStoc()); // Adaugare ascultator pentru evenimentul de adaugare produs

        btnCos = new JButton("Cos"); // Creare buton pentru vizualizarea cosului
        btnCos.setBounds(170, 50, 150, 30); // Setare pozitie si dimensiune
        btnCos.addActionListener(e -> new Cos_Cumparaturi(mainFrame.getCosProduse()).displayCos(mainFrame)); // Adaugare ascultator pentru evenimentul de vizualizare cos

        DTLabel = new JLabel(); // Creare eticheta pentru afisarea datei si orei
        DTLabel.setBounds(530, 10, 200, 30); // Setare pozitie si dimensiune
        mainFrame.updateDateTimeLabel(DTLabel); // Actualizare eticheta pentru data si ora

        JScrollPane scrollPane = new JScrollPane(mainFrame.getTabelStoc()); // Creare componenta scroll pentru tabelul stoc
        scrollPane.setBounds(10, 90, 780, 600); // Setare pozitie si dimensiune

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(mainFrame.getModelTabelDefault()); // Creare sorter pentru tabel
        mainFrame.getTabelStoc().setRowSorter(sorter); // Setare sorter pentru tabel

        add(scrollPane); // Adaugare componenta scroll la panou
        add(searchField); // Adaugare camp text cautare la panou
        add(searchButton); // Adaugare buton cautare la panou
        add(adaugaProdusStoc); // Adaugare buton adaugare produs la panou
        add(btnCos); // Adaugare buton vizualizare cos la panou
        add(DTLabel); // Adaugare eticheta data si ora la panou
    }

    public JTextField getSearchField() {
        return searchField; // Returnare camp text pentru cautare
    }

    // Metoda pentru cautarea unui produs
    public void cautaProduse() {
        String elementCautat = searchField.getText().trim().toLowerCase(); // Obtine termenul de cautare din campul de cautare si il converteste la litere mici
        if (!elementCautat.isEmpty()) { // Verifica daca termenul de cautare nu este gol
            boolean gasit = false; // Flag pentru a indica daca produsul a fost gasit
            for (int rowIndex = ultimaLinieGasita + 1; rowIndex < mainFrame.getModelTabelDefault().getRowCount(); rowIndex++) { // Parcurge randurile tabelului incepand de la randul urmator dupa cel gasit ultima data
                for (int colIndex = 0; colIndex < mainFrame.getModelTabelDefault().getColumnCount(); colIndex++) { // Parcurge coloanele randului curent
                    String cellValue = (String) mainFrame.getModelTabelDefault().getValueAt(rowIndex, colIndex); // Obtine valoarea celulei la randul si coloana curenta
                    if (cellValue != null && cellValue.toLowerCase().contains(elementCautat)) { // Verifica daca valoarea celulei nu este null si contine termenul de cautare
                        mainFrame.getTabelStoc().setRowSelectionInterval(rowIndex, rowIndex); // Selecteaza randul gasit in tabel
                        mainFrame.getTabelStoc().scrollRectToVisible(mainFrame.getTabelStoc().getCellRect(rowIndex, colIndex, true)); // Deruleaza pentru a face vizibila celula selectata
                        mainFrame.getTabelStoc().setSelectionBackground(Color.BLUE); // Seteaza fundalul selectiei la culoarea albastra
                        ultimaLinieGasita = rowIndex; // Actualizeaza ultimaLinieGasita cu indexul randului curent
                        gasit = true; // Seteaza flag-ul gasit la true
                        break; // Iese din bucla coloanelor
                    }
                }
                if (gasit) { // Daca produsul a fost gasit
                    break; // Iese din bucla randurilor
                }
            }
            if (!gasit) { // Daca produsul nu a fost gasit
                ultimaLinieGasita = -1; // Reseteaza ultimaLinieGasita la -1
                mainFrame.showError("Produsul nu exista"); // Afiseaza un mesaj de eroare
            }
        }
    }
}

package application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class Search_Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JTextField searchField;
    private JButton adaugaProdus, btnCos, searchButton;
    private JLabel DTLabel;
    private MainFrame frame;
    private int ultimaLinieGasita = -1;

    public Search_Panel(MainFrame frame) {
        this.frame = frame;
        setLayout(null);
        setupComponents(frame);
    }

    private void setupComponents(MainFrame frame) {
        searchField = new JTextField();
        searchField.setBounds(10, 10, 300, 25);
        searchField.addActionListener(e -> cautaProduse());

        searchButton = new JButton("Search");
        searchButton.setBounds(320, 10, 100, 25);
        searchButton.addActionListener(e -> cautaProduse());

        adaugaProdus = new JButton("Adauga Produs");
        adaugaProdus.setBounds(10, 50, 150, 30);
        adaugaProdus.addActionListener(e -> new Produs_Panel(frame).adaugaProdus());

        btnCos = new JButton("Cos");
        btnCos.setBounds(170, 50, 150, 30);
        btnCos.addActionListener(e -> new Cos_Cumparaturi(frame.getCosProduse()).displayCos(frame));

        DTLabel = new JLabel();
        DTLabel.setBounds(530, 10, 200, 30);
        frame.updateDateTimeLabel(DTLabel);

        JScrollPane scrollPane = new JScrollPane(frame.getTabelStoc());
        scrollPane.setBounds(10, 90, 780, 600);

        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(frame.getModel());
        frame.getTabelStoc().setRowSorter(sorter);

        add(scrollPane);
        add(searchField);
        add(searchButton);
        add(adaugaProdus);
        add(btnCos);
        add(DTLabel);
    }

    public JTextField getSearchField() {
        return searchField;
    }

    // Metoda pentru cautarea unui produs
    public void cautaProduse() {
        String elementCautat = searchField.getText().trim().toLowerCase(); // Obține termenul de căutare din câmpul de căutare și îl convertește la litere mici
        if (!elementCautat.isEmpty()) { // Verifică dacă termenul de căutare nu este gol
            boolean gasit = false; // Flag pentru a indica dacă produsul a fost găsit
            for (int rowIndex = ultimaLinieGasita + 1; rowIndex < frame.getModel().getRowCount(); rowIndex++) { // Parcurge rândurile tabelului începând de la rândul următor după cel găsit ultima dată
                for (int colIndex = 0; colIndex < frame.getModel().getColumnCount(); colIndex++) { // Parcurge coloanele rândului curent
                    String cellValue = (String) frame.getModel().getValueAt(rowIndex, colIndex); // Obține valoarea celulei la rândul și coloana curentă
                    if (cellValue != null && cellValue.toLowerCase().contains(elementCautat)) { // Verifică dacă valoarea celulei nu este null și conține termenul de căutare
                        frame.getTabelStoc().setRowSelectionInterval(rowIndex, rowIndex); // Selectează rândul găsit în tabel
                        frame.getTabelStoc().scrollRectToVisible(frame.getTabelStoc().getCellRect(rowIndex, colIndex, true)); // Derulează pentru a face vizibilă celula selectată
                        frame.getTabelStoc().setSelectionBackground(Color.BLUE); // Setează fundalul selecției la culoarea albastră
                        ultimaLinieGasita = rowIndex; // Actualizează ultimaLinieGasita cu indexul rândului curent
                        gasit = true; // Setează flag-ul gasit la true
                        break; // Iese din bucla coloanelor
                    }
                }
                if (gasit) { // Dacă produsul a fost găsit
                    break; // Iese din bucla rândurilor
                }
            }
            if (!gasit) { // Dacă produsul nu a fost găsit
                ultimaLinieGasita = -1; // Resetează ultimaLinieGasita la -1
                frame.showError("Produsul nu exista"); // Afișează un mesaj de eroare
            }
        }
    }
}

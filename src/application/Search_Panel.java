package application;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class Search_Panel extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField cautaField;
    private JButton adaugaProdusStoc, cosBtn, cautaBtn;
    private JLabel DTLabel;
    private MainFrame mainFrame;

    public Search_Panel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(null);
        setBackground(Color.LIGHT_GRAY);
        displaySearch(mainFrame);
    }

    private void displaySearch(MainFrame mainFrame) {
        cautaField = new JTextField();
        cautaField.setBounds(10, 10, 300, 25);
        cautaField.addActionListener(e -> cautaProduse());

        cautaBtn = new JButton("Cauta Medicament");
        cautaBtn.setBounds(320, 10, 150, 25);
        cautaBtn.addActionListener(e -> cautaProduse());

        adaugaProdusStoc = new JButton("Adauga Produs");
        adaugaProdusStoc.setBounds(10, 50, 150, 30);
        adaugaProdusStoc.addActionListener(e -> new Produs_Panel(mainFrame).adaugaProdusStoc());

        cosBtn = new JButton("Cos");
        cosBtn.setBounds(170, 50, 150, 30);
        cosBtn.addActionListener(e -> new Cos_Cumparaturi(mainFrame.getCosProduse()).displayCos(mainFrame));

        DTLabel = new JLabel();
        DTLabel.setBounds(530, 10, 200, 30);
        mainFrame.updateDateTimeLabel(DTLabel);

        JScrollPane scrollPane = new JScrollPane(mainFrame.getTabelStoc());
        scrollPane.setBounds(10, 90, 780, 600);

        TableRowSorter<DefaultTableModel> sorteazaTabel = new TableRowSorter<>(mainFrame.getModelTabelDefault());
        mainFrame.getTabelStoc().setRowSorter(sorteazaTabel);

        add(scrollPane);
        add(cautaField);
        add(cautaBtn);
        add(adaugaProdusStoc);
        add(cosBtn);
        add(DTLabel);
    }

    public JTextField getSearchField() {
        return cautaField;
    }

    // Cauta produs in tabel
    public void cautaProduse() {
        String elementCautat = cautaField.getText().trim().toLowerCase();
        int ultimaLinieGasita = 0;

        if (!elementCautat.isEmpty()) {
            boolean gasit = false;
            for (int rowIdx = ultimaLinieGasita; rowIdx < mainFrame.getModelTabelDefault().getRowCount(); rowIdx++) {
                for (int colIdx = 0; colIdx < mainFrame.getModelTabelDefault().getColumnCount(); colIdx++) {
                    String valoareaDinCelula = (String) mainFrame.getModelTabelDefault().getValueAt(rowIdx, colIdx);
                    if (valoareaDinCelula != null && valoareaDinCelula.toLowerCase().contains(elementCautat)) {
                        mainFrame.getTabelStoc().setRowSelectionInterval(rowIdx, rowIdx);
                        mainFrame.getTabelStoc().scrollRectToVisible(mainFrame.getTabelStoc().getCellRect(rowIdx, colIdx, true));
                        mainFrame.getTabelStoc().setSelectionBackground(Color.LIGHT_GRAY);
                        ultimaLinieGasita = rowIdx + 1;
                        gasit = true;
                        break;
                    }
                }
                if (gasit) {
                    break;
                }
            }
            if (!gasit) {
                ultimaLinieGasita = 0;
                mainFrame.showError("Produsul nu exista");
            }
        }
    }
}

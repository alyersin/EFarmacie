package application;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.Color;
import java.util.*;
import java.util.List;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private DefaultTableModel modelTabelDefault;
    private JTable tabelStoc;
    private List<String[]> cosProduse;
    private Search_Panel searchPanel;
    private Medic_Panel medicPanel;
    private DTLabel updateDTLabel;
    private JPopupMenu popupStergeProdus;
    private Cos_Cumparaturi cosCumparaturi;

    public MainFrame() {
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EFarmacie");
        setSize(1140, 760);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.LIGHT_GRAY);

        cosProduse = new ArrayList<>();
        cosCumparaturi = new Cos_Cumparaturi(cosProduse);

        configurareTabel();
        initializarePanels();
        new Stoc_Farmacie().incarcaStocDinTabel(modelTabelDefault);
        new Tabel_Listeners(this).adaugaListenersTabel();
        createPopupSterge();
    }

    // Configurare tabel stoc
    private void configurareTabel() {
        modelTabelDefault = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelTabelDefault.addColumn("Nume");
        modelTabelDefault.addColumn("Compozitie");
        modelTabelDefault.addColumn("Indicatii");
        modelTabelDefault.addColumn("Contraindicatii");
        modelTabelDefault.addColumn("Mod de administrare");
        modelTabelDefault.addColumn("Stoc");
        modelTabelDefault.addColumn("Pret");
        tabelStoc = new JTable(modelTabelDefault);
        tabelStoc.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    // Initializare panouri de cautare / medic
    private void initializarePanels() {
        searchPanel = new Search_Panel(this);
        medicPanel = new Medic_Panel(this, cosCumparaturi);

        searchPanel.setBounds(0, 0, 800, 800);
        medicPanel.setBounds(810, 0, 340, 800);

        add(searchPanel);
        add(medicPanel);
    }

    public DefaultTableModel getModelTabelDefault() {
        return modelTabelDefault;
    }

    public JTable getTabelStoc() {
        return tabelStoc;
    }

    public List<String[]> getCosProduse() {
        return cosProduse;
    }

    public JTextField getSearchField() {
        return searchPanel.getSearchField();
    }

    public void updateDateTimeLabel(JLabel DTLabel) {
        updateDTLabel = new DTLabel(DTLabel);
        updateDTLabel.start();
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Eroare", JOptionPane.ERROR_MESSAGE);
    }

    // Generator JOptionPane
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Informatie", JOptionPane.INFORMATION_MESSAGE);
    }


    public Cos_Cumparaturi getCosCumparaturi() {
        return cosCumparaturi;
    }

    // Returneaza meniul popup pentru stergerea produselor
    public JPopupMenu getPopupStergeProdus() {
        return popupStergeProdus;
    }
    

    private void createPopupSterge() {
        popupStergeProdus = new Tabel_Listeners(this).createPopup();
    }
}
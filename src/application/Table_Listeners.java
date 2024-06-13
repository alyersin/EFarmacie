package application;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Table_Listeners {
    private MainFrame mainFrame; // Referinta la obiectul MainFrame

    public Table_Listeners(MainFrame mainFrame) {
        this.mainFrame = mainFrame; // Initializare referinta mainFrame
    }

    // Metoda pentru adaugarea ascultatorilor de evenimente
    public void addListeners() {
        mainFrame.getTabelStoc().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Verifica daca s-a facut dublu click
                    int row = mainFrame.getTabelStoc().getSelectedRow(); // Obtine randul selectat
                    if (row != -1) { // Verifica daca randul selectat este valid
                        new Produs_Panel(mainFrame).openDetailPanel(mainFrame, row); // Deschide panoul de detalii pentru produsul selectat
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) { // Verifica daca evenimentul a fost declansat de un meniu popup
                    int row = mainFrame.getTabelStoc().rowAtPoint(e.getPoint()); // Obtine randul la care s-a facut click
                    if (row != -1) { // Verifica daca randul este valid
                        mainFrame.getTabelStoc().setRowSelectionInterval(row, row); // Selecteaza randul in tabel
                        mainFrame.getPopupStergeProdus().show(e.getComponent(), e.getX(), e.getY()); // Afiseaza meniul popup la pozitia cursorului
                    }
                }
            }
        });
    }

    // Metoda pentru crearea meniului popup
    public JPopupMenu createPopup() {
        JPopupMenu popup = new JPopupMenu(); // Creare meniu popup
        JMenuItem stergeItem = new JMenuItem("Sterge"); // Creare item de meniu pentru stergere
        stergeItem.addActionListener(e -> {
            int selectedRow = mainFrame.getTabelStoc().getSelectedRow(); // Obtine randul selectat
            if (selectedRow != -1) { // Verifica daca randul selectat este valid
                mainFrame.getModelTabelDefault().removeRow(selectedRow); // Sterge randul din model
                new Stoc_Farmacie().saveAllDataToFile(mainFrame.getModelTabelDefault()); // Salveaza toate datele in fisier
            }
        });
        popup.add(stergeItem); // Adauga itemul de meniu la popup
        return popup; // Returneaza meniul popup
    }
}

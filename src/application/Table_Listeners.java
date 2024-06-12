package application;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Table_Listeners {
    private MainFrame frame; // Referinta la obiectul MainFrame

    public Table_Listeners(MainFrame frame) {
        this.frame = frame; // Initializare referinta frame
    }

    // Metoda pentru adaugarea ascultatorilor de evenimente
    public void addListeners() {
        frame.getTabelStoc().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) { // Verifica daca s-a facut dublu click
                    int row = frame.getTabelStoc().getSelectedRow(); // Obtine randul selectat
                    if (row != -1) { // Verifica daca randul selectat este valid
                        new Produs_Panel(frame).openDetailPanel(frame, row); // Deschide panoul de detalii pentru produsul selectat
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) { // Verifica daca evenimentul a fost declansat de un meniu popup
                    int row = frame.getTabelStoc().rowAtPoint(e.getPoint()); // Obtine randul la care s-a facut click
                    if (row != -1) { // Verifica daca randul este valid
                        frame.getTabelStoc().setRowSelectionInterval(row, row); // Selecteaza randul in tabel
                        frame.getPopupStergeProdus().show(e.getComponent(), e.getX(), e.getY()); // Afiseaza meniul popup la pozitia cursorului
                    }
                }
            }
        });
    }

    // Metoda pentru crearea meniului popup
    public JPopupMenu createPopup() {
        JPopupMenu popup = new JPopupMenu(); // Creare meniu popup
        JMenuItem removeItem = new JMenuItem("Remove"); // Creare item de meniu pentru stergere
        removeItem.addActionListener(e -> {
            int selectedRow = frame.getTabelStoc().getSelectedRow(); // Obtine randul selectat
            if (selectedRow != -1) { // Verifica daca randul selectat este valid
                frame.getModel().removeRow(selectedRow); // Sterge randul din model
                new Stoc_Farmacie().saveAllDataToFile(frame.getModel()); // Salveaza toate datele in fisier
            }
        });
        popup.add(removeItem); // Adauga itemul de meniu la popup
        return popup; // Returneaza meniul popup
    }
}

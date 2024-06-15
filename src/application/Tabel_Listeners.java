package application;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Tabel_Listeners {
    private MainFrame mainFrame;
    
    public Tabel_Listeners(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void adaugaListenersTabel() {
        mainFrame.getTabelStoc().addMouseListener(new MouseAdapter() {
            // Metoda apelata la dublu-click pe un rand din tabel
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = mainFrame.getTabelStoc().getSelectedRow();
                    if (row != -1) {
                        new Produs_Panel(mainFrame).deschideDetailPanel(mainFrame, row);
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = mainFrame.getTabelStoc().rowAtPoint(e.getPoint());
                    if (row != -1) {
                        mainFrame.getTabelStoc().setRowSelectionInterval(row, row);
                        mainFrame.getPopupStergeProdus().show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });
    }

    public JPopupMenu createPopup() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem stergeItem = new JMenuItem("Sterge");
        stergeItem.addActionListener(e -> {
            int randSelectat = mainFrame.getTabelStoc().getSelectedRow();
            if (randSelectat != -1) {
                mainFrame.getModelTabelDefault().removeRow(randSelectat);
                new Stoc_Farmacie().salveazaStocInFisier(mainFrame.getModelTabelDefault());
            }
        });
        popup.add(stergeItem);
        return popup;
    }
}

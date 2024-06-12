package application;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Table_Listeners {
    private MainFrame frame;

    public Table_Listeners(MainFrame frame) {
        this.frame = frame;
    }

    public void addListeners() {
        frame.getTabelStoc().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = frame.getTabelStoc().getSelectedRow();
                    if (row != -1) {
                        new Produs_Panel(frame).openDetailPanel(row);
                    }
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = frame.getTabelStoc().rowAtPoint(e.getPoint());
                    if (row != -1) {
                        frame.getTabelStoc().setRowSelectionInterval(row, row);
                        frame.getPopupStergeProdus().show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });
    }

    public JPopupMenu createPopup() {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem removeItem = new JMenuItem("Remove");
        removeItem.addActionListener(e -> {
            int selectedRow = frame.getTabelStoc().getSelectedRow();
            if (selectedRow != -1) {
                frame.getModel().removeRow(selectedRow);
                new Stoc_Farmacie().saveAllDataToFile(frame.getModel());
            }
        });
        popup.add(removeItem);
        return popup;
    }
}

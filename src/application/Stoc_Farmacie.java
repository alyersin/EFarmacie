package application;

import javax.swing.table.DefaultTableModel;
import java.io.*;

public class Stoc_Farmacie {
    public void incarcaStoc(DefaultTableModel model) {
        try (BufferedReader reader = new BufferedReader(new FileReader("stoc.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                if (data.length == 7) {
                    model.addRow(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAllDataToFile(DefaultTableModel model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("stoc.txt"))) {
            for (int row = 0; row < model.getRowCount(); row++) {
                writer.write(model.getValueAt(row, 0) + ":" +
                        model.getValueAt(row, 1) + ":" +
                        model.getValueAt(row, 2) + ":" +
                        model.getValueAt(row, 3) + ":" +
                        model.getValueAt(row, 4) + ":" +
                        model.getValueAt(row, 5) + ":" +
                        model.getValueAt(row, 6));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package application;

import javax.swing.table.DefaultTableModel;
import java.io.*;

public class Stoc_Farmacie {

    public void incarcaStocDinTabel(DefaultTableModel modelTabelDefault) {
        try (BufferedReader br = new BufferedReader(new FileReader("stoc.txt"))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                String[] data = linie.split(":");
                if (data.length == 7) {
                    modelTabelDefault.addRow(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void salveazaStocInFisier(DefaultTableModel modelTabelDefault) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("stoc.txt"))) {
            for (int row = 0; row < modelTabelDefault.getRowCount(); row++) {
                bw.write(modelTabelDefault.getValueAt(row, 0) + ":" +
                        modelTabelDefault.getValueAt(row, 1) + ":" +
                        modelTabelDefault.getValueAt(row, 2) + ":" +
                        modelTabelDefault.getValueAt(row, 3) + ":" +
                        modelTabelDefault.getValueAt(row, 4) + ":" +
                        modelTabelDefault.getValueAt(row, 5) + ":" +
                        modelTabelDefault.getValueAt(row, 6));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

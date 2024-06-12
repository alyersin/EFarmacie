package application;

import javax.swing.table.DefaultTableModel;
import java.io.*;

public class Stoc_Farmacie {
    // Metoda pentru incarcare stoc in modelul tabelului
    public void incarcaStoc(DefaultTableModel model) {
        try (BufferedReader reader = new BufferedReader(new FileReader("stoc.txt"))) { // Deschide fisierul stoc.txt pentru citire
            String line;
            while ((line = reader.readLine()) != null) { // Citeste fiecare linie din fisier
                String[] data = line.split(":"); // Imparte linia citita in campuri, delimitate prin ":"
                if (data.length == 7) { // Verifica daca linia contine exact 7 campuri
                    model.addRow(data); // Adauga datele in modelul tabelului
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // In caz de eroare, afiseaza stiva de apeluri
        }
    }

    // Metoda pentru salvarea tuturor datelor din modelul tabelului in fisier
    public void saveAllDataToFile(DefaultTableModel model) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("stoc.txt"))) { // Deschide fisierul stoc.txt pentru scriere
            for (int row = 0; row < model.getRowCount(); row++) { // Parcurge toate randurile din modelul tabelului
                writer.write(model.getValueAt(row, 0) + ":" + // Scrie fiecare valoare din rand, separate prin ":"
                        model.getValueAt(row, 1) + ":" +
                        model.getValueAt(row, 2) + ":" +
                        model.getValueAt(row, 3) + ":" +
                        model.getValueAt(row, 4) + ":" +
                        model.getValueAt(row, 5) + ":" +
                        model.getValueAt(row, 6));
                writer.newLine(); // Adauga o linie noua in fisier
            }
        } catch (IOException e) {
            e.printStackTrace(); // In caz de eroare, afiseaza stiva de apeluri
        }
    }
}

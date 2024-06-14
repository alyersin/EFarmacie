package application;

import javax.swing.table.DefaultTableModel;
import java.io.*;

public class Stoc_Farmacie {
	
    // Metoda pentru incarcare stoc in modelul tabelului
    public void incarcaStoc(DefaultTableModel modelTabelDefault) {
        try (BufferedReader br = new BufferedReader(new FileReader("stoc.txt"))) { // Deschide fisierul stoc.txt pentru citire
            String linie;
            while ((linie = br.readLine()) != null) { // Citeste fiecare linie din fisier
                String[] data = linie.split(":"); // Imparte linia citita in campuri, delimitate prin ":"
                if (data.length == 7) { // Verifica daca linia contine exact 7 campuri
                    modelTabelDefault.addRow(data); // Adauga datele in modelul tabelului
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // In caz de eroare, afiseaza stiva de apeluri
        }
    }

    // Metoda pentru salvarea tuturor datelor din modelul tabelului in fisier
    public void salveazaStocInFisier(DefaultTableModel modelTabelDefault) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("stoc.txt"))) { // Deschide fisierul stoc.txt pentru scriere
            for (int row = 0; row < modelTabelDefault.getRowCount(); row++) { // Parcurge toate randurile din modelul tabelului
                bw.write(modelTabelDefault.getValueAt(row, 0) + ":" + // Scrie fiecare valoare din rand, separate prin ":"
                        modelTabelDefault.getValueAt(row, 1) + ":" +
                        modelTabelDefault.getValueAt(row, 2) + ":" +
                        modelTabelDefault.getValueAt(row, 3) + ":" +
                        modelTabelDefault.getValueAt(row, 4) + ":" +
                        modelTabelDefault.getValueAt(row, 5) + ":" +
                        modelTabelDefault.getValueAt(row, 6));
                bw.newLine(); // Adauga o linie noua in fisier
            }
        } catch (IOException e) {
            e.printStackTrace(); // In caz de eroare, afiseaza stiva de apeluri
        }
    }
}

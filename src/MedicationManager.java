import java.io.*;
import java.util.ArrayList;

public class MedicationManager {
    private ArrayList<Medication> medications;

    public MedicationManager() {
        this.medications = new ArrayList<>();
        loadMedicationsFromCSV("../data/Medicine_List.csv");
    }

    private void loadMedicationsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 3) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }

                String medicineName = fields[0].trim();
                int quantity = Integer.parseInt(fields[1].trim());
                int lowStockLevel = Integer.parseInt(fields[2].trim());

                Medication medication = new Medication(medicineName, quantity, lowStockLevel);
                medications.add(medication);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void writeMedicationsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../data/Medicine_List.csv"))) {
            // Write the header
            writer.write("Medicine Name,Initial Stock,Low Stock Level Alert\n");
    
            // Write each medication
            for (Medication medication : medications) {
                writer.write(medication.getMedicineName() + "," +
                             medication.getQuantity() + "," +
                             medication.getLowStockLevel() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    

    public ArrayList<Medication> getMedications() {
        return this.medications;
    }
}


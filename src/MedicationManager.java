import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MedicationManager {
    private List<Medication> medications;

    public MedicationManager() {
        medications = new ArrayList<>();
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

    public List<Medication> getMedications() {
        return medications;
    }
}


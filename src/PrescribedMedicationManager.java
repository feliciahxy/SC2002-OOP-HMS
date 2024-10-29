import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PrescribedMedicationManager {
    private ArrayList<PrescribedMedication> prescribedMedication;

    public PrescribedMedicationManager() {
        prescribedMedication = new ArrayList<>();
        loadPrescribedMedicationsFromCSV("../data/prescribed_medications.csv");
    }

    private void loadPrescribedMedicationsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 3) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }

                String appointmentID = fields[0].trim();
                String medicationName = fields[1].trim();
                String medicationStatus = fields[2].trim();

                PrescribedMedication Medication = new PrescribedMedication(appointmentID, medicationName, medicationStatus);
                this.prescribedMedication.add(Medication);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public List<PrescribedMedication> getPrescribedMedications() {
        return prescribedMedication;
    }
}

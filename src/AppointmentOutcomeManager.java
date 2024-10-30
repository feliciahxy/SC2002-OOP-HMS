import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AppointmentOutcomeManager {
    private ArrayList<AppointmentOutcome> appointmentOutcomes;

    public AppointmentOutcomeManager() {
        this.appointmentOutcomes = new ArrayList<>();
        Map<String, ArrayList<PrescribedMedication>> prescribedMedicationsByAppointmentID = loadPrescribedMedicationsFromCSV("../data/PrescribedMedication.csv");
        loadAppointmentOutcomesFromCSV("../data/AppointmentOutcome.csv", prescribedMedicationsByAppointmentID);
    }

    private Map<String, ArrayList<PrescribedMedication>> loadPrescribedMedicationsFromCSV(String filePath) {
        Map<String, ArrayList<PrescribedMedication>> prescribedMedicationsMap = new HashMap<>();

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

                PrescribedMedication prescribedMedication = new PrescribedMedication(appointmentID, medicationName, medicationStatus);

                // Group prescribed medications by appointmentID
                prescribedMedicationsMap
                        .computeIfAbsent(appointmentID, k -> new ArrayList<>())
                        .add(prescribedMedication);
            }
        } catch (IOException e) {
            System.out.println("Error reading prescribed medications file: " + e.getMessage());
        }

        return prescribedMedicationsMap;
    }

    private void loadAppointmentOutcomesFromCSV(String filePath, Map<String, ArrayList<PrescribedMedication>> prescribedMedicationsMap) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 5) {
                    System.out.println("Invalid data: " + line);
                    continue;
                }

                String appointmentID = fields[0].trim();
                String diagnosis = fields[1].trim();
                String serviceType = fields[2].trim();
                String date = fields[3].trim();
                ArrayList<PrescribedMedication> prescribedMedications = new ArrayList<>(prescribedMedicationsMap.getOrDefault(appointmentID, new ArrayList<>()));
                String notes = fields[4].trim();

                // Create an AppointmentOutcome object with your specific constructor
                AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointmentID, diagnosis, serviceType, date, prescribedMedications, notes);
                this.appointmentOutcomes.add(appointmentOutcome);
            }
        } catch (IOException e) {
            System.out.println("Error reading appointment outcomes file: " + e.getMessage());
        }
    }

    public ArrayList<AppointmentOutcome> getAppointmentOutcomes() {
        return this.appointmentOutcomes;
    }
}

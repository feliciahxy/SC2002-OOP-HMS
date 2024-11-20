import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The {@code AppointmentOutcomeManager} class is responsible for managing appointment outcomes and their
 * associated prescribed medications. It provides functionality to load and write data to CSV files.
 * @author Heng Xin Yu Felicia
 * @version 1.0
 * @since 2024-10-08
 */
public class AppointmentOutcomeManager {
    private ArrayList<AppointmentOutcome> appointmentOutcomes;

    /**
     * Constructs an {@code AppointmentOutcomeManager} and initializes appointment outcomes and prescribed medications
     * by loading data from CSV files.
     */
    public AppointmentOutcomeManager() {
        this.appointmentOutcomes = new ArrayList<>();
        Map<String, ArrayList<PrescribedMedication>> prescribedMedicationsByAppointmentID = loadPrescribedMedicationsFromCSV("../data/PrescribedMedication.csv");
        loadAppointmentOutcomesFromCSV("../data/AppointmentOutcome.csv", prescribedMedicationsByAppointmentID);
    }

    /**
     * Loads prescribed medications from a CSV file and groups them by appointment ID.
     *
     * @param filePath the file path of the prescribed medications CSV file.
     * @return a {@code Map} where the key is the appointment ID, and the value is a list of prescribed medications.
     */
    private Map<String, ArrayList<PrescribedMedication>> loadPrescribedMedicationsFromCSV(String filePath) {
        Map<String, ArrayList<PrescribedMedication>> prescribedMedicationsMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); 

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length < 3) {
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

    /**
     * Loads appointment outcomes from a CSV file and associates them with prescribed medications.
     *
     * @param filePath                the file path of the appointment outcomes CSV file.
     * @param prescribedMedicationsMap a map of prescribed medications grouped by appointment ID.
     */
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

    /**
     * Writes appointment outcomes to a CSV file.
     */
    public void writeAppointmentOutcomesToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../data/AppointmentOutcome.csv"))) {
            // Write the header
            writer.write("appointmentID,diagnosis,serviceType,date,notes\n");
    
            // Write each appointment outcome
            for (AppointmentOutcome outcome : appointmentOutcomes) {
                writer.write(outcome.getAppointmentID() + "," +
                    outcome.getDiagnosis() + "," +
                    outcome.getServiceType() + "," +
                    outcome.getDate() + "," +
                    outcome.getNotes().replace(",", ";") + "\n"); // Replace commas in notes to avoid CSV issues
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Writes prescribed medications to a CSV file.
     */
    public void writePrescribedMedicationsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../data/PrescribedMedication.csv"))) {
            // Write the header
            writer.write("Appointment ID,Medication Name,Medication Status\n");
    
            // Write prescribed medications for each appointment
            for (AppointmentOutcome appointmentOutcome : appointmentOutcomes) {
                for (PrescribedMedication medication : appointmentOutcome.getPrescribedMedicationList()) {
                    writer.write(appointmentOutcome.getAppointmentID() + "," +
                                 medication.getMedicationName() + "," +
                                 medication.getMedicationStatus() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing prescribed medications to file: " + e.getMessage());
        }
    }
    
    

    /**
     * Retrieves the list of appointment outcomes managed by this class.
     *
     * @return an {@code ArrayList} of {@link AppointmentOutcome}.
     */
    public ArrayList<AppointmentOutcome> getAppointmentOutcomes() {
        return this.appointmentOutcomes;
    }
}

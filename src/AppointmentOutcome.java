import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentOutcome {

    private String appointmentID;
    private String diagnosis;
    private String serviceType;
    private String date;
    private String notes;
    private final List<PrescribedMedication> prescribedMedicationList;


    public AppointmentOutcome(String appointmentID, String diagnosis, String serviceType, String date, String notes) {
        this.appointmentID = appointmentID;
        this.diagnosis = diagnosis;
        this.serviceType = serviceType;
        this.date = date;
        this.notes = notes;
        prescribedMedicationList = new ArrayList<>();
        loadPrescribedMedicationFromCSV("../data/PrescribedMedication.csv");
    }

    private void loadPrescribedMedicationFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String appointmentID = fields[0];
                String medicine = fields[1];
                String status = fields[2];
                
                PrescribedMedication prescribedMedication = new PrescribedMedication(appointmentID, medicine, status);
                prescribedMedicationList.add(prescribedMedication);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public String getAppointmentID() {
        return this.appointmentID;
    }
    
    public String getDiagnosis(){
        return this.diagnosis;
    }

    public void setDiagnosis(String diagnosis){
        this.diagnosis = diagnosis;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public void setServiceType(String serviceType){
        this.serviceType = serviceType;
    }

    public String getDate() {
        return this.date;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<PrescribedMedication> getPrescribedMedicationList() {
        return this.prescribedMedicationList;
    }
}

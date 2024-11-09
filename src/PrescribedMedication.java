
public class PrescribedMedication {
    
    private String appointmentID; 
    private String medicationName;
    private String medicationStatus;

    public PrescribedMedication(String appointmentID, String medicationName, String medicationStatus){
        this.appointmentID = appointmentID;
        this.medicationName = medicationName;
        this.medicationStatus = medicationStatus;
    }
    public String getAppointmentID(){
        return this.appointmentID;
    }
    public String getMedicationName() {
        return this.medicationName;
    }

    public void setMedicationName(String name) {
        this.medicationName = name;
    }

    public String getMedicationStatus() {
        return this.medicationStatus;
    }

    public void setMedicationStatus(String status) {
        this.medicationStatus = status;
    }
}
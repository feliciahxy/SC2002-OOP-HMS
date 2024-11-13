import java.util.*;

public class AppointmentOutcome {

    private String appointmentID;
    private String diagnosis;
    private String serviceType;
    private String date;
    private ArrayList<PrescribedMedication> PrescribedMedications;
    private String notes;

    public AppointmentOutcome(String appointmentID, String diagnosis,String serviceType, String date, ArrayList<PrescribedMedication> PrescribedMedications, String notes){
        this.appointmentID = appointmentID;
        this.diagnosis = diagnosis;
        this.serviceType = serviceType;
        this.date = date;
        this.PrescribedMedications = PrescribedMedications;
        this.notes = notes;
    }

    public String getAppointmentID() {
        return this.appointmentID;
    }
    
    public String getDiagnosis(){
        return this.diagnosis;
    }
    public String getServiceType() {
        return this.serviceType;
    }

    public String getDate() {
        return this.date;
    }

    // public void setDate(Date date) {
    //     return;
    // }

    public ArrayList<PrescribedMedication> getPrescribedMedicationList() {
        return this.PrescribedMedications;
    }
    
    // public PrescribedMedication getPrescribedMedication(String medicationName){
    //     boolean found = false;
    //     for (int i = 0; i<this.PrescribedMedications.size(); i++){
    //         PrescribedMedication med = PrescribedMedications.get(i);
    //         if (medicationName.equals(med.getMedicationName())){
    //             found = true;
    //             return med;
    //         }
    //     }
    //     if (found == false){
    //         System.out.println("Medication not found.");
    //         return null;
    //     }
    //     return null;
    // }

    public void setMedicationStatus(PrescribedMedication med, String status){
        med.setMedicationStatus(status);
        // for (int i = 0; i<this.PrescribedMedications.size(); i++){
        //     PrescribedMedication med = PrescribedMedications.get(i);
        //     if (medicationName.equals(med.getMedicationName())){
        //         med.setMedicationStatus(status);
        //     }
        // }
    }

    public String getNotes(){
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

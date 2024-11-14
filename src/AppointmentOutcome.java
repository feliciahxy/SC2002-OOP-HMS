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

    public String toString() {
        StringBuilder medications = new StringBuilder();
        for (PrescribedMedication medication : PrescribedMedications) {
            medications.append(medication.toString()).append(", ");
        }
        
        if (medications.length() > 0) {
            medications.setLength(medications.length() - 2);
        }
    
        return "AppointmentOutcome {" +
               "\n  Appointment ID: " + appointmentID +
               "\n  Date: " + date +
               "\n  Diagnosis: " + diagnosis +
               "\n  Service Type: " + serviceType +
               "\n  Notes: " + notes +
               "\n  Prescribed Medications: " + (medications.length() > 0 ? medications.toString() : "None") +
               "\n}";
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Patient

    public void displayAppointmentHistory(String patientID, ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Appointment> appointments) {
        ArrayList<String> patientAppointmentIDs = Appointment.patientAppointmentIDs(patientID, appointments);
        for (String appointmentID : patientAppointmentIDs) {
            for (AppointmentOutcome appointmentOutcome : appointmentOutcomes) {
                if (appointmentOutcome.getAppointmentID().equals(appointmentID)) {
                    System.out.println();
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    

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
import java.util.*;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private String date; 
    private String time;
    private String status;

    public Appointment (String appointmentID, String patientID, String doctorID, String date, String time, String status) {
        this.appointmentID= appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Main Functions

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void createAppointment(ArrayList<Appointment> appointmentList, String patientID, String doctorID, int dateChoice, int slot) {
        int size = appointmentList.size();
        String formattedSize = String.format("%04d", size);
        String formattedDate = String.format("%02d", dateChoice);
        String appointmentID = "AP" + formattedSize;
        Appointment appointment = new Appointment(appointmentID,patientID,doctorID,formattedDate,Schedule.slotToTime(slot),"pending");
        appointmentList.add(appointment);
    }

    public static void changeAppointment(ArrayList<Appointment> appointmentList, String appointmentID, int newDate, int newSlot) {
        String formattedDate = String.format("%02d", newDate);
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                appointment.setDate(formattedDate);
                appointment.setTime(Schedule.slotToTime(newSlot));
                appointment.setStatus("pending");
                break;
            }
        }
    }

    public static void cancelAppointment(ArrayList<Appointment> appointmentList, String appointmentID) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                appointment.setStatus("cancelled");
            }
        }
    }

    public static ArrayList<String> patientAppointmentIDs(String patientID, ArrayList<Appointment> appointmentList) {
        ArrayList<String> patientAppointmentIDs = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientID().equals(patientID)) patientAppointmentIDs.add(appointment.getAppointmentID());
        }
        return patientAppointmentIDs;
    } 

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Helper Functions

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Format of AppointmentID
    public static boolean isValidAppointmentID(String appointmentID) {
        return appointmentID.matches("^AP\\d{4}$");
    }

    // AppointmentID matches patientID
    public static boolean belongToPatient(ArrayList<Appointment> appointmentList, String appointmentID, String patientID) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                if (appointment.getPatientID().equals(patientID)) return true;
            }
        }
        return false;
    }

    public static int timeToSlot(String time) {
        if (time.equals("0900-1000")) return 1;
        else if (time.equals("1000-1100")) return 2;
        else if (time.equals("1100-1200")) return 3;

        else return -1;
    }


    public String getAppointmentID() { 
        return appointmentID; 
    }
public void setAppointmentID(String appointmentID) { 
        this.appointmentID = appointmentID;
    }

    public String getPatientID() { 
        return patientID; 
    }

    public void setPatientID(String patientID) { 
        this.patientID = patientID; 
    }

    public String getDoctorID() { 
        return doctorID; 
    }

    public void setDoctorID(String doctorID) { 
        this.doctorID = doctorID; 
    }

    public String getDate() { 
        return date; 
    }

    public void setDate(String date) { 
        this.date = date; 
    }

    public String getTime() { 
        return time; 
    }

    public void setTime(String time) { 
        this.time = time; 
    }


    public String getStatus() { 
        return status; 
    }

    public void setStatus(String status) { 
        this.status = status; 
    }

    public void cancel(){
        this.status = "canceled";
    }

    public void confirm(){
        this.status = "confirmed";
    }

    public void complete(){
        this.status = "completed";
    }


}

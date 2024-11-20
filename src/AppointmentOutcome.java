import java.util.*;

/**
 * The {@code AppointmentOutcome} class represents the outcome of a medical appointment, including details such as
 * diagnosis, service type, prescribed medications, and additional notes.
 * @author Heng Xin Yu Felicia
 * @version 1.0
 * @since 2024-10-08
 */
public class AppointmentOutcome {

    private String appointmentID;
    private String diagnosis;
    private String serviceType;
    private String date;
    private ArrayList<PrescribedMedication> PrescribedMedications;
    private String notes;

    /**
     * Constructs an {@code AppointmentOutcome} object.
     *
     * @param appointmentID       the appointment ID.
     * @param diagnosis           the diagnosis made during the appointment.
     * @param serviceType         the type of service provided during the appointment.
     * @param date                the date of the appointment.
     * @param PrescribedMedications the list of prescribed medications.
     * @param notes               additional notes for the appointment.
     */
    public AppointmentOutcome(String appointmentID, String diagnosis,String serviceType, String date, ArrayList<PrescribedMedication> PrescribedMedications, String notes) {
        this.appointmentID = appointmentID;
        this.diagnosis = diagnosis;
        this.serviceType = serviceType;
        this.date = date;
        this.PrescribedMedications = PrescribedMedications;
        this.notes = notes;
    }

    /**
     * Returns a string representation of the appointment outcome, including all details.
     *
     * @return a formatted string with appointment details.
     */
    public String toString() {
        StringBuilder medications = new StringBuilder();
        for (PrescribedMedication medication : PrescribedMedications) {
            medications.append(medication.toString()).append(", ");
        }
        
        if (medications.length() > 0) {
            medications.setLength(medications.length() - 2);
        }
    
        return "\nAppointment ID: " + appointmentID +
               "\nDate: " + date + " November" +
               "\nDiagnosis: " + diagnosis +
               "\nService Type: " + serviceType +
               "\nNotes: " + notes +
               "\nPrescribed Medications: " + (medications.length() > 0 ? medications.toString() : "None");
    }

    /**
     * Displays the appointment history for a specific patient.
     *
     * @param patientID           the ID of the patient.
     * @param appointmentOutcomes the list of all appointment outcomes.
     * @param appointments        the list of all appointments.
     */
    public static void displayAppointmentHistory(String patientID, ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Appointment> appointments) {
        ArrayList<String> patientAppointmentIDs = Appointment.patientAppointmentIDs(patientID, appointments);
        for (String appointmentID : patientAppointmentIDs) {
            for (AppointmentOutcome appointmentOutcome : appointmentOutcomes) {
                if (appointmentOutcome.getAppointmentID().equals(appointmentID)) {
                    System.out.println(appointmentOutcome);
                }
            }
        }
    }

    /**
     * Retrieves the appointment ID associated with this appointment outcome.
     *
     * @return the appointment ID.
     */
    public String getAppointmentID() {
        return this.appointmentID;
    }
    
    /**
     * Retrieves the diagnosis made during the appointment.
     *
     * @return the diagnosis as a string.
     */
    public String getDiagnosis(){
        return this.diagnosis;
    }

    /**
     * Retrieves the type of service provided during the appointment.
     *
     * @return the service type as a string.
     */
    public String getServiceType() {
        return this.serviceType;
    }

    /**
     * Retrieves the date of the appointment.
     *
     * @return the appointment date as a string.
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Sets the diagnosis for the appointment outcome.
     *
     * @param diagnosis the new diagnosis to be set.
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Sets the service type for the appointment outcome.
     *
     * @param serviceType the new service type to be set.
     */
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    /**
     * Sets additional notes for the appointment outcome.
     *
     * @param notes the new notes to be set.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Retrieves the list of prescribed medications for the appointment outcome.
     *
     * @return an {@code ArrayList} of {@link PrescribedMedication}.
     */
    public ArrayList<PrescribedMedication> getPrescribedMedicationList() {
        return this.PrescribedMedications;
    }
    
    /**
     * Updates the status of a specific prescribed medication.
     *
     * @param med    the prescribed medication to update.
     * @param status the new status to be set.
     */
    public void setMedicationStatus(PrescribedMedication med, String status){
        med.setMedicationStatus(status);
    }

    /**
     * Retrieves the additional notes for the appointment outcome.
     *
     * @return the notes as a string.
     */
    public String getNotes(){
        return this.notes;
    }
}

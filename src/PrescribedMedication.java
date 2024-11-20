/**
 * Represents a prescribed medication associated with a specific appointment.
 * Stores details about the medication, including the appointment ID, medication name,
 * and the status of the medication (e.g., "Pending", "Dispensed").
 */
public class PrescribedMedication {
    
    private String appointmentID; 
    private String medicationName;
    private String medicationStatus;

    /**
     * Constructs a new PrescribedMedication with the specified details.
     *
     * @param appointmentID   the unique identifier for the associated appointment.
     * @param medicationName  the name of the prescribed medication.
     * @param medicationStatus the status of the medication (e.g., "Pending", "Dispensed").
     */
    public PrescribedMedication(String appointmentID, String medicationName, String medicationStatus){
        this.appointmentID = appointmentID;
        this.medicationName = medicationName;
        this.medicationStatus = medicationStatus;
    }

    /**
     * Returns a string representation of the prescribed medication.
     *
     * @return a string in the format "medicationName (medicationStatus)".
     */
    public String toString() {
        return medicationName + " (" + medicationStatus + ")";
    }

    /**
     * Gets the appointment ID associated with the prescribed medication.
     *
     * @return the appointment ID.
     */
    public String getAppointmentID(){
        return this.appointmentID;
    }

    /**
     * Gets the name of the prescribed medication.
     *
     * @return the medication name.
     */
    public String getMedicationName() {
        return this.medicationName;
    }

    /**
     * Sets the name of the prescribed medication.
     *
     * @param name the new name of the medication.
     */
    public void setMedicationName(String name) {
        this.medicationName = name;
    }

    /**
     * Gets the current status of the prescribed medication.
     *
     * @return the medication status.
     */
    public String getMedicationStatus() {
        return this.medicationStatus;
    }

    /**
     * Sets the status of the prescribed medication.
     *
     * @param status the new status of the medication (e.g., "Pending", "Dispensed").
     */
    public void setMedicationStatus(String status) {
        this.medicationStatus = status;
    }
}
import java.util.*;

/**
 * The {@code Appointment} class represents an appointment with attributes such as 
 * appointment ID, patient ID, doctor ID, date, time, and status. It also includes 
 * utility methods for managing and manipulating appointments.
 */

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private String date; 
    private String time;
    private String status;

    /**
     * Constructs an {@code Appointment} with the specified attributes.
     *
     * @param appointmentID the ID of the appointment.
     * @param patientID the ID of the patient.
     * @param doctorID the ID of the doctor.
     * @param date the date of the appointment.
     * @param time the time of the appointment.
     * @param status the status of the appointment.
     */
    public Appointment (String appointmentID, String patientID, String doctorID, String date, String time, String status) {
        this.appointmentID= appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    /**
     * Returns a string representation of the appointment.
     *
     * @return a formatted string with appointment details.
     */
    public String toString() {
        return "Appointment Details:\n" +
               "Appointment ID: " + appointmentID + "\n" +
               "Patient ID: " + patientID + "\n" +
               "Doctor ID: " + doctorID + "\n" +
               "Date: " + date + "\n" +
               "Time: " + time + "\n" +
               "Status: " + status;
    }

    /**
     * Creates a new appointment and adds it to the specified appointment list.
     *
     * @param appointmentList the list of appointments.
     * @param patientID the ID of the patient.
     * @param doctorID the ID of the doctor.
     * @param dateChoice the chosen date.
     * @param slot the chosen time slot.
     * @return the created {@code Appointment}.
     */
    public static Appointment createAppointment(ArrayList<Appointment> appointmentList, String patientID, String doctorID, int dateChoice, int slot) {
        int size = appointmentList.size();
        String formattedSize = String.format("%04d", size + 1);
        String formattedDate = String.format("%02d", dateChoice);
        String appointmentID = "AP" + formattedSize;
        Appointment appointment = new Appointment(appointmentID,patientID,doctorID,formattedDate,Schedule.slotToTime(slot),"pending");
        appointmentList.add(appointment);
        return appointment;
    }

    /**
     * Changes the date and time of an existing appointment.
     *
     * @param appointmentList the list of appointments.
     * @param appointmentID the ID of the appointment to be changed.
     * @param newDate the new date.
     * @param newSlot the new time slot.
     */
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

    /**
     * Cancels an appointment by updating its status to "cancelled".
     *
     * @param appointmentList the list of appointments.
     * @param appointmentID the ID of the appointment to cancel.
     */
    public static void cancelAppointment(ArrayList<Appointment> appointmentList, String appointmentID) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                appointment.setStatus("cancelled");
            }
        }
    }

    /**
     * Retrieves a list of appointment IDs associated with a given patient ID.
     *
     * @param patientID the ID of the patient.
     * @param appointmentList the list of appointments.
     * @return a list of appointment IDs belonging to the patient.
     */
    public static ArrayList<String> patientAppointmentIDs(String patientID, ArrayList<Appointment> appointmentList) {
        ArrayList<String> patientAppointmentIDs = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientID().equals(patientID)) patientAppointmentIDs.add(appointment.getAppointmentID());
        }
        return patientAppointmentIDs;
    }
    
    /**
     * Displays all appointments along with their outcomes.
     *
     * @param appointmentList the list of appointments.
     * @param appointmentOutcomes the list of appointment outcomes.
     */
    public static void displayAppointments(ArrayList<Appointment> appointmentList, ArrayList<AppointmentOutcome> appointmentOutcomes) {
        for (Appointment appointment : appointmentList) {
            System.out.println("\nAppointmentID: " + appointment.getAppointmentID());
            System.out.println("PatientID: " + appointment.getPatientID());
            System.out.println("DoctorID: " + appointment.getDoctorID());
            System.out.println("Status: " + appointment.getStatus());
            System.out.println("Date: " + appointment.getDate() + " November");
            System.out.println("Time: " + appointment.getTime());

            if (appointment.getStatus().equals("completed")) {
                System.out.print("\nAppointment Outcome Record");
                for (AppointmentOutcome appointmentOutcome : appointmentOutcomes) {
                    if (appointmentOutcome.getAppointmentID().equals(appointment.getAppointmentID())) {
                        System.out.println(appointmentOutcome);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Determines if a doctor can write an outcome for a specified appointment.
     *
     * @param appointments the list of appointments.
     * @param appointmentID the ID of the appointment.
     * @param doctorID the ID of the doctor.
     * @return {@code true} if the doctor can write the outcome; {@code false} otherwise.
     */
    public static boolean doctorCanWriteOutcome(ArrayList<Appointment> appointments, String appointmentID, String doctorID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                if (appointment.getDoctorID().equals(doctorID) && appointment.getStatus().equals("confirmed")) return true;
                else return false;
            }
        }

        return false;
    }

    /**
     * Validates the format of an appointment ID.
     *
     * @param appointmentID the appointment ID to validate.
     * @return {@code true} if the appointment ID is valid; {@code false} otherwise.
     */
    public static boolean isValidAppointmentID(String appointmentID) {
        return appointmentID.matches("^AP\\d{4}$");
    }

    /**
     * Checks if an appointment ID exists in the list of appointments.
     *
     * @param appointments the list of appointments.
     * @param appointmentID the appointment ID to check.
     * @return {@code true} if the appointment ID exists; {@code false} otherwise.
     */
    public static boolean inAppointments(ArrayList<Appointment> appointments, String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) return true;
        }
        return false;
    }

    /**
     * Checks if an appointment belongs to a specified patient.
     *
     * @param appointmentList the list of appointments.
     * @param appointmentID the appointment ID to check.
     * @param patientID the ID of the patient.
     * @return {@code true} if the appointment belongs to the patient; {@code false} otherwise.
     */
    public static boolean belongToPatient(ArrayList<Appointment> appointmentList, String appointmentID, String patientID) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                if (appointment.getPatientID().equals(patientID)) return true;
            }
        }
        return false;
    }

    /**
     * Determines if an appointment can be rescheduled.
     *
     * @param appointmentList the list of appointments.
     * @param appointmentID the ID of the appointment to check.
     * @return {@code true} if the appointment can be rescheduled; {@code false} otherwise.
     */
    public static boolean canReschedule(ArrayList<Appointment> appointmentList, String appointmentID) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus().equals("pending")) return true;
            else if (appointment.getStatus().equals("confirmed")) return true;
        }

        return false;
    }

    /**
     * Converts a time string to a slot number.
     *
     * @param time the time string to convert.
     * @return the corresponding slot number.
     */
    public static int timeToSlot(String time) {
        if (time.equals("0900-1000")) return 1;
        else if (time.equals("1000-1100")) return 2;
        else if (time.equals("1100-1200")) return 3;

        else return -1;
    }

    /**
     * Retrieves the appointment ID.
     *
     * @return the appointment ID.
     */
    public String getAppointmentID() { 
        return appointmentID; 
    }

    /**
     * Updates the appointment ID.
     *
     * @param appointmentID the new appointment ID.
     */

    public void setAppointmentID(String appointmentID) { 
        this.appointmentID = appointmentID;
    }

    /**
     * Retrieves the patient ID.
     *
     * @return the patient ID.
     */
    public String getPatientID() { 
        return patientID; 
    }

    /**
     * Updates the patient ID.
     *
     * @param patientID the new patient ID.
     */
    public void setPatientID(String patientID) { 
        this.patientID = patientID; 
    }

    /**
     * Retrieves the doctor ID.
     *
     * @return the doctor ID.
     */
    public String getDoctorID() { 
        return doctorID; 
    }

    /**
     * Updates the doctor ID.
     *
     * @param doctorID the new doctor ID.
     */
    public void setDoctorID(String doctorID) { 
        this.doctorID = doctorID; 
    }

    /**
     * Retrieves the date of the appointment.
     *
     * @return the appointment date.
     */
    public String getDate() { 
        return date; 
    }

    /**
     * Updates the date of the appointment.
     *
     * @param date the new appointment date.
     */
    public void setDate(String date) { 
        this.date = date; 
    }

    /**
     * Retrieves the time of the appointment.
     *
     * @return the appointment time.
     */
    public String getTime() { 
        return time; 
    }

    /**
     * Updates the time of the appointment.
     *
     * @param time the new appointment time.
     */
    public void setTime(String time) { 
        this.time = time; 
    }

    /**
     * Retrieves the status of the appointment.
     *
     * @return the appointment status.
     */
    public String getStatus() { 
        return status; 
    }

    /**
     * Updates the status of the appointment.
     *
     * @param status the new appointment status.
     */
    public void setStatus(String status) { 
        this.status = status; 
    }

    /**
     * Cancels the appointment by setting its status to "cancelled".
     */
    public void cancel(){
        this.status = "canceled";
    }

    /**
     * Confirms the appointment by setting its status to "confirmed".
     */
    public void confirm(){
        this.status = "confirmed";
    }

    /**
     * Completes the appointment by setting its status to "completed".
     */
    public void complete(){
        this.status = "completed";
    }


}

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

    public String toString() {
        return "Appointment Details:\n" +
               "Appointment ID: " + appointmentID + "\n" +
               "Patient ID: " + patientID + "\n" +
               "Doctor ID: " + doctorID + "\n" +
               "Date: " + date + "\n" +
               "Time: " + time + "\n" +
               "Status: " + status;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Main Functions

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static Appointment createAppointment(ArrayList<Appointment> appointmentList, String patientID, String doctorID, int dateChoice, int slot) {
        int size = appointmentList.size();
        String formattedSize = String.format("%04d", size + 1);
        String formattedDate = String.format("%02d", dateChoice);
        String appointmentID = "AP" + formattedSize;
        Appointment appointment = new Appointment(appointmentID,patientID,doctorID,formattedDate,Schedule.slotToTime(slot),"pending");
        appointmentList.add(appointment);
        return appointment;
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

    // Admin

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Doctor

    public static boolean doctorCanWriteOutcome(ArrayList<Appointment> appointments, String appointmentID, String doctorID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                if (appointment.getDoctorID().equals(doctorID) && appointment.getStatus().equals("confirmed")) return true;
                else return false;
            }
        }

        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Helper Functions

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Format of AppointmentID
    public static boolean isValidAppointmentID(String appointmentID) {
        return appointmentID.matches("^AP\\d{4}$");
    }

    // AppointmentID found in Appointments
    public static boolean inAppointments(ArrayList<Appointment> appointments, String appointmentID) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) return true;
        }
        return false;
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

    public static boolean canReschedule(ArrayList<Appointment> appointmentList, String appointmentID) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getStatus().equals("pending")) return true;
            else if (appointment.getStatus().equals("confirmed")) return true;
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

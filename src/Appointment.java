import java.util.*;

public class Appointment {
    private String appointmentID;
    private String patientID;
    private String doctorID;
    private String date; 
    private String time;
    private String status;

    public Appointment (String appointmentID, String patientID, String doctorID, String date, String time, String status){
        this.appointmentID= appointmentID;
        this.patientID = patientID;
        this.doctorID = doctorID;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public void reschedule(String newDate, String newTime) {
        this.date = newDate;
        this.time = newTime;
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

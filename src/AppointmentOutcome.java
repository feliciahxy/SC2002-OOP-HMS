import java.util.*;

public class AppointmentOutcome {

    private String appointmentID;
    private String serviceType;
    private Date date;
    private ArrayList<PrescribedMedication> PrescribedMedications;
    private String notes;
    public void AppointmentOutcome(String appointmentID, String serviceType, Date date, ArrayList<PrescribedMedication> PrescribedMedications, String notes){
        this.appointmentID = appointmentID;
        this.serviceType = serviceType;
        this.date = date;
        this.PrescribedMedications = PrescribedMedications;
        this.notes = notes;
    }


    public String getAppointmentID() {
        return this.appointmentID;
    }

    public String getServiceType() {
        return this.serviceType;
    }

    public Date getDate() {
        return this.date;
    }

    // public void setDate(Date date) {
    //     return;
    // }

    public ArrayList<PrescribedMedication> getPrescribedMedications() {
        return this.PrescribedMedications;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

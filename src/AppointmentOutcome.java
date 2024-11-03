
public class AppointmentOutcome {

    private String appointmentID;
    private String diagnosis;
    private String serviceType;
    private String date;
    private String notes;


    public AppointmentOutcome(String appointmentID, String diagnosis, String serviceType, String date, String notes){
        this.appointmentID = appointmentID;
        this.diagnosis = diagnosis;
        this.serviceType = serviceType;
        this.date = date;
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

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}

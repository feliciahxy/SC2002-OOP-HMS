import java.util.*;
import java.time.*;

public class Patient extends User {
    private String patientID;
    private String name;
    private LocalDate dob;
    private String gender;
    private ContactInfo contactInfo;
    private String bloodType;
    private MedicalRecord medicalRecord;
    private ArrayList<Appointment> appointments;

    // Constructor

    public Patient(
        String patientID,
        String name,
        LocalDate dob,
        String gender,
        ContactInfo contactInfo,
        String bloodType,
        MedicalRecord medicalRecord,
        String userID,
        String password,
        String role,
        ArrayList<Appointment> appointments
    ) {
        super(userID, password, role);
        this.patientID = patientID;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.bloodType = bloodType;
        this.medicalRecord = medicalRecord;
        this.appointments = appointments;
    }

    // Functions
    // Update Personal Info in Getters & Setters

    public void viewMedicalRecord() {
        System.out.println(this.getMedicalRecord());
    }

    public void scheduleAppointment(Doctor doctor, LocalDate date, LocalTime time) {
        return;
    }

    public void rescheduleAppointment(Appointment appointment, LocalDate newDate, LocalTime newTime) {
        return;
    }

    public void cancelAppointment(Appointment appointment) {
        return;
    }

    public void viewAppointments() {
        System.out.println(this.getAppointments());
    }

    public void viewAppointmentOutcome(String appointmentID) {
        return;
    }




    // Getter and Setter
    public String getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDOB() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public String getBloodType() {
        return bloodType;
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // Update personal Info
    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }
    
    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }
}
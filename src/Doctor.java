import java.util.ArrayList;
import java.util.List;

public class Doctor extends Staff {
    private Schedule schedule;
    private List<Patient> patientList;
    private List<Appointment> appointments;

    public Doctor(
        String staffID,
        String name,
        String department,
        String gender,
        int age,
        Schedule schedule,
        ArrayList<Patient> patientList,
        ArrayList<Appointment> appointments,
        String userID,
        String password,
        String role
    ) {
        super(staffID, name, department, gender, age, userID, password, role);
        this.schedule = schedule;
        this.patientList = patientList;
        this.appointments = appointments;
    }

    public MedicalRecord viewPatientRecords(Patient patient) {
        return patient.getMedicalRecord();
    }

    public void updatePatientRecord(Patient patient, String newDiagnosis, String newPrescription) {
        MedicalRecord medicalRecord = patient.getMedicalRecord();
        medicalRecord.addDiagnosis(newDiagnosis);
    }

    public void setAvailability(List<Date> availableDates, List<Time> availableTimes) {
        this.schedule.setAvailableDates(availableDates);
        this.schedule.setAvailableTimes(availableTimes);
    }

    public void acceptAppointment(String appointmentID) {
        return;
    }

    public void declineAppointment(String appointmentID) {
        return;
    }

    public void recordAppointmentOutcome(String appointmentID, AppointmentOutcome outcome) {
        return;
    }

    public List<Appointment> viewAppointments() {
        return this.appointments;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Patient> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<Patient> patientList) {
        this.patientList = patientList;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }
}
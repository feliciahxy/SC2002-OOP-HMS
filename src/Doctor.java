import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    
    private Schedule schedule;
    private final List<Patient> patientList;
    private final List<Appointment> appointmentList;

    public Doctor(
        String id,
        String name,
        String role,
        String gender,
        int age,
        String password,
        Schedule schedule
    )
    {
        super(id, name, role, gender, age, password);
        this.schedule = schedule;
        patientList = new ArrayList<>();
        loadPatientsFromCSV("../data/Patient_List.csv");
        appointmentList = new ArrayList<>();
        loadAppointmentsFromCSV("../data/Appointment.csv");

        for (Patient patient : patientList) {
            int isUnderCare = 0;
            for (Appointment appointment : appointmentList) {
                if (appointment.getPatientID().equals(patient.getId()) && appointment.getDoctorID().equals(id)) {
                    isUnderCare = 1;
                }
            }
            if ((isUnderCare) == 0) {
                patientList.remove(patient);
            }
        }
    }

    private void loadAppointmentsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String appointmentID = fields[0];
                String patientID = fields[1];
                String doctorID = fields[2];
                String date = fields[3];
                String time = fields[4];
                String status = fields[5];

                Appointment appointment = new Appointment(appointmentID, patientID, doctorID, date, time, status);
                appointmentList.add(appointment);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void loadPatientsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String id = fields[0];
                String name = fields[1];
                LocalDate dob = LocalDate.parse(fields[2]);
                String gender = fields[3];
                String bloodType = fields[4];
                String phoneNumber = fields[5];
                String email = fields[6];
                String password = fields[7];
                
                Patient patient = new Patient(id, name, dob, gender, bloodType, phoneNumber, email, password);
                patientList.add(patient);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public void viewPatientRecords(Patient patient) {
        if (patientList.contains(patient)) {
            System.out.println("Patient ID: " + patient.getId());
            System.out.println("Name: " + patient.getName());
            System.out.println("Date of Birth: " + patient.getDob());
            System.out.println("Gender: " + patient.getGender());
            System.out.println("Phone Number: " + patient.getPhoneNumber());
            System.out.println("Email: " + patient.getEmail());
            System.out.println("Blood Type: " + patient.getBloodType());
        } else {
            System.out.println("This patient is not under your care.");
        }
    }

    public void updatePatientRecord(Patient patient, String newDiagnosis, String newPrescription, String newService) {

    }

} 

    // public MedicalRecord viewPatientRecords(Patient patient) {
    //     return patient.getMedicalRecord();
    // }

    // public void updatePatientRecord(Patient patient, String newDiagnosis, String newPrescription) {
    //     MedicalRecord medicalRecord = patient.getMedicalRecord();
    //     medicalRecord.addDiagnosis(newDiagnosis);
    // }

    // public void setAvailability(List<Date> availableDates, List<Time> availableTimes) {
    //     this.schedule.setAvailableDates(availableDates);
    //     this.schedule.setAvailableTimes(availableTimes);
    // }

    // public void acceptAppointment(String appointmentID) {
    //     return;
    // }

    // public void declineAppointment(String appointmentID) {
    //     return;
    // }

    // public void recordAppointmentOutcome(String appointmentID, AppointmentOutcome outcome) {
    //     return;
    // }

    // public List<Appointment> viewAppointments() {
    //     return this.appointments;
    // }

    // public Schedule getSchedule() {
    //     return schedule;
    // }

    // public void setSchedule(Schedule schedule) {
    //     this.schedule = schedule;
    // }

    // public List<Patient> getPatientList() {
    //     return patientList;
    // }

    // // public void setPatientList(List<Patient> patientList) {
    // //     this.patientList = patientList;
    // // }

    // public List<Appointment> getAppointments() {
    //     return appointments;
    // }

    // public void setAppointments(List<Appointment> appointments) {
    //     this.appointments = appointments;
    // }
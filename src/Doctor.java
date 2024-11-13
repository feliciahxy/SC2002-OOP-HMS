import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    
    private Schedule schedule;
    private final List<Patient> patientList;
    private final List<Appointment> appointmentList;
    private final List<AppointmentOutcome> appointmentOutcomesList;
    private final List<PrescribedMedication> prescribedMedicationList;
    // private final ArrayList<Schedule> scheduleList;

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
        // scheduleList = new ArrayList<>();
        // scheduleList.add(schedule);
        patientList = new ArrayList<>();
        loadPatientsFromCSV("../data/Patient_List.csv");
        appointmentList = new ArrayList<>();
        loadAppointmentsFromCSV("../data/Appointment.csv");
        appointmentOutcomesList = new ArrayList<>();
        loadAppointmentOutcomesFromCSV("../data/AppointmentOutcome.csv");
        prescribedMedicationList = new ArrayList<>();
        loadPrescribedMedicationFromCSV(role);

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

    private void loadAppointmentOutcomesFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String appointmentID = fields[0];
                String diagnosis = fields[1];
                String serviceType = fields[2];
                String date = fields[3];
                String notes = fields[4];

                AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointmentID, diagnosis, serviceType, date, notes);
                appointmentOutcomesList.add(appointmentOutcome);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private void loadPrescribedMedicationFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String appointmentID = fields[0];
                String medicine = fields[1];
                String status = fields[2];

                PrescribedMedication prescribedMedication = new PrescribedMedication(appointmentID, medicine, status);
                prescribedMedicationList.add(prescribedMedication);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    

    // actual doctor functions

    public void viewPatientRecords(Patient patient) {
        if (patientList.contains(patient)) {
            System.out.println("Patient ID: " + patient.getId());
            System.out.println("Name: " + patient.getName());
            System.out.println("Date of Birth: " + patient.getDob());
            System.out.println("Gender: " + patient.getGender());
            System.out.println("Phone Number: " + patient.getPhoneNumber());
            System.out.println("Email: " + patient.getEmail());
            System.out.println("Blood Type: " + patient.getBloodType());
            System.out.println();
            for (Appointment appointment : appointmentList) {
                if (appointment.getPatientID().equals(patient.getId())) {
                    String appointmentIDtosearch = appointment.getAppointmentID();
                    for (AppointmentOutcome appointmentOutcome : appointmentOutcomesList) {
                        if (appointmentOutcome.getAppointmentID().equals(appointmentIDtosearch)) {
                            System.out.println("Appointment ID: " + appointmentOutcome.getAppointmentID());
                            System.out.println("Diagnosis: " + appointmentOutcome.getDiagnosis());
                            System.out.println("Treatment: " + appointmentOutcome.getServiceType());
                            for (PrescribedMedication prescribedMedication : prescribedMedicationList) {
                                if (prescribedMedication.getAppointmentID().equals(appointmentIDtosearch)) {
                                    System.out.println("Prescribed Medication: " + prescribedMedication.getMedicationName());
                                    System.out.println("Status: " + prescribedMedication.getMedicationStatus());
                                }
                            }
                        }
                    }
                }

            }
        } else {
            System.out.println("This patient is not under your care.");
        }
    }

    public void updatePatientRecord(Patient patient) {
        if (patientList.contains(patient)) {
            viewPatientRecords(patient);
            Scanner sc = new Scanner(System.in);
            System.out.println("Which appointment do you want to update? Enter the appointment ID: ");
            String apptID = sc.nextLine();
            for (AppointmentOutcome appointmentOutcome : appointmentOutcomesList) {
                if (appointmentOutcome.getAppointmentID().equals(apptID)) {
                    System.out.println("Appointment ID: " + appointmentOutcome.getAppointmentID());
                    System.out.println("Diagnosis: " + appointmentOutcome.getDiagnosis());
                    System.out.println("Treatment: " + appointmentOutcome.getServiceType());
                    for (PrescribedMedication prescribedMedication : prescribedMedicationList) {
                        if (prescribedMedication.getAppointmentID().equals(apptID)) {
                            System.out.println("Prescribed Medication: " + prescribedMedication.getMedicationName());
                            System.out.println("Status: " + prescribedMedication.getMedicationStatus());
                        }
                    }
                }
            }
            System.out.println("Do you want to update the diagnosis, treatment, prescribed medicine, or status?");
            String choice = sc.nextLine();
            switch(choice) {
                
                case "Diagnosis":
                    System.out.println("Enter the updated diagnosis: ");
                    String newDiagnosis = sc.nextLine();
                    for (AppointmentOutcome appointmentOutcome : appointmentOutcomesList) {
                        if (appointmentOutcome.getAppointmentID().equals(apptID)) {
                            appointmentOutcome.setDiagnosis(newDiagnosis);
                        }
                    }
                    System.out.println("Diagnosis updated!");
                    break;

                case "Treatment":
                    System.out.println("Enter the updated treatment: ");
                    String newTreatment = sc.nextLine();
                    for (AppointmentOutcome appointmentOutcome : appointmentOutcomesList) {
                        if (appointmentOutcome.getAppointmentID().equals(apptID)) {
                            appointmentOutcome.setServiceType(newTreatment);
                        }
                    }
                    System.out.println("Treatment updated!");
                    break;

                case "Prescribed Medicine":
                    System.out.println("Enter the desired medicine to update: ");
                    String oldMedicine = sc.nextLine();
                    System.out.println("Enter the new medicine: ");
                    String newMedicine = sc.nextLine();
                    for (PrescribedMedication prescribedMedication : prescribedMedicationList) {
                        if (prescribedMedication.getMedicationName().equals(oldMedicine)) {
                            prescribedMedication.setMedicationName(newMedicine);
                        }
                    }
                    System.out.println("Medicine updated!");
                    break;
                
                case "Status":
                System.out.println("Enter the desired medicine to update the status of: ");
                oldMedicine = sc.nextLine();
                System.out.println("Enter the new status: ");
                String newStatus = sc.nextLine();
                for (PrescribedMedication prescribedMedication : prescribedMedicationList) {
                    if (prescribedMedication.getMedicationName().equals(oldMedicine)) {
                        prescribedMedication.setMedicationStatus(newStatus);
                    }
                }
                System.out.println("Status updated!");
                break;
            }
        }
    } 

    public void viewPersonalSchedule() {
        for (int i = 0; i < 90; i+=3) {
            int day = 1;
            System.out.println("Day " + day + ": ");
            for (int k = 0; k < 3; k++) {
                if (i == 0) System.out.println("0900-1000: " + schedule.getSlots().get(i));
                else if (i == 1) System.out.println("1000-1100: " + schedule.getSlots().get(i+1));
                else System.out.println("1100-1200: " + schedule.getSlots().get(i + 2));
            }
            day++;
        }
    }

    public void setAvailability() {

    }

    public void recordAppointmentOutcome() {

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
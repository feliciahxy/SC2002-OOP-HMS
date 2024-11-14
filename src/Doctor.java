import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Doctor extends User {
    
    private Schedule schedule;
    private final List<Patient> patientList;
    private final List<Patient> personalPatientList;
    private final List<Appointment> appointmentList;
    private final List<AppointmentOutcome> appointmentOutcomesList;
    private final List<PrescribedMedication> prescribedMedicationList;

    private final ArrayList<Schedule> scheduleList;

    public Doctor(
        String id,
        String name,
        String role,
        String gender,
        int age,
        String password
    )
    {
        super(id, name, role, gender, age, password);

        scheduleList = new ArrayList<>();
        loadSchedulesFromCSV("../data/Schedule.csv");
        patientList = new ArrayList<>();
        loadPatientsFromCSV("../data/Patient_List.csv");
        personalPatientList = patientList;
        appointmentList = new ArrayList<>();
        loadAppointmentsFromCSV("../data/Appointment.csv");
        appointmentOutcomesList = new ArrayList<>();
        loadAppointmentOutcomesFromCSV("../data/AppointmentOutcome.csv");
        prescribedMedicationList = new ArrayList<>();
        loadPrescribedMedicationFromCSV(role);

        for (Schedule scheduleIteration : scheduleList) {
            if (schedule.getDoctorID().equals(this.getId())) {
                this.schedule = scheduleIteration;
            }
        }

        for (Patient patient : personalPatientList) {
            int isUnderCare = 0;
            for (Appointment appointment : appointmentList) {
                if (appointment.getPatientID().equals(patient.getId()) && appointment.getDoctorID().equals(id)) {
                    isUnderCare = 1;
                }
            }
            if ((isUnderCare) == 0) {
                personalPatientList.remove(patient);
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
                ArrayList<PrescribedMedication>uniquePrescribedMedicationList = new ArrayList<>();
                for (PrescribedMedication prescribedMedication : prescribedMedicationList) {
                    if (prescribedMedication.getAppointmentID().equals(appointmentID)) {
                        uniquePrescribedMedicationList.add(prescribedMedication);
                    }
                }
                AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointmentID, diagnosis, serviceType, date, uniquePrescribedMedicationList,notes);
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

    private void loadSchedulesFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            ArrayList<String> slots = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String doctorIDs = fields[0];
                for (int i = 1; i < 91; i++) {
                    String slot = fields[i];
                    slots.add(slot);
                }
                Schedule schedule = new Schedule(doctorIDs, slots);
                scheduleList.add(schedule);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // actual doctor functions

    public void viewPatientRecords(Patient patient) {

        if (personalPatientList.contains(patient)) {
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
        int day = 1;
        for (int i = 0; i < 90; i+=3) {
            System.out.println("Day " + day);
            for (int k = 0; k < 3; k++) {
                if (k == 0) System.out.println("0900-1000: " + schedule.getSlots().get(i + k));
                else if (k == 1) System.out.println("1000-1100: " + schedule.getSlots().get(i + k));
                else System.out.println("1100-1200: " + schedule.getSlots().get(i + k));
            }
            day++;
        }
    }

    public void setAvailability() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the day of the month you wish to update your availability on: ");
        int selectedDate = sc.nextInt();
        System.out.println("Enter the slot of the day you wish to block out: ");
        int selectedTime = sc.nextInt();
        int selectedSlot;
        if (selectedTime == 1) {
            selectedSlot = (selectedDate*3)-3;
        } else if (selectedTime == 2) {
            selectedSlot = (selectedDate*3)-2;
        } else {
            selectedSlot = (selectedDate*3)-1;
        }
        schedule.setSlot(selectedSlot, "N/F");
    }

    public void acceptOrDeclineAppointment() {
        int day = 1; // print all pending appointments
        for (int i = 0; i < 90; i+=3) {
            for (int k = 0; k < 3; k++) {
                if (k == 0 && String.valueOf(schedule.getSlots().get(i+k).charAt(6)).equals("0")) {
                    System.out.println("Day " + day + ": 0900-1000");
                }
                else if (k == 1 && String.valueOf(schedule.getSlots().get(i+k).charAt(6)).equals("0")) {
                    System.out.println("Day " + day + ": 1000-1100");
                }
                else if (k == 2 && String.valueOf(schedule.getSlots().get(i+k).charAt(6)).equals("0")) {
                    System.out.println("Day " + day + ": 1100-1200");
                }
            }
            day++;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Do you want to accept or decline an appointment?: ");
        String actionChoice = sc.nextLine();
        System.out.println("Which day do you want to " + actionChoice + " an appointment on?: ");
        int selectedDate = sc.nextInt();
        System.out.println("Which slot do you want to " + actionChoice + " an appointment on?: ");
        int selectedTime = sc.nextInt();
        int selectedSlot;
        if (selectedTime == 1) {
            selectedSlot = (selectedDate*3)-3;
        } else if (selectedTime == 2) {
            selectedSlot = (selectedDate*3)-2;
        } else {
            selectedSlot = (selectedDate*3)-1;
        }
        schedule.setSlot(selectedSlot, "N/F");

        if (actionChoice.equals("accept")) {
            String initialValue = schedule.getSlots().get(selectedSlot);
            StringBuilder modifiedValue = new StringBuilder(initialValue);
            modifiedValue.setCharAt(6, '1');
            schedule.setSlot(selectedSlot, modifiedValue.toString());
        } else if (actionChoice.equals("decline")) {
            schedule.setSlot(selectedSlot, "N/A");
        }

    }

    public void viewUpcomingAppointments() {
        int day = 1;
        for (int i = 0; i < 90; i+=3) {
            for (int k = 0; k < 3; k++) {
                if (k == 0 && String.valueOf(schedule.getSlots().get(i+k).charAt(6)).equals("1")) {
                    System.out.println("Day " + day + ": 0900-1000");
                }
                else if (k == 1 && String.valueOf(schedule.getSlots().get(i+k).charAt(6)).equals("1")) {
                    System.out.println("Day " + day + ": 1000-1100");
                }
                else if (k == 2 && String.valueOf(schedule.getSlots().get(i+k).charAt(6)).equals("1")) {
                    System.out.println("Day " + day + ": 1100-1200");
                }
            }
            day++;
        }
    }

    public void recordAppointmentOutcome() {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the patient ID: ");
        String pateintServed = sc.nextLine();
        System.out.println("Enter the day of the month: ");
        String dateServed = sc.nextLine();
        System.out.println("Enter the time: ");
        String timeServed = sc.nextLine();
        String appointmentIDServed = null;

        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientID().equals(pateintServed) && appointment.getDate().equals(dateServed) && appointment.getTime().equals(timeServed)) {
                appointment.setStatus("Completed");
                appointmentIDServed = appointment.getAppointmentID();
            }
        }

        System.out.println("What is the diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.println("What is the administered treatment: ");
        String serviceType = sc.nextLine();
        System.out.println("Any additional notes: ");
        String notes = sc.nextLine();

        ArrayList<PrescribedMedication> newPrescribedMedicationList = new ArrayList<>();
        String medicationName = null;
        while (medicationName != "NIL") {
            System.out.println("Enter the medication name dispensed: ");
            medicationName = sc.nextLine();
            if (medicationName != "NIL") {
                PrescribedMedication prescribedMedication = new PrescribedMedication(appointmentIDServed, medicationName, "dispensed");
                newPrescribedMedicationList.add(prescribedMedication);
                prescribedMedicationList.add(prescribedMedication);
            }
        }

        AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointmentIDServed, diagnosis, serviceType, dateServed, newPrescribedMedicationList, notes);
        appointmentOutcomesList.add(appointmentOutcome);
    }



    // writing to CSV



    public void writeAppointmentsToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/Appointment.csv"))) {
            bw.write("appointmentID,patientID,doctorID,date,time,stauts\n");
            for (Appointment appointment : appointmentList) {
                bw.write(appointment.getAppointmentID() + "," + appointment.getPatientID() + "," +
                        appointment.getDoctorID() + "," + appointment.getDate() + "," +
                        appointment.getTime() + "," + appointment.getStatus());
            }
        } catch (IOException e) {
            System.out.println("Error writing to Appointment file: " + e.getMessage());
        }
    }

    public void writeAppointmentOutcomesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/AppointmentOutcome.csv"))) {
            bw.write("appointmentID,diagnosis,serviceType,date,notes\n");
            for (AppointmentOutcome appointmentOutcome : appointmentOutcomesList) {
                bw.write(appointmentOutcome.getAppointmentID() + "," + appointmentOutcome.getDiagnosis() + "," +
                        appointmentOutcome.getServiceType() + "," + appointmentOutcome.getDate() + "," +
                        appointmentOutcome.getNotes());
            }
        } catch (IOException e) {
            System.out.println("Error writing to AppointmentOutcome file: " + e.getMessage());
        }
    }

    public void writePrescribedMedicationToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/PrescribedMedication.csv"))) {
            bw.write("appointmentID,medicine,status\n");
            for (PrescribedMedication prescribedMedication : prescribedMedicationList) {
                bw.write(prescribedMedication.getAppointmentID() + "," + prescribedMedication.getMedicationName() + "," +
                    prescribedMedication.getMedicationStatus());
            }
        } catch (IOException e) {
            System.out.println("Error writing to PrescribedMedication file: " + e.getMessage());
        }
    }

    public void writeSchedulesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/Schedule.csv"))) {
            bw.write("doctorIDs,1/1,2/1,3/1,1/2,2/2,3/2,1/3,2/3,3/3,1/4,2/4,3/4,1/5,2/5,3/5,1/6,2/6,3/6,1/7,2/7,3/7,1/8,2/8,3/8,1/9,2/9,3/9,1/10,2/10,3/10,1/11,2/11,3/11,1/12,2/12,3/12,1/13,2/13,3/13,1/14,2/14,3/14,1/15,2/15,3/15,1/16,2/16,3/16,1/17,2/17,3/17,1/18,2/18,3/18,1/19,2/19,3/19,1/20,2/20,3/20,1/21,2/21,3/21,1/22,2/22,3/22,1/23,2/23,3/23,1/24,2/24,3/24,1/25,2/25,3/25,1/26,2/26,3/26,1/27,2/27,3/27,1/28,2/28,3/28,1/29,2/29,3/29,1/30,2/30,3/30\n");
            for (Schedule schedule : scheduleList) {
                bw.write(schedule.getDoctorID() + "," + schedule.getSlots());
            }
        } catch (IOException e) {
            System.out.println("Error writing to Schedule file: " + e.getMessage());
        }
    }

    // public void writePatientsToCSV() {
    //     try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/AppointmentOutcome.csv"))) {
    //         bw.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Phone Number,Email,Password\n");
    //         for (Patient patient : patientList) {
    //             bw.write(patient.getPatientID() + "," + patient.getName() + "," +
    //                 patient.getDob() + "," + patient.getGender() + "," + patient.getBloodType() + "," + 
    //                 patient.getPhoneNumber() + "," + patient.getEmail() + "," + patient.getPassword());
    //         }
    //     } catch (IOException e) {
    //         System.out.println("Error writing to Patient file: " + e.getMessage());
    //     }
    // }

}
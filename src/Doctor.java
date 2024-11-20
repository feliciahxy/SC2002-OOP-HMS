import java.util.*;

/**
 * The {@code Doctor} class represents a doctor in the healthcare system, extending the {@code Staff} class.
 * It manages the doctor's schedule and provides methods to interact with patient records, update availability, 
 * handle appointments, and record appointment outcomes.
 * @author Heng Xin Yu Felicia
 * @version 1.0
 * @since 2024-10-08
 */
public class Doctor extends Staff {
    private Schedule schedule;

    /**
     * Constructs a {@code Doctor} instance with the given details.
     *
     * @param id the unique identifier of the doctor.
     * @param name the name of the doctor.
     * @param role the role of the doctor (e.g., "Doctor").
     * @param gender the gender of the doctor.
     * @param age the age of the doctor.
     * @param password the login password for the doctor.
     * @param schedule the doctor's schedule.
     */
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
    }

    /**
     * Allows the doctor to view the medical records of patients under their care.
     *
     * @param patientUsers the list of all patients.
     * @param appointmentOutcomes the list of appointment outcomes.
     * @param appointments the list of appointments.
     */
    public void viewPatientRecords(ArrayList<Patient> patientUsers, ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Appointment> appointments) {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> underCarePatientIDs = this.underCarePatientIDs();
        String inputPatientID;

        System.out.println("View Patient Records");
        String regex = "^P\\d{4}$";

        while (true) {
            System.out.print("Enter PatientID: ");
            inputPatientID = sc.nextLine();

            if (!inputPatientID.matches(regex)) {
                System.out.println("Invalid format. Patient ID must start with 'P' followed by 4 digits.");
                continue;
            }

            if (underCarePatientIDs.contains(inputPatientID)) {
                break; 
            } else {
                System.out.println("Patient not under your care.");
            }
        }

        Patient selectedPatient = null;

        for (Patient patient : patientUsers) {
            if (patient.getPatientID().equals(inputPatientID)) {
                selectedPatient = patient;
                break;
            }
        }

        selectedPatient.viewMedicalRecord(appointmentOutcomes, appointments);
    }

    /**
     * Allows the doctor to update the medical records of patients under their care.
     *
     * @param patientUsers the list of all patients.
     * @param appointmentOutcomes the list of appointment outcomes.
     * @param appointments the list of appointments.
     */
    public void updatePatientRecord(ArrayList<Patient> patientUsers, ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Appointment> appointments) {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> underCarePatientIDs = this.underCarePatientIDs();
        String inputPatientID;

        System.out.println("View Patient Records");

        String regex = "^P\\d{4}$";

        while (true) {
            System.out.print("Enter PatientID: ");
            inputPatientID = sc.nextLine();

            if (!inputPatientID.matches(regex)) {
                System.out.println("Invalid format. Patient ID must start with 'P' followed by 4 digits.");
                continue;
            }

            if (underCarePatientIDs.contains(inputPatientID)) {
                break; 
            } else {
                System.out.println("Patient not under your care.");
            }
        }

        System.out.print("Which appointment do you want to update? Enter the appointment ID: ");
        String apptID = sc.nextLine();

        boolean validID = false;
        ArrayList<String> patientAppointmentIDs = this.getPatientAppointmentIDs(inputPatientID, appointments);
        
        AppointmentOutcome selectedAppointmentOutcome = null;

        while (!validID) {
            for (AppointmentOutcome appointmentOutcome : appointmentOutcomes) {
                if (appointmentOutcome.getAppointmentID().equals(apptID) && patientAppointmentIDs.contains(apptID)) {
                    validID = true;
                    selectedAppointmentOutcome = appointmentOutcome;
                    System.out.println("\nAppointment found: " + appointmentOutcome);
                    break; 
                }
            }

            if (!validID) {
                System.out.println("Invalid Appointment ID.");
                System.out.print("Which appointment do you want to update? Enter the appointment ID: ");
                apptID = sc.nextLine(); 
            }
        }
        
        int choice = 0;
        while (choice != -1) { 
            System.out.println("\nWhat do you want to update");
            System.out.println("[1] Diagnosis");
            System.out.println("[2] Treatment");
            System.out.println("[3] Consultation Notes");
            System.out.println("[4] Prescribed Medicine");
            System.out.println("Input -1 to quit");
            System.out.print("Enter choice: ");

            try {
                choice = sc.nextInt();
                sc.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a valid number.");
                sc.nextLine(); 
                continue;  
            }

            switch(choice) {
                case 1:
                    System.out.print("Enter additional diagnosis: ");
                    String newDiagnosis = sc.nextLine();
                    selectedAppointmentOutcome.setDiagnosis(selectedAppointmentOutcome.getDiagnosis() + " " + newDiagnosis);
                    System.out.println("Diagnosis updated!");
                    break;

                case 2:
                    System.out.print("Enter additional service type: ");
                    String newServiceType = sc.nextLine();
                    selectedAppointmentOutcome.setServiceType(selectedAppointmentOutcome.getServiceType() + " " + newServiceType);
                    System.out.println("Service Type updated!");
                    break;

                case 3:
                    System.out.print("Enter additional consultation notes: ");
                    String newNotes = sc.nextLine();
                    selectedAppointmentOutcome.setNotes(selectedAppointmentOutcome.getNotes() + " " + newNotes);
                    System.out.println("Consultation Notes updated!");
                    break;

                case 4:
                    System.out.print("Enter additional prescription: ");
                    String newMedicine = sc.nextLine();
                    PrescribedMedication newPrescription = new PrescribedMedication(apptID, newMedicine, "pending");
                    selectedAppointmentOutcome.getPrescribedMedicationList().add(newPrescription);
                    System.out.println("Prescription updated!");
                    break;
                case -1:
                    break;
                default:
                    System.out.println("Invalid Input. Please enter a valid number.");
            }
        }
    }

    /**
     * Displays the doctor's personal schedule for the current month.
     */
    public void viewPersonalSchedule() {
        ArrayList<String> slots = this.schedule.getSlots();
        System.out.println("\nFormat Explanation \nPatientID-0 means pending, PatientID-1 means confirmed");
        System.out.println("N/A means the slot is free.");
        System.out.println("N/F means the slot is not free.");
        System.out.println("\nNovember Schedule");
    
        System.out.printf("%-10s %-15s %-15s %-15s\n", "Day", "0900-1000", "1000-1100", "1100-1200");
    
        for (int i = 0; i < slots.size(); i += 3) {
            int day = (i / 3) + 1; 
            System.out.printf("%-10d %-15s %-15s %-15s\n", day, 
                              slots.get(i), slots.get(i + 1), slots.get(i + 2));
        }
    }

    /**
     * Allows the doctor to set their availability for specific dates and times.
     */
    public void setAvailability() {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> slots = this.schedule.getSlots();

        int selectedDate = -1;
        while (true) {
            try {
                System.out.println("\nUpdate Availability");
                System.out.print("Enter date: ");
                selectedDate = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input. Please enter a valid date.");
            }
        }

        int selectedSlot = -1;
        while (true) {
            try {
                System.out.println("Slot 1: 0900-1000, Slot 2: 1000-1100, Slot 3: 1100-1200");
                System.out.print("Enter slot: ");
                selectedSlot = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid input. Please enter a valid slot.");
            }
        }

        int slotIndex = (selectedDate - 1) * 3 + selectedSlot - 1;
        System.out.println("Current Arrangement: " + slots.get(slotIndex));

        if (!slots.get(slotIndex).equals("N/A") && ! slots.get(slotIndex).equals("N/F")) {
            System.out.println("There is an ongoing appointment for this slot. Unable to set availability.");
            return;
        }

        System.out.print("\nAre you available for this slot? (yes/no): ");
        String response = sc.nextLine().trim().toLowerCase(); 
        
        while (!(response.equals("yes") || response.equals("no"))) {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            System.out.print("\nAre you available for this slot? (yes/no): ");
            response = sc.nextLine().trim().toLowerCase();
        }
        
        if (response.equals("yes")) {
            schedule.setSlot(slotIndex, "N/A");
        } else if (response.equals("no")) {
            schedule.setSlot(slotIndex, "N/F");
        }
        System.out.println("Availability Updated Successfully!");
    }

    /**
     * Allows the doctor to accept or decline pending appointment requests.
     *
     * @param appointments the list of all appointments.
     * @param doctor the doctor managing the appointments.
     * @param notifications the list of notifications to update.
     */
    public void acceptOrDeclineAppointment(ArrayList<Appointment> appointments, Doctor doctor, ArrayList<Notification> notifications) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> slots = this.schedule.getSlots();
        int count = 1;

        ArrayList<Integer> slotIndexes = new ArrayList<>();

        System.out.println("\nPending Appointments");
        for (int i = 0; i < 90; i ++) {
            int day = i / 3 + 1;
            int daySlot = i % 3 + 1;
            String time = Schedule.slotToTime(daySlot);

            if (slots.get(i).endsWith("-0")) {
                System.out.println("[" + count + "] " + day + " November " + time + " " + slots.get(i).substring(0, 5));
                slotIndexes.add(i);
                count++;
            }
        }

        int choice = -1;
        while (true) {
            if (count == 1) {
                System.out.print("NIL\n");
                return;
            }
            System.out.print("Enter the pending appointment you would like to accept or decline (1-" + String.valueOf(count - 1) + "): ");
            try {
                choice = sc.nextInt();
                if (choice >= 1 && choice <= count - 1) {
                    break;
                } else {
                    System.out.println("Invalid Input. Try Again.");
                }
            } catch (InputMismatchException e) {
                sc.next(); 
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        

        int input = -1;
        while (true) {
            System.out.println("[1] Accept");
            System.out.println("[2] Decline");
            System.out.print("Enter choice: ");
            try {
                input = sc.nextInt();
                if (input == 1 || input == 2) break;
                else System.out.println("Invalid Input. Try Again.");
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        

        int slotIndex = slotIndexes.get(choice - 1);
        int date = slotIndex / 3 + 1;
        int slot = slotIndex % 3 + 1;
        String formattedDate = String.format("%02d", date);
        String time = Schedule.slotToTime(slot);
        
        Appointment selectedAppointment = null;
        for (Appointment appointment : appointments) {
            if (appointment.getDate().equals(formattedDate) && appointment.getTime().equals(time) && appointment.getDoctorID().equals(this.getId())) {
                selectedAppointment = appointment;
                break;
            }
        }

        if (input == 1) {
            slots.set(slotIndex, slots.get(slotIndex).substring(0,5) + "-1");
            selectedAppointment.setStatus("confirmed");
            System.out.println("Appointment confirmed successfully!");
            AppointmentNotificationForPatientCreator notificationWhenDoctorConfirms = new NotificationWhenDoctorConfirms();
            String message = notificationWhenDoctorConfirms.createMessage(selectedAppointment, doctor);
            String notificationID = NotificationIDGenerator.generateNotificationID(notifications);
            notifications.add(NotificationBuilder.buildNotification(notificationID, selectedAppointment.getPatientID(), message));
        }
        else if (input == 2) {
            slots.set(slotIndex, "N/A");
            selectedAppointment.setStatus("cancelled");
            System.out.println("Appointment cancelled successfully!");
            AppointmentNotificationForPatientCreator notificationWhenDoctorCancels = new NotificationWhenDoctorCancels();
            String message = notificationWhenDoctorCancels.createMessage(selectedAppointment, doctor);
            String notificationID = NotificationIDGenerator.generateNotificationID(notifications);
            notifications.add(NotificationBuilder.buildNotification(notificationID, selectedAppointment.getPatientID(), message));
        } 
    }

    /**
     * Displays the doctor's upcoming confirmed appointments.
     */
    public void viewUpcomingAppointments() {
        System.out.println("\nUpcoming confirmed Appointments");
        ArrayList<String> slots = this.schedule.getSlots();

        int count = 1;
        for (int i = 0; i < 90; i ++) {
            int day = i / 3 + 1;
            int daySlot = i % 3 + 1;
            String time = Schedule.slotToTime(daySlot);

            if (slots.get(i).endsWith("-1")) {
                System.out.println("[" + count + "] " + day + " November " + time + " " + "PatientID: "+ slots.get(i).substring(0, 5));
                count++;
            }
        }

        if (count == 1) System.out.println("NIL\n");
    }

    /**
     * Records the outcome of an appointment, including diagnosis, treatment, consultation notes, and prescribed medications.
     *
     * @param appointments the list of all appointments.
     * @param appointmentOutcomes the list of appointment outcomes to update.
     */
    public void recordAppointmentOutcome(ArrayList<Appointment> appointments, ArrayList<AppointmentOutcome> appointmentOutcomes) {

        Scanner sc = new Scanner(System.in);

        String appointmentID;
        while (true) {
            System.out.print("The format should be AP followed by 4 digits (e.g., AP0001).\nEnter AppointmentID: ");
            appointmentID = sc.nextLine();
            if (Appointment.isValidAppointmentID(appointmentID)) {
                if (!Appointment.inAppointments(appointments, appointmentID)) {
                    System.out.print("Appointment not found.");
                    continue;
                }
                if (Appointment.doctorCanWriteOutcome(appointments, appointmentID, this.getId())) {
                    break;
                }
                else {
                    System.out.print("You do not have access to this AppointmentID.");
                }
            }
            else {
                System.out.println("Invalid Input Format.");
            }
        }

        Appointment selectedAppointment = null;

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                selectedAppointment = appointment;
                break;
            }
        }

        selectedAppointment.setStatus("completed");
        int date = Integer.parseInt(selectedAppointment.getDate());
        int slot = Appointment.timeToSlot(selectedAppointment.getTime());
        int slotIndex = (date -  1) * 3 + slot - 1;
        this.schedule.getSlots().set(slotIndex, "P1001-2");

        System.out.print("What is the diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.print("What is the service type: ");
        String serviceType = sc.nextLine();
        System.out.print("Any additional notes: ");
        String notes = sc.nextLine();

        String medicine = "";
        ArrayList<PrescribedMedication> allPrescribedMedications = new ArrayList<>();
        while (!medicine.equals("-1")) {
            System.out.println("Input -1 to quit.");
            System.out.print("Enter medication given: ");
            medicine = sc.nextLine();
            if (medicine.equals("-1")) break;
            PrescribedMedication prescribedMedication = new PrescribedMedication(appointmentID, medicine, "pending");
            allPrescribedMedications.add(prescribedMedication);
        }

        AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointmentID, diagnosis, serviceType, selectedAppointment.getDate(), allPrescribedMedications, notes);
        appointmentOutcomes.add(appointmentOutcome);

        System.out.println();
        System.out.println("The following appointment outcome has been added successfully.");
        System.out.println(appointmentOutcome);
    }

    /**
     * Retrieves the list of patient IDs under the doctor's care based on the doctor's schedule.
     *
     * @return the list of patient IDs.
     */
    public ArrayList<String> underCarePatientIDs() {
        ArrayList<String> underCarePatientIDs = new ArrayList<>();
        ArrayList<String> slots = this.schedule.getSlots();
        for (String slot : slots) {
            if (slot.contains("P")) {
                underCarePatientIDs.add(slot.substring(0, 5));
            }
        }
        return underCarePatientIDs;
    }

    /**
     * Retrieves the list of appointment IDs for a specific patient.
     *
     * @param patientID the unique identifier of the patient.
     * @param appointments the list of all appointments.
     * @return the list of appointment IDs for the patient.
     */
    public ArrayList<String> getPatientAppointmentIDs(String patientID, ArrayList<Appointment> appointments) {
        ArrayList<String> getPatientAppointmentIDs = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID)) {
                getPatientAppointmentIDs.add(appointment.getAppointmentID());
            }
        }

        return getPatientAppointmentIDs;
    }
}
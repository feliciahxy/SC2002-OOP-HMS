import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a patient in the hospital management system.
 * Patients can view their medical records, schedule appointments, 
 * and update personal information.
 */
public class Patient extends User {
    private final String patientID;
    private final String name;
    private final LocalDate dob;
    private final String gender;
    private String phoneNumber;
    private String email;
    private final String bloodType;
    private final List<String> pastDiagnosesAndTreatments;

    /**
     * Constructs a Patient object.
     *
     * @param patientID   the unique ID of the patient.
     * @param name        the name of the patient.
     * @param dob         the date of birth of the patient.
     * @param gender      the gender of the patient.
     * @param bloodType   the blood type of the patient.
     * @param phoneNumber the phone number of the patient.
     * @param email       the email of the patient.
     * @param password    the password for the patient's account.
     */
    public Patient(String patientID, String name, LocalDate dob, String gender, String bloodType, String phoneNumber, String email, String password) {
        super(patientID, name, "Patient", gender, 0, password);
        this.patientID = patientID;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        this.bloodType = bloodType;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.pastDiagnosesAndTreatments = new ArrayList<>();
    }

    /**
     * Displays the patient's medical record, including past diagnoses and treatments.
     *
     * @param appointmentOutcomes the list of appointment outcomes.
     * @param appointments        the list of appointments.
     */
    public void viewMedicalRecord(ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Appointment> appointments) {
        System.out.println("\nPatient ID: " + patientID);
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Gender: " + gender);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Past Diagnoses and Treatments");
        AppointmentOutcome.displayAppointmentHistory(patientID, appointmentOutcomes, appointments);
    }

    /**
     * Validates the format of a phone number.
     *
     * @param phoneNumber the phone number to validate.
     * @return {@code true} if the phone number is valid; {@code false} otherwise.
     *         A valid phone number must start with 6, 8, or 9 and must be 8 digits long.
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^[689]\\d{7}$"); //validator for phone no. format in accordance to starting digit or 6,8, or 9, and length of no. to be 8
    }

    /**
     * Validates the format of an email address.
     *
     * @param email the email address to validate.
     * @return {@code true} if the email address is valid; {@code false} otherwise.
     *         A valid email address must follow the general pattern: 
     *         <local-part>@<domain>.<TLD>, where the local part may include letters, 
     *         digits, underscores, dots, and special characters.
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"; //validator for email format
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Updates the patient's personal information, including phone number and email.
     *
     * @param newPhoneNumber the new phone number.
     * @param newEmail       the new email.
     */
    public void updatePersonalInfo(String newPhoneNumber, String newEmail) {
        Scanner sc = new Scanner(System.in);
    
        while (true) {
            boolean isPhoneNumberValid = isValidPhoneNumber(newPhoneNumber);
            if (isPhoneNumberValid) {
                break; 
            } else {
                System.out.print("Invalid phone number format. Please enter a valid phone number: ");
                newPhoneNumber = sc.nextLine(); 
            }
        }
    
        // Loop for email validation
        while (true) {
            boolean isEmailValid = isValidEmail(newEmail);
            if (isEmailValid) {
                break; 
            } else {
                System.out.print("Invalid email format. Please enter a valid email: ");
                newEmail = sc.nextLine();
            }
        }
    
        this.phoneNumber = newPhoneNumber;
        this.email = newEmail;
        updatePatientInfoInCSV();
        System.out.println("Personal information updated successfully.");
    }

    /**
     * Updates the patient's information in the CSV file.
     */
    private void updatePatientInfoInCSV() {
        String filePath = "../data/Patient_List.csv";
        
        List<String> lines = new ArrayList<>();
        String line;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(patientID)) {
                    values[5] = phoneNumber;
                    values[6] = email;
                    line = String.join(",", values);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String outputLine : lines) {
                writer.write(outputLine);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
        }
    }

    /**
     * Displays the patient's pending and confirmed schedules.
     *
     * @param patientID   the patient's ID.
     * @param schedules   the list of schedules.
     * @param doctors     the list of doctors.
     */
    public void displaySchedules(String patientID, ArrayList<Schedule> schedules, ArrayList<Doctor> doctors) {
        Map<String, ArrayList<Integer>> pendingAppointmentsByDoctor = Schedule.getPendingAppointments(patientID, schedules);
        Map<String, ArrayList<Integer>> confirmedAppointmentsByDoctor = Schedule.getConfirmedAppointments(patientID, schedules);

        if (pendingAppointmentsByDoctor.isEmpty() && confirmedAppointmentsByDoctor.isEmpty()) {
            System.out.println("You have no appointment.");
            return; 
        }

        Schedule.displayPendingAppointment(doctors, pendingAppointmentsByDoctor);
        Schedule.displayConfirmedAppointment(doctors, confirmedAppointmentsByDoctor);
    }

    /**
     * Reschedules an existing appointment.
     *
     * @param patient         the patient rescheduling the appointment.
     * @param appointmentList the list of appointments.
     * @param doctors         the list of doctors.
     * @param schedules       the list of schedules.
     * @param notifications   the list of notifications.
     */
    public void rescheduleAppointment(Patient patient, ArrayList<Appointment> appointmentList, ArrayList<Doctor> doctors, ArrayList<Schedule> schedules, ArrayList<Notification> notifications) {
        Scanner sc = new Scanner(System.in);

        String appointmentID;
        System.out.print("The format should be AP followed by 4 digits (e.g., AP0001).\nEnter AppointmentID to reschedule: ");
        appointmentID = sc.nextLine();
        if (Appointment.isValidAppointmentID(appointmentID)) {
            if (!Appointment.inAppointments(appointmentList, appointmentID)) {
                System.out.println("Appointment not found. Try Again: ");
            }
            if (Appointment.belongToPatient(appointmentList, appointmentID, patient.getPatientID())) {
                if (!Appointment.canReschedule(appointmentList, appointmentID)) {
                    System.out.println("This appointment cannot be rescheduled.");
                }
            }
            else {
                System.out.print("You do not have access to this AppointmentID. Try Again: ");
            }
        }
        else {
            System.out.println("Invalid Input Format.");
        }
    
        Appointment inputAppointment = null;
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                inputAppointment = appointment;
                break;
            }
        }
        if (inputAppointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        Doctor selectedDoctor = null;
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(inputAppointment.getDoctorID())) {
                selectedDoctor = doctor;
                break;
            }
        }

        String doctorID = inputAppointment.getDoctorID();
        String doctorName = selectedDoctor.getName();
        System.out.println("\nAppointment (" + inputAppointment.getStatus() +") selected with: Dr " + doctorName);
        System.out.println("Timing: " + inputAppointment.getDate() + " November " + inputAppointment.getTime());
        int originalSlot = Appointment.timeToSlot(inputAppointment.getTime());
        int originalDate = Integer.parseInt(inputAppointment.getDate());

        ArrayList<Integer> availableDates = Schedule.getAvailableDates(doctorID, schedules);
        Schedule.displayAvailableDates(availableDates);

        int dateChoice;
        while (true) {
            try {
                System.out.print("Select a New Date: ");
                dateChoice = sc.nextInt();
                sc.nextLine(); 
        
                if (availableDates.contains(dateChoice)) {
                    break; 
                } else {
                    System.out.println("Invalid date choice. Please choose from the available dates.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); 
            }
        }

        int slotChoice;
        ArrayList<Integer> availableSlots = Schedule.displayAvailableSlotsForDate(doctorID, dateChoice, schedules);
        while (true) {
            try {
                System.out.print("Select a Slot: ");
                slotChoice = sc.nextInt();
                sc.nextLine(); 
        
                if (availableSlots.contains(slotChoice)) {
                    break;
                } else {
                    System.out.println("Invalid slot choice. Please choose from the available slots.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine(); 
            }
        }

        int slot = Schedule.addSchedule(patient.getPatientID(), doctorID, dateChoice, slotChoice, schedules);
        if (slot == -1) System.out.println("An error has occured.");
                    
        int originalSlotIndex = (originalDate - 1)* 3 + originalSlot - 1;
        Schedule.cancelSlot(schedules, originalSlotIndex, doctorID);

        Appointment.changeAppointment(appointmentList, appointmentID, dateChoice, slot);
        System.out.println("Appointment with Dr " + doctorName + " successfully rescheduled to " + inputAppointment.getDate() + " November " + inputAppointment.getTime());
        
        AppointmentNotificationForDoctorCreator notificationWhenPatientReschedules = new NotificationWhenPatientReschedules();
        String message = notificationWhenPatientReschedules.createMessage(inputAppointment, patient);
        String notificationID = NotificationIDGenerator.generateNotificationID(notifications);
        notifications.add(NotificationBuilder.buildNotification(notificationID, selectedDoctor.getId(), message));
    }


    /**
     * Cancels an existing appointment.
     *
     * @param patient         the patient canceling the appointment.
     * @param appointmentList the list of appointments.
     * @param schedules       the list of schedules.
     * @param doctors         the list of doctors.
     * @param notifications   the list of notifications.
     */

    public void cancelAppointment(Patient patient, ArrayList<Appointment> appointmentList, ArrayList<Schedule> schedules, ArrayList<Doctor> doctors, ArrayList<Notification> notifications) {
        Scanner sc = new Scanner(System.in);

        String appointmentID;
        System.out.print("The format should be AP followed by 4 digits (e.g., AP0001).\nEnter AppointmentID to cancel: ");
        appointmentID = sc.nextLine();
        if (Appointment.isValidAppointmentID(appointmentID)) {
            if (!Appointment.inAppointments(appointmentList, appointmentID)) {
                System.out.print("Appointment not found. Try Again: ");
            }
            if (!Appointment.belongToPatient(appointmentList, appointmentID, patient.getPatientID())) {
                System.out.print("You do not have access to this AppointmentID. Try Again: ");
            }
        }
        else {
            System.out.println("Invalid Input Format.");
        }
    

        Appointment inputAppointment = null;
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                inputAppointment = appointment;
                break;
            }
        }
        if (inputAppointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        Doctor selectedDoctor = null;
        for (Doctor doctor : doctors) {
            if (doctor.getId().equals(inputAppointment.getDoctorID())) {
                selectedDoctor = doctor;
                break;
            }
        }

        Appointment.cancelAppointment(appointmentList, appointmentID);

        int date = Integer.parseInt(inputAppointment.getDate());
        int slot = Appointment.timeToSlot(inputAppointment.getTime());
        int slotIndex = (date - 1) * 3 + slot - 1;
        Schedule.cancelSlot(schedules, slotIndex, inputAppointment.getDoctorID());

        String doctorName = selectedDoctor.getName();
        System.out.println("Appointment with Dr " + doctorName + " (" + inputAppointment.getDate() + " November " + inputAppointment.getTime() + ") successfully cancelled."); 

        AppointmentNotificationForDoctorCreator notificationWhenPatientCancels = new NotificationWhenPatientCancels();
        String message = notificationWhenPatientCancels.createMessage(inputAppointment, patient);
        String notificationID = NotificationIDGenerator.generateNotificationID(notifications);
        notifications.add(NotificationBuilder.buildNotification(notificationID, selectedDoctor.getId(), message));
    }

    /**
     * Schedules a new appointment for the patient.
     *
     * @param doctors         the list of doctors.
     * @param schedules       the list of schedules.
     * @param appointmentList the list of appointments.
     * @param patient         the patient scheduling the appointment.
     * @param notifications   the list of notifications.
     */
    public void scheduleAppointment(ArrayList<Doctor> doctors, ArrayList<Schedule> schedules, ArrayList<Appointment> appointmentList, Patient patient, ArrayList<Notification> notifications){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSchedule An Appointment");
        ArrayList<String> doctorNames = getDoctorNames(doctors);
        displayDoctorNames(doctorNames);
        int numDoctors = doctorNames.size();

        int doctorChoice = -1;
        while (doctorChoice < 1 || doctorChoice > numDoctors) {
            System.out.print("Select a Doctor (1 - " + numDoctors + "): ");
            try {
                doctorChoice = scanner.nextInt();
                if (doctorChoice < 1 || doctorChoice > numDoctors) {
                    System.out.println("Invalid input. Try Again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try Again.");
                scanner.nextLine();
            }
        }
        
        String doctorName = doctorNames.get(doctorChoice - 1);
        Doctor selectedDoctor = null;
        for (Doctor doctor : doctors) {
            if (doctor.getName().equals(doctorName)) {
                selectedDoctor = doctor;
                break;
            }
        }

        String doctorID = selectedDoctor.getId();
        ArrayList<Integer> availableDates = Schedule.getAvailableDates(doctorID, schedules);
        Schedule.displayAvailableDates(availableDates);

        int dateChoice;
        while (true) {
            System.out.print("Select a Date: ");
            try {
                dateChoice = scanner.nextInt();
                if (availableDates.contains(dateChoice)) {
                    break;
                } else {
                    System.out.println("Invalid date choice. Please choose from the available dates.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid date.");
                scanner.nextLine();
            }
        }
        

        int slotChoice;
        ArrayList<Integer> availableSlots = Schedule.displayAvailableSlotsForDate(doctorID, dateChoice, schedules);
        while (true) {
            System.out.print("Select a Slot: ");
            try {
                slotChoice = scanner.nextInt();
                if (availableSlots.contains(slotChoice)) {
                    break;
                } else {
                    System.out.println("Invalid slot choice. Please choose from the available slots.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid slot.");
                scanner.nextLine();
            }
        }
        

        int slot = Schedule.addSchedule(patient.getPatientID(), doctorID, dateChoice, slotChoice, schedules);
        if (slot == -1) System.out.println("An error has occured.");
        Appointment appointment = Appointment.createAppointment(appointmentList, patient.getPatientID(), doctorID, dateChoice, slot);

        System.out.println("Appointment (" + appointment.getAppointmentID()+ ") successfully scheduled with Dr " + doctorName + " on " + dateChoice + " November from " + Schedule.slotToTime(slot));

        AppointmentNotificationForDoctorCreator notificationWhenPatientSchedules = new NotificationWhenPatientSchedules();
        String message = notificationWhenPatientSchedules.createMessage(appointment, patient);
        String notificationID = NotificationIDGenerator.generateNotificationID(notifications);
        notifications.add(NotificationBuilder.buildNotification(notificationID, doctorID, message));
    }

    /**
     * Views available slots for appointments.
     *
     * @param doctors   the list of doctors.
     * @param schedules the list of schedules.
     */
    public void viewAvailableSlots(ArrayList<Doctor> doctors, ArrayList<Schedule> schedules) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nView Available Slots");
        ArrayList<String> doctorNames = getDoctorNames(doctors);
        displayDoctorNames(doctorNames);
        int numDoctors = doctorNames.size();

        int doctorChoice = -1;
        while (doctorChoice < 1 || doctorChoice > numDoctors) { 
            System.out.print("Select a Doctor (1 - " + numDoctors + "): ");
            try {
                if (scanner.hasNextInt()) {
                    doctorChoice = scanner.nextInt();
                } else {
                    scanner.next();
                    System.out.println("Invalid input. Try Again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Try Again.");
                scanner.nextLine();
            }
        }
        
        String doctorName = doctorNames.get(doctorChoice - 1);
        Doctor selectedDoctor = null;
        for (Doctor doctor : doctors) {
            if (doctor.getName().equals(doctorName)) {
                selectedDoctor = doctor;
                break;
            }
        }

        String doctorID = selectedDoctor.getId();
        ArrayList<Integer> availableDates = Schedule.getAvailableDates(doctorID, schedules);
        Schedule.displayAvailableDates(availableDates);

        int dateChoice;
        while (true) {
            System.out.print("Select a Date: ");
            try {
                dateChoice = scanner.nextInt();
                if (availableDates.contains(dateChoice)) {
                    break;
                } else {
                    System.out.println("Invalid date choice. Please choose from the available dates.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
        

        ArrayList<Integer> availableSlots = Schedule.displayAvailableSlotsForDate(doctorID, dateChoice, schedules);
    }

    /**
     * Retrieves a list of doctor names from a list of Doctor objects.
     *
     * @param doctors the list of Doctor objects.
     * @return an ArrayList containing the names of all doctors in the input list.
     */
    public static ArrayList<String> getDoctorNames (ArrayList<Doctor> doctors) {
        ArrayList<String> doctorNames = new ArrayList<>();
        for (Doctor doctor : doctors) {
                doctorNames.add(doctor.getName());
        }
        return doctorNames;
    }

    /**
     * Displays a numbered list of doctor names.
     *
     * @param doctorNames an ArrayList containing doctor names.
     */
    public static void displayDoctorNames(ArrayList<String> doctorNames) {
        System.out.println("List of Doctors:");
        for (int i = 0; i < doctorNames.size(); i++) {
            System.out.println("[" + (i + 1) + "] Dr " + doctorNames.get(i)); 
        }
    }

    /**
     * Retrieves a doctor's ID based on their name.
     *
     * @param doctors    the list of Doctor objects.
     * @param doctorName the name of the doctor.
     * @return the ID of the doctor if found, or {@code null} if no matching doctor is found.
     */
    public static String getDoctorIDFromName (ArrayList<Doctor> doctors, String doctorName) {
        for (Doctor doctor : doctors) {
            if (doctorName.equalsIgnoreCase(doctor.getName())) {
                return doctor.getId();
            }
        }
        return null;
    }

    /**
     * Retrieves a doctor's name based on their ID.
     *
     * @param doctors  the list of Doctor objects.
     * @param doctorID the ID of the doctor.
     * @return the name of the doctor if found, or {@code null} if no matching doctor is found.
     */
    public static String getDoctorNameFromID (ArrayList<Doctor> doctors, String doctorID) {
        for (Doctor doctor : doctors) {
            if (doctorID.equalsIgnoreCase(doctor.getId())) {
                return doctor.getName();
            }
        }
        return null;
    }

    /**
     * Adds a new diagnosis or treatment to the patient's medical history.
     *
     * @param diagnosisAndTreatment the diagnosis or treatment to add.
     */
    public void addDiagnosisAndTreatment(String diagnosisAndTreatment) {
        this.pastDiagnosesAndTreatments.add(diagnosisAndTreatment);
    }

    /**
     * Retrieves the patient's date of birth.
     *
     * @return the patient's date of birth as a {@code LocalDate}.
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Retrieves the patient's blood type.
    *
    * @return the patient's blood type as a {@code String}.
    */
    public String getBloodType() {
        return bloodType;
    }

    /**
     * Retrieves the patient's email address.
     *
     * @return the patient's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the patient's phone number.
     *
     * @return the patient's phone number.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Retrieves the patient's ID.
     *
     * @return the patient's ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Updates the patient's phone number if the format is valid.
     *
     * @param phoneNumber the new phone number to update.
     */
    public void setPhoneNumber(String phoneNumber) {
        if (isValidPhoneNumber(phoneNumber)) {
            this.phoneNumber = phoneNumber;
            System.out.println("Phone number updated sucessfully.");
        } else {
            System.out.println("Invalid phone number format.");
        }
    }

    /**
     * Updates the patient's email address if the format is valid.
     *
     * @param email the new email address to update.
     */
    public void setEmail(String email) {
        if (isValidEmail(email)) {
            this.email = email;
            System.out.println("Email updated sucessfully.");
        } else {
            System.out.println("Invalid email format.");
        }
    }
}

import java.util.*;
/**
 * Main class that handles the flow of the system based on user input.
 * It manages login, menu navigation, and role-specific functionalities for users (Doctor, Pharmacist, Administrator, Patient).
 */
public class Main {
    /**
     * Main method that serves as the entry point for the program.
     * It handles user login, role validation, and directs the user to the respective menu based on their role.
     * It also manages data persistence when the program is shut down.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ensure that there is a Schedule.csv
        ScheduleInitializer.main(args);

        ScheduleManager scheduleManager = new ScheduleManager();
        ArrayList<Schedule> schedules = scheduleManager.getSchedules();

        UserManager userManager = new UserManager(schedules);
        StaffManager staffManager = new StaffManager();
        staffManager.categorizeStaff(userManager.getStaffUsers());
        
        AppointmentManager appointmentManager = new AppointmentManager();
        AppointmentOutcomeManager appointmentOutcomeManager = new AppointmentOutcomeManager();

        MedicationManager medicationManager = new MedicationManager();
        Inventory inventory = new Inventory(medicationManager.getMedications());
        ReplenishmentRequestManager replenishmentRequestManager = new ReplenishmentRequestManager();

        NotificationManager notificationManager = new NotificationManager();
        NotificationReader csvNotificationReader = new CSVNotificationReader();
        for (Notification notification : csvNotificationReader.readNotifications("../data/Notification.csv")) {
            notificationManager.addNotification(notification);
        }

        ArrayList<Patient> patients = userManager.getPatientUsers();
        ArrayList<Staff> staffs = userManager.getStaffUsers();
        ArrayList<Doctor> doctors = staffManager.getDoctors();
        ArrayList<Administrator> administrators = staffManager.getAdministrators();
        ArrayList<Pharmacist> pharmacists = staffManager.getPharmacists();
        ArrayList<AppointmentOutcome> appointmentOutcomes = appointmentOutcomeManager.getAppointmentOutcomes();
        ArrayList<Appointment> appointments = appointmentManager.getAppointmentList();
        ArrayList<ReplenishmentRequest> replenishmentRequest = replenishmentRequestManager.getReplenishmentRequests();
        ArrayList<Notification> notifications = notificationManager.getNotifications();

        System.out.print("Enter your ID: ");
        String userID = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        if (userManager.login(userID, password)) {
            String role = userManager.getRole(userID);
            if (role != null) {
                System.out.println("Login successful! Your role: " + role);

                if (userManager.isFirstLogin(userID)) {
                    boolean passwordChanged = false;
                    while (!passwordChanged) {
                        System.out.print("Do you want to change your password? (yes/no): ");
                        String changePasswordChoice = scanner.nextLine();
                        if (changePasswordChoice.equalsIgnoreCase("yes")) {
                            System.out.print("Enter new password: ");
                            String newPassword = scanner.nextLine();
                            passwordChanged = userManager.changePassword(userID, newPassword);

                            if (!passwordChanged) {
                                System.out.println("Please try again.");
                            }
                        } else {
                            break;
                        }
                    }
                }

                
                int firstNotification = 0;

                for (Notification notification : notifications) {
                    if (notification.getReceiverID().equals(userID) && notification.getStatus().equals("pending")) {
                        if (firstNotification == 0) {
                            System.out.println("\nLatest Notification(s)");
                            firstNotification = 1;
                        }
                        System.out.println(notification.getMessage());
                        notification.setStatus("sent");
                    }
                }

                switch (role) {
                    case "Doctor":
                        displayDoctorMenu(userID, staffManager, patients, appointmentOutcomes, appointments, notifications);
                        break;
                    case "Pharmacist":
                        inventory.notifyLowStock();
                        displayPharmacistMenu(userID, staffManager, appointmentOutcomes,inventory, replenishmentRequest);
                        break;
                    case "Administrator":
                        inventory.notifyLowStock();
                        inventory.notifyReplenishmentRequest(replenishmentRequest);
                        displayAdminMenu(userID, staffManager, appointments, appointmentOutcomes, inventory, replenishmentRequest, userManager, schedules);
                        break;
                    case "Patient":
                        displayPatientMenu(userManager, doctors, userID, schedules, appointments, appointmentOutcomes, notifications);
                        break;
                    default:
                        System.out.println("Role not recognized.");
                }
            } else {
                System.out.println("No user found with this ID.");
            }
        } else {
            System.out.println("Invalid login credentials.");
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            userManager.writeUsersToCSV();
            scheduleManager.writeSchedulesToCSV();
            appointmentManager.writeAppointmentsToCSV();
            appointmentOutcomeManager.writeAppointmentOutcomesToCSV();
            appointmentOutcomeManager.writePrescribedMedicationsToCSV();
            medicationManager.writeMedicationsToCSV();
            replenishmentRequestManager.writeReplenishmentRequestsToCSV();
            NotificationWriter csvNotificationWriter = new CSVNotificationWriter();
            csvNotificationWriter.writeNotifications("../data/Notification.csv", notifications);
        }));

        scanner.close();
    }

    /**
     * Displays the Doctor's menu and handles the interaction with the user.
     * The menu allows the doctor to view and update patient records, manage their schedule, and more.
     *
     * @param userID          the ID of the logged-in doctor
     * @param staffManager    the manager that handles the staff records
     * @param patientUsers    the list of patients
     * @param appointmentOutcomes the list of appointment outcomes
     * @param appointments    the list of appointments
     * @param notifications   the list of notifications
     */

    public static void displayDoctorMenu(String userID, StaffManager staffManager, ArrayList<Patient> patientUsers, ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Appointment> appointments, ArrayList<Notification> notifications) {
        Scanner scanner = new Scanner(System.in);
        Doctor doctor = staffManager.findDoctorByID(userID);
        int choice = -1;

        do {
            System.out.println("\nDoctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        doctor.viewPatientRecords(patientUsers, appointmentOutcomes, appointments);
                        break;
                    case 2:
                        doctor.updatePatientRecord(patientUsers, appointmentOutcomes, appointments);
                        break;
                    case 3:
                        doctor.viewPersonalSchedule();
                        break;
                    case 4:
                        doctor.setAvailability();
                        break;
                    case 5:
                        doctor.acceptOrDeclineAppointment(appointments, doctor, notifications);
                        break;
                    case 6:
                        doctor.viewUpcomingAppointments();
                        break;
                    case 7:
                        doctor.recordAppointmentOutcome(appointments, appointmentOutcomes);
                        break;
                    case 8:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        } while (choice != 8);
    }

    /**
     * Displays the Pharmacist's menu and handles the interaction with the user.
     * The menu allows the pharmacist to view and update appointment outcomes, manage the medication inventory, and handle replenishment requests.
     *
     * @param userID              the ID of the logged-in pharmacist
     * @param staffManager        the manager that handles the staff records
     * @param appointmentOutcomes the list of appointment outcomes
     * @param inventory           the medication inventory
     * @param replenishmentRequest the list of replenishment requests
     */
    public static void displayPharmacistMenu(String userID, StaffManager staffManager, ArrayList<AppointmentOutcome> appointmentOutcomes, Inventory inventory, ArrayList<ReplenishmentRequest> replenishmentRequest) {
        Scanner scanner = new Scanner(System.in);
        Pharmacist pharmacist = staffManager.findPharmacistByID(userID);
        int choice = -1;

        do {
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        pharmacist.viewAppointmentOutcomeRecord(appointmentOutcomes);
                        break;
                    case 2:
                        pharmacist.updatePrescription(appointmentOutcomes, inventory);
                        break;
                    case 3:
                        inventory.viewInventory();
                        break;
                    case 4:
                        pharmacist.requestReplenishment(replenishmentRequest, inventory);
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice, please enter a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        } while (choice != 5);
    }

     /**
     * Displays the Administrator's menu and handles the interaction with the user.
     * The menu allows the administrator to manage staff, patients, appointments, inventory, and replenishment requests.
     *
     * @param userID             the ID of the logged-in administrator
     * @param staffManager       the manager that handles the staff records
     * @param appointmentList    the list of appointments
     * @param appointmentOutcomes the list of appointment outcomes
     * @param inventory          the medication inventory
     * @param replenishmentRequest the list of replenishment requests
     * @param userManager        the manager that handles user accounts
     * @param schedules          the list of schedules
     */
    public static void displayAdminMenu(String userID, StaffManager staffManager, ArrayList<Appointment> appointmentList, ArrayList<AppointmentOutcome> appointmentOutcomes, Inventory inventory, ArrayList<ReplenishmentRequest> replenishmentRequest, UserManager userManager, ArrayList<Schedule> schedules) {

        Scanner scanner = new Scanner(System.in);
        Administrator administrator = staffManager.findAdministratorByID(userID);
        int choice = -1;

        do {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View and Manage Patients");
            System.out.println("3. View Appointment Details");
            System.out.println("4. View and Manage Medication Inventory");
            System.out.println("5. Approve Replenishment Requests");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            
            try {
                choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        administrator.manageStaff(staffManager, userManager, schedules);
                        break;
                    case 2:
                        administrator.managePatient(userManager);
                        break;
                    case 3:
                        Appointment.displayAppointments(appointmentList, appointmentOutcomes);
                        break;
                    case 4:
                        inventory.manageInventory();
                        break;
                    case 5:
                        inventory.approveReplenishmentRequest(replenishmentRequest);
                        break;
                    case 6:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                } 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        } while (choice != 6);
    }

    /**
     * Displays the Patient's menu and handles the interaction with the user.
     * The menu allows the patient to view medical records, update personal information, schedule or cancel appointments, and view past appointment outcomes.
     *
     * @param userManager      the manager that handles user accounts
     * @param doctors          the list of doctors available for appointments
     * @param userID           the ID of the logged-in patient
     * @param schedules        the list of schedules
     * @param appointments     the list of appointments
     * @param appointmentOutcomes the list of appointment outcomes
     * @param notifications    the list of notifications for the patient
     */
    public static void displayPatientMenu(UserManager userManager, ArrayList<Doctor> doctors, String userID, ArrayList<Schedule> schedules, ArrayList<Appointment> appointments, ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Notification> notifications) {
        Scanner scanner = new Scanner(System.in);
        Patient patient = userManager.findPatientByID(userID);
        int choice = -1;

        do {
            System.out.println("\nPatient Menu:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");
            System.out.print("Enter your choice: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            
                switch (choice) {
                    case 1:
                        patient.viewMedicalRecord(appointmentOutcomes, appointments);
                        break;
                    case 2:
                        System.out.print("Enter new phone number: ");
                        String newPhoneNumber = scanner.nextLine();
                        System.out.print("Enter new email: ");
                        String newEmail = scanner.nextLine();
                        patient.updatePersonalInfo(newPhoneNumber, newEmail);
                        break;
                    case 3:
                        patient.viewAvailableSlots(doctors, schedules);
                        break;
                    case 4:
                        patient.scheduleAppointment(doctors, schedules, appointments, patient, notifications);
                        break;
                    case 5:
                        patient.rescheduleAppointment(patient, appointments, doctors, schedules, notifications);
                        break;
                    case 6:
                        patient.cancelAppointment(patient, appointments, schedules, doctors, notifications);
                        break;
                    case 7:
                        patient.displaySchedules(patient.getPatientID(), schedules, doctors);
                        break;
                    case 8:
                        AppointmentOutcome.displayAppointmentHistory(patient.getPatientID(), appointmentOutcomes, appointments);
                        break;
                    case 9:
                        System.out.println("Logging out...");
                        break;
                    default:
                        System.out.println("Invalid choice, please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        } while (choice != 9);
    }
}
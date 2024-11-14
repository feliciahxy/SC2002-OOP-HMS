import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Ensure that there is a Schedule.csv
        ScheduleInitializer.main(args);

        ScheduleManager scheduleManager = new ScheduleManager();
        scheduleManager.loadSchedulesFromCSV("../data/Schedule.csv");

        UserManager userManager = new UserManager();
        StaffManager staffManager = new StaffManager();
        staffManager.categorizeStaff(userManager.getStaffUsers());
        
        AppointmentManager appointmentManager = new AppointmentManager();
        AppointmentOutcomeManager appointmentOutcomeManager = new AppointmentOutcomeManager();

        ArrayList<AppointmentOutcome> appointmentOutcomes = appointmentOutcomeManager.getAppointmentOutcomes();
        MedicationManager medicationManager = new MedicationManager();
        Inventory inventory = new Inventory(medicationManager.getMedications());
        ReplenishmentRequestManager replenishmentRequestManager = new ReplenishmentRequestManager();
        ArrayList<ReplenishmentRequest> replenishmentRequest = replenishmentRequestManager.getReplenishmentRequests();

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

                switch (role) {
                    case "Doctor":
                        displayDoctorMenu(userID, staffManager, userManager.getPatientUsers(), appointmentOutcomes, appointmentManager.getAppointmentList());
                        break;
                    case "Pharmacist":
                        displayPharmacistMenu(userID, staffManager, appointmentOutcomes,inventory, replenishmentRequest);
                        break;
                    case "Administrator":
                        displayAdminMenu(userID, staffManager, appointmentManager.getAppointmentList(), appointmentOutcomeManager.getAppointmentOutcomes(), inventory, replenishmentRequest, userManager);
                        break;
                    case "Patient":
                        displayPatientMenu(userManager, staffManager, userID, scheduleManager, appointmentManager, appointmentOutcomeManager);
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
        scanner.close();
    }

    public static void displayDoctorMenu(String userID, StaffManager staffManager, ArrayList<Patient> patientUsers, ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Appointment> appointments) {
        Scanner scanner = new Scanner(System.in);
        Doctor doctor = staffManager.findDoctorByID(userID);
        int choice;

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
                    doctor.acceptOrDeclineAppointment(appointments);
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
        } while (choice != 8);
    }

    public static void displayPharmacistMenu(String userID, StaffManager staffManager, ArrayList<AppointmentOutcome> appointmentOutcomes, Inventory inventory, ArrayList<ReplenishmentRequest> replenishmentRequest) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        Pharmacist pharmacist = staffManager.findPharmacistByID(userID);

        do {
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    pharmacist.viewAppointmentOutcomeRecord(appointmentOutcomes);
                    //implement logic to view appointment outcome record
                     
                    break;
                case 2:
                    pharmacist.updatePrescription(appointmentOutcomes, inventory);

                    //implement logic to update prescription status
                    break;
                case 3:
                    //implement logic to view medication inventory
                    inventory.viewInventory();
                    break;
                case 4:
                    //implement logic to submit replenishment request
                    pharmacist.requestReplenishment(replenishmentRequest, inventory);

                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    public static void displayAdminMenu(String userID, StaffManager staffManager, ArrayList<Appointment> appointmentList, ArrayList<AppointmentOutcome> appointmentOutcomes, Inventory inventory, ArrayList<ReplenishmentRequest> replenishmentRequest, UserManager userManager) {

        Scanner scanner = new Scanner(System.in);
        Administrator administrator = staffManager.findAdministratorByID(userID);
        int choice;

        do {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointment details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Administrator.manageStaff(staffManager, userManager);
                    break;
                case 2:
                    Appointment.displayAppointments(appointmentList, appointmentOutcomes);
                    break;
                case 3:
                    //implement logic to view and manage medication inventory
                    inventory.manageInventory();
                    break;
                case 4:
                    // implement logic to approve replenishment requests
                    inventory.approveReplenishmentRequest(replenishmentRequest);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    public static void displayPatientMenu(UserManager userManager, StaffManager staffManager, String userID, ScheduleManager scheduleManager, AppointmentManager appointmentManager, AppointmentOutcomeManager appointmentOutcomeManager) {
        Scanner scanner = new Scanner(System.in);
        Patient patient = userManager.findPatientByID(userID);
        int choice;

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
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    patient.viewMedicalRecord(appointmentOutcomeManager.getAppointmentOutcomes(), appointmentManager.getAppointmentList());
                    break;
                case 2:
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    patient.updatePersonalInfo(newPhoneNumber, newEmail);
                    userManager.writeUsersToCSV(); //updates csv also
                    break;
                case 3:
                    patient.viewAvailableSlots(staffManager.getDoctors(), scheduleManager.getSchedules());
                    break;
                case 4:
                    patient.scheduleAppointment(staffManager.getDoctors(), scheduleManager.getSchedules(), appointmentManager.getAppointmentList(), patient.getPatientID());
                    break;
                case 5:
                    patient.rescheduleAppointment(patient.getPatientID(), appointmentManager.getAppointmentList(), staffManager.getDoctors(), scheduleManager.getSchedules());
                    break;
                case 6:
                    patient.cancelAppointment(patient.getPatientID(), appointmentManager.getAppointmentList(), scheduleManager.getSchedules(), staffManager.getDoctors());
                    break;
                case 7:
                    patient.displaySchedules(patient.getPatientID(), scheduleManager.getSchedules(), staffManager.getDoctors());
                    break;
                case 8:
                    AppointmentOutcome.displayAppointmentHistory(patient.getPatientID(), appointmentOutcomeManager.getAppointmentOutcomes(), appointmentManager.getAppointmentList());
                    break;
                case 9:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 9);
    }
}
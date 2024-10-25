import java.util.*;

public class Main {
    private static Patient currentPatient;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter your ID: ");
        String userID = scanner.nextLine();
        
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        UserManager userManager = new UserManager();
        
        if (userManager.login(userID, password)) {
            String role = userManager.getRole(userID);
            if (role != null) {
                System.out.println("Login successful! Your role: " + role);
                
                if (userManager.isFirstLogin(userID)) {
                    System.out.print("Do you want to change your password? (yes/no): ");
                    String changePasswordChoice = scanner.nextLine();
                    if (changePasswordChoice.equalsIgnoreCase("yes")) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        userManager.changePassword(userID, newPassword);
                    }
                }

                switch (role) {
                    case "Doctor":
                        displayDoctorMenu();
                        break;
                    case "Pharmacist":
                        displayPharmacistMenu();
                        break;
                    case "Administrator":
                        displayAdminMenu();
                        break;
                    case "Patient":
                        displayPatientMenu(userManager, userID);
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

    public static void displayDoctorMenu() {
        Scanner scanner = new Scanner(System.in);
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
                    //implement logic to view patient medical records
                    break;
                case 2:
                    //implement logic to update patient medical records
                    break;
                case 3:
                    //implement logic to view personal schedule
                    break;
                case 4:
                    //implement logic to set availability for appointments
                    break;
                case 5:
                    //implement logic to accept or decline appointment requests
                    break;
                case 6:
                    //implement logic to view upcoming appointments
                    break;
                case 7:
                    //implement logic to record appointment outcome
                    break;
                case 8:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 8);
    }

    public static void displayPharmacistMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

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
                    //implement logic to view appointment outcome record
                    break;
                case 2:
                    //implement logic to update prescription status
                    break;
                case 3:
                    //implement logic to view medication inventory
                    break;
                case 4:
                    //implement logic to submit replenishment request
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    public static void displayAdminMenu() {
        Scanner scanner = new Scanner(System.in);
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
                    //implement logic to view and manage hospital staff
                    break;
                case 2:
                    //implement logic to view appointment details
                    break;
                case 3:
                    //implement logic to view and manage medication inventory
                    break;
                case 4:
                    // implement logic to approve replenishment requests
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }

    public static void displayPatientMenu(UserManager userManager, String userID) {
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
                    patient.viewMedicalRecord();
                    break;
                case 2:
                    System.out.print("Enter new phone number: ");
                    String newPhoneNumber = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    patient.updatePersonalInfo(newPhoneNumber, newEmail);
                    userManager.writeUsersToCSV(); //updates csv also
                    System.out.println("Personal information updated successfully.");
                    break;
                case 3:
                    //implement logic to view available appointment slots
                    break;
                case 4:
                    //implement logic to schedule an appointment
                    break;
                case 5:
                    //implement logic to reschedule an appointment
                    break;
                case 6:
                    //implement logic to cancel an appointment
                    break;
                case 7:
                    //implement logic to view scheduled appointments
                    break;
                case 8:
                    //Implement logic to view past appointment outcome records
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

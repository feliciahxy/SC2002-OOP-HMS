import java.io.*;
import java.util.*;

public class Main {
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
                
                // checks if its the first login
                if (userManager.isFirstLogin(userID)) {
                    System.out.print("Do you want to change your password? (yes/no): ");
                    String changePasswordChoice = scanner.nextLine();
                    if (changePasswordChoice.equalsIgnoreCase("yes")) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();
                        userManager.changePassword(userID, newPassword);
                    }
                }

                // displays the role-specific menus
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
                        displayPatientMenu();
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
        System.out.println("Welcome, Doctor! Here are your options:");
        // implement doctor menu logic
    }

    public static void displayPharmacistMenu() {
        System.out.println("Welcome, Pharmacist! Here are your options:");
        // implement pharmacist menu logic
    }

    public static void displayAdminMenu() {
        System.out.println("Welcome, Administrator! Here are your options:");
        // implement administrator menu logic
    }

    public static void displayPatientMenu() {
        System.out.println("Welcome, Patient! Here are your options:");
        // implement patient menu logic
    }
}
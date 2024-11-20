import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Administrator extends Staff {

    public Administrator(String id, String name, String role, String gender, int age, String password) {
        super(id, name, role, gender, age, password);
    }
  
    public void manageStaff(StaffManager staffManager, UserManager userManager, ArrayList<Schedule> schedules) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("View and manage staff");
                System.out.println("[1] View All Staff");
                System.out.println("[2] Add Staff");
                System.out.println("[3] Update Staff Details");
                System.out.println("[4] Delete Staff");
                System.out.println("[5] Quit");
                System.out.print("Enter Choice: ");
                
                int choice = scanner.nextInt(); // Read the choice
                
                switch (choice) {
                    case 1:
                        viewStaff(staffManager, userManager);
                        break;
                    case 2:
                        addStaff(staffManager, userManager, schedules);
                        break;
                    case 3:
                        updateStaff(userManager);
                        break;
                    case 4:
                        deleteStaff(staffManager, userManager);
                        break;
                    case 5:
                        System.out.println("Exiting staff management.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
    

    public static void viewStaff(StaffManager staffManager, UserManager userManager) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Select Display Filter: ");
                System.out.println("[1] Role");
                System.out.println("[2] Gender");
                System.out.println("[3] Age");
                System.out.println("[4] Display all Staff");
                System.out.println("[5] Quit to Main Menu");
                System.out.print("Enter Choice: ");
    
                int choice = scanner.nextInt();
    
                switch (choice) {
                    case 1:
                        // Filter by role
                        filterStaffByRole(staffManager);
                        break;
                    case 2:
                        filterStaffByGender(userManager);
                        break;
                    case 3:
                        filterStaffByAge(userManager);
                        break;
                    case 4:
                        displayAllStaff(userManager);
                        break;
                    case 5:
                        System.out.println("Returning to the main menu.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
    }
    

    public static void printTableHeader() {
        System.out.printf("%-12s | %-25s | %-20s | %-10s | %-5s\n", 
                          "ID", "Name", "Role", "Gender", "Age");
        System.out.println("------------------------------------------------------------------------------------");
    }

    
    public static void filterStaffByRole(StaffManager staffManager) {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            try {
                System.out.println("Enter the Role to be Displayed:");
                System.out.println("[1] Doctor");
                System.out.println("[2] Pharmacist");
                System.out.println("[3] Administrator");
                System.out.println("[4] Quit to Previous Menu");
                System.out.print("Enter Choice: ");
    
                int choice = scanner.nextInt(); // Read user input
    
                switch (choice) {
                    case 1:
                        printTableHeader();
                        for (Doctor doctor : staffManager.getDoctors()) {
                            System.out.printf("%-12s | %-25s | %-20s | %-10s | %-5d\n",
                                    doctor.getId(), doctor.getName(), doctor.getRole(),
                                    doctor.getGender(), doctor.getAge());
                        }
                        break;
                    case 2:
                        printTableHeader();
                        for (Pharmacist pharmacist : staffManager.getPharmacists()) {
                            System.out.printf("%-12s | %-25s | %-20s | %-10s | %-5d\n",
                                    pharmacist.getId(), pharmacist.getName(), pharmacist.getRole(),
                                    pharmacist.getGender(), pharmacist.getAge());
                        }
                        break;
                    case 3:
                        printTableHeader();
                        for (Administrator admin : staffManager.getAdministrators()) {
                            System.out.printf("%-12s | %-25s | %-20s | %-10s | %-5d\n",
                                    admin.getId(), admin.getName(), admin.getRole(),
                                    admin.getGender(), admin.getAge());
                        }
                        break;
                    case 4:
                        System.out.println("Returning to the previous menu.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
    

    public static void filterStaffByGender(UserManager userManager) {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            try {
                System.out.println("Enter the Gender to be Displayed:");
                System.out.println("[1] Male");
                System.out.println("[2] Female");
                System.out.println("[3] Quit to Previous Menu");
                System.out.print("Enter Choice: ");
    
                int choice = scanner.nextInt(); // Read user input
    
                String gender;
                switch (choice) {
                    case 1:
                        gender = "Male";
                        break;
                    case 2:
                        gender = "Female";
                        break;
                    case 3:
                        System.out.println("Returning to the previous menu.");
                        return; // Exit the method
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                        continue;
                }
    
                // Display the filtered staff
                printTableHeader();
                boolean found = false;
                for (Staff staff : userManager.getStaffUsers()) {
                    if (staff.getGender().equalsIgnoreCase(gender)) {
                        System.out.printf("%-12s | %-25s | %-20s | %-10s | %-5d\n",
                                staff.getId(), staff.getName(), staff.getRole(),
                                staff.getGender(), staff.getAge());
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("No staff members found for the selected gender.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    

    public static void filterStaffByAge(UserManager userManager) {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            try {
                System.out.println("Enter the Minimum Age to Display:");
                int minAge = scanner.nextInt();
    
                System.out.println("Choose the Sorting Order:");
                System.out.println("[1] Ascending");
                System.out.println("[2] Descending");
                System.out.println("[3] Quit to Previous Menu");
                System.out.print("Enter Choice: ");
                int orderChoice = scanner.nextInt();
    
                if (orderChoice == 3) {
                    System.out.println("Returning to the previous menu.");
                    return;
                }
                // Copy the ArrayList of staff onto a new array list to be used for sorting
                ArrayList<Staff> filteredStaff = new ArrayList<>();
                for (Staff staff : userManager.getStaffUsers()) {
                    if (staff.getAge() >= minAge) {
                        filteredStaff.add(staff);
                    }
                }
    
                if (filteredStaff.isEmpty()) {
                    System.out.println("No staff members found above the age of " + minAge);
                    return;
                }
    
                if (orderChoice == 1) {
                    filteredStaff.sort(Comparator.comparingInt(Staff::getAge));
                } else if (orderChoice == 2) {
                    filteredStaff.sort(Comparator.comparingInt(Staff::getAge).reversed());
                } else {
                    System.out.println("Invalid sorting order. Please enter 1 or 2.");
                    continue;
                }
    
                printTableHeader();
                for (Staff staff : filteredStaff) {
                    System.out.printf("%-12s | %-25s | %-20s | %-10s | %-5d\n",
                            staff.getId(), staff.getName(), staff.getRole(),
                            staff.getGender(), staff.getAge());
                }
                return;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
    

    public static void displayAllStaff(UserManager userManager) {
        printTableHeader();
        for (Staff staff : userManager.getStaffUsers()) {
            System.out.printf("%-12s | %-25s | %-20s | %-10s | %-5d\n",
                    staff.getId(), staff.getName(), staff.getRole(),
                    staff.getGender(), staff.getAge());
        }
    }

    public static void addStaff(StaffManager staffManager, UserManager userManager, ArrayList<Schedule> schedules) {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            try {
                System.out.println("Enter the type of staff to add: ");
                System.out.println("[1] Doctor");
                System.out.println("[2] Pharmacist");
                System.out.println("[3] Administrator");
                System.out.println("[4] Quit to Previous Menu");
                System.out.print("Enter Choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the leftover newline
    
                if (choice == 4) {
                    System.out.println("Returning to the previous menu.");
                    return; // Exit the method
                }
    
                String name, gender;
                int age, size;
                String formattedSize;
    
                switch (choice) {
                    case 1:
                        System.out.println("Enter Doctor Name:");
                        name = scanner.nextLine();
    
                        // Inline gender validation
                        while (true) {
                            System.out.println("Enter Doctor Gender (Male/Female):");
                            gender = scanner.nextLine();
                            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                                break;
                            } else {
                                System.out.println("Invalid gender. Please enter 'Male' or 'Female'.");
                            }
                        }
    
                        // Inline age validation
                        while (true) {
                            try {
                                System.out.println("Enter Doctor Age:");
                                age = scanner.nextInt();
                                if (age > 0) {
                                    break;
                                } else {
                                    System.out.println("Age must be a positive integer. Please try again.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number for age.");
                                scanner.nextLine(); // Clear invalid input
                            }
                        }
    
                        ArrayList<String> slots = new ArrayList<>();
                        for (int i = 0; i < 90; i++) {
                            slots.add("N/A");
                        }
                        size = staffManager.getDoctors().size();
                        formattedSize = String.format("%03d", size + 1);
                        String doctorId = "D" + formattedSize;
                        Schedule schedule = new Schedule(doctorId, slots);
                        schedules.add(schedule);
                        Doctor doctor = new Doctor(doctorId, name, "Doctor", gender, age, "password", schedule);
                        staffManager.getDoctors().add(doctor);
                        userManager.getStaffUsers().add(doctor);
                        break;
    
                    case 2:
                        System.out.println("Enter Pharmacist Name:");
                        name = scanner.nextLine();
    
                        // Inline gender validation
                        while (true) {
                            System.out.println("Enter Pharmacist Gender (Male/Female):");
                            gender = scanner.nextLine();
                            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                                break;
                            } else {
                                System.out.println("Invalid gender. Please enter 'Male' or 'Female'.");
                            }
                        }
    
                        // Inline age validation
                        while (true) {
                            try {
                                System.out.println("Enter Pharmacist Age:");
                                age = scanner.nextInt();
                                if (age > 0) {
                                    break;
                                } else {
                                    System.out.println("Age must be a positive integer. Please try again.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number for age.");
                                scanner.nextLine(); // Clear invalid input
                            }
                        }
    
                        size = staffManager.getPharmacists().size();
                        formattedSize = String.format("%03d", size + 1);
                        String pharmacistId = "P" + formattedSize;
                        Pharmacist pharmacist = new Pharmacist(pharmacistId, name, "Pharmacist", gender, age, "password");
                        staffManager.getPharmacists().add(pharmacist);
                        userManager.getStaffUsers().add(pharmacist);
                        break;
    
                    case 3:
                        System.out.println("Enter Administrator Name:");
                        name = scanner.nextLine();
    
                        // Inline gender validation
                        while (true) {
                            System.out.println("Enter Administrator Gender (Male/Female):");
                            gender = scanner.nextLine();
                            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                                break;
                            } else {
                                System.out.println("Invalid gender. Please enter 'Male' or 'Female'.");
                            }
                        }
    
                        // Inline age validation
                        while (true) {
                            try {
                                System.out.println("Enter Administrator Age:");
                                age = scanner.nextInt();
                                if (age > 0) {
                                    break;
                                } else {
                                    System.out.println("Age must be a positive integer. Please try again.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid number for age.");
                                scanner.nextLine(); // Clear invalid input
                            }
                        }
    
                        size = staffManager.getAdministrators().size();
                        formattedSize = String.format("%03d", size + 1);
                        String administratorId = "A" + formattedSize;
                        Administrator administrator = new Administrator(administratorId, name, "Administrator", gender, age, "password");
                        staffManager.getAdministrators().add(administrator);
                        userManager.getStaffUsers().add(administrator);
                        break;
    
                    default:
                        System.out.println("Invalid Choice. Please enter a number between 1 and 4.");
                }
    
                System.out.println("Staff details added successfully.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    
    public static void updateStaff(UserManager userManager) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Staff ID to update:");
        String staffID = scanner.nextLine();
    
        Staff staffToUpdate = null;
        for (Staff staff : userManager.getStaffUsers()) {
            if (staff.getId().equalsIgnoreCase(staffID)) {
                staffToUpdate = staff;
                break;
            }
        }
    
        if (staffToUpdate == null) {
            System.out.println("Staff with ID " + staffID + " not found.");
            return;
        }
    
        System.out.println("Updating details for: " + staffToUpdate.getName());
    
        while (true) {
            try {
                System.out.println("[1] Update Name");
                System.out.println("[2] Update Gender");
                System.out.println("[3] Update Age");
                System.out.println("[4] Quit");
                System.out.print("Enter Choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the leftover newline
    
                switch (choice) {
                    case 1:
                        // Update Name
                        System.out.println("Enter new Name:");
                        String newName = scanner.nextLine();
                        staffToUpdate.setName(newName);
                        System.out.println("Name updated successfully.");
                        break;
    
                    case 2:
                        // Inline gender validation for update
                        while (true) {
                            System.out.println("Enter new Gender (Male/Female):");
                            String newGender = scanner.nextLine();
                            if (newGender.equalsIgnoreCase("Male") || newGender.equalsIgnoreCase("Female")) {
                                staffToUpdate.setGender(newGender);
                                System.out.println("Gender updated successfully.");
                                break;
                            } else {
                                System.out.println("Invalid gender. Please enter 'Male' or 'Female'.");
                            }
                        }
                        break;
    
                    case 3:
                        // Update Age
                        while (true) {
                            try {
                                System.out.println("Enter new Age:");
                                int newAge = scanner.nextInt();
                                if (newAge > 0) {
                                    staffToUpdate.setAge(newAge);
                                    System.out.println("Age updated successfully.");
                                    break;
                                } else {
                                    System.out.println("Age must be a positive integer. Please try again.");
                                }
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid age.");
                                scanner.nextLine(); // Clear invalid input
                            }
                        }
                        break;
    
                    case 4:
                        // Quit
                        System.out.println("Exiting update menu.");
                        return;
    
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                }
    
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    
    public static void deleteStaff(StaffManager staffManager, UserManager userManager) {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            try {
                System.out.println("Enter Staff ID to Delete:");
                String staffID = scanner.nextLine();
    
                // Find the staff using the helper method
                Staff staffToDelete = findStaff(userManager, staffID);
    
                if (staffToDelete == null) {
                    System.out.println("Staff with ID " + staffID + " not found. Please try again.");
                    System.out.println("[1] Try Again");
                    System.out.println("[2] Quit to Previous Menu");
                    System.out.print("Enter Choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine();
    
                    if (choice == 2) {
                        System.out.println("Returning to the previous menu.");
                        return;
                    } else if (choice != 1) {
                        System.out.println("Invalid choice. Returning to the previous menu.");
                        return;
                    }
                    continue;
                }
    
                // Staff from the staff list
                userManager.getStaffUsers().remove(staffToDelete);
    
                if (staffToDelete instanceof Doctor) {
                    staffManager.getDoctors().remove(staffToDelete);
                } else if (staffToDelete instanceof Pharmacist) {
                    staffManager.getPharmacists().remove(staffToDelete);
                } else if (staffToDelete instanceof Administrator) {
                    staffManager.getAdministrators().remove(staffToDelete);
                }
    
                System.out.println("Staff with ID " + staffID + " has been successfully deleted.");
                return; // 
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine(); // Clear the buffer
            }
        }
    }  

    public static Staff findStaff(UserManager userManager, String staffID) {
        for (Staff staff : userManager.getStaffUsers()) {
            if (staff.getId().equalsIgnoreCase(staffID)) {
                return staff;
            }
        }
        return null;
    }
    
    public void managePatient(UserManager userManager) {
        Scanner scanner = new Scanner(System.in);
    
        while (true) {
            try {
                System.out.println("View and Manage Patient");
                System.out.println("[1] View All Patients");
                System.out.println("[2] Add Patient");
                System.out.println("[3] Update Patient Details");
                System.out.println("[4] Delete Patient");
                System.out.println("[5] Quit to Main Menu");
                System.out.print("Enter Choice: ");
                
                int choice = scanner.nextInt();
                scanner.nextLine();
    
                switch (choice) {
                    case 1:
                        viewPatient(userManager);
                        break;
                    case 2:
                        addPatient(userManager);
                        break;
                    case 3:
                        updatePatient(userManager);
                        break;
                    case 4:
                        deletePatient(userManager);
                        break;
                    case 5:
                        System.out.println("Returning to the main menu.");
                        return; // Exit the method
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }
    

    public static void viewPatient(UserManager userManager) {
    // Print table headers
    System.out.printf("%-12s | %-20s | %-15s | %-8s | %-10s | %-12s | %-30s\n", 
            "Patient ID", "Name", "Date of Birth", "Gender", "Blood Type", "Phone", "Email");
    System.out.println("------------------------------------------------------------------------------------------------------------");
    
    for (Patient patient : userManager.getPatientUsers()) {
        System.out.printf("%-12s | %-20s | %-15s | %-8s | %-10s | %-12s | %-30s\n",
                patient.getPatientID(), 
                patient.getName(),
                patient.getDob(),
                patient.getGender(),
                patient.getBloodType(),
                patient.getPhoneNumber(),
                patient.getEmail());
    }
}

    public static void addPatient(UserManager userManager) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter the Name of Patient: ");
            String name = scanner.nextLine();

            System.out.println("Enter Date of Birth (yyyy-MM-dd): ");
            LocalDate dob;
            while (true) {
                try {
                    String dobInput = scanner.next();
                    dob = LocalDate.parse(dobInput, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
                }
            }
            scanner.nextLine();

            String gender;
            while (true) {
                System.out.println("Enter Gender (M/F):");
                gender = scanner.nextLine();
                if (gender.equalsIgnoreCase("M") || gender.equalsIgnoreCase("F")) {
                    break;
                } else {
                    System.out.println("Invalid gender. Please enter 'M' or 'F'.");
                }
            }

            int size = userManager.getPatientUsers().size();
            String formattedSize = String.format("%03d", size + 1);
            String id = "P1" + formattedSize;
            String phoneNumber;
            System.out.println("Enter Phone Number: ");
            while (true) {
                phoneNumber = scanner.nextLine();
                if (phoneNumber.matches("\\d{8}")) { 
                    break;
                } else {
                    System.out.println("Invalid phone number. Please enter an 8-digit number:");
                }
            }
            String email;
            System.out.println("Enter Email: ");
            while (true) {
                email = scanner.nextLine();
                if (email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    break;
                } else {
                    System.out.println("Invalid email address. Please enter a valid email:");
                }
            }
            System.out.println("Enter Blood Type: ");
            String bloodType = scanner.nextLine();

            Patient patient = new Patient(id, name, dob, gender, bloodType, phoneNumber, email, "password");
            userManager.getPatientUsers().add(patient);
            System.out.println("Patient added successfully.");
        } catch (Exception e) {
            System.out.println("An error occurred while adding the patient. Please try again.");
        }
    }

    public static void updatePatient(UserManager userManager) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Enter Patient ID to update:");
                String patientId = scanner.nextLine();

                Patient patientToUpdate = findPatient(userManager, patientId);
                if (patientToUpdate == null) {
                    System.out.println("Patient with ID " + patientId + " not found.");
                    return;
                }

                System.out.println("Updating details for: " + patientToUpdate.getName());
                System.out.println("[1] Update Phone Number");
                System.out.println("[2] Update Email");
                System.out.println("[3] Quit");
                System.out.print("Enter Choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume leftover newline

                switch (choice) {
                    case 1:
                        // Validate phone number
                        while (true) {
                            System.out.println("Enter new Phone Number:");
                            String newPhone = scanner.nextLine();
                            if (newPhone.matches("\\d{8}")) { // Checks if the phone number is exactly 8 digits
                                patientToUpdate.setPhoneNumber(newPhone);
                                System.out.println("Phone number updated successfully.");
                                break;
                            } else {
                                System.out.println("Invalid phone number. Please enter an 8-digit number.");
                            }
                        }
                        break;
                    case 2:
                        // Validate email address
                        while (true) {
                            System.out.println("Enter new Email:");
                            String newEmail = scanner.nextLine();
                            if (newEmail.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) { // Validates a general email format
                                patientToUpdate.setEmail(newEmail);
                                System.out.println("Email updated successfully.");
                                break;
                            } else {
                                System.out.println("Invalid email address. Please enter a valid email.");
                            }
                        }
                        break;
                    case 3:
                        System.out.println("Exiting update menu.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    public static void deletePatient(UserManager userManager) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Enter Patient ID to delete:");
                String patientId = scanner.nextLine();

                Patient patientToDelete = findPatient(userManager, patientId);
                if (patientToDelete == null) {
                    System.out.println("Patient with ID " + patientId + " not found. Please try again.");
                    System.out.println("[1] Try Again");
                    System.out.println("[2] Quit to Previous Menu");
                    System.out.print("Enter Choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); 

                    if (choice == 2) {
                        System.out.println("Returning to the previous menu.");
                        return;
                    } else if (choice != 1) {
                        System.out.println("Invalid choice. Returning to the previous menu.");
                        return;
                    }
                    continue;
                }

                userManager.getPatientUsers().remove(patientToDelete);
                System.out.println("Patient with ID " + patientId + " has been successfully deleted.");
                return;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
            }
        }
    }

    public static Patient findPatient(UserManager userManager, String patientId) {
        for (Patient patient : userManager.getPatientUsers()) {
            if (patientId.equalsIgnoreCase(patient.getId())) {
                return patient;
            }
        }
        return null;
    }
}
import java.io.*;
import java.time.LocalDate;
import java.util.*;
/**
 * The UserManager class manages user data for staff and patients, including login,
 * password management, and loading and saving user data from and to CSV files.
 */
public class UserManager {
    private final ArrayList<Staff> staffUsers;
    private final ArrayList<Patient> patientUsers;

    /**
     * Constructs a UserManager instance and initializes user data by
     * loading from CSV files.
     * 
     * @param schedules the list of schedules to assign to doctors.
     */
    public UserManager(ArrayList<Schedule> schedules) {
        staffUsers = new ArrayList<>();
        patientUsers = new ArrayList<>();
        
        loadUsersFromCSV("../data/Staff_List.csv", true, schedules);
        loadUsersFromCSV("../data/Patient_List.csv", false, null);
    }

    /**
     * Retrieves the list of staff users.
     * 
     * @return the list of staff users.
     */
    public ArrayList<Staff> getStaffUsers() {
        return staffUsers;
    } 

    /**
     * Retrieves the list of patient users.
     * 
     * @return the list of patient users.
     */
    public ArrayList<Patient> getPatientUsers() {
        return patientUsers;
    }

    /**
     * Loads user data from a CSV file and populates the respective user list.
     * 
     * @param filePath the path to the CSV file.
     * @param isStaff  true if loading staff users, false for patient users.
     * @param schedules the list of schedules for doctors (only used for staff).
     */
    private void loadUsersFromCSV(String filePath, boolean isStaff, ArrayList<Schedule> schedules) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                if (isStaff) {
                    String id = fields[0];
                    String name = fields[1];
                    String role = fields[2];
                    String gender = fields[3];
                    int age = Integer.parseInt(fields[4]);
                    String password = fields[5];
                    
                    if (role.equals("Doctor")) {
                        Schedule doctorSchedule = null;
                        for (Schedule schedule : schedules) {
                            if (schedule.getDoctorID().equals(id)) doctorSchedule = schedule;
                            break;
                        }
                        staffUsers.add(new Doctor(id, name, role, gender, age, password, doctorSchedule));
                    }
                    else if (role.equals("Pharmacist")) {
                        staffUsers.add(new Pharmacist(id, name, role, gender, age, password));
                    }
                    else if (role.equals("Administrator")) {
                        staffUsers.add(new Administrator(id, name, role, gender, age, password));
                    }       
                } else {
                    String id = fields[0];
                    String name = fields[1];
                    LocalDate dob = LocalDate.parse(fields[2]);
                    String gender = fields[3];
                    String bloodType = fields[4];
                    String phoneNumber = fields[5];
                    String email = fields[6];
                    String password = fields[7];
                    
                    Patient patient = new Patient(id, name, dob, gender, bloodType, phoneNumber, email, password);
                    patientUsers.add(patient);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Finds a user by their unique ID in the specified list of users.
     * 
     * @param userID the unique ID of the user.
     * @param users  the list of users to search in.
     * @return the User object if found, otherwise null.
     */
    private User findUser(String userID, ArrayList<? extends User> users) {
        for (User user : users) {
            if (user.getId().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Checks if the user is logging in for the first time by comparing their password
     * to the default password.
     * 
     * @param userID the unique ID of the user.
     * @return true if the user is logging in for the first time, otherwise false.
     */
    public boolean isFirstLogin(String userID) {
        User user = findUser(userID, staffUsers);
        if (user == null) {
            user = findUser(userID, patientUsers);
        }
        return user != null && user.getPassword().equals("password");
    }

    /**
     * Changes the user's password if it's their first login and validates the new password.
     * 
     * @param userID      the unique ID of the user.
     * @param newPassword the new password to set.
     * @return true if the password was successfully changed, otherwise false.
     */
    public boolean changePassword(String userID, String newPassword) {
        User user = findUser(userID, staffUsers);
        if (user == null) {
            user = findUser(userID, patientUsers);
        }
        if (user != null) {
            if (!user.isPasswordChanged()) {
                if (isValidPassword(newPassword)) {
                    user.setPassword(newPassword);
                    writeUsersToCSV();
                    System.out.println("Password changed successfully for user ID: " + userID);
                    return true;
                } else {
                    System.out.println("Password does not meet requirements.");
                    System.out.println("- must be min. 8 characters long.");
                    System.out.println("- must contain at least 1 uppercase & 1 lowercase");
                    System.out.println("- must contain at least 1 digit");
                    System.out.println("- must contain at least 1 special character.");
                }
            } else {
                System.out.println("Password change is only allowed on initial login.");
            }
        }
        return false;
    }

    /**
     * Validates a password against security requirements.
     * 
     * @param password the password to validate.
     * @return true if the password meets all requirements, otherwise false.
     */
    private boolean isValidPassword(String password) {
        return password.length() >= 8 && //min. length of 8
            password.matches(".*[A-Z].*") && //at least 1 uppercase
            password.matches(".*[a-z].*") && //at least 1 lowercase
            password.matches(".*\\d.*") && //at least 1 digit/numerical
            password.matches(".*[!@#$%^&*(),.?\":{}|<>].*"); //at least 1 special character
    }

    /**
     * Authenticates a user by validating their login credentials.
     * 
     * @param userID   the unique ID of the user.
     * @param password the password provided during login.
     * @return true if the credentials are valid, otherwise false.
     */
    public boolean login(String userID, String password) {
        User user = findUser(userID, staffUsers);
        if (user != null) {
            return password.equals(user.getPassword());
        }
        user = findUser(userID, patientUsers);
        return user != null && password.equals(user.getPassword());
    }

    /**
     * Retrieves the role of a user by their unique ID.
     * 
     * @param userID the unique ID of the user.
     * @return the role of the user (e.g., Doctor, Patient), or null if not found.
     */
    public String getRole(String userID) {
        User user = findUser(userID, staffUsers);
        if (user != null) {
            return user.getRole();
        }
        user = findUser(userID, patientUsers);
        return user != null ? user.getRole() : null;
    }

    /**
     * Finds a patient by their unique ID.
     * 
     * @param userID the unique ID of the patient.
     * @return the Patient object if found, otherwise null.
     */
    public Patient findPatientByID(String userID) {
        for (Patient patient : patientUsers) {
            if (patient.getId().equals(userID)) {
                return patient;
            }
        }
        return null;
    }

    /**
     * Writes the current user data to CSV files for both staff and patients.
     * Updates their respective files with the latest user information.
     */
    public void writeUsersToCSV() {
        //write to staff.csv
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../data/Staff_List.csv"))) {
            writer.write("Staff ID,Name,Role,Gender,Age,Password");
            writer.newLine();
            for (Staff staff : staffUsers) {
                writer.write(String.join(",",
                    staff.getId(),
                    staff.getName(),
                    staff.getRole(),
                    staff.getGender(),
                    String.valueOf(staff.getAge()),
                    staff.getPassword()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to Staff_List.csv: " + e.getMessage());
        }

        //write to patient.csv
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../data/Patient_List.csv"))) {
            writer.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Phone Number,Email,Password");
            writer.newLine();
            for (Patient patient : patientUsers) {
                writer.write(String.join(",",
                    patient.getId(),
                    patient.getName(),
                    patient.getDob().toString(),
                    patient.getGender(),
                    patient.getBloodType(),
                    patient.getPhoneNumber(),
                    patient.getEmail(),
                    patient.getPassword()
                ));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to Patient_List.csv: " + e.getMessage());
        }
    }
}
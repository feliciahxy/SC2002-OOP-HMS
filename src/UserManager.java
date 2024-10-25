import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class UserManager {
    private final List<User> staffUsers;
    private final List<Patient> patientUsers;

    public UserManager() {
        staffUsers = new ArrayList<>();
        patientUsers = new ArrayList<>();
       
        loadUsersFromCSV("../data/Staff_List.csv", true);
        loadUsersFromCSV("../data/Patient_List.csv", false);
    }

    private void loadUsersFromCSV(String filePath, boolean isStaff) {
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
                    User user = new User(id, name, role, gender, age, password);
                    staffUsers.add(user);
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

    private User findUser(String userID, List<? extends User> users) {
        for (User user : users) {
            if (user.getId().equals(userID)) {
                return user;
            }
        }
        return null;
    }

    public boolean isFirstLogin(String userID) {
        User user = findUser(userID, staffUsers);
        if (user == null) {
            user = findUser(userID, patientUsers);
        }
        return user != null && user.getPassword().equals("password");
    }

    public boolean changePassword(String userID, String newPassword) {
        User user = findUser(userID, staffUsers);
        if (user == null) {
            user = findUser(userID, patientUsers);
        }
        if (user != null) {
            user.setPassword(newPassword);
            writeUsersToCSV();
            System.out.println("Password changed successfully for user ID: " + userID);
            return true;
        }
        return false;
    }

    public boolean login(String userID, String password) {
        User user = findUser(userID, staffUsers);
        if (user != null) {
            return password.equals(user.getPassword());
        }
        user = findUser(userID, patientUsers);
        return user != null && password.equals(user.getPassword());
    }

    public String getRole(String userID) {
        User user = findUser(userID, staffUsers);
        if (user != null) {
            return user.getRole();
        }
        user = findUser(userID, patientUsers);
        return user != null ? user.getRole() : null;
    }

    public Patient findPatientByID(String userID) {
        for (Patient patient : patientUsers) {
            if (patient.getId().equals(userID)) {
                return patient;
            }
        }
        return null;
    }

    public void writeUsersToCSV() {
        try (BufferedWriter staffWriter = new BufferedWriter(new FileWriter("../data/Staff_List.csv"))) {
            staffWriter.write("Staff ID,Name,Role,Gender,Age,Password\n");
            for (User staffUser : staffUsers) {
                staffWriter.write(staffUser.getId() + "," + staffUser.getName() + "," +
                        staffUser.getRole() + "," + staffUser.getGender() + "," +
                        staffUser.getAge() + "," + staffUser.getPassword() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to staff file: " + e.getMessage());
        }

        try (BufferedWriter patientWriter = new BufferedWriter(new FileWriter("../data/Patient_List.csv"))) {
            patientWriter.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Phone Number,Email,Password\n");
            for (Patient patient : patientUsers) {
                patientWriter.write(patient.getId() + "," + patient.getName() + "," +
                        patient.getDob() + "," + patient.getGender() + "," +
                        patient.getBloodType() + "," + patient.getPhoneNumber() + "," +
                        patient.getEmail() + "," + patient.getPassword() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to patient file: " + e.getMessage());
        }
    }
}

import java.io.*;
import java.util.*;

public class UserManager {
    private List<User> staffUsers;
    private List<User> patientUsers;

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
                // Staff user
                if (fields.length < 6) {
                    System.out.println("Invalid staff data: " + line);
                    continue; 
                }
                String id = fields[0];
                String name = fields[1];
                String role = fields[2];
                String gender = fields[3];
                int age = Integer.parseInt(fields[4]);
                String password = fields[5]; // read password
                User user = new User(id, name, role, gender, age, password);
                staffUsers.add(user);
            } else {
                // Patient user
                if (fields.length < 7) {
                    System.out.println("Invalid patient data: " + line);
                    continue;
                }
                String id = fields[0];
                String name = fields[1];
                String password = fields[6]; // read password
                User user = new User(id, name, "Patient", "", 0, password);
                patientUsers.add(user);
            }
        }
    } catch (IOException e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}

// check if its user first login to change password
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
            user.setPassword(newPassword); // update the password in the User object
            writeUsersToCSV(); // write back to the CSV files
            System.out.println("Password changed successfully for user ID: " + userID);
            return true;
        }
        return false;
    }

    private void writeUsersToCSV() {
        // write staff users
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

        // write patient users
        try (BufferedWriter patientWriter = new BufferedWriter(new FileWriter("../data/Patient_List.csv"))) {
            patientWriter.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Information,Password\n");
            for (User patientUser : patientUsers) {
                patientWriter.write(patientUser.getId() + "," + patientUser.getName() + "," +
                        "N/A,N/A,N/A,N/A," + patientUser.getPassword() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to patient file: " + e.getMessage());
        }
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
        if (user != null) {
            return user.getRole(); // this function will return the Patient
        }
        return null;
    }

    private User findUser(String userID, List<User> users) {
        for (User user : users) {
            if (user.getId().equals(userID)) {
                return user;
            }
        }
        return null;
    }
}
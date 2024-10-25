import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Patient extends User {
    private final String patientID;
    private final String name;
    private final LocalDate dob;
    private final String gender;
    private String phoneNumber;
    private String email;
    private final String bloodType;
    private final List<String> pastDiagnosesAndTreatments;

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

    public void viewMedicalRecord() {
        System.out.println("Patient ID: " + patientID);
        System.out.println("Name: " + name);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Gender: " + gender);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Past Diagnoses and Treatments: " + pastDiagnosesAndTreatments);
    }

    public void updatePersonalInfo(String newPhoneNumber, String newEmail) {
        this.phoneNumber = newPhoneNumber;
        this.email = newEmail;
        updatePatientInfoInCSV();
    }

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

    public void addDiagnosisAndTreatment(String diagnosisAndTreatment) {
        this.pastDiagnosesAndTreatments.add(diagnosisAndTreatment);
    }

    public LocalDate getDob() {
        return dob;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}

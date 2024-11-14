import java.io.*;
import java.util.*;

public class ScheduleInitializer {
    public static void main(String[] args) {
        String staffListFile = "../data/Staff_List.csv"; 
        String scheduleFile = "../data/Schedule.csv"; 
        
        List<String> doctorIDs = readDoctorIDs(staffListFile); 
        if (doctorIDs.isEmpty()) {
            System.out.println("No doctor IDs found in " + staffListFile);
            return; 
        }

        updateScheduleFile(scheduleFile, doctorIDs); 
        System.out.println("Schedule has been updated successfully.");
    }

    // Method to read doctor IDs from Staff_List.csv
    private static List<String> readDoctorIDs(String filePath) {
        List<String> doctorIDs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 6 && 
                !fields[0].trim().isEmpty() && 
                !fields[1].trim().isEmpty() && 
                !fields[2].trim().isEmpty() && 
                !fields[3].trim().isEmpty() && 
                !fields[4].trim().isEmpty() && 
                !fields[5].trim().isEmpty()) { 

                    if (fields[2].equals("Doctor")) {
                        doctorIDs.add(fields[0]); 
                    }
                } 
                else {
                    System.out.println("Missing fields in line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doctorIDs;
    }

    // Method to update Schedule.csv with new doctor IDs or use existing data
    private static void updateScheduleFile(String filePath, List<String> newDoctorIDs) {
        Set<String> existingDoctorIDs = new HashSet<>();
        List<String> existingLines = new ArrayList<>();

        // Check if the schedule file exists
        File scheduleFile = new File(filePath);
        boolean isNewFile = !scheduleFile.exists() || (scheduleFile.exists() && scheduleFile.length() == 0);

        if (scheduleFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(scheduleFile))) {
                String line;
                boolean isFirstLine = true;

                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        existingLines.add(line); 
                        isFirstLine = false;
                    } else {
                        existingLines.add(line); 
                        String[] fields = line.split(",");
                        existingDoctorIDs.add(fields[0]); 
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<String> newEntries = new ArrayList<>();
        for (String doctorID : newDoctorIDs) {
            if (!existingDoctorIDs.contains(doctorID)) {
                newEntries.add(doctorID);
            }
        }

        if (!newEntries.isEmpty()) {
            for (String doctorID : newEntries) {
                StringBuilder newEntry = new StringBuilder(doctorID);
                for (int i = 0; i < 90; i++) { 
                    newEntry.append(",N/A");
                }
                existingLines.add(newEntry.toString()); 
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(scheduleFile, false))) {

            if (isNewFile) {
                writer.write("doctorIDs");
                for (int day = 1; day <= 30; day++) {
                    for (int slot = 1; slot <= 3; slot++) {
                        writer.write("," + slot + "/" + day);
                    }
                }
                writer.newLine();
            }

            for (String line : existingLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
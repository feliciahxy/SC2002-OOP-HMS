import java.io.*;
import java.util.*;

/**
 * The ScheduleManager class manages the schedules for doctors, including
 * loading schedules from a CSV file and writing updated schedules back to the file.
 */
public class ScheduleManager {
    private final ArrayList<Schedule> schedules;
    
    /**
     * Constructs a ScheduleManager instance and loads schedules from a predefined CSV file.
     */
    public ScheduleManager() {
        schedules = new ArrayList<>();
        loadSchedulesFromCSV("../data/Schedule.csv");
    }
    
    /**
     * Loads schedules from a CSV file and populates the schedules list.
     * 
     * @param filePath the path to the CSV file containing the schedules.
     */
    public void loadSchedulesFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length < 2) {
                    continue;
                }

                String doctorID = fields[0];

                ArrayList<String> slots = new ArrayList<>();

                for (int i = 1; i < fields.length; i++) slots.add(fields[i]);

                Schedule schedule = new Schedule(doctorID, slots);
                schedules.add(schedule);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Writes the current schedules to a CSV file.
     * 
     * The CSV file includes headers representing dates and time slots and 
     * writes each doctor's schedule in the file.
     */
    public void writeSchedulesToCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/Schedule.csv", false))) {
            StringBuilder header = new StringBuilder("doctorIDs");
            for (int i = 1; i <= 30; i++) {
                for (int j = 1; j <= 3; j++) {
                    header.append(",").append(j).append("/").append(i);
                }
            }
            bw.write(header.toString());
            bw.newLine(); 

            for (Schedule schedule : schedules) {
                bw.write(schedule.getDoctorID());  
                ArrayList<String> slots = schedule.getSlots();
                for (String slot : slots) {
                    bw.write("," + slot);  
                }
                bw.newLine(); 
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    /**
     * Retrieves the list of all schedules.
     * 
     * @return the list of schedules.
     */
    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }
}
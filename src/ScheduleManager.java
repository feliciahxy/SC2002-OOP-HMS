import java.io.*;
import java.util.*;

public class ScheduleManager {
    private final ArrayList<Schedule> schedules;
    
    public ScheduleManager() {
        schedules = new ArrayList<>();
        loadSchedulesFromCSV("../data/Schedule.csv");
    }
    
    private void loadSchedulesFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
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

    public ArrayList<Schedule> getSchedules() {
        return schedules;
    }
}
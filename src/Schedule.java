import java.io.*;
import java.util.*;

public class Schedule { 

    public static void viewAvailableSlots(String staffListFile, String scheduleFilePath) {
        Scanner scanner = new Scanner(System.in);

        // Display list of doctors
        System.out.println("\nList of Doctors:");
        List<String> doctorNames = displayDoctorNames(staffListFile);
        System.out.print("Select doctor by entering the corresponding number: ");
        int doctorChoice = scanner.nextInt();

        if (doctorChoice > 0 && doctorChoice <= doctorNames.size()) {
            String selectedDoctor = doctorNames.get(doctorChoice - 1); 
            System.out.println("\nYou selected: " + selectedDoctor);
            
            // Display available days for selected doctor
            List<Integer> availableDays = getAvailableDays(selectedDoctor, scheduleFilePath);
            if (availableDays.isEmpty()) {
                System.out.println("No available dates for this doctor.");
                return;
            }
            System.out.println("Available dates: " + availableDays);
            
            // Prompt for date selection with error handling
            int dateChoice;
            while (true) {
                System.out.print("Input date to view available slots: ");
                dateChoice = scanner.nextInt();
                if (availableDays.contains(dateChoice)) {
                    break;
                } else {
                    System.out.println("\nInvalid date. Please select from available dates: " + availableDays);
                }
            }

            // Display available slots for the selected day
            List<Integer> availableSlots = getAvailableSlots(selectedDoctor, scheduleFilePath, dateChoice);
            System.out.println("\nSlot 1: 0900-1000");
            System.out.println("Slot 2: 1000-1100");
            System.out.println("Slot 3: 1100-1200");
            System.out.println("Available Slots: " + availableSlots);

        } else {
            System.out.println("Invalid choice, please try again.");
        }
    }

    private static String getDoctorName(String doctorID) {
        String staffListFile = "../data/Staff_List.csv"; 
        try (BufferedReader reader = new BufferedReader(new FileReader(staffListFile))) {
            String line;
            reader.readLine(); 
        
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length >= 2 && fields[0].trim().equals(doctorID)) {
                    return fields[1].trim(); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }

    private static String getDoctorID(String doctorName) {
        String staffListFile = "../data/Staff_List.csv"; 
        try (BufferedReader reader = new BufferedReader(new FileReader(staffListFile))) {
            String line;
            reader.readLine(); 
            
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length >= 2 && fields[1].trim().equalsIgnoreCase(doctorName)) {
                    return fields[0].trim(); 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; 
    }

    public static List<String> displayDoctorNames(String filePath) {
        List<String> doctorNames = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine(); // Skip header line, assuming first line is headers
        
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length > 2) { 
                    
                    String doctorName = fields[1].trim();
                    String role = fields[2].trim();

                    if ("Doctor".equalsIgnoreCase(role)) {
                        doctorNames.add(doctorName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (doctorNames.isEmpty()) {
            System.out.println("No doctors found in the schedule file.");
        } else {
            int index = 1; 
            for (String name : doctorNames) {
                System.out.println("[" + index + "] " + name);
                index++; 
            }
        }

        return doctorNames;
    }

    public static List<Integer> getAvailableDays(String doctorName, String scheduleFilePath) {
        String doctorID = getDoctorID(doctorName);
        List<Integer> availableDays = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(scheduleFilePath))) {
            String line = reader.readLine(); 
            
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length > 0 && fields[0].trim().equals(doctorID)) { 
                    for (int day = 1; day <= 30; day++) {

                        int baseIndex = (day - 1) * 3 + 1;
                        
                        if (fields[baseIndex].equals("N/A") || fields[baseIndex + 1].equals("N/A") || fields[baseIndex + 2].equals("N/A")) {
                            availableDays.add(day); 
                        }
                    }
                    break; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return availableDays;
    }

    public static List<Integer> getAvailableSlots(String doctorName, String scheduleFilePath, int day) {
        String doctorID = getDoctorID(doctorName); 
        List<Integer> availableSlots = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader(scheduleFilePath))) {
            String line = reader.readLine(); 
            
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                
                if (fields.length > 0 && fields[0].trim().equals(doctorID)) { 
  
                    int baseIndex = (day - 1) * 3 + 1;
                    

                    for (int slot = 0; slot < 3; slot++) {
                        if (fields[baseIndex + slot].equals("N/A")) {
                            availableSlots.add(slot + 1); 
                        }
                    }
                    break; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        return availableSlots;
    }
}

/* public class Schedule {
    private List<Date> availableDates;
    private List<Time> availableTimes;

    public Schedule(List<Date> availableDates, List<Time> availableTimes) {
        this.availableDates = availableDates;
        this.availableTimes = availableTimes;
    }

    public void updateAvailability(Date date, Time time) {
        return;
    }

    public List<Date> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(List<Date> availableDates) {
        this.availableDates = availableDates;
    }

    public List<Time> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<Time> availableTimes) {
        this.availableTimes = availableTimes;
    }
}
*/
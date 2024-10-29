import java.io.*;
import java.util.*;

public class Schedule { 
    private final String doctorID;
    private final ArrayList<String> slots;

    public Schedule(String doctorID, ArrayList<String> slots) {
        this.doctorID = doctorID;
        this.slots = slots;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public ArrayList<String> getSlots() {
        return slots;
    }

     public static void viewAvailableSlots(List<User> staffUsers, ArrayList<Schedule> schedules) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> doctorNames = getDoctorNames(staffUsers);
        displayDoctorNames(doctorNames);
        int numDoctors = doctorNames.size();

        int doctorChoice = -1;
        while (doctorChoice < 1 || doctorChoice > numDoctors) { 
            System.out.print("Enter a valid Doctor Choice (1 - " + numDoctors + "): ");
            if (scanner.hasNextInt()) {
                doctorChoice = scanner.nextInt();
            } else {
                scanner.next(); 
                System.out.println("Invalid input. Try Again.");
            }
        }
          String doctorName = doctorNames.get(doctorChoice - 1);
        String doctorID = getDoctorIDFromName(staffUsers, doctorName);
        ArrayList<Integer> availableDates = getAvailableDates(doctorID, schedules);
        displayAvailableDates(availableDates);

        int dateChoice;
        while (true) {
            System.out.print("Enter a valid Date Choice: ");
            dateChoice = scanner.nextInt();
            if (availableDates.contains(dateChoice)) {
                break; 
            } else {
                System.out.println("Invalid date choice. Please choose from the available dates.");
            }
            displayAvailableSlotsForDate(doctorID, dateChoice, schedules);
        }
     }

    public static void displayAvailableSlotsForDate(String doctorID, int date, ArrayList<Schedule> schedules) {

        System.out.println("\nAvailable Timings: ");
        for (Schedule schedule : schedules) {
            if (schedule.getDoctorID().equals(doctorID)) {
                int startIndex = (date - 1) * 3; 
                for (int i = 0; i < 3; i++) { 
                    if (startIndex + i < schedule.getSlots().size()) { 
                        String slot = schedule.getSlots().get(startIndex + i);
                        if (slot.equals("N/A")) {
                            if (i == 0) System.out.println("0900-1000");
                            else if (i == 1) System.out.println("1000-1100");
                            else System.out.println("1100-1200");
                        }
                    }
                }
                break;
            }
        }
    }
    
    public static void displayAvailableDates(ArrayList<Integer> availableDates) {
        System.out.println("\nIn the month of November, the Doctor is free on: ");
        System.out.println(availableDates);
    }

    public static ArrayList<Integer> getAvailableDates(String doctorID, ArrayList<Schedule> schedules) {
        ArrayList<Integer> availableDates = new ArrayList<>();
         for (Schedule schedule : schedules) {
            if (schedule.getDoctorID().equals(doctorID)) {
                ArrayList<String> slots = schedule.getSlots();
                int totalDays = slots.size() / 3; 
                 for (int day = 0; day < totalDays; day++) {
                    boolean isDayAvailable = false;
                    

                    for (int slot = 0; slot < 3; slot++) { 
                        if (slots.get(day * 3 + slot).equals("N/A")) {
                            isDayAvailable = true; 
                            break; 
                        }
                    }
                    if (isDayAvailable) {
                        availableDates.add(day + 1);
                    }
                }
            }
         }
         return availableDates;
    }

    public static ArrayList<String> getDoctorNames (List<User> staffUsers) {
        ArrayList<String> doctorNames = new ArrayList<>();
        for (User user : staffUsers) {
            if ("Doctor".equalsIgnoreCase(user.getRole())) { 
                doctorNames.add(user.getName());
            }
        }
        return doctorNames;
    }

    public static void displayDoctorNames(ArrayList<String> doctorNames) {
        System.out.println("\nList of Doctors:");
        for (int i = 0; i < doctorNames.size(); i++) {
            System.out.println("[" + (i + 1) + "] Dr " + doctorNames.get(i)); // Display with index starting from 1
        }
    }

    public static String getDoctorIDFromName (List<User> staffUsers, String doctorName) {
        for (User user : staffUsers) {
            if (doctorName.equalsIgnoreCase(user.getName())) {
                return user.getId();
            }
        }
         return null;
    }
}

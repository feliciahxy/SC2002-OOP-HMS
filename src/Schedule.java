import java.util.*;

public class Schedule { 
    private final String doctorID;
    private final ArrayList<String> slots;

    // Constructor
    public Schedule(String doctorID, ArrayList<String> slots) {
        this.doctorID = doctorID;
        this.slots = slots;
    }

    // Object getters & accessors
    public String getDoctorID() {
        return doctorID;
    }

    public ArrayList<String> getSlots() {
        return slots;
    }

    public void setSlot(int index, String input) {
        if (index >= 0 && index < slots.size()) {
            slots.set(index, input);
        } else {
            System.out.println("Invalid index.");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Main Functions

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void displaySchedules(String patientID, ArrayList<Schedule> schedules, List<User> staffUsers) {
        Map<String, ArrayList<Integer>> pendingAppointmentsByDoctor = getPendingAppointments(patientID, schedules);
        Map<String, ArrayList<Integer>> confirmedAppointmentsByDoctor = getConfirmedAppointments(patientID, schedules);

        if (pendingAppointmentsByDoctor.isEmpty() && confirmedAppointmentsByDoctor.isEmpty()) {
            System.out.println("You have no appointment to cancel.");
            return; 
        }

        displayPendingAppointment(staffUsers, pendingAppointmentsByDoctor);
        displayConfirmedAppointment(staffUsers, confirmedAppointmentsByDoctor);
    }

    
    public static void rescheduleAppointment(String patientID, ArrayList<Schedule> schedules, List<User> staffUsers) {
        Map<String, ArrayList<Integer>> pendingAppointmentsByDoctor = getPendingAppointments(patientID, schedules);
        Map<String, ArrayList<Integer>> confirmedAppointmentsByDoctor = getConfirmedAppointments(patientID, schedules);

        if (pendingAppointmentsByDoctor.isEmpty() && confirmedAppointmentsByDoctor.isEmpty()) {
            System.out.println("You have no appointment to reschedule.");
            return; 
        }

        displayPendingAppointment(staffUsers, pendingAppointmentsByDoctor);
        displayConfirmedAppointment(staffUsers, confirmedAppointmentsByDoctor);

        Scanner scanner = new Scanner(System.in);
        System.out.println("[1] Pending");
        System.out.println("[2] Confirmed");
        
        int typeChoice;
        while (true) {
            System.out.print("Type of appointment to reschedule: ");
            typeChoice = scanner.nextInt();
            if (typeChoice == 1 || typeChoice == 2) {
                break; 
            } 
            else {
                System.out.println("Invalid Choice.");
            }
        }

        Map<String, ArrayList<Integer>> appointmentsToDisplay = (typeChoice == 1) ? pendingAppointmentsByDoctor : confirmedAppointmentsByDoctor;

        if (appointmentsToDisplay.isEmpty()) {
            System.out.println("No appointments to display.");
            return;
        }

        if (typeChoice == 1) {
            System.out.println("\nYou have pending appointments with the following doctors.");
            displayDoctorsPending(staffUsers, pendingAppointmentsByDoctor);

            int doctorChoice = -1;
            while (doctorChoice < 1 || doctorChoice > appointmentsToDisplay.size()) {
                System.out.print("Select a Doctor (1 - " + appointmentsToDisplay.size() + "): ");
                if (scanner.hasNextInt()) {
                    doctorChoice = scanner.nextInt();
                } else {
                    scanner.next(); 
                    System.out.println("Invalid input. Try Again.");
                }
            }   
            
            String doctorID = new ArrayList<>(pendingAppointmentsByDoctor.keySet()).get(doctorChoice - 1);
            String doctorName = getDoctorNameFromID(staffUsers, doctorID);
            System.out.println("\nAppointments with Dr " + doctorName + ":");

            for (Map.Entry<String, ArrayList<Integer>> entry : pendingAppointmentsByDoctor.entrySet()) {
                if (entry.getKey() == doctorID) {
                    ArrayList<Integer> pendingSlots = entry.getValue();
        
                    int count = 1;
                    for (Integer slot : pendingSlots) {
                        int date = slot / 3 + 1;
                        int actualSlot = slot % 3 + 1;
                        System.out.println("[" + count + "] " + date + " November " + slotToTime(actualSlot));
                        count ++;
                    }

                    int rescheduleChoice = -1;
                    while (true) {
                        System.out.print("Select an appointment to reschedule (1 - " + pendingSlots.size() + "): ");
                        rescheduleChoice = scanner.nextInt();
                        if (rescheduleChoice >= 1 && rescheduleChoice <= pendingSlots.size()) {
                            break; 
                        } else {
                            System.out.println("Invalid choice. Please select a valid appointment.");
                        }
                    }

                    ArrayList<Integer> availableDates = getAvailableDates(doctorID, schedules);
                    displayAvailableDates(availableDates);

                    int dateChoice;
                    while (true) {
                        System.out.print("Select a Date: ");
                        dateChoice = scanner.nextInt();
                        if (availableDates.contains(dateChoice)) {
                            break; 
                        } else {
                            System.out.println("Invalid date choice. Please choose from the available dates.");
                        }
                    }

                    int slotChoice;
                    ArrayList<Integer> availableSlots = displayAvailableSlotsForDate(doctorID, dateChoice, schedules);
                    while (true) {
                        System.out.print("Select a Slot: ");
                        slotChoice = scanner.nextInt();
                        if (availableSlots.contains(slotChoice)) {
                            break;
                        } else {
                            System.out.println("Invalid slot choice. Please choose from the available slots.");
                        }
                    }

                    int slot = addSchedule(patientID, doctorID, dateChoice, slotChoice, schedules);
                    if (slot == -1) System.out.println("An error has occured.");
                                
                    int selectedSlot = pendingSlots.get(rescheduleChoice - 1);
                    cancelSlot(schedules, selectedSlot, doctorID);
                }
            }

            System.out.println("Appointment rescheduled successfully.");
        }
        else if (typeChoice == 2) {
            System.out.println("\nYou have confirmed appointments with the following doctors.");
            displayDoctorsConfirmed(staffUsers, confirmedAppointmentsByDoctor);

            int doctorChoice = -1;
            while (doctorChoice < 1 || doctorChoice > appointmentsToDisplay.size()) {
                System.out.print("Select a Doctor (1 - " + appointmentsToDisplay.size() + "): ");
                if (scanner.hasNextInt()) {
                    doctorChoice = scanner.nextInt();
                } else {
                    scanner.next(); 
                    System.out.println("Invalid input. Try Again.");
                }
            }   
            
            String doctorID = new ArrayList<>(confirmedAppointmentsByDoctor.keySet()).get(doctorChoice - 1);
            String doctorName = getDoctorNameFromID(staffUsers, doctorID);
            System.out.println("\nAppointments with Dr " + doctorName + ":");

            for (Map.Entry<String, ArrayList<Integer>> entry : confirmedAppointmentsByDoctor.entrySet()) {
                if (entry.getKey() == doctorID) {
                    ArrayList<Integer> confirmedSlots = entry.getValue();
        
                    int count = 1;
                    for (Integer slot : confirmedSlots) {
                        int date = slot / 3 + 1;
                        int actualSlot = slot % 3 + 1;
                        System.out.println("[" + count + "] " + date + " November " + slotToTime(actualSlot));
                        count ++;
                    }

                    int rescheduleChoice = -1;
                    while (true) {
                        System.out.print("Select an appointment to reschedule (1 - " + confirmedSlots.size() + "): ");
                        rescheduleChoice = scanner.nextInt();
                        if (rescheduleChoice >= 1 && rescheduleChoice <= confirmedSlots.size()) {
                            break; 
                        } else {
                            System.out.println("Invalid choice. Please select a valid appointment.");
                        }
                    }

                    ArrayList<Integer> availableDates = getAvailableDates(doctorID, schedules);
                    displayAvailableDates(availableDates);

                    int dateChoice;
                    while (true) {
                        System.out.print("Select a Date: ");
                        dateChoice = scanner.nextInt();
                        if (availableDates.contains(dateChoice)) {
                            break; 
                        } else {
                            System.out.println("Invalid date choice. Please choose from the available dates.");
                        }
                    }

                    int slotChoice;
                    ArrayList<Integer> availableSlots = displayAvailableSlotsForDate(doctorID, dateChoice, schedules);
                    while (true) {
                        System.out.print("Select a Slot: ");
                        slotChoice = scanner.nextInt();
                        if (availableSlots.contains(slotChoice)) {
                            break;
                        } else {
                            System.out.println("Invalid slot choice. Please choose from the available slots.");
                        }
                    }

                    int slot = addSchedule(patientID, doctorID, dateChoice, slotChoice, schedules);
                    if (slot == -1) System.out.println("An error has occured.");
                    
                    int selectedSlot = confirmedSlots.get(rescheduleChoice - 1);
                    cancelSlot(schedules, selectedSlot, doctorID);
                }
            }
            System.out.println("Appointment rescheduled successfully.");
        }


    }

    public static void cancelAppointment(String patientID, ArrayList<Schedule> schedules, List<User> staffUsers) {
        Map<String, ArrayList<Integer>> pendingAppointmentsByDoctor = getPendingAppointments(patientID, schedules);
        Map<String, ArrayList<Integer>> confirmedAppointmentsByDoctor = getConfirmedAppointments(patientID, schedules);

        if (pendingAppointmentsByDoctor.isEmpty() && confirmedAppointmentsByDoctor.isEmpty()) {
            System.out.println("You have no appointment to cancel.");
            return; 
        }

        displayPendingAppointment(staffUsers, pendingAppointmentsByDoctor);
        displayConfirmedAppointment(staffUsers, confirmedAppointmentsByDoctor);

        Scanner scanner = new Scanner(System.in);
        System.out.println("[1] Pending");
        System.out.println("[2] Confirmed");
        
        int typeChoice;
        while (true) {
            System.out.print("Type of appointment to cancel: ");
            typeChoice = scanner.nextInt();
            if (typeChoice == 1 || typeChoice == 2) {
                break; 
            } 
            else {
                System.out.println("Invalid Choice.");
            }
        }

        Map<String, ArrayList<Integer>> appointmentsToDisplay = (typeChoice == 1) ? pendingAppointmentsByDoctor : confirmedAppointmentsByDoctor;

        if (appointmentsToDisplay.isEmpty()) {
            System.out.println("No appointments to display.");
            return;
        }

        if (typeChoice == 1) {
            System.out.println("\nYou have pending appointments with the following doctors.");
            displayDoctorsPending(staffUsers, pendingAppointmentsByDoctor);

            int doctorChoice = -1;
            while (doctorChoice < 1 || doctorChoice > appointmentsToDisplay.size()) {
                System.out.print("Select a Doctor (1 - " + appointmentsToDisplay.size() + "): ");
                if (scanner.hasNextInt()) {
                    doctorChoice = scanner.nextInt();
                } else {
                    scanner.next(); 
                    System.out.println("Invalid input. Try Again.");
                }
            }   
            
            String doctorID = new ArrayList<>(pendingAppointmentsByDoctor.keySet()).get(doctorChoice - 1);
            String doctorName = getDoctorNameFromID(staffUsers, doctorID);
            System.out.println("\nAppointments with Dr " + doctorName + ":");

            for (Map.Entry<String, ArrayList<Integer>> entry : pendingAppointmentsByDoctor.entrySet()) {
                if (entry.getKey() == doctorID) {
                    ArrayList<Integer> pendingSlots = entry.getValue();
        
                    int count = 1;
                    for (Integer slot : pendingSlots) {
                        int date = slot / 3 + 1;
                        int actualSlot = slot % 3 + 1;
                        System.out.println("[" + count + "] " + date + " November " + slotToTime(actualSlot));
                        count ++;
                    }

                    int cancelChoice = -1;
                    while (true) {
                        System.out.print("Select an appointment to cancel (1 - " + pendingSlots.size() + "): ");
                        cancelChoice = scanner.nextInt();
                        if (cancelChoice >= 1 && cancelChoice <= pendingSlots.size()) {
                            break; 
                        } else {
                            System.out.println("Invalid choice. Please select a valid appointment.");
                        }
                    }

                    ArrayList<Integer> availableDates = getAvailableDates(doctorID, schedules);
                    displayAvailableDates(availableDates);
                    
                    int selectedSlot = pendingSlots.get(cancelChoice - 1);
                    cancelSlot(schedules, selectedSlot, doctorID);
                }
            }
            System.out.println("Appointment cancelled successfully.");
        }
        else if (typeChoice == 2) {
            System.out.println("\nYou have confirmed appointments with the following doctors.");
            displayDoctorsConfirmed(staffUsers, confirmedAppointmentsByDoctor);

            int doctorChoice = -1;
            while (doctorChoice < 1 || doctorChoice > appointmentsToDisplay.size()) {
                System.out.print("Select a Doctor (1 - " + appointmentsToDisplay.size() + "): ");
                if (scanner.hasNextInt()) {
                    doctorChoice = scanner.nextInt();
                } else {
                    scanner.next(); 
                    System.out.println("Invalid input. Try Again.");
                }
            }   
            
            String doctorID = new ArrayList<>(confirmedAppointmentsByDoctor.keySet()).get(doctorChoice - 1);
            String doctorName = getDoctorNameFromID(staffUsers, doctorID);
            System.out.println("\nAppointments with Dr " + doctorName + ":");

            for (Map.Entry<String, ArrayList<Integer>> entry : confirmedAppointmentsByDoctor.entrySet()) {
                if (entry.getKey() == doctorID) {
                    ArrayList<Integer> confirmedSlots = entry.getValue();
        
                    int count = 1;
                    for (Integer slot : confirmedSlots) {
                        int date = slot / 3 + 1;
                        int actualSlot = slot % 3 + 1;
                        System.out.println("[" + count + "] " + date + " November " + slotToTime(actualSlot));
                        count ++;
                    }

                    int cancelChoice = -1;
                    while (true) {
                        System.out.print("Select an appointment to cancel (1 - " + confirmedSlots.size() + "): ");
                        cancelChoice = scanner.nextInt();
                        if (cancelChoice >= 1 && cancelChoice <= confirmedSlots.size()) {
                            break; 
                        } else {
                            System.out.println("Invalid choice. Please select a valid appointment.");
                        }
                    }
                    
                    int selectedSlot = confirmedSlots.get(cancelChoice - 1);
                    cancelSlot(schedules, selectedSlot, doctorID);
                }
            }
            System.out.println("Appointment rescheduled successfully.");
        }
    }

    public static void scheduleAppointment(List<User> staffUsers, ArrayList<Schedule> schedules, String patientID){
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nSchedule An Appointment");
        ArrayList<String> doctorNames = getDoctorNames(staffUsers);
        displayDoctorNames(doctorNames);
        int numDoctors = doctorNames.size();

        int doctorChoice = -1;
        while (doctorChoice < 1 || doctorChoice > numDoctors) { 
            System.out.print("Select a Doctor (1 - " + numDoctors + "): ");
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
            System.out.print("Select a Date: ");
            dateChoice = scanner.nextInt();
            if (availableDates.contains(dateChoice)) {
                break; 
            } else {
                System.out.println("Invalid date choice. Please choose from the available dates.");
            }
            displayAvailableSlotsForDate(doctorID, dateChoice, schedules);
        }

        int slotChoice;
        ArrayList<Integer> availableSlots = displayAvailableSlotsForDate(doctorID, dateChoice, schedules);
        while (true) {
            System.out.print("Select a Slot: ");
            slotChoice = scanner.nextInt();
            if (availableSlots.contains(slotChoice)) {
                break;
            } else {
                System.out.println("Invalid slot choice. Please choose from the available slots.");
            }
        }

        int slot = addSchedule(patientID, doctorID, dateChoice, slotChoice, schedules);
        if (slot == -1) System.out.println("An error has occured.");



        System.out.println("Appointment successfully scheduled with Dr " + doctorName + " on " + dateChoice + " November from " + slotToTime(slot));
    }

    public static void viewAvailableSlots(List<User> staffUsers, ArrayList<Schedule> schedules) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nView Available Slots");
        ArrayList<String> doctorNames = getDoctorNames(staffUsers);
        displayDoctorNames(doctorNames);
        int numDoctors = doctorNames.size();

        int doctorChoice = -1;
        while (doctorChoice < 1 || doctorChoice > numDoctors) { 
            System.out.print("Select a Doctor (1 - " + numDoctors + "): ");
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
            System.out.print("Select a Date: ");
            dateChoice = scanner.nextInt();
            if (availableDates.contains(dateChoice)) {
                break; 
            } else {
                System.out.println("Invalid date choice. Please choose from the available dates.");
            }
        }

        ArrayList<Integer> availableSlots = displayAvailableSlotsForDate(doctorID, dateChoice, schedules);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Helper Functions

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void cancelSlot(ArrayList<Schedule> schedules, int slot, String doctorID) {
        for (Schedule schedule : schedules) {
            if (schedule.getDoctorID().equals(doctorID)) {
                schedule.getSlots().set(slot, "N/A");
            }
        }
                
    }

    public static void displayDoctorsPending(List<User> staffUsers, Map<String, ArrayList<Integer>> pendingAppointmentsByDoctor) {
        int count = 1;
        for (Map.Entry<String, ArrayList<Integer>> entry : pendingAppointmentsByDoctor.entrySet()) {
            String doctorID = entry.getKey();
            System.out.println("[" + count + "] " + "Dr " + getDoctorNameFromID(staffUsers, doctorID));
            count ++;
        }
    }

    public static void displayDoctorsConfirmed(List<User> staffUsers, Map<String, ArrayList<Integer>> confirmedAppointmentsByDoctor) {
        int count = 1;
        for (Map.Entry<String, ArrayList<Integer>> entry : confirmedAppointmentsByDoctor.entrySet()) {
            String doctorID = entry.getKey();
            System.out.println("[" + count + "] " + "Dr " + getDoctorNameFromID(staffUsers, doctorID));
            count ++;
        }
    }

    public static void displayPendingAppointment(List<User> staffUsers, Map<String, ArrayList<Integer>> pendingAppointmentsByDoctor) {
        System.out.println("\nPending Appointments:");
        System.out.println();

        for (Map.Entry<String, ArrayList<Integer>> entry : pendingAppointmentsByDoctor.entrySet()) {
            String doctorID = entry.getKey();
            ArrayList<Integer> pendingSlots = entry.getValue();
            String doctorName = getDoctorNameFromID(staffUsers, doctorID);
            System.out.println("Dr " + doctorName);
            
            int count = 1;
            for (Integer slot : pendingSlots) {
                int date = slot / 3 + 1;
                int actualSlot = slot % 3 + 1;
                System.out.println("[" + count + "] " + date + " November " + slotToTime(actualSlot));
                count ++;
            }

            System.out.println();
        }
    }

    public static void displayConfirmedAppointment(List<User> staffUsers, Map<String, ArrayList<Integer>> confirmedAppointmentsByDoctor) {
        System.out.println("\nConfirmed Appointments:");
        System.out.println();

        for (Map.Entry<String, ArrayList<Integer>> entry : confirmedAppointmentsByDoctor.entrySet()) {
            String doctorID = entry.getKey();
            ArrayList<Integer> pendingSlots = entry.getValue();
            String doctorName = getDoctorNameFromID(staffUsers, doctorID);
            System.out.println("Dr " + doctorName);
            
            int count = 1;
            for (Integer slot : pendingSlots) {
                int date = slot / 3 + 1;
                int actualSlot = slot % 3 + 1;
                System.out.println("[" + count + "] " + date + " November " + slotToTime(actualSlot));
                count ++;
            }

            System.out.println();
        }
    }

    public static ArrayList<Integer> displayAvailableSlotsForDate(String doctorID, int date, ArrayList<Schedule> schedules) {
        ArrayList<Integer> availableSlots = new ArrayList<>();
    
        System.out.println("\nAvailable Timings: ");
        for (Schedule schedule : schedules) {
            if (schedule.getDoctorID().equals(doctorID)) {
                int startIndex = (date - 1) * 3; 
                int count = 1;
                for (int i = 0; i < 3; i++) { 
                    if (startIndex + i < schedule.getSlots().size()) { 
                        String slot = schedule.getSlots().get(startIndex + i);
                        if (slot.equals("N/A")) {
                            availableSlots.add(count); // Add to available slots
                            if (i == 0) {
                                System.out.println("[" + count + "] 0900-1000");
                            } else if (i == 1) {
                                System.out.println("[" + count + "] 1000-1100");
                            } else {
                                System.out.println("[" + count + "] 1100-1200");
                            }
                            count++;
                        }
                    }
                }
                break;
            }
        }
        return availableSlots;
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
        System.out.println("List of Doctors:");
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

    public static String getDoctorNameFromID (List<User> staffUsers, String doctorID) {
        for (User user : staffUsers) {
            if (doctorID.equalsIgnoreCase(user.getId())) {
                return user.getName();
            }
        }
        return null;
    }


    public static String slotToTime(int slot) {
        if (slot == 1) return "0900-1000";
        else if (slot == 2) return "1000-1100";
        else if (slot == 3) return "1100-1200";
        else return "Error slot value.";
    }

    public static Map<String, ArrayList<Integer>> getPendingAppointments(String patientID, ArrayList<Schedule> schedules) {
        Map<String, ArrayList<Integer>> pendingAppointmentsByDoctor = new HashMap<>();
    
        for (Schedule schedule : schedules) {
            String doctorID = schedule.getDoctorID();
            ArrayList<String> slots = schedule.getSlots();
    
            ArrayList<Integer> pendingAppointments = new ArrayList<>();
    
            for (int i = 0; i < slots.size(); i++) {
                String slotField = slots.get(i);
                if (slotField.equals(patientID + "-0")) {
                    pendingAppointments.add(i);
                }
            }

            if (!pendingAppointments.isEmpty()) {
                pendingAppointmentsByDoctor.put(doctorID, pendingAppointments);
            }
        }
        return pendingAppointmentsByDoctor;
    }
    
    public static Map<String, ArrayList<Integer>> getConfirmedAppointments(String patientID, ArrayList<Schedule> schedules) {
        Map<String, ArrayList<Integer>> confirmedAppointmentsByDoctor = new HashMap<>();
    
        for (Schedule schedule : schedules) {
            String doctorID = schedule.getDoctorID();
            ArrayList<String> slots = schedule.getSlots();
    
            ArrayList<Integer> confirmedAppointments = new ArrayList<>();
    
            for (int i = 0; i < slots.size(); i++) {
                String slotField = slots.get(i);
                if (slotField.equals(patientID + "-1")) {
                    confirmedAppointments.add(i);
                }
            }
    
            if (!confirmedAppointments.isEmpty()) {
                confirmedAppointmentsByDoctor.put(doctorID, confirmedAppointments);
            }
        }
        return confirmedAppointmentsByDoctor;
    }

    public static int addSchedule(String patientID, String doctorID, int dateChoice, int slotChoice, ArrayList<Schedule> schedules) {
        for (Schedule schedule : schedules) {
            if (schedule.getDoctorID().equals(doctorID)) {
                ArrayList<String> slots = schedule.getSlots();
                int dayIndex = (dateChoice - 1) * 3;
                    for (int slot = 0; slot < 3; slot ++) {
                        if (slots.get(dayIndex + slot).equals("N/A")) {
                            if (slotChoice == 1) {
                                slots.set(dayIndex + slot, patientID + "-0");
                                return slot + 1;
                            }
                            slotChoice --;
                        }
                    }
                }
            }

        return -1;
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
} */

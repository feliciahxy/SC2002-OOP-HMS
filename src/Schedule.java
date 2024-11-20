import java.util.*;

/**
 * The Schedule class represents a doctor's schedule, including their ID and available slots.
 * It also provides static utility methods for managing appointments and displaying available dates and slots.
 * @author Chua Yu Hui 
 * @version 1.0
 * @since 2024-10-08
 */
public class Schedule { 
    private final String doctorID;
    private final ArrayList<String> slots;

    /**
     * Constructor to initialize a Schedule with a doctor ID and a list of slots.
     *
     * @param doctorID the unique ID of the doctor.
     * @param slots    a list of appointment slots for the doctor.
     */
    public Schedule(String doctorID, ArrayList<String> slots) {
        this.doctorID = doctorID;
        this.slots = slots;
    }

    /**
     * Gets the doctor ID associated with the schedule.
     *
     * @return the doctor ID.
     */
    public String getDoctorID() {
        return doctorID;
    }

    /**
     * Gets the list of appointment slots for the doctor.
     *
     * @return a list of appointment slots.
     */
    public ArrayList<String> getSlots() {
        return slots;
    }

    /**
     * Updates a specific slot in the schedule.
     *
     * @param index the index of the slot to update.
     * @param input the new value for the slot.
     */
    public void setSlot(int index, String input) {
        if (index >= 0 && index < slots.size()) {
            slots.set(index, input);
        } else {
            System.out.println("Invalid index.");
        }
    }

    /**
     * Cancels an appointment slot for a specific doctor.
     *
     * @param schedules the list of all schedules.
     * @param slot      the slot index to cancel.
     * @param doctorID  the ID of the doctor whose slot is to be canceled.
     */
    public static void cancelSlot(ArrayList<Schedule> schedules, int slot, String doctorID) {
        for (Schedule schedule : schedules) {
            if (schedule.getDoctorID().equals(doctorID)) {
                schedule.getSlots().set(slot, "N/A");
            }
        }
                
    }

    /**
     * Displays doctors with pending appointments.
     *
     * @param doctors                      the list of all doctors.
     * @param pendingAppointmentsByDoctor  a map of doctor IDs to their pending appointment slots.
     */
    public static void displayDoctorsPending(ArrayList<Doctor> doctors, Map<String, ArrayList<Integer>> pendingAppointmentsByDoctor) {
        int count = 1;
        for (Map.Entry<String, ArrayList<Integer>> entry : pendingAppointmentsByDoctor.entrySet()) {
            String doctorID = entry.getKey();
            System.out.println("[" + count + "] " + "Dr " + Patient.getDoctorNameFromID(doctors, doctorID));
            count ++;
        }
    }

    /**
     * Displays doctors with confirmed appointments.
     *
     * @param doctors                       the list of all doctors.
     * @param confirmedAppointmentsByDoctor a map of doctor IDs to their confirmed appointment slots.
     */
    public static void displayDoctorsConfirmed(ArrayList<Doctor> doctors, Map<String, ArrayList<Integer>> confirmedAppointmentsByDoctor) {
        int count = 1;
        for (Map.Entry<String, ArrayList<Integer>> entry : confirmedAppointmentsByDoctor.entrySet()) {
            String doctorID = entry.getKey();
            System.out.println("[" + count + "] " + "Dr " + Patient.getDoctorNameFromID(doctors, doctorID));
            count ++;
        }
    }

    /**
     * Displays pending appointments for each doctor.
     *
     * @param doctors                      the list of all doctors.
     * @param pendingAppointmentsByDoctor  a map of doctor IDs to their pending appointment slots.
     */
    public static void displayPendingAppointment(ArrayList<Doctor> doctors, Map<String, ArrayList<Integer>> pendingAppointmentsByDoctor) {
        if (pendingAppointmentsByDoctor.size() == 0) {
            System.out.println("\nPending Appointments: NIL"); return;
        }

        System.out.println("\nPending Appointments:");
        System.out.println();

        for (Map.Entry<String, ArrayList<Integer>> entry : pendingAppointmentsByDoctor.entrySet()) {
            String doctorID = entry.getKey();
            ArrayList<Integer> pendingSlots = entry.getValue();
            String doctorName = Patient.getDoctorNameFromID(doctors, doctorID);
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

    /**
     * Displays confirmed appointments for each doctor.
     *
     * @param doctors                       the list of all doctors.
     * @param confirmedAppointmentsByDoctor a map of doctor IDs to their confirmed appointment slots.
     */
    public static void displayConfirmedAppointment(ArrayList<Doctor> doctors, Map<String, ArrayList<Integer>> confirmedAppointmentsByDoctor) {
        if (confirmedAppointmentsByDoctor.size() == 0) {
            System.out.println("\nConfirmed Appointments: NIL"); return;
        }

        System.out.println("\nConfirmed Appointments:");
        System.out.println();

        for (Map.Entry<String, ArrayList<Integer>> entry : confirmedAppointmentsByDoctor.entrySet()) {
            String doctorID = entry.getKey();
            ArrayList<Integer> confirmedSlots = entry.getValue();
            String doctorName = Patient.getDoctorNameFromID(doctors, doctorID);
            System.out.println("Dr " + doctorName);
            
            int count = 1;
            for (Integer slot : confirmedSlots) {
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


    /**
     * Converts a slot number to a time range.
     *
     * @param slot the slot number (1-3).
     * @return the corresponding time range as a string.
     */
    public static String slotToTime(int slot) {
        if (slot == 1) return "0900-1000";
        else if (slot == 2) return "1000-1100";
        else if (slot == 3) return "1100-1200";
        else return "Error slot value.";
    }

    /**
     * Retrieves a map of pending appointments for a specific patient.
     *
     * @param patientID the ID of the patient.
     * @param schedules the list of all schedules.
     * @return a Map where the key is the doctor ID and the value is a list of slot indices representing pending appointments.
     */
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
    
    /**
     * Retrieves a map of confirmed appointments for a specific patient.
     *
     * @param patientID the ID of the patient.
     * @param schedules the list of all schedules.
     * @return a Map where the key is the doctor ID and the value is a list of slot indices representing confirmed appointments.
     */
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

    /**
     * Adds a new schedule for a patient with a specific doctor.
     *
     * @param patientID the ID of the patient.
     * @param doctorID the ID of the doctor.
     * @param dateChoice the date of the appointment (1-30).
     * @param slotChoice the slot choice (1-3).
     * @param schedules the list of all schedules.
     * @return the slot number (1-3) if successfully added; -1 if the slot is unavailable.
     */
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


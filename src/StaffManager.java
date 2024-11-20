import java.util.*;

/**
 * The StaffManager class manages and categorizes staff members in the hospital system,
 * including doctors, pharmacists, and administrators. It provides methods for 
 * categorizing staff, retrieving categorized staff, and finding specific staff members by ID.
 * @author Chua Yu Hui 
 * @version 1.0
 * @since 2024-10-08
 */
public class StaffManager {
    private final ArrayList<Doctor> doctors;
    private final ArrayList<Pharmacist> pharmacists;
    private final ArrayList<Administrator> administrators;

    /**
     * Constructs a StaffManager instance with empty lists for doctors,
     * pharmacists, and administrators.
     */
    public StaffManager() {
        doctors = new ArrayList<>();
        pharmacists = new ArrayList<>();
        administrators = new ArrayList<>();
    }

    /**
     * Categorizes staff users into doctors, pharmacists, and administrators.
     * 
     * @param staffUsers the list of all staff users to be categorized.
     */
    public void categorizeStaff(ArrayList<Staff> staffUsers) {
        for (Staff staff : staffUsers) {
            if (staff instanceof Doctor) {
                doctors.add((Doctor) staff); 
            } else if (staff instanceof Pharmacist) {
                pharmacists.add((Pharmacist) staff);
            } else if (staff instanceof Administrator) {
                administrators.add((Administrator) staff);
            }
        }
    }

    /**
     * Finds an administrator by their unique ID.
     * 
     * @param administratorID the unique ID of the administrator.
     * @return the Administrator object if found, otherwise null.
     */
    public Administrator findAdministratorByID(String administratorID) {
        for (Administrator administrator : administrators) {
            if (administrator.getId().equals(administratorID)) return administrator;
        }

        return null;
    }

    /**
     * Finds a pharmacist by their unique ID.
     * 
     * @param pharmacistID the unique ID of the pharmacist.
     * @return the Pharmacist object if found, otherwise null.
     */
    public Pharmacist findPharmacistByID(String pharmacistID){
        for(Pharmacist pharmacist : pharmacists){
            if (pharmacist.getId().equals(pharmacistID)) return pharmacist;
        }
        return null;
    }

    /**
     * Finds a doctor by their unique ID.
     * 
     * @param doctorID the unique ID of the doctor.
     * @return the Doctor object if found, otherwise null.
     */
    public Doctor findDoctorByID(String doctorID){
        for(Doctor doctor : doctors){
            if (doctor.getId().equals(doctorID)) return doctor;
        }
        return null;
    }

    /**
     * Retrieves the list of all categorized doctors.
     * 
     * @return the list of doctors.
     */
    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    /**
     * Retrieves the list of all categorized pharmacists.
     * 
     * @return the list of pharmacists.
     */
    public ArrayList<Pharmacist> getPharmacists() {
        return pharmacists;
    }

    /**
     * Retrieves the list of all categorized administrators.
     * 
     * @return the list of administrators.
     */
    public ArrayList<Administrator> getAdministrators() {
        return administrators;
    }
}  
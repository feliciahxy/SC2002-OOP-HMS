import java.util.*;

public class StaffManager {
    private final ArrayList<Doctor> doctors;
    private final ArrayList<Pharmacist> pharmacists;
    private final ArrayList<Administrator> administrators;

    public StaffManager() {
        doctors = new ArrayList<>();
        pharmacists = new ArrayList<>();
        administrators = new ArrayList<>();
    }

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

    public Administrator findAdministratorByID(String administratorID) {
        for (Administrator administrator : administrators) {
            if (administrator.getId().equals(administratorID)) return administrator;
        }

        return null;
    }

    public Pharmacist findPharmacistByID(String pharmacistID){
        for(Pharmacist pharmacist : pharmacists){
            if (pharmacist.getId().equals(pharmacistID)) return pharmacist;
        }
        return null;
    }

    public Doctor findDoctorByID(String doctorID){
        for(Doctor doctor : doctors){
            if (doctor.getId().equals(doctorID)) return doctor;
        }
        return null;
    }

    public ArrayList<Doctor> getDoctors() {
        return doctors;
    }

    public ArrayList<Pharmacist> getPharmacists() {
        return pharmacists;
    }

    public ArrayList<Administrator> getAdministrators() {
        return administrators;
    }
}  
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

    public void categorizeStaff(ArrayList<Staff> staffUsers, ArrayList<Schedule> schedules) {

        Map<String, Schedule> scheduleMap = new HashMap<>();
        for (Schedule schedule : schedules) {
            scheduleMap.put(schedule.getDoctorID(), schedule);
        }
    
        for (Staff staff : staffUsers) {
            String role = staff.getRole(); 
    
            switch (role.toLowerCase()) {
                case "doctor":
                    Schedule doctorSchedule = scheduleMap.get(staff.getId());
                    doctors.add(new Doctor(staff.getId(), staff.getName(), staff.getRole(), staff.getGender(), staff.getAge(), staff.getPassword(), doctorSchedule));
                    break;
                case "pharmacist":
                    pharmacists.add(new Pharmacist(staff.getId(), staff.getName(), staff.getRole(), staff.getGender(), staff.getAge(), staff.getPassword()));
                    break;
                case "administrator":
                    administrators.add(new Administrator(staff.getId(), staff.getName(), staff.getRole(), staff.getGender(), staff.getAge(), staff.getPassword()));
                    break;
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
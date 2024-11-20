import java.util.*;
/**
 * Represents a pharmacist who can manage prescriptions, view appointment outcomes,
 * and request replenishments for low-stock medications.
 * @author Chew Jin Cheng 
 * @version 1.0
 * @since 2024-10-08
 */
public class Pharmacist extends Staff{
    Scanner scanner = new Scanner(System.in);

    /**
     * Constructs a Pharmacist object.
     *
     * @param staffID   the unique ID of the pharmacist.
     * @param name      the name of the pharmacist.
     * @param role      the role of the pharmacist.
     * @param gender    the gender of the pharmacist.
     * @param age       the age of the pharmacist.
     * @param password  the password for the pharmacist's account.
     */
    public Pharmacist(String staffID, String name, String role, String gender, int age, String password){
        super(staffID, name, role, gender, age, password);
    }
    
    /**
     * Finds an appointment outcome record by its ID.
     *
     * @param appointmentID      the ID of the appointment.
     * @param appointmentOutcomes the list of appointment outcomes.
     * @return the matching {@link AppointmentOutcome} object, or {@code null} if not found.
     */

    private AppointmentOutcome findAppointmentOutcomeRecord(String appointmentID, ArrayList<AppointmentOutcome> appointmentOutcomes){
        for (int i = 0; i<appointmentOutcomes.size(); i++){
            if (appointmentOutcomes.get(i).getAppointmentID().equals(appointmentID)){
                return appointmentOutcomes.get(i);
            }
        }
        System.out.println("AppointmentID not found");
        return null;
    }
    
    /**
     * Allows the pharmacist to view an appointment outcome record by its ID.
     *
     * @param appointmentOutcome the list of appointment outcomes.
     */
    public void viewAppointmentOutcomeRecord(ArrayList<AppointmentOutcome> appointmentOutcome){
        while (true) { 
            try {
                System.out.print("Enter 0 to quit.\nEnter appointment ID: ");
                String appointmentID = scanner.nextLine(); 
                if (appointmentID.equals("0")){
                    return;
                }
                AppointmentOutcome apptOutcome = this.findAppointmentOutcomeRecord(appointmentID, appointmentOutcome);
                if (apptOutcome != null){
                    ArrayList<PrescribedMedication> PrescribedMedications = apptOutcome.getPrescribedMedicationList();
                    System.out.printf("AppointmentID: %s\n", apptOutcome.getAppointmentID());
                    for (int i = 0; i<PrescribedMedications.size(); i++){
                        PrescribedMedication med = PrescribedMedications.get(i);
                        System.out.printf("Medication Name: %s, status: %s\n", med.getMedicationName(), med.getMedicationStatus());
                    }
                    return;
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
                
    }

    /**
     * Retrieves an appointment outcome record by ID and returns it.
     *
     * @param appointmentOutcome the list of appointment outcomes.
     * @param appointmentID      the ID of the appointment to retrieve.
     * @return the corresponding {@link AppointmentOutcome} object, or {@code null} if not found.
     */
    public AppointmentOutcome viewAppointmentOutcomeRecord(ArrayList<AppointmentOutcome> appointmentOutcome, String appointmentID){
        AppointmentOutcome apptOutcome = this.findAppointmentOutcomeRecord(appointmentID, appointmentOutcome);
        if (apptOutcome != null){
            apptOutcome.getPrescribedMedicationList();
        }
        return apptOutcome;        
    }

    /**
     * Allows the pharmacist to update the prescription status for medications in an appointment.
     *
     * @param appointmentOutcomes the list of appointment outcomes.
     * @param inventory           the current inventory of medications.
     */
    public void updatePrescription(ArrayList<AppointmentOutcome> appointmentOutcomes, Inventory inventory){
        while (true) { 
            try {
                System.out.print("Enter 0 to quit.\nEnter appointment ID: ");
                String appointmentID = scanner.nextLine(); 
                if (appointmentID.equals("0")){
                    return;
                }
                AppointmentOutcome appointmentOutcome = viewAppointmentOutcomeRecord(appointmentOutcomes, appointmentID);
                if (appointmentOutcome != null){
                    ArrayList<PrescribedMedication> medlist = appointmentOutcome.getPrescribedMedicationList();
                    int numdispensed = 0;
                    for (int i = 1; i<=medlist.size();i++){
                        PrescribedMedication med = medlist.get(i-1);
                        System.out.printf("[%d]: %s: %s\n", i, med.getMedicationName(), med.getMedicationStatus());
                        if (med.getMedicationStatus().equals("dispensed")){
                            numdispensed++;
                        }
                    }
                    if (numdispensed == medlist.size()){
                        return;
                    }
                    while (true) { 
                        System.out.print("Enter 0 to quit.\nSelect medication no. to dispense: ");
                        int mednum = scanner.nextInt();
                        scanner.nextLine();
                        if (mednum == 0){
                            return;
                        }
                        if (mednum <= 0 || mednum > medlist.size()){
                            System.out.println("Invalid option");
                        }
                        else{
                            PrescribedMedication med = medlist.get(mednum-1);
                            if (med.getMedicationStatus().equals("dispensed")){
                                System.out.println("Unsuccessful, already dispensed!");
                            }
                            else{
                                if(inventory.updateStock(med.getMedicationName(), -1)){
                                    appointmentOutcome.setMedicationStatus(med, "dispensed");
                                    System.out.println("Medication dispensed. Status updated.");
                                }
                            }
                        }
                    }
                      
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
    
    /**
     * Allows the pharmacist to request replenishment for low-stock medications.
     *
     * @param requestList the list of existing replenishment requests.
     * @param inventory   the current inventory of medications.
     */
    public void requestReplenishment(ArrayList<ReplenishmentRequest> requestList, Inventory inventory){//return all low stock medicine
        inventory.getReplenishmentRequest(requestList);       
    }

}

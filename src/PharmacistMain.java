import java.util.*;

public class PharmacistMain {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        int choice;

        //Initializers
        AppointmentOutcomeManager appointmentOutcomeManager = new AppointmentOutcomeManager();
        ArrayList<AppointmentOutcome> appointmentOutcomes = appointmentOutcomeManager.getAppointmentOutcomes();
        MedicationManager medicationManager = new MedicationManager();
        Inventory inventory = new Inventory(medicationManager.getMedications());
        Pharmacist pharmacist = new Pharmacist("P001","Mark Lee","Pharmacist","Male",29,"password");
        ReplenishmentRequestManager replenishmentRequestManager = new ReplenishmentRequestManager();
        ArrayList<ReplenishmentRequest> replenishmentRequest = replenishmentRequestManager.getReplenishmentRequests();
        //
        
        do {
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:

                    pharmacist.viewAppointmentOutcomeRecord(appointmentOutcomes);
                    //implement logic to view appointment outcome record
                     
                    break;
                case 2:
                    pharmacist.updatePrescription(appointmentOutcomes, inventory);

                    //implement logic to update prescription status
                    break;
                case 3:
                    //implement logic to view medication inventory
                    inventory.viewInventory();
                    break;
                case 4:
                    //implement logic to submit replenishment request
                    pharmacist.requestReplenishment(replenishmentRequest, inventory);

                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 5);
    }
}

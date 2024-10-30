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
        Pharmacist pharmacist = new Pharmacist("P001","Mark Lee","Pharmacist","Male",29,"password", inventory);
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
                    //implement logic to view appointment outcome record
                    scanner.nextLine();
                    System.out.print("Enter appointment ID: ");
                    String appointmentID = scanner.nextLine(); 
                    AppointmentOutcome apptOutcome = pharmacist.findAppointmentOutcomeRecord(appointmentID, appointmentOutcomes);
                    if (apptOutcome != null){
                        pharmacist.viewAppointmentOutcomeRecord(apptOutcome);
                    }   
                    
                    break;
                case 2:
                    //implement logic to update prescription status
                    scanner.nextLine();
                    System.out.print("Enter appointment ID: ");
                    String appointmentID1 = scanner.nextLine(); 
                    AppointmentOutcome apptOutcome1 = pharmacist.findAppointmentOutcomeRecord(appointmentID1, appointmentOutcomes);
                    if (apptOutcome1 != null){
                        System.out.print("Enter medication name: ");
                        String medicationName = scanner.nextLine();
                        System.out.print("Enter updated status: ");
                        String status = scanner.nextLine();
                        pharmacist.updatePrescription(apptOutcome1, medicationName, status);
                    }
                    break;
                case 3:
                    //implement logic to view medication inventory
                    pharmacist.viewInventory();
                    break;
                case 4:
                    //implement logic to submit replenishment request
                    scanner.nextLine();
                    System.out.println("Enter RequestID: ");
                    String requestID = scanner.nextLine();
                    replenishmentRequest = pharmacist.requestReplenishment(requestID, replenishmentRequest);
                    
                    //for sending to admin
                    //test printing 
                    for (int i = 0; i<replenishmentRequest.size(); i++){
                        System.out.printf("%s\n", (replenishmentRequest.get(i)).getMedicine());
                    }
                    //
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

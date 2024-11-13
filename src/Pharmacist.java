import java.util.*;

public class Pharmacist extends User{
    Scanner scanner = new Scanner(System.in);
    
    public Pharmacist(String staffID, String name, String role, String gender, int age, String password){
        super(staffID, name, role, gender, age, password);
    }
    
    public AppointmentOutcome findAppointmentOutcomeRecord(String appointmentID, ArrayList<AppointmentOutcome> appointmentOutcomes){
        for (int i = 0; i<appointmentOutcomes.size(); i++){
            if (appointmentOutcomes.get(i).getAppointmentID().equals(appointmentID)){
                return appointmentOutcomes.get(i);
            }
        }
        System.out.println("AppointmentID not found");
        return null;
    }
    
    public void viewAppointmentOutcomeRecord(ArrayList<AppointmentOutcome> appointmentOutcome){
        scanner.nextLine();
        System.out.print("Enter appointment ID: ");
        String appointmentID = scanner.nextLine(); 
        AppointmentOutcome apptOutcome = this.findAppointmentOutcomeRecord(appointmentID, appointmentOutcome);
        if (apptOutcome != null){
            ArrayList<PrescribedMedication> PrescribedMedications = apptOutcome.getPrescribedMedicationList();
            System.out.printf("AppointmentID: %s\n", apptOutcome.getAppointmentID());
            for (int i = 0; i<PrescribedMedications.size(); i++){
                PrescribedMedication med = PrescribedMedications.get(i);
                System.out.printf("Medication Name: %s, status: %s\n", med.getMedicationName(), med.getMedicationStatus());
            }
        }        
    }

    // public void updatePrescription(AppointmentOutcome appointmentOutcome, String medicationName, String status){
    public void updatePrescription(ArrayList<AppointmentOutcome> appointmentOutcomes, Inventory inventory){
        scanner.nextLine();
        System.out.print("Enter appointment ID: ");
        String appointmentID1 = scanner.nextLine(); 
        AppointmentOutcome appointmentOutcome = this.findAppointmentOutcomeRecord(appointmentID1, appointmentOutcomes);
        if (appointmentOutcome != null){
            System.out.print("Enter medication name: ");
            String medicationName = scanner.nextLine();
            System.out.print("Enter updated status: ");
            String status = scanner.nextLine();
            ArrayList<PrescribedMedication> medlist = appointmentOutcome.getPrescribedMedicationList();
            boolean found = false;
            for (int i = 0; i<medlist.size(); i++){
                PrescribedMedication med = medlist.get(i);
                if (med.getMedicationName().equals(medicationName)){
                    found = true;
                    if (status.equals("dispensed")){
                        if(inventory.updateStock(medicationName, -1)){
                            appointmentOutcome.setMedicationStatus(med, status);
                            System.out.println("Medication dispensed. Status updated.");
                            break;
                        }
                    }
                    else{
                        appointmentOutcome.setMedicationStatus(med, status);
                        System.out.println("Status updated.");
                        break;
                    }
                }
            }
            if (found == false){
                System.out.println("Medication not found.");
            }
        }
    }
    
    
    public ArrayList<ReplenishmentRequest> requestReplenishment(ArrayList<ReplenishmentRequest> requestList, Inventory inventory){//return all low stock medicine
        scanner.nextLine();
        System.out.println("Enter RequestID: ");
        String requestID = scanner.nextLine();
        ArrayList<ReplenishmentRequest> replenishmentRequest = inventory.getReplenishmentRequest(requestID, requestList);
       
        //for sending to admin
        //test printing///////////////////////////////////////////////////////////////////////
        for (int i = 0; i<replenishmentRequest.size(); i++){
            System.out.printf("%s\n", (replenishmentRequest.get(i)).getMedicine());
        }
        /////////////////////////////////////////////////////////////////////////////////////         
        return inventory.getReplenishmentRequest(requestID, requestList); 
    }

    // public Inventory getInventory(Inventory inven){
    //     return inventory;
    // }
    // public void setInventory(Inventory inventory){

}

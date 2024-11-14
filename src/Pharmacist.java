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

    public AppointmentOutcome viewAppointmentOutcomeRecord(ArrayList<AppointmentOutcome> appointmentOutcome, String appointmentID){
        AppointmentOutcome apptOutcome = this.findAppointmentOutcomeRecord(appointmentID, appointmentOutcome);
        if (apptOutcome != null){
            ArrayList<PrescribedMedication> PrescribedMedications = apptOutcome.getPrescribedMedicationList();
            System.out.printf("AppointmentID: %s\n", apptOutcome.getAppointmentID());
            for (int i = 0; i<PrescribedMedications.size(); i++){
                PrescribedMedication med = PrescribedMedications.get(i);
                System.out.printf("Medication Name: %s, status: %s\n", med.getMedicationName(), med.getMedicationStatus());
            }
        }
        return apptOutcome;        
    }

    public void updatePrescription(ArrayList<AppointmentOutcome> appointmentOutcomes, Inventory inventory){
        System.out.print("Enter appointment ID: ");
        String appointmentID = scanner.nextLine(); 
        AppointmentOutcome appointmentOutcome = viewAppointmentOutcomeRecord(appointmentOutcomes, appointmentID);
        if (appointmentOutcome != null){
            ArrayList<PrescribedMedication> medlist = appointmentOutcome.getPrescribedMedicationList();
            for (int i = 1; i<=medlist.size();i++){
                PrescribedMedication med = medlist.get(i-1);
                System.out.printf("(%d): %s: %s\n", i, med.getMedicationName(), med.getMedicationStatus());
            }
            System.out.print("Select medication no.: ");
            int mednum = scanner.nextInt();
            if (mednum <= 0 || mednum > medlist.size()){
                System.out.println("Invalid option");
            }
            else{
                PrescribedMedication med = medlist.get(mednum-1);
                if (med.getMedicationStatus().equals("dispensed")){
                    System.out.println("Unsuccessful, already dispensed!");
                }
                else{
                    System.out.print("Enter (1) to dispense. Choice: ");
                    int choice = scanner.nextInt();
                    if (choice ==  1){
                        if(inventory.updateStock(med.getMedicationName(), -1)){
                            appointmentOutcome.setMedicationStatus(med, "dispensed");
                            System.out.println("Medication dispensed. Status updated.");
                        }
                    }
                    else{
                        // appointmentOutcome.setMedicationStatus(med, status);
                        System.out.println("Invalid input.");
                    }
                }
            }  
        }
    }
    
    public ArrayList<ReplenishmentRequest> requestReplenishment(ArrayList<ReplenishmentRequest> requestList, Inventory inventory){//return all low stock medicine
        System.out.println("Enter RequestID: ");
        String requestID = scanner.nextLine();
        ArrayList<ReplenishmentRequest> replenishmentRequest = inventory.getReplenishmentRequest(requestID, requestList);
       
        //for sending to admin
        //test printing///////////////////////////////////////////////////////////////////////
        for (int i = 0; i<replenishmentRequest.size(); i++){
            System.out.printf("%s\n", (replenishmentRequest.get(i)).getMedicine());
        }
        /////////////////////////////////////////////////////////////////////////////////////         
        return inventory.getReplenishmentRequest(requestID, requestList); // to check if need return 
    }

    // public Inventory getInventory(Inventory inven){
    //     return inventory;
    // }
    // public void setInventory(Inventory inventory){

}

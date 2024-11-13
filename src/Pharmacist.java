import java.util.*;

public class Pharmacist extends User{
    private Inventory inventory;
    
    public Pharmacist(String staffID, String name, String department, String gender, int age, String password, Inventory inventory){
        super(staffID, name, department, gender, age, password);
        this.inventory = inventory;
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
    
    public void viewAppointmentOutcomeRecord(AppointmentOutcome appointmentOutcome){
        ArrayList<PrescribedMedication> PrescribedMedications = appointmentOutcome.getPrescribedMedicationList();
        System.out.printf("AppointmentID: %s\n", appointmentOutcome.getAppointmentID());
        for (int i = 0; i<PrescribedMedications.size(); i++){
            PrescribedMedication med = PrescribedMedications.get(i);
            System.out.printf("Medication Name: %s, status: %s\n", med.getMedicationName(), med.getMedicationStatus());
        }
    }

    public void updatePrescription(AppointmentOutcome appointmentOutcome, String medicationName, String status){
        ArrayList<PrescribedMedication> medlist = appointmentOutcome.getPrescribedMedicationList();
        boolean found = false;
        for (int i = 0; i<medlist.size(); i++){
            PrescribedMedication med = medlist.get(i);
            if (med.getMedicationName().equals(medicationName)){
                found = true;
                if (status.equals("dispensed")){
                    if(this.inventory.updateStock(medicationName, -1)){
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
    
    public void viewInventory(){
        ArrayList<Medication> medlist = inventory.getMedicines();
        if (medlist.size() == 0){
            System.out.println("No medicine in inventory.");
        }
        else{
            for (int i = 0; i<medlist.size(); i++){
                Medication med = medlist.get(i);
                System.out.printf("Medicine Name: %s, Quantity: %d, Low Stock Level: %d\n", med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            }
        }
        
    }
    
    public ArrayList<ReplenishmentRequest> requestReplenishment(String requestID, ArrayList<ReplenishmentRequest> requestList){//return all low stock medicine
        return this.inventory.getReplenishmentRequest(requestID, requestList); 
    }

    public Inventory getInventory(){
        return this.inventory;
    }
    // public void setInventory(Inventory inventory){

}

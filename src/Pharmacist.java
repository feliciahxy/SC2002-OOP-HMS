import java.util.*;

public class Pharmacist extends User{
    private Inventory inventory;
    
    public Pharmacist(String staffID, String name, String department, String gender, int age, String password, Inventory inventory){
        super(staffID, name, department, gender, age, password);
        this.inventory = inventory;
    } 
    
    public void viewPrescriptions(AppointmentOutcome appointmentOutcome){
        ArrayList<PrescribedMedication> PrescribedMedications = appointmentOutcome.getPrescribedMedications();
        System.out.printf("AppointmentID: %s", appointmentOutcome.getAppointmentID());
        for (int i = 0; i<PrescribedMedications.size(); i++){
            PrescribedMedication med = PrescribedMedications.get(i);
            System.out.printf("Medication Name: %s, status: %s\n", med.getMedicationName(), med.getMedicationStatus());
        }
    };
    public void updateAppointmentOutcome(AppointmentOutcome appointmentOutcome, String medicationName, String status){
        ArrayList<PrescribedMedication> PrescribedMedications = appointmentOutcome.getPrescribedMedications();
        System.out.printf("AppointmentID: %s", appointmentOutcome.getAppointmentID());
        for (int i = 0; i<PrescribedMedications.size(); i++){
            PrescribedMedication med = PrescribedMedications.get(i);
            if (med.getMedicationName().equals(medicationName)){
                if (status.equals("dispensed")){
                    if (inventory.updateStock(medicationName, -1)){
                        med.setMedicationStatus(status);
                    }
                    else{
                        System.out.println("Insufficient stock");
                    }
                }
            }
        }
    }
    public void viewInventory(){
        ArrayList<Medication> medlist = inventory.getMedicines();
        for (int i = 0; i<medlist.size(); i++){
            Medication med = medlist.get(i);
            System.out.printf("Medicine Name: %s, Quantity: %d, Low Stock Level: %d", med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
        }
    }
    public void requestReplenishment(String medicineID){
        ArrayList<Medication> replenishmentRequest = inventory.getReplenishmentRequest(); //how to pass this around?
    }
    public Inventory getInventory(){
        return inventory;
    }
    // public void setInventory(Inventory inventory){

}

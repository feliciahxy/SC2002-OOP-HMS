import java.util.*;

public class Inventory {
    ArrayList<Medication> medications;
    public Inventory(ArrayList<Medication> medications){
        this.medications = medications;
    }
    public boolean updateStock(String medicineName, int quantitychange){ // need this function? or just call get medications list then setqty mthd for medication 
        for(int i = 0; i<medications.size(); i++){
            if (medications.get(i).getMedicineName().equals(medicineName)){
                Medication med = medications.get(i);
                if (med.quantityChange(quantitychange)){
                    return true;
                }
                else{
                    return false;
                } 
            }
        }
        System.out.println("Medication not found");
        return false;
    }

    public void newMedication(String medicineName, int quantity, int lowStockLevel){
        Medication med = new Medication(medicineName, quantity, lowStockLevel);
        medications.add(med);
        System.out.println("Medication added");
    }

    public ArrayList<Medication> getReplenishmentRequest(){
        ArrayList<Medication> replenishmentList = new ArrayList<>();
        for (int i = 0; i<medications.size(); i++){
            if (medications.get(i).getQuantity() < medications.get(i).getLowStockLevel()){
                replenishmentList.add(medications.get(i));
            }
        }
        if (replenishmentList.isEmpty()){
            System.out.println("No medications require replenishment.");
        }
        return replenishmentList;
    }
    public ArrayList<Medication> getMedicines(){
        return this.medications;
    }
    // public void setMedicines(ArrayList<Medication> medications){

    // }

}

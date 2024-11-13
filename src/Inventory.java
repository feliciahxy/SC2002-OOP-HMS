import java.util.*;

public class Inventory {
    ArrayList<Medication> medications;
    public Inventory(ArrayList<Medication> medications){
        this.medications = medications;
    }
    public void viewInventory(){
        ArrayList<Medication> medlist = this.getMedicines();
        if (medlist.isEmpty()){
            System.out.println("No medicine in inventory.");
        }
        else{
            for (int i = 0; i<medlist.size(); i++){
                Medication med = medlist.get(i);
                System.out.printf("Medicine Name: %s, Quantity: %d, Low Stock Level: %d\n", med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            }
        }
        
    }

    public void newMedication(String medicineName, int quantity, int lowStockLevel){
        Medication med = new Medication(medicineName, quantity, lowStockLevel);
        medications.add(med);
        System.out.println("Medication added\n");
    }

    public boolean updateStock(String medicineName, int quantitychange){ // need this function? or just call get medications list then setqty mthd for medication 
        for(int i = 0; i<medications.size(); i++){
            Medication med = medications.get(i);
            if (med.getMedicineName().equals(medicineName)){
                if (med.quantityChange(quantitychange)){
                    return true;
                }
                else{
                    System.out.println("Out of stock.\n");
                    return false;
                } 
            }
        }
        System.out.println("Medication not found\n");
        return false;
    }

    public void viewLowMedicationStock(){
        boolean low = false;
        for (int i = 0; i<medications.size(); i++){
            if (medications.get(i).getQuantity() < medications.get(i).getLowStockLevel()){
                low = true;
                Medication med = medications.get(i);
                System.out.printf("Medication Name: %s, Current Quantity: %s, Low Stock Level Alert:%s ", med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            }
        }
        if (low == false){
            System.out.println("No Low Stock Medication.");
        }
    }

    public ArrayList<ReplenishmentRequest> getReplenishmentRequest(String requestID, ArrayList<ReplenishmentRequest> requestList){
        Scanner sc = new Scanner(System.in);
        ArrayList<Medication> replenishmentList = new ArrayList<>();
        for (int i = 0; i<medications.size(); i++){
            if (medications.get(i).getQuantity() < medications.get(i).getLowStockLevel()){
                replenishmentList.add(medications.get(i));
            }
        }
        if (replenishmentList.isEmpty()){
            System.out.println("No medications require replenishment.\n");
            return null;
        }
        
        for (int i = 0; i<replenishmentList.size(); i++){
            Medication med = replenishmentList.get(i);
            System.out.printf("%s quantity requested: ", med.getMedicineName());
            int quantity = sc.nextInt();
            ReplenishmentRequest req = new ReplenishmentRequest(requestID, med.getMedicineName(), quantity, "pending approval");
            requestList.add(req);
        }
        return requestList;
    }

    // for admin
    public void approveReplenishmentRequest(String requestID, ArrayList<ReplenishmentRequest> requestList, Inventory inventory){
        Scanner sc = new Scanner(System.in);
        boolean reqIDfound = false;
        boolean anypending = false;
        for (int i = 0; i < requestList.size(); i++){
            if(requestID.equals(requestList.get(i).getRequestID())){
                reqIDfound = true;
                ReplenishmentRequest req = requestList.get(i);
                if (req.getStatus().equals("pending approval")){
                    anypending = true;
                    System.out.printf("%s, quantitiy requested: %d. Approve? (y/n) \n", req.getMedicine(), req.getQuantity());
                    String approve = sc.nextLine();
                    if (approve.equals("y")){
                        System.out.println("Request approved.");
                        req.setStatus("Approved");
                        inventory.updateStock(req.getMedicine(),req.getQuantity());
                    }
                    else if (approve.equals("n")){
                        System.out.println("Request rejected.");
                        req.setStatus("Rejected");
                    }
                }
            }
        }
        if (reqIDfound == false){
            System.out.println("No requestID found.");
        }
        if (anypending == false){
            System.out.println("No requests pending approval.");
        }
    }

    
    public ArrayList<Medication> getMedicines(){
        return this.medications;
    }
    // public void setMedicines(ArrayList<Medication> medications){

    // }

}

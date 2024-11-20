import java.util.*;

/**
 * The {@code Inventory} class manages a collection of medications, enabling actions such as viewing inventory,
 * adding or removing medications, updating stock levels, handling low stock alerts, and managing replenishment requests.
 * @author Chong Wei Thai 
 * @version 1.0
 * @since 2024-10-08
 */
public class Inventory {
    ArrayList<Medication> medications;

    /**
     * Constructs an {@code Inventory} instance with the specified list of medications.
     *
     * @param medications the list of medications to manage.
     */
    public Inventory(ArrayList<Medication> medications){
        this.medications = medications;
    }

    /**
     * Displays the current inventory, listing all medications, their quantities, and low stock levels.
     */
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

    /**
     * Displays the current inventory with numbered entries.
     *
     * @param withnumbers indicator for displaying numbered entries.
     */
    public void viewInventory(int withnumbers){
        ArrayList<Medication> medlist = this.getMedicines();
        if (medlist.isEmpty()){
            System.out.println("No medicine in inventory.");
        }
        else{
            for (int i = 0; i<medlist.size(); i++){
                Medication med = medlist.get(i);
                System.out.printf("[%d] Medicine Name: %s, Quantity: %d, Low Stock Level: %d\n", i+1, med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            }
        }
        
    }

    /**
     * Adds a new medication to the inventory.
     *
     * @param medicineName the name of the medication.
     * @param quantity the initial quantity of the medication.
     * @param lowStockLevel the low stock level threshold for the medication.
     */
    public void newMedication(String medicineName, int quantity, int lowStockLevel){
        Medication med = new Medication(medicineName, quantity, lowStockLevel);
        medications.add(med);
        System.out.println("Medication added");
    }

    /**
     * Updates the stock level of a specific medication.
     *
     * @param medicineName the name of the medication.
     * @param quantitychange the amount to increase or decrease the stock by.
     * @return {@code true} if the stock was successfully updated; {@code false} otherwise.
     */
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

    /**
     * Displays all medications that have stock levels below their respective low stock thresholds.
     */
    public void viewLowMedicationStock(){
        boolean low = false;
        for (int i = 0; i<medications.size(); i++){
            if (medications.get(i).getQuantity() < medications.get(i).getLowStockLevel()){
                low = true;
                Medication med = medications.get(i);
                System.out.printf("Medication Name: %s, Current Quantity: %s, Low Stock Level Alert:%s \n", med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            }
        }
        if (low == false){
            System.out.println("No Low Stock Medication.");
        }
    }

    /**
     * Allows a pharmacist to create replenishment requests for low-stock medications.
     *
     * @param requestList the list of replenishment requests to update.
     */
    public void getReplenishmentRequest(ArrayList<ReplenishmentRequest> requestList){
        Scanner sc = new Scanner(System.in);
        ArrayList<Medication> replenishmentList = new ArrayList<>();
        for (int i = 0; i<medications.size(); i++){
            if (medications.get(i).getQuantity() < medications.get(i).getLowStockLevel()){
                replenishmentList.add(medications.get(i));
            }
        }
        if (replenishmentList.isEmpty()){
            System.out.println("No medications require replenishment.");
            return;
        }
        else{
            for (int i = 0; i<replenishmentList.size(); i++){
                Medication med = replenishmentList.get(i);
                System.out.printf("[%d] Medication: %s, Current quantity: %d, Low stock level: %d\n", i+1, med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            }
        }
        
        while(true){ 
            int choice;

            while (true){
                try{
                    System.out.println("Enter 0 to exit.");
                    System.out.print("Select medication to replenish (no.): ");
                    choice = sc.nextInt();
                    if (choice == 0){
                        return;
                    }
                    if (choice>replenishmentList.size()){
                        throw new InputMismatchException();
                    }
                    Medication med = replenishmentList.get(choice-1);
                    System.out.printf("%s, Current quantity: %d, Low stock level: %d, quantity requested: ", med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
                    int quantity = sc.nextInt();
                    String formattedSize = String.format("%04d", requestList.size()+1);
                    String requestID = "R" + formattedSize;
                    ReplenishmentRequest req = new ReplenishmentRequest(requestID, med.getMedicineName(), quantity, "pending");
                    requestList.add(req);
                    System.out.println("Medication requested.");
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    sc.nextLine();
                }
            }       
        }
    }

    /**
     * Approves or rejects pending replenishment requests. For admin use.
     *
     * @param requestList the list of replenishment requests.
     */
    public void approveReplenishmentRequest(ArrayList<ReplenishmentRequest> requestList){
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i<requestList.size(); i++){
            ReplenishmentRequest Req = requestList.get(i);
            System.out.printf("RequestID: %s, Medicine: %s, Quantity: %d, Status: %s\n", Req.getRequestID(), Req.getMedicine(), Req.getQuantity(), Req.getStatus());
        }
        while(true){
            try{
                System.out.print("Enter 0 to exit. \nEnter requestID: ");
                String requestID = sc.nextLine();
                if (requestID.equals("0")){
                    return;
                }
                boolean reqIDfound = false;
                for (int i = 0; i < requestList.size(); i++){
                    if(requestID.equals(requestList.get(i).getRequestID())){
                        reqIDfound = true;
                        ReplenishmentRequest req = requestList.get(i);
                        if (req.getStatus().equals("approved")){
                            System.out.println("Already approved!");
                        }
                        if (req.getStatus().equals("rejected")){
                            System.out.println("Already rejected!");
                        }
                        if (req.getStatus().equals("pending")){
                            System.out.printf("Medication: %s, quantitiy requested: %d. Approve? (y/n): ", req.getMedicine(), req.getQuantity());
                            String approve = sc.nextLine();
                            if (approve.equals("y")){
                                System.out.println("Request approved.");
                                req.setStatus("approved");
                                this.updateStock(req.getMedicine(),req.getQuantity());
                            }
                            else if (approve.equals("n")){
                                System.out.println("Request rejected.");
                                req.setStatus("rejected");
                            }
                            else{
                                throw new InputMismatchException();
                            }
                        }
                    }
                }
                if (reqIDfound == false){
                    System.out.println("No requestID found.");
                    return;
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
            }
            
        }
    }

    /**
     * Notifies the user of pending replenishment requests.
     *
     * @param requestList the list of replenishment requests.
     */
    public void notifyReplenishmentRequest(ArrayList<ReplenishmentRequest> requestList){
        for (int i = 0; i<requestList.size(); i++){
            if (requestList.get(i).getStatus().equals("pending")){
                System.out.println("Pending replenishment requests awaiting approval.");
                break;
            }
        }
    }

    /**
     * Notifies the user of low-stock medications.
     */
    public void notifyLowStock(){
        for (int i = 0; i<medications.size(); i++){
            if (medications.get(i).getQuantity() < medications.get(i).getLowStockLevel()){
                System.out.println("Low stock detected! Check inventory!");
                break;
            }
        }
    }

    /**
     * Removes a medication from the inventory.
     *
     * @param medicineName the name of the medication to remove.
     */
    public void removeMedication(String medicineName){
        for(int i = 0; i<medications.size(); i++){
            Medication med = medications.get(i);
            if (med.getMedicineName().equals(medicineName)){
                medications.remove(i);
                System.out.println("Medication successfully removed.");
                return;
            }
        }
    }

    /**
     * Updates the low stock level of a medication.
     *
     * @param medicineName the name of the medication.
     * @param lowLevel the new low stock level.
     */
    public void updateLowStockLevel(String medicineName, int lowLevel){
        if (lowLevel<0){
            System.out.println("Cannot be negative!");
            return;
        }
        for(int i = 0; i<medications.size(); i++){
            Medication med = medications.get(i);
            if (med.getMedicineName().equals(medicineName)){
                med.setLowStockLevel(lowLevel);
                System.out.println("Medication low stock level successfully updated.");
                return;
            }
        }
    }

    /**
     * Manages the inventory through various operations like viewing, adding, and updating medications.
     */
    public void manageInventory(){
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do{
            try {
                System.out.println("[1] View inventory");
                System.out.println("[2] Add new medication");
                System.out.println("[3] Update stock level of medication");
                System.out.println("[4] Remove medication");
                System.out.println("[5] Change low stock level");
                System.out.println("[6] Exit");
                System.out.print("Enter choice: ");
                choice = sc.nextInt();
                sc.nextLine();
                switch(choice){
                    case 1:
                        this.viewInventory();
                        break;
                    case 2:
                        System.out.print("Medication Name: ");
                        String medicineName = sc.nextLine(); 
                        System.out.print("Quantity: ");
                        int quantity = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Low stock level: ");
                        int lowstock = sc.nextInt();
                        sc.nextLine();
                        this.newMedication(medicineName, quantity, lowstock);
                        break;
        
                    case 3:
                        this.viewInventory(1);
                        System.out.print("Select medication (no.): ");
                        int mednum = sc.nextInt();
                        sc.nextLine();
                        if (mednum<= 0 || mednum>medications.size()){
                            System.out.println("Invalid input");
                        }
                        else{
                            System.out.print("Quantity to be added (input -x to remove): ");
                            int quantity1 = sc.nextInt();
                            sc.nextLine();
                            this.updateStock(medications.get(mednum-1).getMedicineName(), quantity1);
                        }
                        break;
                    
                    case 4:
                        this.viewInventory(1);
                        System.out.print("Select medication (no.): ");
                        int mednum1 = sc.nextInt();
                        sc.nextLine();
                        if (mednum1<= 0 || mednum1>medications.size()){
                            System.out.println("Invalid input");
                        }
                        else{
                            this.removeMedication(medications.get(mednum1-1).getMedicineName());
                        }    
                        break;
                    
                    case 5:
                        this.viewInventory(1);
                        System.out.print("Select medication (no.): ");
                        int mednum2 = sc.nextInt();
                        sc.nextLine();
                        if (mednum2<= 0 || mednum2>medications.size()){
                            System.out.println("Invalid input");
                            break;
                        }
                        System.out.print("New low stock level: ");
                        int lowstockqty = sc.nextInt();
                        this.updateLowStockLevel(medications.get(mednum2-1).getMedicineName(), lowstockqty);
                        break;
                    case 6:
                        System.out.println("Exit...");
                        break;
                    default:
                        System.out.println("Invalid choice.");
                        break;
                        
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                sc.nextLine();
            }
            
        } while(choice != 6);
        
    }

    /**
     * Returns the list of medications in the inventory.
     *
     * @return the list of medications.
     */
    public ArrayList<Medication> getMedicines(){
        return this.medications;
    }

}

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

    public void viewInventory(int withnumbers){
        ArrayList<Medication> medlist = this.getMedicines();
        if (medlist.isEmpty()){
            System.out.println("No medicine in inventory.");
        }
        else{
            for (int i = 0; i<medlist.size(); i++){
                Medication med = medlist.get(i);
                System.out.printf("(%d) Medicine Name: %s, Quantity: %d, Low Stock Level: %d\n", i+1, med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            }
        }
        
    }

    public void newMedication(String medicineName, int quantity, int lowStockLevel){
        Medication med = new Medication(medicineName, quantity, lowStockLevel);
        medications.add(med);
        System.out.println("Medication added");
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
                System.out.printf("Medication Name: %s, Current Quantity: %s, Low Stock Level Alert:%s \n", med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            }
        }
        if (low == false){
            System.out.println("No Low Stock Medication.");
        }
    }

    //for pharmacist
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
                System.out.printf("(%d) Medication: %s, Current quantity: %d, Low stock level: %d\n", i+1, med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            }
        }
        
        while(true){ 
            int choice;
            
            System.out.println("Enter 0 to exit.");
            System.out.print("Select medication to replenish (no.): ");
            choice = sc.nextInt();
            if (choice == 0){
                break;
            }
            if (choice>replenishmentList.size()){
                System.out.println("Invalid input");
                break;
            }
            Medication med = replenishmentList.get(choice-1);
            System.out.printf("Medication: %s, Current quantity: %d, Low stock level: %d, quantity requested: ", med.getMedicineName(), med.getQuantity(), med.getLowStockLevel());
            int quantity = sc.nextInt();
            String formattedSize = String.format("%04d", requestList.size()+1);
            String requestID = "R" + formattedSize;
            ReplenishmentRequest req = new ReplenishmentRequest(requestID, med.getMedicineName(), quantity, "pending");
            requestList.add(req);
            System.out.println("Medication requested.");   
        }
        // return requestList; //check if need return or if added, global obj changed
    }

    // for admin!!!!!
    public void approveReplenishmentRequest(ArrayList<ReplenishmentRequest> requestList){
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i<requestList.size(); i++){
            ReplenishmentRequest Req = requestList.get(i);
            System.out.printf("RequestID: %s, Medicine: %s, Quantity: %d, Status: %s\n", Req.getRequestID(), Req.getMedicine(), Req.getQuantity(), Req.getStatus());
        }
        while(true){
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
                            System.out.println("Invalid input");
                        }
                    }
                }
            }
            if (reqIDfound == false){
                System.out.println("No requestID found.");
                break;
            }
        }
    }

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

    public void updateLowStockLevel(String medicineName, int lowLevel){
        for(int i = 0; i<medications.size(); i++){
            Medication med = medications.get(i);
            if (med.getMedicineName().equals(medicineName)){
                med.setLowStockLevel(lowLevel);
                System.out.println("Medication low stock level successfully updated.");
                return;
            }
        }
    }

    public void manageInventory(){
        Scanner sc = new Scanner(System.in);
        int choice = 0;
        do{
            System.out.println("(1) View inventory");
            System.out.println("(2) Add new medication");
            System.out.println("(3) Update stock level of medication");
            System.out.println("(4) Remove medication");
            System.out.println("(5) Change low stock level");
            System.out.println("(6) Exit");
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
                    }
                    System.out.print("New low stock level: ");
                    int lowstockqty = sc.nextInt();
                    sc.nextLine();
                    this.updateLowStockLevel(medications.get(mednum2-1).getMedicineName(), lowstockqty);
                    break;
                case 6:
                    System.out.println("Exit...");
                    break;
                default:
                    System.out.println("Invalid choice.");
                    break;
                    
            }
        } while(choice != 6);
        
    }

    
    public ArrayList<Medication> getMedicines(){
        return this.medications;
    }
    // public void setMedicines(ArrayList<Medication> medications){

    // }

}

import java.util.*;

public class Pharmacist extends Staff{
    Scanner scanner = new Scanner(System.in);
    
    public Pharmacist(String staffID, String name, String role, String gender, int age, String password){
        super(staffID, name, role, gender, age, password);
    }
    
    private AppointmentOutcome findAppointmentOutcomeRecord(String appointmentID, ArrayList<AppointmentOutcome> appointmentOutcomes){
        for (int i = 0; i<appointmentOutcomes.size(); i++){
            if (appointmentOutcomes.get(i).getAppointmentID().equals(appointmentID)){
                return appointmentOutcomes.get(i);
            }
        }
        System.out.println("AppointmentID not found");
        return null;
    }
    
    public void viewAppointmentOutcomeRecord(ArrayList<AppointmentOutcome> appointmentOutcome){
        while (true) { 
            try {
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
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
                
    }

    public AppointmentOutcome viewAppointmentOutcomeRecord(ArrayList<AppointmentOutcome> appointmentOutcome, String appointmentID){
        AppointmentOutcome apptOutcome = this.findAppointmentOutcomeRecord(appointmentID, appointmentOutcome);
        if (apptOutcome != null){
            apptOutcome.getPrescribedMedicationList();
        }
        return apptOutcome;        
    }

    public void updatePrescription(ArrayList<AppointmentOutcome> appointmentOutcomes, Inventory inventory){
        while (true) { 
            try {
                System.out.print("Enter 0 to quit.\nEnter appointment ID: ");
                String appointmentID = scanner.nextLine(); 
                if (appointmentID.equals("0")){
                    return;
                }
                AppointmentOutcome appointmentOutcome = viewAppointmentOutcomeRecord(appointmentOutcomes, appointmentID);
                if (appointmentOutcome != null){
                    ArrayList<PrescribedMedication> medlist = appointmentOutcome.getPrescribedMedicationList();
                    int numdispensed = 0;
                    for (int i = 1; i<=medlist.size();i++){
                        PrescribedMedication med = medlist.get(i-1);
                        System.out.printf("[%d]: %s: %s\n", i, med.getMedicationName(), med.getMedicationStatus());
                        if (med.getMedicationStatus().equals("dispensed")){
                            numdispensed++;
                        }
                    }
                    if (numdispensed == medlist.size()){
                        return;
                    }
                    while (true) { 
                        System.out.print("Enter 0 to quit.\nSelect medication no. to dispense: ");
                        int mednum = scanner.nextInt();
                        scanner.nextLine();
                        if (mednum == 0){
                            return;
                        }
                        if (mednum <= 0 || mednum > medlist.size()){
                            System.out.println("Invalid option");
                        }
                        else{
                            PrescribedMedication med = medlist.get(mednum-1);
                            if (med.getMedicationStatus().equals("dispensed")){
                                System.out.println("Unsuccessful, already dispensed!");
                            }
                            else{
                                if(inventory.updateStock(med.getMedicationName(), -1)){
                                    appointmentOutcome.setMedicationStatus(med, "dispensed");
                                    System.out.println("Medication dispensed. Status updated.");
                                }
                            }
                        }
                    }
                      
                }
            } 
            catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }
    
    public void requestReplenishment(ArrayList<ReplenishmentRequest> requestList, Inventory inventory){//return all low stock medicine
        inventory.getReplenishmentRequest(requestList);       
    }

}

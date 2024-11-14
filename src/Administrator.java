import java.util.*;

public class Administrator extends Staff {

    public Administrator(String id, String name, String role, String gender, int age, String password) {
        super(id, name, role, gender, age, password);
    }
}
//     public static void manageStaff(StaffManager staffManager, UserManager userManager){
//         Scanner scanner = new Scanner(System.in);
//         System.out.println("View and manage staff");
//         System.out.println("[1] View All Staff");
//         System.out.println("[2] Add Staff");
//         System.out.println("[3] Update Staff Details");
//         System.out.println("[4] Delete Staff");
//         System.out.println("[5] Quit");
//         int choice;
//         choice = scanner.nextInt();
//         switch(choice){
//             case 1:
//                 viewStaff(staffManager);
//                 break;
//             case 2:
//                 addStaff(staffManager, userManager);
//                 break;
//             case 3:
//                 updateStaff(staffManager);
//                 break;
//             case 4:
//                 deleteStaff(staffManager);
//             case 5:
//                 break;
//         }
//     }

//     public static void viewStaff(StaffManager staffManager, UserManager userManager){
//         //select filter
//         Scanner scanner = new Scanner(System.in);
//         System.out.println("Select Display Filter: ");
//         System.out.println("[1] Role");
//         System.out.println("[2] Gender");
//         System.out.println("[3] Age");
//         System.out.println("[4] Display all Staff");
//         int choice = scanner.nextInt();
//         switch (choice){
//             case 1:
//                 //filter by role
//                 filterStaffByRole(staffManager, userManager);
//                 break;
//             case 2:
//                 //filter by gender
//                 filterStaffByGender(staffManager, userManager);
//                 break;
//             case 3:
//                 //filter by age
//                 filterStaffByAge(staffManager, userManager);
//                 break;
//             case 4:
//                 displayAllStaff(staffManager);
//                 break;
//             default:
//                 System.out.println("Invalid Input");
//                 break;
//         }
//     }

//     //Helper functions to filter by role
//     public static void filterStaffByRole(StaffManager staffManager, UserManager userManager) {
//         Scanner scanner = new Scanner (System.in);
//         System.out.println("Enter the Role to be Displayed:");
//         System.out.println("[1] Doctor");
//         System.out.println("[2] Pharmacist");
//         System.out.println("[3] Adminstrator");
//         int choice = scanner.nextInt();
//         switch (choice){
//             case 1:
//                 for (Doctor doctor : staffManager.getDoctors()){
//                     System.out.println("ID: " + doctor.getId() + " Name: " + doctor.getName() + " Gender: " + doctor.getGender() + " Age: " + doctor.getAge());
//                 }
//                 break;
//             case 2:
//                 for (Pharmacist pharmacist : staffManager.getPharmacists()){
//                     System.out.println("ID: " + pharmacist.getId() + " Name: " + pharmacist.getName() + "Gender: " + pharmacist.getGender() + "Age: " + pharmacist.getAge());
//                 }
//                 break;
//             case 3:
//                 for (Administrator administrator : staffManager.getAdministrators()){
//                     System.out.println("ID: " + administrator.getId() + " Name: " + administrator.getName() + "Gender: " + administrator.getGender() + "Age: " + administrator.getAge());
//                 }
//                 break;
//             default:
//                 System.out.println("Invalid Input");
//                 break;
//         }

//     }

//     //Helper function to filter by gender
//     public static void filterStaffByGender(StaffManager staffManager, UserManager userManager){
//         Scanner scanner = new Scanner(System.in);
//         System.out.println("Enter the Gender to be Displayed:");
//         System.out.println("[1] Male");
//         System.out.println("[2] Female");
//         String gender = scanner.nextInt() == 1 ? "Male" : "Female";

//         for (Staff staff : userManager.getStaffUsers()) {
//             if (staff.getGender().equalsIgnoreCase(gender)) {
//                 System.out.println("ID: " + staff.getId() + " Name: " + staff.getName() + " Role: " + staff.getRole() + " Age: " + staff.getAge());
//             }
//         }
//     }

//     //Helper function to filter by age
//     public static void filterStaffByAge(StaffManager staffManager, UserManager userManager){
//         Scanner scanner = new Scanner(System.in);
//         System.out.println("Enter the Minimum Age to Display:");
//         int minAge = scanner.nextInt();

//         for (Staff staff : userManager.getStaffUsers()) {
//             if (staff.getAge() >= minAge) {
//                 System.out.println("ID: " + staff.getId() + " Name: " + staff.getName() + " Role: " + staff.getRole() + " Gender: " + staff.getGender() + " Age: " + staff.getAge());
//             }
//         }
//     }

//     public static void displayAllStaff(StaffManager staffManager){

//     }


//     public static void addStaff(StaffManager staffManager, UserManager userManager){
//         Scanner scanner = new Scanner (System.in);
//         System.out.println("Enter the type of staff to add: ");
//         System.out.println("[1] Doctor");
//         System.out.println("[2] Pharmacist");
//         System.out.println("[3] Administrator");
//         int choice = scanner.nextInt();

//         String id, name, gender;
//         int age;
//         switch(choice){
//             case 1:
//                 System.out.println("Enter Doctor ID: ");
//                 id = scanner.next();
//                 System.out.println("Enter Doctor Name");
//                 name = scanner.next();
//                 System.out.println("Enter Doctor Gender");
//                 gender = scanner.next();
//                 System.out.println("Enter Doctor Age");
//                 age = scanner.nextInt();
//                 ArrayList<String> slots = new ArrayList<>();
//                 for (int i = 0; i < 90; i++){
//                     slots.add("N/A");
//                 }
//                 Schedule schedule  = new Schedule(id,slots);
//                 Doctor doctor = new Doctor(id,name,"Doctor",gender,age,"password",schedule);
//                 staffManager.getDoctors().add(doctor);
//                 userManager.getStaffUsers().add(doctor);
//                 break;
//             case 2:
//                 System.out.println("Enter Pharmacist ID: ");
//                 id = scanner.next();
//                 System.out.println("Enter Pharmacist Name");
//                 name = scanner.next();
//                 System.out.println("Enter Pharmacist Gender");
//                 gender = scanner.next();
//                 System.out.println("Enter Pharmacist Age");
//                 age = scanner.nextInt();
//                 Pharmacist pharmacist = new Pharmacist(id, name, "Pharmacist", gender, age, "password");
//                 staffManager.getPharmacists().add(pharmacist);
//                 userManager.getStaffUsers().add(pharmacist);
//                 break;
//             case 3:
//                 System.out.println("Enter Administrator ID: ");
//                 id = scanner.next();
//                 System.out.println("Enter Administrator Name");
//                 name = scanner.next();
//                 System.out.println("Enter Administrator Gender");
//                 gender = scanner.next();
//                 System.out.println("Enter Administrator Age");
//                 age = scanner.nextInt();
//                 Administrator administrator = new Administrator (id, name, "Pharmacist", gender, age, "password");
//                 staffManager.getAdministrators().add(administrator);
//                 userManager.getStaffUsers().add(administrator);
//                 break;
//             default:
//                 System.out.println("Invalid Choice");
//                 break;
//         }
//     }

//     public static void updateStaff(StaffManager staffManager, UserManager userManager){
//         //loop through all the staff id
//         //do error checking using findStaff function


//     }

//     public static void deleteStaff(StaffManager staffManager, UserManager userManager){
//         //do error checking using findStaff function
//     }

//     //method used to find if the Staff exists in the list for error handling
//     public static void findStaff(StaffManager staffManager){
//         return;
//     }
       
//     // public void manageStaff(String staffID, String action){
//     //     // 
//     // }
//     // public ArrayList<Staff> viewAppointments(){
//     //     return 
//     // }
//     // public void manageInventory(String medicineID, int quantity){

//     // }
//     // public void approveReplenishmentRequest(String requestID){

//     // }
//     // public ArrayList<Staff> getStaffList(){

//     // }
//     // public void setStaffList(ArrayList<Staff> staffList){

//     // }
//     // public Inventory getInventory(){
//     //     return
//     // }
//     // public void setInventory(Inventory inventory){

//     // }
//     // public ArrayList<Staff> displayStaff(String filter){

//     // }
//     // public ArrayList<Staff> sortBy(String criteria){

//     // 
// }
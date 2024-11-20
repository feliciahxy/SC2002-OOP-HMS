/**
 * The Staff class represents a staff member in the hospital system.
 * It is a subclass of the User class and inherits attributes like ID, name,
 * role, gender, age, and password.
 * 
 * This class serves as a base class for specific types of staff, such as doctors,
 * pharmacists, and administrators.
 * @author Chua Yu Hui 
 * @version 1.0
 * @since 2024-10-08
 */
public class Staff extends User {

    /**
     * Constructs a Staff object with the specified details.
     * 
     * @param id       the unique ID of the staff member.
     * @param name     the name of the staff member.
     * @param role     the role of the staff member (e.g., Doctor, Pharmacist, Administrator).
     * @param gender   the gender of the staff member.
     * @param age      the age of the staff member.
     * @param password the password for the staff member's account.
     */
    public Staff(String id, String name, String role, String gender, int age, String password) {
        super(id, name, role, gender, age, password);
    }

    

}
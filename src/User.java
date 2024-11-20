/**
 * The User class represents a user in the system, which could be a staff member or a patient.
 * It includes attributes like ID, name, role, gender, age, and password, along with methods
 * to retrieve and modify these attributes. It also tracks whether the user's password has been changed.
 * @author Chua Yu Hui 
 * @version 1.0
 * @since 2024-10-08
 */
public class User {
    private String id;
    private String name;
    private String role;
    private String gender;
    private int age;
    private String password;
    private boolean isPasswordChanged; // New flag

    /**
     * Constructs a User instance with the specified details.
     * 
     * @param id       the unique ID of the user.
     * @param name     the name of the user.
     * @param role     the role of the user (e.g., Doctor, Patient).
     * @param gender   the gender of the user.
     * @param age      the age of the user.
     * @param password the initial password of the user.
     */
    public User(String id, String name, String role, String gender, int age, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.password = password;
        this.isPasswordChanged = password.equals("password") ? false : true; // Initialize based on default password
    }

    /**
     * Retrieves the unique ID of the user.
     * 
     * @return the ID of the user.
     */
    public String getId() {
        return id;
    }

    /**
     * Retrieves the name of the user.
     * 
     * @return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the role of the user.
     * 
     * @return the role of the user (e.g., Doctor, Patient).
     */
    public String getRole() {
        return role;
    }

    /**
     * Retrieves the gender of the user.
     * 
     * @return the gender of the user.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Retrieves the age of the user.
     * 
     * @return the age of the user.
     */
    public int getAge() {
        return age;
    }

    /**
     * Retrieves the password of the user.
     * 
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks if the user's password has been changed from the default.
     * 
     * @return true if the password has been changed, false otherwise.
     */
    public boolean isPasswordChanged() {
        return isPasswordChanged;
    }

    /**
     * Sets a new password for the user and marks the password as changed.
     * 
     * @param password the new password to set.
     */
    public void setPassword(String password) {
        this.password = password;
        this.isPasswordChanged = true; //set true when password changed
    }

    /**
     * Updates the ID of the user.
     * 
     * @param id the new ID to set.
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * Updates the name of the user.
     * 
     * @param name the new name to set.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Updates the role of the user.
     * 
     * @param role the new role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }
    
    /**
     * Updates the gender of the user.
     * 
     * @param gender the new gender to set.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * Updates the age of the user.
     * 
     * @param age the new age to set.
     */
    public void setAge(int age) {
        this.age = age;
    }
    
    /**
     * Sets whether the password has been changed.
     * 
     * @param isPasswordChanged true if the password has been changed, false otherwise.
     */
    public void setPasswordChanged(boolean isPasswordChanged) {
        this.isPasswordChanged = isPasswordChanged;
    }
    
}
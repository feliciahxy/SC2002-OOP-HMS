public class User {
    private String id;
    private String name;
    private String role;
    private String gender;
    private int age;
    private String password;

    // Constructor
    public User(String id, String name, String role, String gender, int age, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.password = password;
    }

    // Getter methods
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getPassword() {
        return password; // Ensure this method is defined
    }

    public void setPassword(String password) {
        this.password = password; // Ensure this method is defined
    }
}

/*
public class User {
    private String userID;
    private String password;
    private String role;

    // Constructor
    public User(String userID, String password, String role) {
        this.userID = userID;
        this.password = password;
        this.role = role;
    }


    // Setter & Getter

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    // To be done
    // public boolean login();

    public void changePassword(String newPassword) {
        this.setPassword(newPassword);
    }
}
*/
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
public class User {
    private String id;
    private String name;
    private String role;
    private String gender;
    private int age;
    private String password;
    private boolean isPasswordChanged; // New flag

    public User(String id, String name, String role, String gender, int age, String password) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.gender = gender;
        this.age = age;
        this.password = password;
        this.isPasswordChanged = password.equals("password") ? false : true; // Initialize based on default password
    }

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
        return password;
    }

    public boolean isPasswordChanged() {
        return isPasswordChanged;
    }

    public void setPassword(String password) {
        this.password = password;
        this.isPasswordChanged = true; //set true when password changed
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setPasswordChanged(boolean isPasswordChanged) {
        this.isPasswordChanged = isPasswordChanged;
    }
    
}
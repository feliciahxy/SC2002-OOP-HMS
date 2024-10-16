public class Medication {
    private String medicineID;
    private String medicineName;
    private int quantity;
    private int lowStockLevel;

    public Medication(String medicineID, String medicineName, int quantity, int lowStockLevel) {
        this.medicineID = medicineID;
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.lowStockLevel = lowStockLevel;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLowStockLevel() {
        return lowStockLevel;
    }

    public void setLowStockLevel(int lowStockLevel) {
        this.lowStockLevel = lowStockLevel;
    }
}
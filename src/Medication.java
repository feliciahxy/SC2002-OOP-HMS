/**
 * Represents a medication with its name, quantity, and low stock level alert.
 */
public class Medication {
    private String medicineName;
    private int quantity;
    private int lowStockLevel;

   /**
     * Constructs a new {@link Medication} with the specified name, quantity, and low stock level.
     *
     * @param medicineName the name of the medication.
     * @param quantity the initial quantity of the medication in stock.
     * @param lowStockLevel the threshold quantity at which a low stock alert is triggered.
     */
    public Medication(String medicineName, int quantity, int lowStockLevel) {
        this.medicineName = medicineName;
        this.quantity = quantity;
        this.lowStockLevel = lowStockLevel;
    }

    /**
     * Retrieves the name of the medication.
     *
     * @return the name of the medication.
     */
    public String getMedicineName() {
        return this.medicineName;
    }

    /**
     * Updates the name of the medication.
     *
     * @param medicineName the new name of the medication.
     */
    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    /**
     * Retrieves the current quantity of the medication in stock.
     *
     * @return the current quantity of the medication.
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Updates the quantity of the medication in stock by adding the specified change.
     *
     * @param change the change in quantity (positive to add stock, negative to reduce stock).
     * @return {@code true} if the quantity is updated successfully; {@code false} if the resulting quantity would be negative.
     */
    public boolean quantityChange(int change) {
        if (this.quantity+change>=0){
            this.quantity = this.quantity+change;
            return true;
        }
        return false;
    }

    /**
     * Retrieves the low stock level alert threshold for the medication.
     *
     * @return the low stock level threshold.
     */
    public int getLowStockLevel() {
        return this.lowStockLevel;
    }

    /**
     * Updates the low stock level alert threshold for the medication.
     *
     * @param lowStockLevel the new low stock level threshold.
     */
    public void setLowStockLevel(int lowStockLevel) {
        this.lowStockLevel = lowStockLevel;
    }
}
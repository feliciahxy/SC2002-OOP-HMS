/**
 * Represents a replenishment request for medical supplies.
 * Stores details about the request, including request ID, medicine name,
 * quantity requested, and the current status of the request.
 */
public class ReplenishmentRequest {
    private String requestID;
    private String medicine;
    private int quantity;
    private String status;

    /**
     * Constructs a new ReplenishmentRequest with the specified details.
     *
     * @param requestID the unique identifier for the replenishment request.
     * @param medicine  the name of the medicine requested.
     * @param quantity  the quantity of the medicine requested.
     * @param status    the current status of the request (e.g., "Pending", "Approved").
     */
    public ReplenishmentRequest(String requestID, String medicine, int quantity, String status) {
        this.requestID = requestID;
        this.medicine = medicine;
        this.quantity = quantity;
        this.status = status;
    }

    /**
     * Gets the unique identifier of the replenishment request.
     *
     * @return the request ID.
     */
    public String getRequestID() {
        return this.requestID;
    }

    /**
     * Gets the name of the medicine requested.
     *
     * @return the name of the medicine.
     */
    public String getMedicine() {
        return this.medicine;
    }

    /**
     * Gets the quantity of the medicine requested.
     *
     * @return the quantity requested.
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Gets the current status of the replenishment request.
     *
     * @return the status of the request.
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the status of the replenishment request.
     *
     * @param status the new status of the request.
     */
    public void setStatus(String status){
        this.status = status;
    }
}

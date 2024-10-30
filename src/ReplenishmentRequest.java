public class ReplenishmentRequest {
    private String requestID;
    private String medicine;
    private int quantity;
    private String status;

    public ReplenishmentRequest(String requestID, String medicine, int quantity, String status) {
        this.requestID = requestID;
        this.medicine = medicine;
        this.quantity = quantity;
        this.status = status;
    }

    // Getters (optional, for accessing fields later)
    public String getRequestID() {
        return this.requestID;
    }

    public String getMedicine() {
        return this.medicine;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}

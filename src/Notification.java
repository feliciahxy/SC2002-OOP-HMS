public class Notification {
    private String notificationID;
    private String receiverID;
    private String message;
    private String status;

    public Notification(String notificationID, String receiverID, String message, String status) {
        this.notificationID = notificationID;
        this.receiverID = receiverID;
        this.message = message;
        this.status = status;
    }

    public String getNotificationID() {
        return notificationID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String newStatus) {
        status = newStatus;
    } 
}

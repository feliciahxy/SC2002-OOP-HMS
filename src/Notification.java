/**
 * Represents a notification in the system.
 */
public class Notification {
    private String notificationID;
    private String receiverID;
    private String message;
    private String status;

    /**
     * Constructs a new {@link Notification}.
     *
     * @param notificationID the unique identifier for the notification.
     * @param receiverID the ID of the user receiving the notification.
     * @param message the content of the notification.
     * @param status the current status of the notification (e.g., "pending", "read").
     */
    public Notification(String notificationID, String receiverID, String message, String status) {
        this.notificationID = notificationID;
        this.receiverID = receiverID;
        this.message = message;
        this.status = status;
    }

    /**
     * Gets the unique identifier of the notification.
     *
     * @return the notification ID.
     */
    public String getNotificationID() {
        return notificationID;
    }

    /**
     * Gets the ID of the user receiving the notification.
     *
     * @return the receiver ID.
     */
    public String getReceiverID() {
        return receiverID;
    }

    /**
     * Gets the content of the notification.
     *
     * @return the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the current status of the notification.
     *
     * @return the status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Updates the status of the notification.
     *
     * @param newStatus the new status to set (e.g., "read").
     */
    public void setStatus(String newStatus) {
        status = newStatus;
    } 
}

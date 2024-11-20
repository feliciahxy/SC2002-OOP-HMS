/**
 * A utility class for building {@link Notification} objects.
 */
public class NotificationBuilder {
    /**
     * Creates and returns a {@link Notification} object with the given details.
     *
     * @param notificationID the unique identifier for the notification.
     * @param receiverID the ID of the user receiving the notification.
     * @param message the content of the notification.
     * @return a {@link Notification} object with the specified attributes.
     */
    public static Notification buildNotification (String notificationID, String receiverID, String message) {
        String status = "pending";
        return new Notification(notificationID, receiverID, message, status);
    }
}

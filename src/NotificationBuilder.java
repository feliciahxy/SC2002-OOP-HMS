public class NotificationBuilder {
    public static Notification buildNotification (String notificationID, String receiverID, String message) {
        String status = "pending";
        return new Notification(notificationID, receiverID, message, status);
    }
}

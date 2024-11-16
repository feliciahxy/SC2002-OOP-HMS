import java.util.*;

public class NotificationManager {
    private final ArrayList<Notification> notifications;

    public NotificationManager() {
        notifications = new ArrayList<>();
    }

    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
}

   
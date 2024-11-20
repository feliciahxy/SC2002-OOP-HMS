import java.util.*;

/**
 * Manages a collection of notifications.
 * This class provides methods to add and retrieve notifications.
 */
public class NotificationManager {
    private final ArrayList<Notification> notifications;

    /**
     * Constructs a new {@code NotificationManager}.
     * Initializes an empty list of notifications.
     */
    public NotificationManager() {
        notifications = new ArrayList<>();
    }

    /**
     * Adds a notification to the list.
     *
     * @param notification the {@link Notification} to be added.
     */
    public void addNotification(Notification notification) {
        notifications.add(notification);
    }

    /**
     * Retrieves the list of notifications.
     *
     * @return an {@link ArrayList} of {@link Notification} objects.
     */
    public ArrayList<Notification> getNotifications() {
        return notifications;
    }
}

   
import java.util.*;

/**
 * A utility class for generating unique notification IDs.
 */
public class NotificationIDGenerator {
    /**
     * Generates a unique notification ID based on the size of the existing notification list.
     * The ID follows the format "Nxxxx", where "xxxx" is a zero-padded number.
     *
     * @param notifications the list of existing {@link Notification} objects.
     * @return a unique notification ID as a {@link String}.
     */
    public static String generateNotificationID (ArrayList<Notification> notifications) {
        int size = notifications.size();
        return "N" + String.format("%04d", size + 1); 
    }
}

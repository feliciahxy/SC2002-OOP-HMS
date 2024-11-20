import java.util.ArrayList;
/**
 * Interface for reading notification data from a file.
 * Implementations of this interface are responsible for loading notifications from a specified file path.
 */
public interface NotificationReader {
    /**
     * Reads notifications from the specified file path.
     *
     * @param filePath the path to the file containing notification data.
     * @return an {@link ArrayList} of {@link Notification} objects loaded from the file.
     */

    ArrayList<Notification> readNotifications(String filePath);
}

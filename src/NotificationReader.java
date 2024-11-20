import java.util.ArrayList;
/**
 * Interface for reading notification data from a file.
 * Implementations of this interface are responsible for loading notifications from a specified file path.
 * @author Chew Jin Cheng 
 * @version 1.0
 * @since 2024-10-08
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

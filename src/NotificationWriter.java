import java.util.ArrayList;

/**
 * The NotificationWriter interface defines a method for writing a list of notifications to a file.
 * @author Chew Jin Cheng 
 * @version 1.0
 * @since 2024-10-08
 */
public interface NotificationWriter {
        /**
         * Writes a list of notifications to a specified file.
         *
         * @param filePath      the path of the file where the notifications will be written.
         * @param notifications the list of Notification objects to write.
         */
        void writeNotifications(String filePath, ArrayList<Notification> notifications);
}

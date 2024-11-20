
import java.io.*;
import java.util.ArrayList;

/**
 * The {@code CSVNotificationWriter} class implements the {@code NotificationWriter} interface
 * to write notification data to a CSV file.
 * @author Heng Xin Yu Felicia
 * @version 1.0
 * @since 2024-10-08
 */
public class CSVNotificationWriter implements NotificationWriter {

    /**
     * Writes a list of notifications to a specified CSV file.
     *
     * @param filePath      the file path where notifications should be written.
     * @param notifications the list of {@link Notification} objects to be written.
     */
    public void writeNotifications(String filePath, ArrayList<Notification> notifications) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write("notificationID,receiverID,message,status");
            bw.newLine();
            for (Notification notification : notifications) {
                bw.write(String.format("%s,%s,%s,%s",
                        notification.getNotificationID(),
                        notification.getReceiverID(),
                        notification.getMessage(),
                        notification.getStatus()));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}

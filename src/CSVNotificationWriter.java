
import java.io.*;
import java.util.ArrayList;

public class CSVNotificationWriter implements NotificationWriter {

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

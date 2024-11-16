
import java.io.*;
import java.util.ArrayList;

public class CSVNotificationReader implements NotificationReader {

    public ArrayList<Notification> readNotifications(String filePath) {
        ArrayList<Notification> notifications = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length < 4) {
                    continue; 
                }

                String notificationID = fields[0];
                String receiverID = fields[1];
                String message = fields[2];
                String status = fields[3];

                Notification notification = new Notification(notificationID, receiverID, message, status);
                notifications.add(notification);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return notifications;
    }
}

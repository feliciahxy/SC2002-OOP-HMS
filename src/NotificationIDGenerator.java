import java.util.*;

public class NotificationIDGenerator {
    public static String generateNotificationID (ArrayList<Notification> notifications) {
        int size = notifications.size();
        return "N" + String.format("%04d", size + 1); 
    }
}

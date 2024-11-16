public interface NotificationMessageCreator {
    default String createMessage() {
        return "Generic notification message.";
    }
}

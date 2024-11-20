/**
 * Interface for creating notification messages.
 * Provides a default implementation for generating a generic notification message.
 */
public interface NotificationMessageCreator {
    /**
     * Creates a generic notification message.
     * This default implementation can be overridden by specific implementations
     * to generate customized notification messages.
     *
     * @return a {@link String} representing the generic notification message.
     */
    default String createMessage() {
        return "Generic notification message.";
    }
}

/**
 * The {@code AppointmentNotificationForDoctorCreator} interface extends the {@link AppointmentNotificationMessageCreator} interface.
 * It defines the contract for creating notification messages intended for doctors regarding appointment-related events.
 * @author Denzel Tan Yong Liang
 * @version 1.0
 * @since 2024-10-08
 */
public interface AppointmentNotificationForDoctorCreator extends AppointmentNotificationMessageCreator {
    /**
     * Creates a notification message for a doctor regarding an appointment with a patient.
     *
     * @param appointment the {@link Appointment} object containing details of the appointment.
     * @param patient the {@link Patient} object containing details of the patient associated with the appointment.
     * @return a {@link String} containing the generated notification message.
     */
    String createMessage(Appointment appointment, Patient patient);
}
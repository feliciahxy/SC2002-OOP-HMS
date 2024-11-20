/**
 * The {@code AppointmentNotificationForPatientCreator} interface extends the {@link AppointmentNotificationMessageCreator} interface.
 * It defines the contract for creating notification messages intended for patients regarding appointment-related events.
 * @author Denzel Tan Yong Liang
 * @version 1.0
 * @since 2024-10-08
 */
public interface AppointmentNotificationForPatientCreator extends AppointmentNotificationMessageCreator {
    /**
     * Creates a notification message for a patient regarding an appointment with a doctor.
     *
     * @param appointment the {@link Appointment} object containing details of the appointment.
     * @param doctor the {@link Doctor} object containing details of the doctor associated with the appointment.
     * @return a {@link String} containing the generated notification message.
     */
    String createMessage(Appointment appointment, Doctor doctor);
}

/**
 * Class responsible for creating notification messages when a doctor confirms an appointment.
 * Implements the {@link AppointmentNotificationForPatientCreator} interface.
 * @author Chew Jin Cheng 
 * @version 1.0
 * @since 2024-10-08
 */
public class NotificationWhenDoctorConfirms implements AppointmentNotificationForPatientCreator {
    /**
     * Creates a notification message for the patient when a doctor confirms an appointment.
     *
     * @param appointment the {@link Appointment} that has been confirmed.
     * @param doctor the {@link Doctor} who confirmed the appointment.
     * @return a formatted message indicating the confirmation details, including the doctor's name,
     *         the appointment ID, date, and time.
     */
    public String createMessage(Appointment appointment, Doctor doctor) {
        return "Dr " + doctor.getName() + " has confirmed the appointment (ID: " + appointment.getAppointmentID() + ") with you" +
               " on " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}
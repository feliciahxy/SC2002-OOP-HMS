/**
 * Class responsible for creating notification messages when a doctor cancels an appointment.
 * Implements the {@link AppointmentNotificationForPatientCreator} interface.
 */
public class NotificationWhenDoctorCancels implements AppointmentNotificationForPatientCreator {
    /**
     * Creates a notification message for the patient when a doctor cancels an appointment.
     *
     * @param appointment the {@link Appointment} that has been cancelled.
     * @param doctor the {@link Doctor} who cancelled the appointment.
     * @return a formatted message indicating the cancellation details, including the doctor's name,
     *         the appointment ID, date, and time.
     */
    public String createMessage(Appointment appointment, Doctor doctor) {
        return "Dr " + doctor.getName() + " has cancelled the appointment (ID: " + appointment.getAppointmentID() + ") with you" +
               " on " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}
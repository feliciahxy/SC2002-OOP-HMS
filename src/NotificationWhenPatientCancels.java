/**
 * Class responsible for creating notification messages when a patient cancels an appointment.
 * Implements the {@link AppointmentNotificationForDoctorCreator} interface.
 * @author Chew Jin Cheng 
 * @version 1.0
 * @since 2024-10-08
 */
public class NotificationWhenPatientCancels implements AppointmentNotificationForDoctorCreator {
    /**
     * Creates a notification message for the doctor when a patient cancels an appointment.
     *
     * @param appointment the {@link Appointment} that has been canceled.
     * @param patient the {@link Patient} who canceled the appointment.
     * @return a formatted message indicating the cancellation details, including the patient's name,
     *         the appointment ID, date, and time.
     */
    public String createMessage(Appointment appointment, Patient patient) {
        return "Patient " + patient.getName() + " has cancelled the appointment (ID: " + appointment.getAppointmentID() + ") on " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}

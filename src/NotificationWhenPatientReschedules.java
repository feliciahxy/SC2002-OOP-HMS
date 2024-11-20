/**
 * The NotificationWhenPatientReschedules class implements the 
 * AppointmentNotificationForDoctorCreator interface and generates 
 * notifications for doctors when a patient reschedules an appointment.
 * @author Chew Jin Cheng 
 * @version 1.0
 * @since 2024-10-08
 */
public class NotificationWhenPatientReschedules implements AppointmentNotificationForDoctorCreator {
    /**
     * Creates a notification message for the doctor when a patient reschedules an appointment.
     *
     * @param appointment the {@link Appointment} object containing the details of the rescheduled appointment.
     * @param patient the {@link Patient} object containing the details of the patient.
     * @return a formatted notification message string.
     */
    public String createMessage(Appointment appointment, Patient patient) {
        return "Patient " + patient.getName() + " has rescheduled his/her appointment (ID: " + appointment.getAppointmentID() + ") with you" +
               " to " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}

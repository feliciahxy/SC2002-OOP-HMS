/**
 * The NotificationWhenPatientSchedules class implements the 
 * AppointmentNotificationForDoctorCreator interface and generates 
 * notifications for doctors when a patient schedules an appointment.
 */
public class NotificationWhenPatientSchedules implements AppointmentNotificationForDoctorCreator {
    /**
     * Creates a notification message for the doctor when a patient schedules an appointment.
     *
     * @param appointment the {@link Appointment} object containing the details of the scheduled appointment.
     * @param patient the {@link Patient} object containing the details of the patient.
     * @return a formatted notification message string.
     */
    public String createMessage(Appointment appointment, Patient patient) {
        return "Patient " + patient.getName() + " has scheduled an appointment (ID: " + appointment.getAppointmentID() + ") with you" +
               " on " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}
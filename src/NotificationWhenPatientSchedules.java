public class NotificationWhenPatientSchedules implements AppointmentNotificationForDoctorCreator {
    public String createMessage(Appointment appointment, Patient patient) {
        return "Patient " + patient.getName() + " has scheduled an appointment (ID: " + appointment.getAppointmentID() + ") with you" +
               " on " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}
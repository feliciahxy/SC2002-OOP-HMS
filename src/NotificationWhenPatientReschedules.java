public class NotificationWhenPatientReschedules implements AppointmentNotificationForDoctorCreator {
    public String createMessage(Appointment appointment, Patient patient) {
        return "Patient " + patient.getName() + " has rescheduled his/her appointment (ID: " + appointment.getAppointmentID() + ") with you" +
               " to " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}

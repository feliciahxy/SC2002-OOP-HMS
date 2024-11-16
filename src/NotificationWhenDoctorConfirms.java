public class NotificationWhenDoctorConfirms implements AppointmentNotificationForPatientCreator {
    public String createMessage(Appointment appointment, Doctor doctor) {
        return "Dr " + doctor.getName() + " has confirmed the appointment (ID: " + appointment.getAppointmentID() + ") with you" +
               " on " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}
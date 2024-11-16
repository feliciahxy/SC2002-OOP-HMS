public class NotificationWhenDoctorCancels implements AppointmentNotificationForPatientCreator {
    public String createMessage(Appointment appointment, Doctor doctor) {
        return "Dr " + doctor.getName() + " has cancelled the appointment (ID: " + appointment.getAppointmentID() + ") with you" +
               " on " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}
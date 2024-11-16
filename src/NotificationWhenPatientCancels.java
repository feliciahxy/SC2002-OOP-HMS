public class NotificationWhenPatientCancels implements AppointmentNotificationForDoctorCreator {
    public String createMessage(Appointment appointment, Patient patient) {
        return "Patient " + patient.getName() + " has cancelled the appointment (ID: " + appointment.getAppointmentID() + ") on " + appointment.getDate() + " November at " + appointment.getTime() + ".";
    }
}

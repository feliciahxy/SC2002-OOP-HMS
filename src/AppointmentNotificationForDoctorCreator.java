public interface AppointmentNotificationForDoctorCreator extends AppointmentNotificationMessageCreator {
    String createMessage(Appointment appointment, Patient patient);
}
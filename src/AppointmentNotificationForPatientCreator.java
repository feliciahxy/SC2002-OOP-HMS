public interface AppointmentNotificationForPatientCreator extends AppointmentNotificationMessageCreator {
    String createMessage(Appointment appointment, Doctor doctor);
}

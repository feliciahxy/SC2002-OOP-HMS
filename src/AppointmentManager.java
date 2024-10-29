import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AppointmentManager {
    private final List<Appointment> appointmentList;

    public AppointmentManager() {
        appointmentList = new ArrayList<>();
        loadAppointmentsFromCSV("../data/Appointment.csv");
    }

    private void loadAppointmentsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");
                String appointmentID = fields[0];
                String patientID = fields[1];
                String doctorID = fields[2];
                String slotDate = fields[3];
                String status = fields[4];

                Appointment appointment = new Appointment(appointmentID, patientID, doctorID, slotDate, status);
                appointmentList.add(appointment);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    public Appointment findAppointmentByID(String appointmentID) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                return appointment;
            }
        }
        return null;
    }

    public void saveAppointmentsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../data/Appointment.csv"))) {
            writer.write("appointmentID,patientID,doctorID,slot_date,status\n");
            for (Appointment appointment : appointmentList) {
                writer.write(appointment.getAppointmentID() + "," + appointment.getPatientID() + "," +
                        appointment.getDoctorID() + "," + appointment.getDate() + "," + appointment.getStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }
}

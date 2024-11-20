import java.io.*;
import java.util.ArrayList;

/**
 * The {@code AppointmentManager} class is responsible for managing appointments, 
 * including loading them from a CSV file, writing them to a CSV file, and 
 * providing access to the appointment list.
 */
public class AppointmentManager {
    private final ArrayList<Appointment> appointmentList;

    /**
     * Constructs an {@code AppointmentManager} instance and initializes the appointment list
     * by loading data from the specified CSV file.
     */
    public AppointmentManager() {
        appointmentList = new ArrayList<>();
        loadAppointmentsFromCSV("../data/Appointment.csv");
    }

    /**
     * Loads appointments from a CSV file and populates the {@code appointmentList}.
     *
     * @param filePath the path of the CSV file containing appointment data.
     */

    private void loadAppointmentsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length < 6) {
                    continue; 
                }

                String appointmentID = fields[0];
                String patientID = fields[1];
                String doctorID = fields[2];
                String date = fields[3];
                String time = fields[4];
                String status = fields[5];

                Appointment appointment = new Appointment(appointmentID, patientID, doctorID, date, time, status);
                appointmentList.add(appointment);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    /**
     * Writes the current list of appointments to a CSV file.
     */
    public void writeAppointmentsToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("../data/Appointment.csv"))) {
            // Write the header
            writer.write("appointmentID,patientID,doctorID,date,time,status\n");
    
            // Write each appointment
            for (Appointment appointment : appointmentList) {
                writer.write(appointment.getAppointmentID() + "," +
                    appointment.getPatientID() + "," +
                    appointment.getDoctorID() + "," +
                    appointment.getDate() + "," +
                    appointment.getTime() + "," +
                    appointment.getStatus() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }    

    /**
     * Returns the list of appointments managed by this {@code AppointmentManager}.
     *
     * @return an {@link ArrayList} of {@link Appointment} objects.
     */
    public ArrayList<Appointment> getAppointmentList() {
        return appointmentList;
    }
}
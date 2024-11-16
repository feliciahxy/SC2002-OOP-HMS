import java.io.*;
import java.util.ArrayList;

public class AppointmentManager {
    private final ArrayList<Appointment> appointmentList;

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

    // public Appointment findAppointmentByID(String appointmentID) {
    //     for (Appointment appointment : appointmentList) {
    //         if (appointment.getAppointmentID().equals(appointmentID)) {
    //             return appointment;
    //         }
    //     }
    //     return null;
    // }

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

    public ArrayList<Appointment> getAppointmentList() {
        return appointmentList;
    }
}
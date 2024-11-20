import java.util.*;

public class Doctor extends Staff {
    private Schedule schedule;

    public Doctor(
        String id,
        String name,
        String role,
        String gender,
        int age,
        String password,
        Schedule schedule
    )
    {
        super(id, name, role, gender, age, password);
        this.schedule = schedule;
    }

    // actual doctor functions

    public void viewPatientRecords(ArrayList<Patient> patientUsers, ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Appointment> appointments) {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> underCarePatientIDs = this.underCarePatientIDs();
        String inputPatientID;

        System.out.println("View Patient Records");
        System.out.println("Enter PatientID: ");

        String regex = "^P\\d{4}$";

        while (true) {
            inputPatientID = sc.nextLine();

            if (!inputPatientID.matches(regex)) {
                System.out.println("Invalid format. Patient ID must start with 'P' followed by 4 digits.");
                continue;
            }

            if (underCarePatientIDs.contains(inputPatientID)) {
                break; 
            } else {
                System.out.println("Patient not under your care.");
            }
        }

        Patient selectedPatient = null;

        for (Patient patient : patientUsers) {
            if (patient.getPatientID().equals(inputPatientID)) {
                selectedPatient = patient;
                break;
            }
        }

        selectedPatient.viewMedicalRecord(appointmentOutcomes, appointments);
    }



    public void updatePatientRecord(ArrayList<Patient> patientUsers, ArrayList<AppointmentOutcome> appointmentOutcomes, ArrayList<Appointment> appointments) {
        Scanner sc = new Scanner(System.in);

        ArrayList<String> underCarePatientIDs = this.underCarePatientIDs();
        String inputPatientID;

        System.out.println("View Patient Records");

        String regex = "^P\\d{4}$";

        while (true) {
            System.out.println("Enter PatientID: ");
            inputPatientID = sc.nextLine();

            if (!inputPatientID.matches(regex)) {
                System.out.println("Invalid format. Patient ID must start with 'P' followed by 4 digits.");
                continue;
            }

            if (underCarePatientIDs.contains(inputPatientID)) {
                break; 
            } else {
                System.out.println("Patient not under your care.");
            }
        }

        System.out.print("Which appointment do you want to update? Enter the appointment ID: ");
        String apptID = sc.nextLine();

        boolean validID = false;
        ArrayList<String> patientAppointmentIDs = this.getPatientAppointmentIDs(inputPatientID, appointments);
        
        AppointmentOutcome selectedAppointmentOutcome = null;

        while (!validID) {
            for (AppointmentOutcome appointmentOutcome : appointmentOutcomes) {
                if (appointmentOutcome.getAppointmentID().equals(apptID) && patientAppointmentIDs.contains(apptID)) {
                    validID = true;
                    selectedAppointmentOutcome = appointmentOutcome;
                    System.out.println("\nAppointment found: " + appointmentOutcome);
                    break; 
                }
            }

            if (!validID) {
                System.out.print("Invalid Appointment ID. Please try again: ");
                System.out.print("Which appointment do you want to update? Enter the appointment ID: ");
                apptID = sc.nextLine(); 
            }
        }
        
        int choice = 0;
        while (choice != -1) { 
            System.out.println("\nWhat do you want to update");
            System.out.println("[1] Diagnosis");
            System.out.println("[2] Treatment");
            System.out.println("[3] Consultation Notes");
            System.out.println("[4] Prescribed Medicine");
            System.out.println("Input -1 to quit");

            try {
                choice = sc.nextInt();
                sc.nextLine(); 
            } catch (InputMismatchException e) {
                System.out.println("Invalid Input. Please enter a valid number.");
                sc.nextLine(); 
                continue;  
            }

            switch(choice) {
                case 1:
                    System.out.println("Enter additional diagnosis");
                    String newDiagnosis = sc.nextLine();
                    selectedAppointmentOutcome.setDiagnosis(selectedAppointmentOutcome.getDiagnosis() + " " + newDiagnosis);
                    System.out.println("Diagnosis updated!");
                    break;

                case 2:
                    System.out.println("Enter additional service type");
                    String newServiceType = sc.nextLine();
                    selectedAppointmentOutcome.setServiceType(selectedAppointmentOutcome.getServiceType() + " " + newServiceType);
                    System.out.println("Service Type updated!");
                    break;

                case 3:
                    System.out.println("Enter additional consultation notes");
                    String newNotes = sc.nextLine();
                    selectedAppointmentOutcome.setNotes(selectedAppointmentOutcome.getNotes() + " " + newNotes);
                    System.out.println("Consultation Notes updated!");
                    break;

                case 4:
                    System.out.println("Enter additional prescription");
                    String newMedicine = sc.nextLine();
                    PrescribedMedication newPrescription = new PrescribedMedication(apptID, newMedicine, "pending");
                    selectedAppointmentOutcome.getPrescribedMedicationList().add(newPrescription);
                    System.out.println("Prescription updated!");
                    break;
                case -1:
                    break;
                default:
                    System.out.println("Invalid Input. Please enter a valid number.");
            }
        }
    }


    public void viewPersonalSchedule() {
        ArrayList<String> slots = this.schedule.getSlots();
        System.out.println("\nFormat Explanation \nPatientID-0 means pending, PatientID-1 means confirmed");
        System.out.println("N/A means the slot is free.");
        System.out.println("N/F means the slot is not free.");
        System.out.println("\nNovember Schedule");
    
        System.out.printf("%-10s %-15s %-15s %-15s\n", "Day", "0900-1000", "1000-1100", "1100-1200");
    
        for (int i = 0; i < slots.size(); i += 3) {
            int day = (i / 3) + 1; 
            System.out.printf("%-10d %-15s %-15s %-15s\n", day, 
                              slots.get(i), slots.get(i + 1), slots.get(i + 2));
        }
    }

    public void setAvailability() {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> slots = this.schedule.getSlots();

        System.out.println("\nUpdate Availability");
        System.out.println("Enter date: ");
        int selectedDate = sc.nextInt();
        System.out.println("Slot 1: 0900-1000, Slot 2: 1000-1100, Slot 3: 1100-1200");
        System.out.println("Enter slot: ");
        int selectedSlot = sc.nextInt();
        sc.nextLine();

        int slotIndex = (selectedDate - 1) * 3 + selectedSlot - 1;
        System.out.println("Current Arrangement: " + slots.get(slotIndex));

        if (!slots.get(slotIndex).equals("N/A") && ! slots.get(slotIndex).equals("N/F")) {
            System.out.println("There is an ongoing appointment for this slot. Unable to set availability.");
            return;
        }

        System.out.print("\nAre you available for this slot? (yes/no): ");
        String response = sc.nextLine().trim().toLowerCase(); 
        
        while (!(response.equals("yes") || response.equals("no"))) {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            System.out.print("\nAre you available for this slot? (yes/no): ");
            response = sc.nextLine().trim().toLowerCase();
        }
        
        if (response.equals("yes")) {
            schedule.setSlot(slotIndex, "N/A");
        } else if (response.equals("no")) {
            schedule.setSlot(slotIndex, "N/F");
        }
        System.out.println("Availability Updated Successfully!");
    }

    public void acceptOrDeclineAppointment(ArrayList<Appointment> appointments, Doctor doctor, ArrayList<Notification> notifications) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> slots = this.schedule.getSlots();
        int count = 1;

        ArrayList<Integer> slotIndexes = new ArrayList<>();

        System.out.println("\nPending Appointments");
        for (int i = 0; i < 90; i ++) {
            int day = i / 3 + 1;
            int daySlot = i % 3 + 1;
            String time = Schedule.slotToTime(daySlot);

            if (slots.get(i).endsWith("-0")) {
                System.out.println("[" + count + "] " + day + " November " + time + " " + slots.get(i).substring(0, 5));
                slotIndexes.add(i);
                count++;
            }
        }

        int choice = -1;
        while (true) {
            if (count == 1) {
                System.out.print("NIL\n");
                return;
            }
            System.out.print("Enter the pending appointment you would like to accept or decline (1-" + String.valueOf(count - 1) + "): ");
            try {
                choice = sc.nextInt();
                if (choice >= 1 && choice <= count - 1) {
                    break;
                } else {
                    System.out.println("Invalid Input. Try Again.");
                }
            } catch (InputMismatchException e) {
                sc.next(); 
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        

        int input = -1;
        while (true) {
            System.out.println("[1] Accept");
            System.out.println("[2] Decline");
            System.out.println("Enter choice: ");
            try {
                input = sc.nextInt();
                if (input == 1 || input == 2) break;
                else System.out.println("Invalid Input. Try Again.");
            } catch (InputMismatchException e) {
                sc.next();
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        

        int slotIndex = slotIndexes.get(choice - 1);
        int date = slotIndex / 3 + 1;
        int slot = slotIndex % 3 + 1;
        String formattedDate = String.format("%02d", date);
        String time = Schedule.slotToTime(slot);
        
        Appointment selectedAppointment = null;
        for (Appointment appointment : appointments) {
            if (appointment.getDate().equals(formattedDate) && appointment.getTime().equals(time) && appointment.getDoctorID().equals(this.getId())) {
                selectedAppointment = appointment;
                break;
            }
        }

        if (input == 1) {
            slots.set(slotIndex, slots.get(slotIndex).substring(0,5) + "-1");
            selectedAppointment.setStatus("confirmed");
            System.out.println("Appointment confirmed successfully!");
            AppointmentNotificationForPatientCreator notificationWhenDoctorConfirms = new NotificationWhenDoctorConfirms();
            String message = notificationWhenDoctorConfirms.createMessage(selectedAppointment, doctor);
            String notificationID = NotificationIDGenerator.generateNotificationID(notifications);
            notifications.add(NotificationBuilder.buildNotification(notificationID, selectedAppointment.getPatientID(), message));
        }
        else if (input == 2) {
            slots.set(slotIndex, "N/A");
            selectedAppointment.setStatus("cancelled");
            System.out.println("Appointment cancelled successfully!");
            AppointmentNotificationForPatientCreator notificationWhenDoctorCancels = new NotificationWhenDoctorCancels();
            String message = notificationWhenDoctorCancels.createMessage(selectedAppointment, doctor);
            String notificationID = NotificationIDGenerator.generateNotificationID(notifications);
            notifications.add(NotificationBuilder.buildNotification(notificationID, selectedAppointment.getPatientID(), message));
        } 
    }

    public void viewUpcomingAppointments() {
        System.out.println("\nUpcoming confirmed Appointments");
        ArrayList<String> slots = this.schedule.getSlots();

        int count = 1;
        for (int i = 0; i < 90; i ++) {
            int day = i / 3 + 1;
            int daySlot = i % 3 + 1;
            String time = Schedule.slotToTime(daySlot);

            if (slots.get(i).endsWith("-1")) {
                System.out.println("[" + count + "] " + day + " November " + time + " " + "PatientID: "+ slots.get(i).substring(0, 5));
                count++;
            }
        }

        if (count == 1) System.out.println("NIL\n");
    }

    public void recordAppointmentOutcome(ArrayList<Appointment> appointments, ArrayList<AppointmentOutcome> appointmentOutcomes) {

        Scanner sc = new Scanner(System.in);

        System.out.print("The format should be AP followed by 4 digits (e.g., AP0001).\nEnter AppointmentID: ");
        String appointmentID;
        while (true) {
            appointmentID = sc.nextLine();
            if (Appointment.isValidAppointmentID(appointmentID)) {
                if (!Appointment.inAppointments(appointments, appointmentID)) {
                    System.out.print("Appointment not found. Try Again: ");
                    continue;
                }
                if (Appointment.doctorCanWriteOutcome(appointments, appointmentID, this.getId())) {
                    break;
                }
                else {
                    System.out.print("You do not have access to this AppointmentID. Try Again: ");
                }
            }
            else {
                System.out.println("Invalid Input Format.");
            }
        }

        Appointment selectedAppointment = null;

        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentID().equals(appointmentID)) {
                selectedAppointment = appointment;
                break;
            }
        }

        selectedAppointment.setStatus("completed");
        int date = Integer.parseInt(selectedAppointment.getDate());
        int slot = Appointment.timeToSlot(selectedAppointment.getTime());
        int slotIndex = (date -  1) * 3 + slot - 1;
        this.schedule.getSlots().set(slotIndex, "P1001-2");

        System.out.println("What is the diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.println("What is the service type: ");
        String serviceType = sc.nextLine();
        System.out.println("Any additional notes: ");
        String notes = sc.nextLine();

        String medicine = "";
        ArrayList<PrescribedMedication> allPrescribedMedications = new ArrayList<>();
        while (!medicine.equals("-1")) {
            System.out.println("Input -1 to quit.");
            System.out.println("Enter medication given: ");
            medicine = sc.nextLine();
            if (medicine.equals("-1")) break;
            PrescribedMedication prescribedMedication = new PrescribedMedication(appointmentID, medicine, "pending");
            allPrescribedMedications.add(prescribedMedication);
        }

        AppointmentOutcome appointmentOutcome = new AppointmentOutcome(appointmentID, diagnosis, serviceType, selectedAppointment.getDate(), allPrescribedMedications, notes);
        appointmentOutcomes.add(appointmentOutcome);

        System.out.println();
        System.out.println("The following appointment outcome has been added successfully.");
        System.out.println(appointmentOutcome);
    }

    // Helper

    public ArrayList<String> underCarePatientIDs() {
        ArrayList<String> underCarePatientIDs = new ArrayList<>();
        ArrayList<String> slots = this.schedule.getSlots();
        for (String slot : slots) {
            if (slot.contains("P")) {
                underCarePatientIDs.add(slot.substring(0, 5));
            }
        }
        return underCarePatientIDs;
    }

    public ArrayList<String> getPatientAppointmentIDs(String patientID, ArrayList<Appointment> appointments) {
        ArrayList<String> getPatientAppointmentIDs = new ArrayList<>();
        for (Appointment appointment : appointments) {
            if (appointment.getPatientID().equals(patientID)) {
                getPatientAppointmentIDs.add(appointment.getAppointmentID());
            }
        }

        return getPatientAppointmentIDs;
    }
}

//     // writing to CSV



//     public void writeAppointmentsToCSV() {
//         try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/Appointment.csv"))) {
//             bw.write("appointmentID,patientID,doctorID,date,time,stauts\n");
//             for (Appointment appointment : appointmentList) {
//                 bw.write(appointment.getAppointmentID() + "," + appointment.getPatientID() + "," +
//                         appointment.getDoctorID() + "," + appointment.getDate() + "," +
//                         appointment.getTime() + "," + appointment.getStatus());
//             }
//         } catch (IOException e) {
//             System.out.println("Error writing to Appointment file: " + e.getMessage());
//         }
//     }

//     public void writeAppointmentOutcomesToCSV() {
//         try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/AppointmentOutcome.csv"))) {
//             bw.write("appointmentID,diagnosis,serviceType,date,notes\n");
//             for (AppointmentOutcome appointmentOutcome : appointmentOutcomesList) {
//                 bw.write(appointmentOutcome.getAppointmentID() + "," + appointmentOutcome.getDiagnosis() + "," +
//                         appointmentOutcome.getServiceType() + "," + appointmentOutcome.getDate() + "," +
//                         appointmentOutcome.getNotes());
//             }
//         } catch (IOException e) {
//             System.out.println("Error writing to AppointmentOutcome file: " + e.getMessage());
//         }
//     }

//     public void writePrescribedMedicationToCSV() {
//         try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/PrescribedMedication.csv"))) {
//             bw.write("appointmentID,medicine,status\n");
//             for (PrescribedMedication prescribedMedication : prescribedMedicationList) {
//                 bw.write(prescribedMedication.getAppointmentID() + "," + prescribedMedication.getMedicationName() + "," +
//                     prescribedMedication.getMedicationStatus());
//             }
//         } catch (IOException e) {
//             System.out.println("Error writing to PrescribedMedication file: " + e.getMessage());
//         }
//     }

//     public void writeSchedulesToCSV() {
//         try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/Schedule.csv"))) {
//             bw.write("doctorIDs,1/1,2/1,3/1,1/2,2/2,3/2,1/3,2/3,3/3,1/4,2/4,3/4,1/5,2/5,3/5,1/6,2/6,3/6,1/7,2/7,3/7,1/8,2/8,3/8,1/9,2/9,3/9,1/10,2/10,3/10,1/11,2/11,3/11,1/12,2/12,3/12,1/13,2/13,3/13,1/14,2/14,3/14,1/15,2/15,3/15,1/16,2/16,3/16,1/17,2/17,3/17,1/18,2/18,3/18,1/19,2/19,3/19,1/20,2/20,3/20,1/21,2/21,3/21,1/22,2/22,3/22,1/23,2/23,3/23,1/24,2/24,3/24,1/25,2/25,3/25,1/26,2/26,3/26,1/27,2/27,3/27,1/28,2/28,3/28,1/29,2/29,3/29,1/30,2/30,3/30\n");
//             for (Schedule schedule : scheduleList) {
//                 bw.write(schedule.getDoctorID() + "," + schedule.getSlots());
//             }
//         } catch (IOException e) {
//             System.out.println("Error writing to Schedule file: " + e.getMessage());
//         }
//     }

//     // public void writePatientsToCSV() {
//     //     try (BufferedWriter bw = new BufferedWriter(new FileWriter("../data/AppointmentOutcome.csv"))) {
//     //         bw.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Phone Number,Email,Password\n");
//     //         for (Patient patient : patientList) {
//     //             bw.write(patient.getPatientID() + "," + patient.getName() + "," +
//     //                 patient.getDob() + "," + patient.getGender() + "," + patient.getBloodType() + "," + 
//     //                 patient.getPhoneNumber() + "," + patient.getEmail() + "," + patient.getPassword());
//     //         }
//     //     } catch (IOException e) {
//     //         System.out.println("Error writing to Patient file: " + e.getMessage());
//     //     }
//     // }

// }
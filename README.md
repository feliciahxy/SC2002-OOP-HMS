# Hospital Management System (HMS)

![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=flat&logo=openjdk&logoColor=white)
![Build](https://github.com/dreonic/SC2002-CAMs/actions/workflows/maven-pr.yml/badge.svg)

Hospital Management System(HMS) is a CLI-based application aimed at automating the management of hospital operations, including patient management, appointment scheduling, staff management, and billing. The system is expected to facilitate efficient management of hospital resources, enhance patient care, and streamline administrative processes.

## Group Members

SC2002 Object Oriented Design & Programming SCEB Group 5:
| Name                   | Email                    |                                                                      GitHub Profile                                                                       |
| ---------------------- | ------------------------ | :-------------------------------------------------------------------------------------------------------------------------------------------------------: |
| Heng Xin Yu Felicia  | xheng004@e.ntu.edu.sg |        [![GitHub](https://img.shields.io/badge/feliciahxy-%23121011.svg?style=flat-square&logo=github&logoColor=white)](https://github.com/feliciahxy)        |
| Denzel Tan Yong Liang         | denz0007@e.ntu.edu.sg    |         [![GitHub](https://img.shields.io/badge/D1zzy123-%23121011.svg?style=flat-square&logo=github&logoColor=white)](https://github.com/D1zzy123)         |
| Chong Wei Thai       | ch0009ai@e.ntu.edu.sg    |      [![GitHub](https://img.shields.io/badge/weithai-%23121011.svg?style=flat-square&logo=github&logoColor=white)](https://github.com/weithai)      |
| Chew Jin Cheng        | jchew051@e.ntu.edu.sg |    [![GitHub](https://img.shields.io/badge/ChewJinCheng-%23121011.svg?style=flat-square&logo=github&logoColor=white)](https://github.com/ChewJinCheng)    |
| Chua Yu Hui | ychua056@e.ntu.edu.sg    | [![GitHub](https://img.shields.io/badge/chuayhhh-%23121011.svg?style=flat-square&logo=github&logoColor=white)](https://github.com/chuayhhh) |

## Directory Layout

```
./
├── data/
│   ├── Appointment.csv                   
│   ├── AppointmentOutcome.csv
│   ├── Medicine_List.csv
│   ├── Notification.csv
│   ├── Patient_List.csv
│   ├── PrescribedMedication.csv
│   ├── ReplenishRequest.csv
│   ├── Schedule.csv
│   └── Staff_List.csv
├── src/
│   ├── Main.java
│   └── ...
└── README.md
```

## Prerequisites: 
- `Java Development Kit (JDK)`
- `Git`

## Running the Application

To run the application, clone this repository to your local machine.

```bash
git clone https://github.com/feliciahxy/SC2002-OOP.git
cd SC2002-OOP
java Main
```

The initial patient and staff list bundled inside can be seen at `/data`. The default password for a user is `password`. Users will be able to change passwords upon intiial login. Application data will be saved when exiting; data will also be saved if application quits unexpectedly.

## Description of Files and Folders

### `/src`
This directory contains the main source code for the HMS application. Each class represents a component of the system according to the UML design.

- **`Administrator.java`**: Manages hospital staff and inventory, providing administrative functionalities.
- **`Appointment.java`**: Represents appointment details, including scheduling, rescheduling, and cancellation functionalities.
- **`AppointmentManager.java`**: Manages the loading, retrieval, and saving of appointment data from/to the Appointment.csv file.
- **`AppointmentNotificationForDoctorCreator.java`**: Defines an interface for creating customized appointment notification messages for doctors based on appointment and patient details.
- **`AppointmentNotificationForPatientCreator.java`**: Defines an interface for creating customized appointment notification messages for patients based on appointment and doctor details.
- **`AppointmentNotificationMessageCreator.java`**: Extends the NotificationMessageCreator interface, specializing it for generating appointment-related notifications.
- **`AppointmentOutcome.java`**: Manages the outcomes of appointments, including prescribed medications.
- **`AppointmentOutcomeManager.java`**: Manages the appointment outcomes and associated prescribed medications, including loading data from CSV files, linking prescribed medications with appointment outcomes, and writing updated data back to the CSV files.
- **`CSVNotificationReader.java`**: Implements the NotificationReader interface to read and parse notifications from a specified CSV file into a list of Notification objects.
- **`CSVNotificationWriter.java`**: Implements the NotificationWriter interface to write a list of Notification objects into a specified CSV file with appropriate formatting.
- **`Doctor.java`**: Implements functionalities for doctors, including managing their schedules, viewing and updating patient records, accepting or declining appointments, and recording appointment outcomes.
- **`Inventory.java`**: Represents the inventory of medications available in the hospital.
- **`Main.java`**: The main entry point for the application where the program starts executing.
- **`Medication.java`**: Models medication's name, quantity, and low stock level, with methods for managing and updating stock.
- **`MedicationManager.java`**: Manages a list of Medication objects, loading from and writing to a CSV file for tracking stock levels.
- **`Notification.java`**: Represents a notification with details such as ID, receiver, message, and status, and provides methods to retrieve and update its status.
- **`NotificationBuilder.java`**: Provides a static method to create a new Notification with a default "pending" status.
- **`NotificationIDGenerator.java`**: Generates a unique notification ID based on the current
- **`NotificationManager.java`**: Manages a list of notifications, allowing additions and retrievals.
- **`NotificationMessageCreator.java`**: Defines an interface for creating notification messages, with a default implementation for a generic message.
- **`NotificationReader.java`**: Defines an interface for reading notifications from a specified file path.
- **`NotificationWhenDoctorCancels.java`**: Creates a notification message when a doctor cancels an appointment with a patient.
- **`NotificationWhenDoctorConfirms.java`**: Creates a notification message when a doctor confirms an appointment with a patient.
- **`NotificationWhenPatientCancels.java`**: Creates a notification message when a patient cancels an appointment with a doctor.
- **`NotificationWhenPatientReschedules.java`**: Creates a notification message when a patient reschedules an appointment with a doctor.
- **`NotificationWhenPatientSchedules.java`**: Creates a notification message when a patient schedules an appointment with a doctor.
- **`NotificationWriter.java`**: Defines an interface for writing notifications to a specified file path.
- **`Patient.java`**: Contains methods and properties specific to patient functionalities, including viewing medical records and managing appointments.
- **`Pharmacist.java`**: Handles operations related to pharmacists, such as managing prescriptions and inventory.
- **`PharmacistMain.java`**: Main interface for a pharmacist to manage appointment outcomes, prescriptions, medication inventory, and replenishment requests.
- **`PrescribedMedication.java`**: Represents prescribed medications and their statuses.
- **`ReplenishmentRequest.java`**: Represents a replenishment request with details like request ID, medicine, quantity, and status.
- **`ReplenishmentRequestManager.java`**: Manages loading, saving, and retrieving replenishment requests from a CSV file.
- **`Schedule.java`**: Represents a doctor's schedule with their availability for each day and slot.
- **`ScheduleInitializer.java`**: Initializes the doctor schedule by reading doctor IDs from a staff list and updating the schedule file accordingly.
- **`ScheduleManager.java`**: Manages the loading, saving, and updating of doctor schedules from a CSV file.
- **`Staff.java`**: Represents a staff member, extending the User class with common properties like ID, name, role, and password.
- **`StaffManager.java`**: Manages staff members by categorizing them into doctors, pharmacists, and administrators, and provides methods to find staff by ID.
- **`User.java`**: An abstract class defining the common properties and methods for all users (Patient, Doctor, Pharmacist, Administrator).
- **`UserManager.java`**: Manages user login and authentication, ensuring that users have the correct access based on their roles.

### `data`
This directory contains CSV files used for initializing the HMS with data.

- **`Appointment.csv`**: Contains a list of appointment details.
- **`AppointmentOutcome.csv`**: Contains a list of completed appointment outcomes.
- **`Medicine_List.csv`**: Contains a list of medications and their stock levels for inventory management.
- **`Notification.csv`**: Contains a list of notification details sent within the system.
- **`Patient_list.csv`**: Contains a list of patients with their details for populating the system.
- **`PrescribedMedication.csv`**: Contains a list of prescribed medications linked to appointments.
- **`ReplenishRequest.csv`**: Contains a list of tracked requests for replenishing medicine stock.
- **`Schedule.csv`**: Contains a list of work schedules for doctors.
- **`Staff_list.csv`**: Contains a list of hospital staff members (doctors and pharmacists) for system initialization.

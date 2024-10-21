
## Instructions to run the system
0. Prerequisites: 
- `Java Development Kit (JDK)`
- `Git`

## Getting Started
1. Clone this repository to your local machine.
2. Navigate to the Project Directory - `/src` directory and compile the Java files using a Java compiler: `javac  User.java UserManager.java Main.java`
3. After compilation, run the `Main.java` file to start the application while in src file path: `java Main`
4. Follow the prompts in the CLI to log in and access the functionalities available for your user role. 
Log In with a valid ID, default password is "password"
5. Change password upon initial login

## Description of Files and Folders

### `/src/main`
This directory contains the main source code for the HMS application. Each class represents a component of the system according to the UML design.

- `Main.java`: The main entry point for the application where the program starts executing.
- `User.java`: An abstract class defining the common properties and methods for all users (Patient, Doctor, Pharmacist, Administrator).
- `UserManager.java`: Manages user login and authentication, ensuring that users have the correct access based on their roles.
- `Patient.java`: Contains methods and properties specific to patient functionalities, including viewing medical records and managing appointments.
- `Doctor.java`: Defines the functionalities for doctors, including managing patient records and appointments.
- `Pharmacist.java`: Handles operations related to pharmacists, such as managing prescriptions and inventory.
- `Administrator.java`: Manages hospital staff and inventory, providing administrative functionalities.
- `Appointment.java`: Represents appointment details, including scheduling, rescheduling, and cancellation functionalities.
- `MedicalRecord.java`: Manages the medical records associated with each patient.
- `Inventory.java`: Represents the inventory of medications available in the hospital.
- `Medication.java`: Defines individual medication properties and methods.
- `Schedule.java`: Manages the availability of doctors for appointments.
- `ContactInfo.java`: Represents patient contact information.
- `AppointmentOutcome.java`: Manages the outcomes of appointments, including prescribed medications.
- `PrescribedMedication.java`: Represents prescribed medications and their statuses.

### `/src/data`
This directory contains CSV files used for initializing the HMS with data.

- `Pattient_list.csv`: Contains a list of patients with their details for populating the system.
- `Staff_list.csv`: Contains a list of hospital staff members (doctors and pharmacists) for system initialization.
- `Medicine_List.csv`: Contains a list of medications and their stock levels for inventory management.

## Assumptions
- The system assumes that all users have a unique hospital ID.
- The default password for all users is "password", which they are prompted to change upon initial login.
- The application does not use any external databases or graphical interfaces.



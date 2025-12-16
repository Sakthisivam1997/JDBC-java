package Sample;

import java.sql.*;
import java.util.Scanner;

public class JDBCHospital {
	static final String URL = "jdbc:mysql://localhost:3306/hospital_db";
	static final String USER = "root";
	static final String PASS = "Sakthi@1997"; 

    public static void main(String[] args) {
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
        	Connection conn = DriverManager.getConnection(URL, USER, PASS);
        	Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n=== HOSPITAL MANAGEMENT MENU ===");
                System.out.println("1. Add Doctor");
                System.out.println("2. Add Patient");
                System.out.println("3. Book Appointment");
                System.out.println("4. View Appointments");
                System.out.println("5. View All Doctors");
                System.out.println("6. View All Patients");
                System.out.println("7. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();  
                
                
             // ADD DOCTOR
                switch (choice) {
                    case 1:  
                        System.out.print("Enter doctor name: ");
                        String dname = scanner.nextLine();
                        System.out.print("Enter specialization: ");
                        String specialization = scanner.nextLine();

                        String insertDoctor = "INSERT INTO doctors (name, specialization) VALUES (?,?)";
                        PreparedStatement addDoc = conn.prepareStatement(insertDoctor);
                        addDoc.setString(1, dname);
                        addDoc.setString(2, specialization);
                        addDoc.executeUpdate();
                        addDoc.close();
                        System.out.println("Doctor added successfully.");
                        break;

                    case 2: // ADD PATIENT
                        System.out.print("Enter patient name: ");
                        String pname = scanner.nextLine();
                        System.out.print("Enter age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter gender: ");
                        String gender = scanner.nextLine();
                        System.out.print("Enter disease: ");
                        String disease = scanner.nextLine();

                        String insertPatient = "INSERT INTO patients (name, age, gender, disease) VALUES (?, ?, ?, ?)";
                        PreparedStatement Pat = conn.prepareStatement(insertPatient);
                        Pat.setString(1, pname);
                        Pat.setInt(2, age);
                        Pat.setString(3, gender);
                        Pat.setString(4, disease);
                        Pat.executeUpdate();
                        Pat.close();
                        System.out.println("Patient added successfully.");
                        break;

                    case 3: // BOOK APPOINTMENT
                        System.out.print("Enter Patient ID: ");
                        int pid = scanner.nextInt();
                        System.out.print("Enter Doctor ID: ");
                        int did = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();

                        String insertApp = "INSERT INTO appointments (patient_id, doctor_id, appointment_date) VALUES (?, ?, ?)";
                        PreparedStatement bookApp = conn.prepareStatement(insertApp);
                        bookApp.setInt(1, pid);
                        bookApp.setInt(2, did);
                        bookApp.setString(3, date);
                        bookApp.executeUpdate();
                        bookApp.close();
                        System.out.println("Appointment booked successfully.");
                        break;

                    case 4: // VIEW APPOINTMENTS
                        String sql = """
                            SELECT a.appointment_id, p.name AS patient, p.disease, d.name AS doctor,
                                   d.specialization, a.appointment_date
                            FROM appointments a
                            JOIN patients p ON a.patient_id = p.patient_id
                            JOIN doctors d ON a.doctor_id = d.doctor_id
                            ORDER BY a.appointment_date
                            """;
                        Statement s = conn.createStatement();
                        ResultSet rs = s.executeQuery(sql);
                        System.out.println("\n--- Appointments ---");
                        while (rs.next()) {
                            System.out.println("Appointment ID: " + rs.getInt("appointment_id") +
                                    " | Date: " + rs.getDate("appointment_date") +
                                    " | Patient: " + rs.getString("patient") +
                                    " | Disease: " + rs.getString("disease") +
                                    " | Doctor: " + rs.getString("doctor") +
                                    " (" + rs.getString("specialization") + ")");
                        }
                        rs.close();
                        s.close();
                        break;

                    case 5: // VIEW DOCTORS
                        Statement st1 = conn.createStatement();
                        ResultSet VD = st1.executeQuery("SELECT * FROM doctors");
                        System.out.println("\n--- Doctors ---");
                        while (VD.next()) {
                            System.out.println("ID: " + VD.getInt("doctor_id") + " | Name: " + VD.getString("name")
                                    + " | Specialization: " + VD.getString("specialization"));
                        }
                        VD.close();
                        st1.close();
                        break;
                        // VIEW PATIENTS
                    case 6: 
                        Statement st2 = conn.createStatement();
                        ResultSet vp = st2.executeQuery("SELECT * FROM patients");
                        System.out.println("\n--- Patients ---");
                        while (vp.next()) {
                            System.out.println("ID: " + vp.getInt("patient_id") + " | Name: " + vp.getString("name")
                                    + " | Age: " + vp.getInt("age") + " | Gender: " + vp.getString("gender")
                                    +   " | Disease: " + vp.getString("disease"));
                        }
                        vp.close();
                        st2.close();
                        break;

                    case 7: 
						EXIT running = true;
                        System.out.println("Exiting...");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

            conn.close();
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

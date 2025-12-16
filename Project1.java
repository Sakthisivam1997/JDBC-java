package Sample;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Project1 {
static final String URL = "jdbc:mysql://localhost:3306/jdbc"; 
static final String USER = "root";
static final String PASS = "Sakthi@1997"; 
 
public static void main(String[] args) {
try {
Class.forName("com.mysql.cj.jdbc.Driver");
Connection conn = DriverManager.getConnection(URL, USER, PASS);
Scanner scanner = new Scanner(System.in);
boolean running = true;
while (running) {
System.out.println("\n=== JDBC CRUD MENU ===");
System.out.println("1. Insert User");
System.out.println("2. Read All Users");
System.out.println("3. Update User Email");
System.out.println("4. Delete User");
System.out.println("5. Exit");
System.out.print("Enter choice: ");
int choice = scanner.nextInt();
scanner.nextLine(); // consume newline
switch (choice) {
case 1: // INSERT
System.out.print("Enter name: ");
String name = scanner.nextLine();
System.out.print("Enter email: ");
String email = scanner.nextLine();
String insertSQL = "INSERT INTO users (name, email) VALUES (?, ?)";
PreparedStatement psInsert = conn.prepareStatement(insertSQL);
psInsert.setString(1, name);
psInsert.setString(2, email);
psInsert.executeUpdate();
psInsert.close();
System.out.println("Inserted successfully.");
break;

  case 2: // READ
   String selectSQL = "SELECT * FROM users";
  Statement stmt = conn.createStatement();
   ResultSet rs = stmt.executeQuery(selectSQL);
   System.out.println("User List:");
  while (rs.next()) {
  System.out.println(rs.getInt("id") + ", " + rs.getString("name") + ", " +rs.getString("email"));

  }
rs.close();
stmt.close();
break;
case 3: // UPDATE
System.out.print("Enter name to update: ");
String nameToUpdate = scanner.nextLine();
System.out.print("Enter new email: ");
String newEmail = scanner.nextLine();

String updateSQL = "UPDATE users SET email = ? WHERE name = ?";
PreparedStatement psUpdate = conn.prepareStatement(updateSQL);
psUpdate.setString(1, newEmail);
psUpdate.setString(2, nameToUpdate);
int rowsUpdated = psUpdate.executeUpdate();
psUpdate.close();
if (rowsUpdated > 0)
System.out.println("Updated successfully.");
else
System.out.println("User not found.");
break;
case 4: // DELETE
System.out.print("Enter name to delete: ");
String nameToDelete = scanner.nextLine();

String deleteSQL = "DELETE FROM users WHERE name = ?";
PreparedStatement psDelete = conn.prepareStatement(deleteSQL);
psDelete.setString(1, nameToDelete);
int rowsDeleted = psDelete.executeUpdate();
psDelete.close();
if (rowsDeleted > 0)
System.out.println("Deleted successfully.");
else
System.out.println("User not found.");
break;

case 5: // EXIT
running = false;
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


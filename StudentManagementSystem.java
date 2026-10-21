
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentManagementSystem {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                sc.nextLine();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    searchStudent();
                    break;
                case 4:
                    updateStudent();
                    break;
                case 5:
                    deleteStudent();
                    break;
                case 0:
                    System.out.println("Exiting application...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 0);

        sc.close();
    }

    // 1. Add a student
    public static void addStudent() {

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter department: ");
        String department = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        String sql = "INSERT INTO students (name, department, email) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, email);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    // 2. View all students
    public static void viewStudents() {

        String sql = "SELECT * FROM students";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean found = false;

            System.out.println("\n----- STUDENT LIST -----");

            while (rs.next()) {
                found = true;

                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Department: " + rs.getString("department"));
                System.out.println("Email: " + rs.getString("email"));
                System.out.println("------------------------");
            }

            if (!found) {
                System.out.println("No students found.");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing students: " + e.getMessage());
        }
    }

    // 3. Search student by ID
    public static void searchStudent() {

        System.out.print("Enter student ID: ");
        int id = readInt();

        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    System.out.println("\nStudent found!");
                    System.out.println("ID: " + rs.getInt("id"));
                    System.out.println("Name: " + rs.getString("name"));
                    System.out.println("Department: " + rs.getString("department"));
                    System.out.println("Email: " + rs.getString("email"));
                } else {
                    System.out.println("No student found with ID: " + id);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching student: " + e.getMessage());
        }
    }

    // 4. Update student details
    public static void updateStudent() {

        System.out.print("Enter student ID to update: ");
        int id = readInt();

        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        System.out.print("Enter new department: ");
        String department = sc.nextLine();

        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        String sql = "UPDATE students SET name = ?, department = ?, email = ? WHERE id = ?";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, department);
            ps.setString(3, email);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("No student found with ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage());
        }
    }

    // 5. Delete a student
    public static void deleteStudent() {

        System.out.print("Enter student ID to delete: ");
        int id = readInt();

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection con = DBConnection.getCon();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("No student found with ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage());
        }
    }

    // Helper method to read an integer safely
    private static int readInt() {

        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid numeric ID: ");
            sc.nextLine();
        }

        int value = sc.nextInt();
        sc.nextLine();

        return value;
    }
}

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentController {

    
static final String URL = "jdbc:mysql://127.0.0.1:3306/schooldb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata";
static final String USER = "javauser";
static final String PASSWORD = "javapass";


    private Connection conn;

    public StudentController() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // CREATE
    public void addStudent(Student student) {
        String sql = "INSERT INTO Student (Name, Department, Marks) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            ps.setString(1, student.getName());
            ps.setString(2, student.getDepartment());
            ps.setDouble(3, student.getMarks());
            ps.executeUpdate();
            conn.commit();
            System.out.println("Student added successfully!");
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("Error adding student.");
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // READ
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Student";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("StudentID"),
                        rs.getString("Name"),
                        rs.getString("Department"),
                        rs.getDouble("Marks")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students.");
            e.printStackTrace();
        }
        return students;
    }

    // UPDATE
    public void updateStudent(Student student) {
        String sql = "UPDATE Student SET Name = ?, Department = ?, Marks = ? WHERE StudentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            ps.setString(1, student.getName());
            ps.setString(2, student.getDepartment());
            ps.setDouble(3, student.getMarks());
            ps.setInt(4, student.getStudentID());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("Student updated successfully!");
            } else {
                conn.rollback();
                System.out.println("StudentID not found. No changes made.");
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("Error updating student.");
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // DELETE
    public void deleteStudent(int studentID) {
        String sql = "DELETE FROM Student WHERE StudentID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            ps.setInt(1, studentID);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("Student deleted successfully!");
            } else {
                conn.rollback();
                System.out.println("StudentID not found. No changes made.");
            }
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("Error deleting student.");
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
}

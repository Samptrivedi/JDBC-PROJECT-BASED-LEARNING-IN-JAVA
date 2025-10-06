import java.sql.*;

public class JDBCFetchEmployee {

public static void main(String[] args) {
    String url = "jdbc:mysql://127.0.0.1:3306/companydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata";
    String user = "javauser";
    String password = "javapass";
    String query = "SELECT EmpID, Name, Salary FROM Employee";

    try {
        // Step 1: Load JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Step 2: Connect and query
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%-6s %-20s %-10s%n", "EmpID", "Name", "Salary");
            System.out.println("---------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");
                System.out.printf("%-6d %-20s %-10.2f%n", id, name, salary);
            }

        } // end inner try-with-resources

    } catch (ClassNotFoundException e) {
        System.out.println("MySQL JDBC Driver not found.");
        e.printStackTrace();
    } catch (SQLException e) {
        System.out.println("Database error occurred.");
        e.printStackTrace();
    }
} 
}
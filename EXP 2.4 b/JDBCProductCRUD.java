import java.sql.*;
import java.util.Scanner;

public class JDBCProductCRUD {

    static final String URL = "jdbc:mysql://127.0.0.1:3306/companydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata";
    static final String USER = "javauser";
    static final String PASSWORD = "javapass";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner sc = new Scanner(System.in)) {

            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected to the database successfully!");

            boolean exit = false;
            while (!exit) {
                System.out.println("\n=== Product CRUD Menu ===");
                System.out.println("1. Create Product");
                System.out.println("2. Read Products");
                System.out.println("3. Update Product");
                System.out.println("4. Delete Product");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        createProduct(conn, sc);
                        break;
                    case 2:
                        readProducts(conn);
                        break;
                    case 3:
                        updateProduct(conn, sc);
                        break;
                    case 4:
                        deleteProduct(conn, sc);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error occurred.");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    // CREATE
    private static void createProduct(Connection conn, Scanner sc) {
        System.out.print("Enter Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        String sql = "INSERT INTO Product (ProductName, Price, Quantity) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // start transaction
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            ps.executeUpdate();
            conn.commit();
            System.out.println("Product added successfully!");
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("Error adding product.");
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // READ
    private static void readProducts(Connection conn) {
        String sql = "SELECT * FROM Product";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.printf("%-10s %-20s %-10s %-10s%n", "ProductID", "ProductName", "Price", "Quantity");
            System.out.println("-----------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-10d %-20s %-10.2f %-10d%n",
                        rs.getInt("ProductID"),
                        rs.getString("ProductName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"));
            }

        } catch (SQLException e) {
            System.out.println("Error reading products.");
            e.printStackTrace();
        }
    }

    // UPDATE
    private static void updateProduct(Connection conn, Scanner sc) {
        System.out.print("Enter Product ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new Product Name: ");
        String name = sc.nextLine();
        System.out.print("Enter new Price: ");
        double price = sc.nextDouble();
        System.out.print("Enter new Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        String sql = "UPDATE Product SET ProductName = ?, Price = ?, Quantity = ? WHERE ProductID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // start transaction
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("Product updated successfully!");
            } else {
                conn.rollback();
                System.out.println("Product ID not found. No changes made.");
            }

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("Error updating product.");
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }

    // DELETE
    private static void deleteProduct(Connection conn, Scanner sc) {
        System.out.print("Enter Product ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();

        String sql = "DELETE FROM Product WHERE ProductID = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false); // start transaction
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                conn.commit();
                System.out.println("Product deleted successfully!");
            } else {
                conn.rollback();
                System.out.println("Product ID not found. No changes made.");
            }

        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            System.out.println("Error deleting product.");
            e.printStackTrace();
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
}

import java.util.List;
import java.util.Scanner;

public class StudentManagementApp {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            StudentController controller = new StudentController();
            boolean exit = false;

            while (!exit) {
                System.out.println("\n=== Student Management Menu ===");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Update Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Exit");
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();
                sc.nextLine(); // consume newline

                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = sc.nextDouble();
                        sc.nextLine();
                        controller.addStudent(new Student(0, name, dept, marks));
                        break;
                    case 2:
                        List<Student> students = controller.getAllStudents();
                        System.out.printf("%-10s %-20s %-15s %-10s%n", "ID", "Name", "Department", "Marks");
                        for (Student s : students) {
                            System.out.printf("%-10d %-20s %-15s %-10.2f%n",
                                    s.getStudentID(), s.getName(), s.getDepartment(), s.getMarks());
                        }
                        break;
                    case 3:
                        System.out.print("Enter StudentID to update: ");
                        int updateID = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter new Department: ");
                        String newDept = sc.nextLine();
                        System.out.print("Enter new Marks: ");
                        double newMarks = sc.nextDouble();
                        sc.nextLine();
                        controller.updateStudent(new Student(updateID, newName, newDept, newMarks));
                        break;
                    case 4:
                        System.out.print("Enter StudentID to delete: ");
                        int deleteID = sc.nextInt();
                        sc.nextLine();
                        controller.deleteStudent(deleteID);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

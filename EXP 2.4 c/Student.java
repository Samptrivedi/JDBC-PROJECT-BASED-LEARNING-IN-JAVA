public class Student {
    private int studentID;
    private String name;
    private String department;
    private double marks;

    // Constructors
    public Student() {}

    public Student(int studentID, String name, String department, double marks) {
        this.studentID = studentID;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    // Getters and setters
    public int getStudentID() { return studentID; }
    public void setStudentID(int studentID) { this.studentID = studentID; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public double getMarks() { return marks; }
    public void setMarks(double marks) { this.marks = marks; }
}

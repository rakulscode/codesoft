import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Grade Calculator!");

        List<Student> students = new ArrayList<>();

        do {
            System.out.print("Enter student name: ");
            String studentName = scanner.nextLine();

            Student student = new Student(studentName);

            try {
                student.inputSubjectMarks(scanner);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter valid marks.");
                return;
            }

            students.add(student);

            System.out.print("Do you want to add another student? (yes/no): ");
        } while (scanner.nextLine().equalsIgnoreCase("yes"));

        // Display statistics for each student
        for (Student student : students) {
            student.displayResults();
            System.out.println("--------------------------");
        }

        scanner.close();
    }
}

class Person {
    protected String name;

    public Person(String name) {
        this.name = name;
    }
}

enum Grade {
    A, B, C, D, F
}

interface ResultDisplay {
    void displayResults();
}

class Student extends Person implements ResultDisplay {
    private List<Integer> subjectMarks = new ArrayList<>();

    private static final int MAX_MARKS = 100;
    private static final int PASS_PERCENTAGE = 40;

    public Student(String name) {
        super(name);
    }

    public void inputSubjectMarks(Scanner scanner) {
        System.out.print("Enter the number of subjects: ");
        int numSubjects = scanner.nextInt();

        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter marks for Subject " + (i + 1) + ": ");
            int marks = scanner.nextInt();

            if (marks < 0 || marks > MAX_MARKS) {
                System.out
                        .println("Invalid marks! Marks should be between 0 and " + MAX_MARKS + ". Please enter again.");
                i--;
            } else {
                subjectMarks.add(marks);
            }
        }

        scanner.nextLine(); // Consume the newline character
    }

    public void setMarks(String subjectName, int marks) {
        subjectMarks.add(marks);
    }

    private double calculateAveragePercentage() {
        int totalMarks = 0;
        for (int marks : subjectMarks) {
            totalMarks += marks;
        }
        return (double) totalMarks / subjectMarks.size();
    }

    private Grade calculateGrade() {
        double averagePercentage = calculateAveragePercentage();
        if (averagePercentage >= 90) {
            return Grade.A;
        } else if (averagePercentage >= 80) {
            return Grade.B;
        } else if (averagePercentage >= 70) {
            return Grade.C;
        } else if (averagePercentage >= 60) {
            return Grade.D;
        } else {
            return Grade.F;
        }
    }

    public void displayResults() {
        System.out.println("Results for " + name + ":");
        System.out.println("Individual Subject Marks:");
        int i = 1;
        for (int marks : subjectMarks) {
            System.out.println("Subject " + i++ + ": " + marks);
        }

        System.out.println("Total Marks: " + calculateTotalMarks());
        System.out.println("Average Percentage: " + String.format("%.2f", calculateAveragePercentage()) + "%");
        System.out.println("Grade: " + calculateGrade());

        displayGradeMessage();
        displayPassStatus();
        printSubjectWithHighestMarks();
        printSubjectWithLowestMarks();
        printSubjectsAboveAverage();
    }

    private int calculateTotalMarks() {
        int totalMarks = 0;
        for (int marks : subjectMarks) {
            totalMarks += marks;
        }
        return totalMarks;
    }

    private void displayGradeMessage() {
        switch (calculateGrade()) {
            case A:
                System.out.println("Excellent! Keep up the good work!");
                break;
            case B:
                System.out.println("Well done! You're performing above average.");
                break;
            case C:
                System.out.println("Good job! Your performance is satisfactory.");
                break;
            case D:
                System.out.println("Keep working hard to improve your performance.");
                break;
            case F:
                System.out.println("You need to work on your studies. Seek help if needed.");
                break;
        }
    }

    private void displayPassStatus() {
        double averagePercentage = calculateAveragePercentage();
        System.out.println("Pass Status: " + (averagePercentage >= PASS_PERCENTAGE ? "Pass" : "Fail"));
    }

    private void printSubjectWithHighestMarks() {
        int maxMarks = Integer.MIN_VALUE;
        int subjectIndex = -1;

        for (int i = 0; i < subjectMarks.size(); i++) {
            if (subjectMarks.get(i) > maxMarks) {
                maxMarks = subjectMarks.get(i);
                subjectIndex = i;
            }
        }

        System.out.println("Subject with Highest Marks: Subject " + (subjectIndex + 1) + " (" + maxMarks + " marks)");
    }

    private void printSubjectWithLowestMarks() {
        int minMarks = Integer.MAX_VALUE;
        int subjectIndex = -1;

        for (int i = 0; i < subjectMarks.size(); i++) {
            if (subjectMarks.get(i) < minMarks) {
                minMarks = subjectMarks.get(i);
                subjectIndex = i;
            }
        }

        System.out.println("Subject with Lowest Marks: Subject " + (subjectIndex + 1) + " (" + minMarks + " marks)");
    }

    private void printSubjectsAboveAverage() {
        double averagePercentage = calculateAveragePercentage();
        System.out.println("Subjects with Marks Above Average:");

        for (int i = 0; i < subjectMarks.size(); i++) {
            if (subjectMarks.get(i) > averagePercentage) {
                System.out.println("Subject " + (i + 1) + ": " + subjectMarks.get(i) + " marks");
            }
        }
    }
}

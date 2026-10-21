
import java.sql.*;
import java.util.Scanner;

public class LibraryService {

    public static void registerStudent(Scanner sc) {
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();

        System.out.print("Enter student email: ");
        String email = sc.nextLine().trim();

        if (name.isBlank() || email.isBlank()
                || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            System.out.println("Enter a valid name and email.");
            return;
        }

        String sql = "INSERT INTO students(name, email) VALUES (?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.executeUpdate();
            System.out.println("Student registered successfully!");
        } catch (SQLException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    public static void issueBook(Scanner sc) {
        System.out.print("Enter book ID: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid book ID.");
            sc.nextLine();
            return;
        }
        int bookId = sc.nextInt();

        System.out.print("Enter student ID: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid student ID.");
            sc.nextLine();
            return;
        }
        int studentId = sc.nextInt();
        sc.nextLine();

        String checkSql =
                "SELECT quantity FROM books WHERE book_id = ? FOR UPDATE";
        String insertSql =
                "INSERT INTO issued_books(book_id, student_id, issue_date, due_date) "
                + "VALUES (?, ?, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 14 DAY))";
        String updateSql =
                "UPDATE books SET quantity = quantity - 1 "
                + "WHERE book_id = ? AND quantity > 0";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int quantity;

                try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                    ps.setInt(1, bookId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Book ID not found.");
                        }
                        quantity = rs.getInt("quantity");
                    }
                }

                if (quantity <= 0) {
                    throw new SQLException("No copies available.");
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "SELECT student_id FROM students WHERE student_id = ?")) {
                    ps.setInt(1, studentId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Student ID not found.");
                        }
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                    ps.setInt(1, bookId);
                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Could not update book quantity.");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                    ps.setInt(1, bookId);
                    ps.setInt(2, studentId);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Book issued successfully! Due in 14 days.");

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Issue failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void returnBook(Scanner sc) {
        System.out.print("Enter issue ID: ");
        if (!sc.hasNextInt()) {
            System.out.println("Invalid issue ID.");
            sc.nextLine();
            return;
        }
        int issueId = sc.nextInt();
        sc.nextLine();

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int bookId;

                String findSql = "SELECT book_id FROM issued_books "
                        + "WHERE issue_id = ? AND return_date IS NULL FOR UPDATE";

                try (PreparedStatement ps = con.prepareStatement(findSql)) {
                    ps.setInt(1, issueId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "Active issue not found or already returned.");
                        }
                        bookId = rs.getInt("book_id");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE issued_books SET return_date = CURDATE() "
                        + "WHERE issue_id = ? AND return_date IS NULL")) {
                    ps.setInt(1, issueId);
                    if (ps.executeUpdate() != 1) {
                        throw new SQLException("Could not record return.");
                    }
                }

                try (PreparedStatement ps = con.prepareStatement(
                        "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?")) {
                    ps.setInt(1, bookId);
                    ps.executeUpdate();
                }

                con.commit();
                System.out.println("Book returned successfully!");

            } catch (SQLException e) {
                con.rollback();
                System.out.println("Return failed: " + e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}

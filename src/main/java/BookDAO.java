
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookDAO {

    public static void addBook(Scanner sc) {
        System.out.print("Enter book title: ");
        String title = sc.nextLine();

        System.out.print("Enter author: ");
        String author = sc.nextLine();

        System.out.print("Enter category: ");
        String category = sc.nextLine();

        System.out.print("Enter quantity: ");
        while (!sc.hasNextInt()) {
            System.out.print("Enter a valid quantity: ");
            sc.next();
        }
        int quantity = sc.nextInt();
        sc.nextLine();

        if (title.isBlank() || author.isBlank()
                || category.isBlank() || quantity < 0) {
            System.out.println("Invalid book details.");
            return;
        }

        String sql = "INSERT INTO books "
                + "(title, author, category, quantity) "
                + "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, author);
            ps.setString(3, category);
            ps.setInt(4, quantity);

            ps.executeUpdate();
            System.out.println("Book added successfully!");

        } catch (SQLException e) {
            System.out.println("Could not add book: " + e.getMessage());
        }
    }

    public static void viewBooks() {
        String sql = "SELECT * FROM books ORDER BY book_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== AVAILABLE BOOK RECORDS =====");

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    "ID: " + rs.getInt("book_id")
                    + " | Title: " + rs.getString("title")
                    + " | Author: " + rs.getString("author")
                    + " | Category: " + rs.getString("category")
                    + " | Quantity: " + rs.getInt("quantity")
                );
            }

            if (!found) {
                System.out.println("No books found.");
            }

        } catch (SQLException e) {
            System.out.println("Could not retrieve books: "
                    + e.getMessage());
        }
    }
    
    public static void searchBook(Scanner sc) {
    System.out.print("Enter book title or author to search: ");
    String keyword = sc.nextLine();

    String sql = "SELECT * FROM books "
            + "WHERE title LIKE ? OR author LIKE ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");

        try (ResultSet rs = ps.executeQuery()) {
            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println(
                    "ID: " + rs.getInt("book_id")
                    + " | Title: " + rs.getString("title")
                    + " | Author: " + rs.getString("author")
                    + " | Category: " + rs.getString("category")
                    + " | Quantity: " + rs.getInt("quantity")
                );
            }

            if (!found) {
                System.out.println("No matching books found.");
            }
        }

    } catch (SQLException e) {
        System.out.println("Search failed: " + e.getMessage());
    }
}

public static void updateBook(Scanner sc) {
    System.out.print("Enter book ID to update: ");
    if (!sc.hasNextInt()) {
        System.out.println("Invalid book ID.");
        sc.nextLine();
        return;
    }
    int id = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter new title: ");
    String title = sc.nextLine();

    System.out.print("Enter new author: ");
    String author = sc.nextLine();

    System.out.print("Enter new category: ");
    String category = sc.nextLine();

    System.out.print("Enter new quantity: ");
    if (!sc.hasNextInt()) {
        System.out.println("Invalid quantity.");
        sc.nextLine();
        return;
    }
    int quantity = sc.nextInt();
    sc.nextLine();

    if (title.isBlank() || author.isBlank()
            || category.isBlank() || quantity < 0) {
        System.out.println("Invalid details.");
        return;
    }

    String sql = "UPDATE books SET title=?, author=?, "
            + "category=?, quantity=? WHERE book_id=?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, title);
        ps.setString(2, author);
        ps.setString(3, category);
        ps.setInt(4, quantity);
        ps.setInt(5, id);

        int rows = ps.executeUpdate();
        System.out.println(rows > 0
                ? "Book updated successfully!"
                : "Book ID not found.");

    } catch (SQLException e) {
        System.out.println("Update failed: " + e.getMessage());
    }
}

public static void deleteBook(Scanner sc) {
    System.out.print("Enter book ID to delete: ");

    if (!sc.hasNextInt()) {
        System.out.println("Invalid book ID.");
        sc.nextLine();
        return;
    }
    int id = sc.nextInt();
    sc.nextLine();

    String sql = "DELETE FROM books WHERE book_id=?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);

        int rows = ps.executeUpdate();
        System.out.println(rows > 0
                ? "Book deleted successfully!"
                : "Book ID not found.");

    } catch (SQLException e) {
        System.out.println(
            "Delete failed. This book may have issue records: "
            + e.getMessage()
        );
    }
}

}

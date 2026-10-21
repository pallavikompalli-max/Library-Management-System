
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Register Student");
            System.out.println("7. Issue Book");
            System.out.println("8. Return Book");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            while (!sc.hasNextInt()) {
                System.out.print("Enter a valid number: ");
                sc.next();
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    BookDAO.addBook(sc);
                    break;

                case 2:
                    BookDAO.viewBooks();
                    break;
                
                case 3:
                    BookDAO.searchBook(sc);
                    break;

                case 4:
                    BookDAO.updateBook(sc);
                    break;

                case 5:
                    BookDAO.deleteBook(sc);
                    break;

                case 6:
                    LibraryService.registerStudent(sc);
                    break;
                
                case 7:
                    LibraryService.issueBook(sc);
                    break;

                case 8:
                    LibraryService.returnBook(sc);
                    break;

                case 9:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 9);

        sc.close();
    }
}

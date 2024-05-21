package user_management_subsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Interface representing the state of the user session
interface UserSessionState {
    void handle(UserSessionContext context);
}

// Context class to maintain the state of the user session
class UserSessionContext {
    private UserSessionState state;

    public void setState(UserSessionState state) {
        this.state = state;
    }

    public UserSessionState getState() {
        return state;
    }

    public void request() {
        state.handle(this);
    }
}

// Concrete state representing an active user session
class ActiveSessionState implements UserSessionState {
    public void handle(UserSessionContext context) {
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Search Books");
            System.out.println("2. View Book Details");
            System.out.println("3. Update Profile");
            System.out.println("4. Logout");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    context.setState(new SearchBooksState(UserInterfaceManagementSystem.getInstance().getBookCatalog()));
                    context.request(); // Request to handle the new state
                    break;
                case 2:
               
    context.setState(new ViewBookDetailsState(UserInterfaceManagementSystem.getInstance().getBookCatalog()));
    context.request();
    break;
                case 3:
                    context.setState(new UpdateProfileState());
                    context.request();
                    break;
                case 4:
                    System.out.println("Logout successful. Thank you for using the Online Library Management System!");
                    return; // Exit the program
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}


class SearchBooksState implements UserSessionState {
    private List<Book> bookCatalog;

    public SearchBooksState(List<Book> bookCatalog) {
        this.bookCatalog = bookCatalog;
    }

    public void handle(UserSessionContext context) {
        System.out.println("Search Books:");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by Genre");
        System.out.println("4. Search by ISBN");
        System.out.println("5. Go back to Main Menu");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter the title of the book: ");
                String title = scanner.nextLine();
                searchByTitle(title);
                break;
            case 2:
                System.out.print("Enter the author of the book: ");
                String author = scanner.nextLine();
                searchByAuthor(author);
                break;
            case 3:
                System.out.print("Enter the genre of the book: ");
                String genre = scanner.nextLine();
                searchByGenre(genre);
                break;
            case 4:
                System.out.print("Enter the ISBN of the book: ");
                String isbn = scanner.nextLine();
                searchByISBN(isbn);
                break;
            case 5:
                context.setState(new ActiveSessionState());
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public void searchByTitle(String title) {
        boolean found = false;
        for (Book book : bookCatalog) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                displayBookDetails(book);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No book found with the given title.");
        }
    }

    public void searchByAuthor(String author) {
        boolean found = false;
        for (Book book : bookCatalog) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                displayBookDetails(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No book found with the given author.");
        }
    }

    public void searchByGenre(String genre) {
        boolean found = false;
        for (Book book : bookCatalog) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                displayBookDetails(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found in the given genre.");
        }
    }

    public void searchByISBN(String isbn) {
        boolean found = false;
        for (Book book : bookCatalog) {
            if (book.getIsbn().equalsIgnoreCase(isbn)) {
                displayBookDetails(book);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No book found with the given ISBN.");
        }
    }

    private void displayBookDetails(Book book) {
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Genre: " + book.getGenre());
        System.out.println("Year: " + book.getYear());
        System.out.println("Quantity: " + book.getQuantity());
    }
}

// Class representing a book
class Book {
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private int year;
    private int quantity;

    public Book(String title, String author, String isbn, String genre, int year, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
        this.quantity = quantity;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class ViewBookDetailsState implements UserSessionState {
    private List<Book> bookCatalog;

    public ViewBookDetailsState(List<Book> bookCatalog) {
        this.bookCatalog = bookCatalog;
    }

    public void handle(UserSessionContext context) {
        System.out.println("View Book Details:");
        System.out.println("1. View by Title");
        System.out.println("2. View by Author");
        System.out.println("3. View by Genre");
        System.out.println("4. Go back to Main Menu");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter the title of the book: ");
                String title = scanner.nextLine();
                viewByTitle(title);
                break;
            case 2:
                System.out.print("Enter the author of the book: ");
                String author = scanner.nextLine();
                viewByAuthor(author);
                break;
            case 3:
                System.out.print("Enter the genre of the book: ");
                String genre = scanner.nextLine();
                viewByGenre(genre);
                break;
            case 4:
                context.setState(new ActiveSessionState());
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    public void viewByTitle(String title) {
        boolean found = false;
        for (Book book : bookCatalog) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                displayBookDetails(book);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("No book found with the given title.");
        }
    }

    public void viewByAuthor(String author) {
        boolean found = false;
        for (Book book : bookCatalog) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                displayBookDetails(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No book found with the given author.");
        }
    }

    public void viewByGenre(String genre) {
        boolean found = false;
        for (Book book : bookCatalog) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                displayBookDetails(book);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found in the given genre.");
        }
    }

    private void displayBookDetails(Book book) {
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Genre: " + book.getGenre());
        System.out.println("Year: " + book.getYear());
        System.out.println("Quantity: " + book.getQuantity());
    }
}


class UpdateProfileState implements UserSessionState {
    private Scanner scanner;

    public UpdateProfileState() {
        this.scanner = new Scanner(System.in);
    }

    public void handle(UserSessionContext context) {
        System.out.println("Update Profile:");
        System.out.println("1. Change Password");
        System.out.println("2. Change Name");
        System.out.println("3. Go back to Main Menu");

        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                changePassword();
                break;
            case 2:
                changeName();
                break;
            case 3:
                context.setState(new ActiveSessionState());
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void changePassword() {
    System.out.print("Enter your current password: ");
    String currentPassword = scanner.nextLine();

    if (!currentPassword.equals("user123")) {
        System.out.println("Incorrect current password. Password change failed.");
        return;
    }

    System.out.print("Enter your new password: ");
    String newPassword = scanner.nextLine();

    System.out.println("Password changed successfully. Your new password is: " + newPassword);
}


  private void changeName() {
    System.out.print("Enter your new name: ");
    String newName = scanner.nextLine();
    
    System.out.println("Name changed successfully. Your new name is: " + newName);
}

}

// Singleton class representing the User Interface Management Subsystem
class UserInterfaceManagementSystem {
    private static UserInterfaceManagementSystem instance;
    private UserSessionContext userSessionContext;
    private List<Book> bookCatalog; // Add book catalog

    private UserInterfaceManagementSystem() {
        this.userSessionContext = new UserSessionContext();
        this.userSessionContext.setState(new ActiveSessionState());
        this.bookCatalog = new ArrayList<>(); // Initialize book catalog
    }

    public static synchronized UserInterfaceManagementSystem getInstance() {
        if (instance == null) {
            instance = new UserInterfaceManagementSystem();
        }
        return instance;
    }

    public void start() {
        System.out.println("Welcome to the Online Library Management System!\n");

        // Initialize some sample books
        bookCatalog.add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "9780439064866", "Fantasy", 1997, 5));
        bookCatalog.add(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "9780439064867", "Fantasy", 1998, 3));
        bookCatalog.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", "Fiction", 1925, 2));

        // Simulate user login 
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please log in to continue\nUsername: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        // Check login credentials 
        if (!username.equals("user") || !password.equals("user123")) {
            System.out.println("Your login credentials are incorrect. Please try again.");
            return;
        }

        System.out.println("Login successful.");
System.out.println("Commit successfull: Changes have been committed.");

        // Start user session with book catalog
        userSessionContext.request();
    }

    public List<Book> getBookCatalog() {
        return bookCatalog;
    }
}

// Main class to simulate the Library Management System
public class UserManagementSystem {
    public static void main(String[] args) {
        // Initialize User Interface Management System
        UserInterfaceManagementSystem uiSystem = UserInterfaceManagementSystem.getInstance();
        uiSystem.start();
    }
}


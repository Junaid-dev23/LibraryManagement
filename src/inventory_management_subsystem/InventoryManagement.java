package inventory_management_subsystem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

// Interface representing the state of the inventory
interface InventoryState {
    void handle(InventoryContext context);
}

// Context class to maintain the state of the inventory
class InventoryContext {
    private InventoryState state;

    public void setState(InventoryState state) {
        this.state = state;
    }

    public InventoryState getState() {
        return state;
    }

    public void request() {
        state.handle(this);
    }
}

// Concrete state representing available inventory state
class AvailableState implements InventoryState {
    public void handle(InventoryContext context) {
        System.out.println("Inventory is available.");
    }
}

// Concrete state representing low inventory state
class LowInventoryState implements InventoryState {
    public void handle(InventoryContext context) {
        System.out.println("Inventory is running low.");
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

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                ", genre='" + genre + '\'' +
                ", year=" + year +
                ", quantity=" + quantity +
                '}';
    }
}

// Singleton class representing the Inventory Management Subsystem
class InventoryManagementSystem {
    private static InventoryManagementSystem instance;
    private List<Book> books;
    private InventoryContext inventoryContext;

    private InventoryManagementSystem() {
        this.books = new ArrayList<>();
        this.inventoryContext = new InventoryContext();
        this.inventoryContext.setState(new AvailableState());
    }

    public static synchronized InventoryManagementSystem getInstance() {
        if (instance == null) {
            instance = new InventoryManagementSystem();
        }
        return instance;
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added successfully.");
    }

    public void updateBookQuantity(String title, int quantity) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.setQuantity(quantity);
                System.out.println("Quantity updated successfully for " + title);
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void deleteBook(String title) {
        Iterator<Book> iterator = books.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getTitle().equalsIgnoreCase(title)) {
                iterator.remove(); // Correctly remove the book using iterator
                System.out.println("Book deleted successfully.");
                return;
            }
        }
        System.out.println("Book not found.");
    }


    public void setInventoryState(InventoryState state) {
        inventoryContext.setState(state);
    }

    public void getCurrentInventoryState() {
        if (books.isEmpty()) {
            System.out.println("There are no books in inventory.");
        } else {
            inventoryContext.request();
        }
    }

    // Method to get all books
    public List<Book> getAllBooks() {
        return books;
    }
}

// Interface representing user operations
interface UserOperations {
    void checkoutBook(String title);

    void returnBook(String title);

    void reserveBook(String title);
}

// Singleton class representing the User Management Subsystem
class UserManagementSystem implements UserOperations {
    private static UserManagementSystem instance;
    private InventoryManagementSystem inventorySystem;

    private UserManagementSystem() {
        this.inventorySystem = InventoryManagementSystem.getInstance();
    }

    public static synchronized UserManagementSystem getInstance() {
        if (instance == null) {
            instance = new UserManagementSystem();
        }
        return instance;
    }

    @Override
    public void checkoutBook(String title) {
        // Find the book in the inventory
        for (Book book : inventorySystem.getAllBooks()) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                // Check if the book is available
                if (book.getQuantity() > 0) {
                    // If available, decrement the quantity
                    book.setQuantity(book.getQuantity() - 1);
                    System.out.println("Book \"" + title + "\" checked out successfully.");
                    return;
                } else {
                    System.out.println("Book \"" + title + "\" is currently not available.");
                    return;
                }
            }
        }
        // If book not found
        System.out.println("Book \"" + title + "\" not found in inventory.");
    }


   

  @Override
public void returnBook(String title) {
    // Check if the book exists in the inventory
    boolean found = false;
    for (Book book : inventorySystem.getAllBooks()) {
        if (book.getTitle().equalsIgnoreCase(title)) {
            found = true;
            break;
        }
    }
    if (!found) {
        System.out.println("Book \"" + title + "\" not found in inventory.");
        return;
    }

    // Logic to return book
    System.out.println("Book \"" + title + "\" returned successfully.");
}

  @Override
  public void reserveBook(String title) {
      // Check if the book exists in the inventory
      boolean found = false;
      for (Book book : inventorySystem.getAllBooks()) {
          if (book.getTitle().equalsIgnoreCase(title)) {
              found = true;
              if (book.getQuantity() > 0) {
                  book.setQuantity(book.getQuantity() - 1);
                  System.out.println("Book \"" + title + "\" reserved successfully.");
                  return;
              } else {
                  System.out.println("Book \"" + title + "\" is currently not available for reservation.");
                  return;
              }
          }
      }
      if (!found) {
          System.out.println("Book \"" + title + "\" not found in inventory.");
      }
  }

}

// Main class to simulate the Library Management System
public class InventoryManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Library Management System!\n");

        // Initialize Inventory Management System
        InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();

        // Initialize User Management System
        UserManagementSystem userSystem = UserManagementSystem.getInstance();

        boolean continueOperations = true;

        while (continueOperations) {
            System.out.println("Select your role:");
            System.out.println("1. Librarian/Administrator");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int role;
            try {
                role = Integer.parseInt(scanner.nextLine()); // Input validation
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }
            switch (role) {
                case 1:
                    // Librarian/Administrator Operations
                    System.out.println("Librarian/Administrator Operations:");
                    boolean continueLibrarianOperations = true;
                    while (continueLibrarianOperations) {
                        System.out.println("Choose an operation:");
                        System.out.println("1. Add Book");
                        System.out.println("2. Update Book Quantity");
                        System.out.println("3. Delete Book");
                        System.out.println("4. Check Inventory State");
                        System.out.println("5. View All Books");
                        System.out.println("6. Switch Role");
                        System.out.println("7. Exit");
                        System.out.print("Enter your choice: ");
                        int librarianChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        switch (librarianChoice) {
                            case 1:
                                System.out.print("Enter Book Title: ");
                                String title = scanner.nextLine();
                                System.out.print("Enter Author Name: ");
                                String author = scanner.nextLine();
                                System.out.print("Enter ISBN: ");
                                String isbn = scanner.nextLine();
                                System.out.print("Enter Genre: ");
                                String genre = scanner.nextLine();
                                System.out.print("Enter Year: ");
                                int year = scanner.nextInt();
                                System.out.print("Enter Quantity: ");
                                int quantity = scanner.nextInt();
                                inventorySystem.addBook(new Book(title, author, isbn, genre, year, quantity));
                                break;
                            case 2:
                                System.out.print("Enter Book Title: ");
                                String updateTitle = scanner.nextLine();
                                System.out.print("Enter New Quantity: ");
                                int newQuantity = scanner.nextInt();
                                inventorySystem.updateBookQuantity(updateTitle, newQuantity);
                                break;
                            case 3:
                                System.out.print("Enter Book Title: ");
                                String deleteTitle = scanner.nextLine();
                                inventorySystem.deleteBook(deleteTitle);
                                break;
                            case 4:
                                inventorySystem.getCurrentInventoryState();
                                break;
                            case 5:
                                List<Book> allBooks = inventorySystem.getAllBooks();
                                if (allBooks.isEmpty()) {
                                    System.out.println("There are no books in inventory.");
                                } else {
                                    System.out.println("\nAll Books in Inventory:");
                                    for (Book book : allBooks) {
                                        System.out.println(book);
                                    }
                                }
                                break;
                            case 6:
                                continueLibrarianOperations = false;
                                break;
                            case 7:
                                continueLibrarianOperations = false;
                                continueOperations = false;
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                    break;
                case 2:
                    // User Operations
                    System.out.println("User Operations:");
                    boolean continueUserOperations = true;
                    while (continueUserOperations) {
                        System.out.println("Choose an operation:");
                        System.out.println("1. Check Out Book");
                        System.out.println("2. Return Book");
                        System.out.println("3. Reserve Book");
                        System.out.println("4. Switch Role");
                        System.out.println("5. Exit");
                        System.out.print("Enter your choice: ");
                        int userChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        switch (userChoice) {
                            case 1:
                                if (inventorySystem.getAllBooks().isEmpty()) {
                                    System.out.println("There are no books available for checkout.");
                                } else {
                                    System.out.print("Enter Book Title: ");
                                    String title = scanner.nextLine();
                                    userSystem.checkoutBook(title);
                                }
                                break;
                            case 2:
                                System.out.print("Enter Book Title: ");
                                String title = scanner.nextLine();
                                userSystem.returnBook(title);
                                break;
                            case 3:
                                System.out.print("Enter Book Title: ");
                                String reserveTitle = scanner.nextLine();
                                userSystem.reserveBook(reserveTitle);
                                break;
                            case 4:
                                continueUserOperations = false;
                                break;
                            case 5:
                                continueUserOperations = false;
                                continueOperations = false;
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }
                    }
                    break;
                case 3:
                    continueOperations = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}

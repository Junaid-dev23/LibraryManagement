package Group_file_library_management_system;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;




class Book {
    private String title;
    private String author;
    private String isbn;
    private String genre;
    private int year;
    private int quantity;
    private String authorInfo; // Declare authorInfo variable

    public Book(String title, String author, String isbn, String genre, int year, int quantity) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
        this.quantity = quantity;
    }

    // Add a constructor to match the parameters used in the Admin class
    public Book(String title, String author, String isbn, String genre, int year) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.genre = genre;
        this.year = year;
        this.quantity = 0; // Default quantity
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

    // Setter for author information
    public void setAuthorInfo(String authorInfo) {
        this.authorInfo = authorInfo;
    }

    // Getter for author information
    public String getAuthorInfo() {
        return authorInfo;
    }
}



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

// Interface representing a user
interface User {
    void login(String username, String password);
    void logout();
    void showMenu();
    boolean isLoggedIn();
}

// Class representing an admin user
class Admin implements User {
    private static Admin instance;
    private boolean loggedIn;
    private List<Book> books;
    
    public List<Book> getBooks() {
    return books;
}


    private Admin() {
        this.loggedIn = false;
        this.books = new ArrayList<>();
    }

    public static Admin getInstance() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public void login(String username, String password) {
        if (username.equals("admin") && password.equals("admin123")) {
            loggedIn = true;
            System.out.println("Admin logged in successfully.");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    public void logout() {
        loggedIn = false;
        System.out.println("Admin logged out successfully.");
    }

    public void showMenu() {
        System.out.println("\nAvailable functionalities:");
        System.out.println("1. Add Book");
        System.out.println("2. Update Book");
        System.out.println("3. Delete Book");
        System.out.println("4. Generate Reports");
        System.out.println("5. Exit");
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void addBook(String title, String author, String isbn, String genre, int year) {
        books.add(new Book(title, author, isbn, genre, year));
        System.out.println("Book added successfully.");
    }

    public void updateBook(String title) {
        for (Book book : books) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                System.out.println("\nEnter updated book details:");
                Scanner scanner = new Scanner(System.in);
                System.out.print("New Title: ");
                String newTitle = scanner.nextLine();
                System.out.print("New Author: ");
                String newAuthor = scanner.nextLine();
                System.out.print("New ISBN: ");
                String newIsbn = scanner.nextLine();
                System.out.print("New Genre: ");
                String newGenre = scanner.nextLine();
                System.out.print("New Year: ");
                int newYear = 0;
                try {
                    newYear = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Please enter year in number.");
                    return;
                }
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setIsbn(newIsbn);
                book.setGenre(newGenre);
                book.setYear(newYear);
                System.out.println("Book details updated successfully.");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void deleteBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                books.remove(book);
                System.out.println("Book deleted successfully.");
                return;
            }
        }
        System.out.println("Book not found.");
    }

    public void generateReports() {
        System.out.println("\nGenerating reports...");

        // Display all book details
        System.out.println("\nBook Details:");
        for (Book book : books) {
            System.out.println("Title: " + book.getTitle());
            System.out.println("Author: " + book.getAuthor());
            System.out.println("ISBN: " + book.getIsbn());
            System.out.println("Genre: " + book.getGenre());
            System.out.println("Year: " + book.getYear());
            System.out.println();
        }

        System.out.println("Reports generated successfully.");
    }
}



// Class representing a Library Patron user
class LibraryPatron implements User {
    private boolean loggedIn;

    public LibraryPatron() {
        this.loggedIn = false;
    }

    public void login(String username, String password) {
        // Authentication logic for Library Patron
        if (username.equals("library_patron") && password.equals("patron123")) {
            loggedIn = true;
            System.out.println("Library Patron logged in successfully.");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    public void logout() {
        loggedIn = false;
        System.out.println("Library Patron logged out successfully.");
    }

    public void showMenu() {
        System.out.println("\nAvailable functionalities:");
        System.out.println("1. Search for Books");

    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void searchBooks(List<Book> books, String keyword) {
        System.out.println("\nSearch results:");
        int count = 0;
        InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
        
        for (Book book : inventorySystem.getAllBooks()) {
            if (book.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                count++;
                System.out.println(count + ". " + book.getTitle() + " by " + book.getAuthor() + " (ISBN: " + book.getIsbn() + ")");
                System.out.println("   Genre: " + book.getGenre() + ", Year: " + book.getYear());
            }
        }
        if (count == 0) {
            System.out.println("No books found matching the search keyword.");
        }
    }
}

// Class representing an Author user
class Author implements User {
    private boolean loggedIn;

    public Author() {
        this.loggedIn = false;
    }

    public void login(String username, String password) {
        // Authentication logic for Author
        if (username.equals("author") && password.equals("author123")) {
            loggedIn = true;
            System.out.println("Author logged in successfully.");
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    public void logout() {
        loggedIn = false;
        System.out.println("Author logged out successfully.");
    }

     public void showMenu() {
        System.out.println("\nAvailable functionalities:");
        System.out.println("1. Manage Author Info");
 
              System.out.println("2. Display Author Information");
        System.out.println("3. Exit");
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public void displayAuthorInformation(List<Book> books) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the title of the book to display author information: ");
        String bookTitle = scanner.nextLine();

        boolean found = false;
        InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
        
        for (Book book : inventorySystem.getAllBooks()) {
            if (book.getTitle().equalsIgnoreCase(bookTitle)) {
                String authorInfo = book.getAuthor();
                if (authorInfo != null) {
                    System.out.println("\nDisplaying Author Information for Book: " + book.getTitle());
                    System.out.println("Author Information: " + book.getAuthor());
                    found = true;
                    break;
                } else {
                    System.out.println("Author information not available for the book: " + book.getTitle());
                    found = true;
                    break;
                }
            }
        }
        if (!found) {
            System.out.println("Book with title \"" + bookTitle + "\" not found.");
        }
    }



   // Method for managing author information
    public void manageAuthorInfo(List<Book> books) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nManage Author Info:");
        System.out.println("1. Add Author Info");
        System.out.println("2. Update Author Info");
        System.out.println("3. Delete Author Info");
        System.out.print("\nEnter the number corresponding to the functionality you want to perform: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter ISBN of the book: ");
                String isbnToAdd = scanner.nextLine();
                System.out.print("Enter author information: ");
                String authorInfoToAdd = scanner.nextLine();
                addAuthorInfo(books, isbnToAdd, authorInfoToAdd);
                break;
            case 2:
                System.out.print("Enter ISBN of the book: ");
                String isbnToUpdate = scanner.nextLine();
                System.out.print("Enter updated author information: ");
                String authorInfoToUpdate = scanner.nextLine();
                updateAuthorInfo(books, isbnToUpdate, authorInfoToUpdate);
                break;
            case 3:
                System.out.print("Enter ISBN of the book: ");
                String isbnToDelete = scanner.nextLine();
                deleteAuthorInfo(books, isbnToDelete);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void addAuthorInfo(List<Book> books, String isbn, String authorInfo) {
    	  InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
    	   
    	   for (Book book : inventorySystem.getAllBooks()) {            if (book.getIsbn().equals(isbn)) {
                book.setAuthorInfo(authorInfo);
                System.out.println("Author info added successfully for ISBN: " + isbn);
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }
    
   

    private void updateAuthorInfo(List<Book> books, String isbn, String updatedAuthorInfo) {
    	  InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
    	   
    	   for (Book book : inventorySystem.getAllBooks()) {
            if (book.getIsbn().equals(isbn)) {
                book.setAuthorInfo(updatedAuthorInfo);
                System.out.println("Author info updated successfully for ISBN: " + isbn);
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

    private void deleteAuthorInfo(List<Book> books, String isbn) {
    	  InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
    	   
    	   for (Book book : inventorySystem.getAllBooks()) {
            if (book.getIsbn().equals(isbn)) {
                book.setAuthorInfo(null);
                System.out.println("Author info deleted successfully for ISBN: " + isbn);
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

}

//Interface representing the state of the user session
interface UserSessionState {
void handle(UserSessionContext context);
}

//Context class to maintain the state of the user session
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

//Concrete state representing an active user session
class ActiveSessionState implements UserSessionState {
	 public void handle(UserSessionContext context) {
		 boolean continueSession = true;
	        while (true) {
	            System.out.println("Main Menu:");
	            System.out.println("1. Search Books");
	            System.out.println("2. User Operations:");
	      
	            System.out.println("3. Update Profile");
	            System.out.println("4. View Book Details");
	            System.out.println("5. Switch Role");

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
	                    context.setState(new UserOperationsState());
	                    context.request();
	                    break;
	                case 3:
	                    context.setState(new UpdateProfileState());
	                    context.request();
	                    break;
	                case 4:
	                	 context.setState(new ViewBookDetailsState(UserInterfaceManagementSystem.getInstance().getBookCatalog()));
                 	    context.request();
                 	    break;

	                case 5:
	                	  continueSession = false; // Return to the role menu
	                      break;
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
   InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
   
   for (Book book : inventorySystem.getAllBooks()) {
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
 InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
   
   for (Book book : inventorySystem.getAllBooks()) {
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
 InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
   
   for (Book book : inventorySystem.getAllBooks()) {
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
 InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
   
   for (Book book : inventorySystem.getAllBooks()) {
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
 InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
   
   for (Book book : inventorySystem.getAllBooks()) {
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
 InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
   
   for (Book book : inventorySystem.getAllBooks()) {
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
 InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
   
   for (Book book : inventorySystem.getAllBooks()) {
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

class UserOperationsState implements UserSessionState {
    public void handle(UserSessionContext context) {
    	  // Initialize Inventory Management System
        InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
        // Initialize User Management System
        UserManagementSystem userSystem = UserManagementSystem.getInstance();
        System.out.println("User Operations:");
        System.out.println("1. Check Out Book");
        System.out.println("2. Return Book");
        System.out.println("3. Reserve Book");
        System.out.println("4. Go back to Main Menu");

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        boolean continueUserOperations = true;

        switch (choice) {
          
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
                String returnTitle = scanner.nextLine();
                userSystem.returnBook(returnTitle);
                break;
            case 3:
                System.out.print("Enter Book Title: ");
                String reserveTitle = scanner.nextLine();
                userSystem.reserveBook(reserveTitle);
                break;
            case 4:
                continueUserOperations = false;
                break;
            default:
                System.out.println("Invalid choice.");
        }
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

//Singleton class representing the User Interface Management Subsystem
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
}

 
public List<Book> getBookCatalog() {
   return bookCatalog;
}
}


public class Library_management {
	public static void main(String[] args) {
      Scanner scanner = new Scanner(System.in);

      System.out.println("Welcome to the Library Management System!\n");

      // Initialize Inventory Management System
      InventoryManagementSystem inventorySystem = InventoryManagementSystem.getInstance();
      

      // Initialize User Interface Management System
      UserInterfaceManagementSystem uiSystem = UserInterfaceManagementSystem.getInstance();
      uiSystem.start(); // Start the system

      // Initialize User Session Context
      UserSessionContext context = new UserSessionContext();
      
      // Initialize User Management System
      UserManagementSystem userSystem = UserManagementSystem.getInstance();

      // Initialize users
      Admin admin = Admin.getInstance();
      LibraryPatron libraryPatron = new LibraryPatron();
      Author author = new Author();
      

      boolean continueOperations = true;

      while (continueOperations) {
          System.out.println("Select your role:");
          System.out.println("1. Librarian/Administrator");
          System.out.println("2. User");
          System.out.println("3. Library Patron");
     
          System.out.println("4. Author");
          System.out.println("5. Exit");
          System.out.print("Enter your choice: ");
          int role = Integer.parseInt(scanner.nextLine());

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
                  System.out.println("6. Generate Reports");
                  System.out.println("7. Switch Role");
                  System.out.println("8. Exit");
                  System.out.print("Enter your choice: ");
                  int librarianChoice = 0;
                  try {
                      librarianChoice = Integer.parseInt(scanner.nextLine());
                  } catch (NumberFormatException e) {
                      System.out.println("Invalid input. Please enter a valid number.");
                      continue;
                  }

                  switch (librarianChoice) {
                      case 1:
                          System.out.print("Enter Book Title: ");
                          String title = scanner.nextLine();
                          System.out.print("Enter Author Name: ");
                          String authorName = scanner.nextLine();
                          System.out.print("Enter ISBN: ");
                          String isbn = scanner.nextLine();
                          System.out.print("Enter Genre: ");
                          String genre = scanner.nextLine();
                          System.out.print("Enter Year: ");
                          int year = 0;
                          try {
                              year = Integer.parseInt(scanner.nextLine());
                          } catch (NumberFormatException e) {
                              System.out.println("Invalid input for year. Please enter a valid number.");
                              continue;
                          }
                          System.out.print("Enter Quantity: ");
                          int quantity = 0;
                          try {
                              quantity = Integer.parseInt(scanner.nextLine());
                          } catch (NumberFormatException e) {
                              System.out.println("Invalid input for quantity. Please enter a valid number.");
                              continue;
                          }
                          inventorySystem.addBook(new Book(title, authorName, isbn, genre, year, quantity));
                          break;
                      case 2:
                          System.out.print("Enter Book Title: ");
                          String updateTitle = scanner.nextLine();
                          System.out.print("Enter New Quantity: ");
                          int newQuantity = 0;
                          try {
                              newQuantity = Integer.parseInt(scanner.nextLine());
                          } catch (NumberFormatException e) {
                              System.out.println("Invalid input. Please enter a valid number.");
                              continue;
                          }
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
                      	  admin.generateReports();
                            break;
                      	
                      case 7:
                          continueLibrarianOperations = false;
                          break;
                      case 8:
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
        	
              System.out.print("Enter your username: ");
              String username = scanner.nextLine();
              System.out.print("Enter your password: ");
              String password = scanner.nextLine();

              if (username.equals("user") && password.equals("user123")) {
                  System.out.println("Login successful.");
                  context.setState(new ActiveSessionState());
                  context.request();
                  System.out.println("Main Menu:");
                  System.out.println("1. Search Books");
                  System.out.println("2. User Operations:");
                  System.out.println("3. Update Profile");
                  System.out.println("4. View Book Details");
                  System.out.println("5. Logout");

                  System.out.print("Enter your choice: ");
                  int userOperationChoice = Integer.parseInt(scanner.nextLine()); // Consume newline

                  switch (userOperationChoice) {
                      case 1:
                          context.setState(new SearchBooksState(UserInterfaceManagementSystem.getInstance().getBookCatalog()));
                          context.request(); // Request to handle the new state
                          break;
                      case 2:
                          // User Operations
                          boolean continueUserOperations = true;
                          while (continueUserOperations) {
                              System.out.println("Choose an operation:");
                              System.out.println("1. Check Out Book");
                              System.out.println("2. Return Book");
                              System.out.println("3. Reserve Book");
                              System.out.println("4. Go back to Main Menu");
                              System.out.print("Enter your choice: ");
                              int userChoice = Integer.parseInt(scanner.nextLine()); // Consume newline

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
                                      String returnTitle = scanner.nextLine();
                                      userSystem.returnBook(returnTitle);
                                      break;
                                  case 3:
                                      System.out.print("Enter Book Title: ");
                                      String reserveTitle = scanner.nextLine();
                                      userSystem.reserveBook(reserveTitle);
                                      break;
                                  case 4:
                                      continueUserOperations = false;
                                      break;
                                  default:
                                      System.out.println("Invalid choice.");
                              }
                          }
                          break;
                      case 3:
                          context.setState(new UpdateProfileState());
                          context.request();
                          break;
                      case 4:
                    	   context.setState(new ViewBookDetailsState(UserInterfaceManagementSystem.getInstance().getBookCatalog()));
                    	    context.request();
                    	    break;

                      case 5:
                          System.out.println("Logout successful. Thank you for using the Online Library Management System!");
                          return; // Exit the program
                      default:
                          System.out.println("Invalid choice.");
                  }
                  break;
              } else {
                  System.out.println("Invalid username or password.");
              }
              break;
             


              case 3:
              	  System.out.print("Enter your username: ");
                    String patronUsername = scanner.nextLine();
                    System.out.print("Enter your password: ");
                    String patronPassword = scanner.nextLine();
                    libraryPatron.login(patronUsername, patronPassword);

                    if (libraryPatron.isLoggedIn()) {
                        libraryPatron.showMenu();

                        boolean continueOperation = true;
                        while (continueOperation) {
                            System.out.print("\nEnter the number corresponding to the functionality you want to perform: ");
                            int choice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline

                            switch (choice) {
                                case 1:
                                    System.out.print("Enter search keyword: ");
                                    String keyword = scanner.nextLine();
                                    libraryPatron.searchBooks(admin.getBooks(), keyword);
                                    break;
                                default:
                                    System.out.println("\nInvalid choice.");
                            }

                            System.out.print("\nDo you want to perform another operation? (yes/no): ");
                            String continueChoice = scanner.nextLine();
                            if (!continueChoice.equalsIgnoreCase("yes")) {
                                continueOperation = false;
                            }
                        }

                        libraryPatron.logout();
                    }
                    break;
                
            
              case 4:
              	 System.out.print("Enter your username: ");
                   String authorUsername = scanner.nextLine();
                   System.out.print("Enter your password: ");
                   String authorPassword = scanner.nextLine();
                   author.login(authorUsername, authorPassword);

                   if (author.isLoggedIn()) {
                       author.showMenu();

                       boolean authorContinueOperation = true;
                       while (authorContinueOperation) {
                           System.out.print("\nEnter the number corresponding to the functionality you want to perform: ");
                           int authorChoice = scanner.nextInt();
                           scanner.nextLine(); // Consume newline

                           switch (authorChoice) {
                               case 1:
                                   author.manageAuthorInfo(admin.getBooks());
                                   break;
                               case 2:
                                   author.displayAuthorInformation(admin.getBooks());
                                   break;
                               case 3:
                                   authorContinueOperation = false;
                                   break;
                               default:
                                   System.out.println("\nInvalid choice.");
                           }

                           if (authorContinueOperation) {
                               System.out.print("\nDo you want to perform another operation? (yes/no): ");
                               String authorContinueChoice = scanner.nextLine();
                               if (!authorContinueChoice.equalsIgnoreCase("yes")) {
                                   author.logout();
                                   authorContinueOperation = false;
                               }
                           }
                       }
                   }
                   break;
              case 5:
                  continueOperations = false;
                  break;
              default:
                  System.out.println("Invalid choice.");
          }
      }

  }
}

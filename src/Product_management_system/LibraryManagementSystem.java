package Product_management_system;

    	  
    	  
    	  import java.util.ArrayList;
    	  import java.util.List;
    	  import java.util.Scanner;

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

    	  // Class representing a book
    	  class Book {
    	      private String title;
    	      private String author;
    	      private String isbn;
    	      private String genre;
    	      private int year;
    	       private String authorInfo; // Declare authorInfo variable

    	      public Book(String title, String author, String isbn, String genre, int year) {
    	          this.title = title;
    	          this.author = author;
    	          this.isbn = isbn;
    	          this.genre = genre;
    	          this.year = year;
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

    	        // Setter for author information
    	      public void setAuthorInfo(String authorInfo) {
    	          this.authorInfo = authorInfo;
    	      }

    	      // Getter for author information
    	      public String getAuthorInfo() {
    	          return authorInfo;
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
    	          for (Book book : books) {
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
    	          for (Book book : books) {
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
    	          for (Book book : books) {
    	              if (book.getIsbn().equals(isbn)) {
    	                  book.setAuthorInfo(authorInfo);
    	                  System.out.println("Author info added successfully for ISBN: " + isbn);
    	                  return;
    	              }
    	          }
    	          System.out.println("Book with ISBN " + isbn + " not found.");
    	      }
    	      
    	     

    	      private void updateAuthorInfo(List<Book> books, String isbn, String updatedAuthorInfo) {
    	          for (Book book : books) {
    	              if (book.getIsbn().equals(isbn)) {
    	                  book.setAuthorInfo(updatedAuthorInfo);
    	                  System.out.println("Author info updated successfully for ISBN: " + isbn);
    	                  return;
    	              }
    	          }
    	          System.out.println("Book with ISBN " + isbn + " not found.");
    	      }

    	      private void deleteAuthorInfo(List<Book> books, String isbn) {
    	          for (Book book : books) {
    	              if (book.getIsbn().equals(isbn)) {
    	                  book.setAuthorInfo(null);
    	                  System.out.println("Author info deleted successfully for ISBN: " + isbn);
    	                  return;
    	              }
    	          }
    	          System.out.println("Book with ISBN " + isbn + " not found.");
    	      }

    	  }

    	  // Main class to simulate the Library Management System
    	  public class LibraryManagementSystem {
    	      public static void main(String[] args) {
    	          Scanner scanner = new Scanner(System.in);

    	          System.out.println("Welcome to the Library Management System!\n");
    	        // Initialize users
    	          Admin admin = Admin.getInstance();
    	          LibraryPatron libraryPatron = new LibraryPatron();
    	          Author author = new Author();

    	        
    	          // Role selection loop
    	          boolean continueRoleSelection = true;
    	          while (continueRoleSelection) {
    	              System.out.println("Please select your role:");
    	              System.out.println("1. Library Patron");
    	              System.out.println("2. Admin");
    	              System.out.println("3. Author");
    	              System.out.println("4. Exit");
    	              System.out.print("\nEnter the number corresponding to your role: ");
    	              int role = Integer.parseInt(scanner.nextLine());

    	          switch (role) {
    	              case 1:
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
    	              case 2:
    	              
    	       System.out.print("Enter your username: ");
    	                      String adminUsername = scanner.nextLine();
    	                      System.out.print("Enter your password: ");
    	                      String adminPassword = scanner.nextLine();
    	                      admin.login(adminUsername, adminPassword);

    	                      if (admin.isLoggedIn()) {
    	                          admin.showMenu();

    	                          boolean continueOperation = true;
    	                          while (continueOperation) {
    	                              System.out.print("\nEnter the number corresponding to the functionality you want to perform: ");
    	                              int choice = scanner.nextInt();
    	                              scanner.nextLine(); // Consume newline

    	                              switch (choice) {
    	                                  case 1:
    	                                      System.out.println("\nEnter book details:");
    	                                      System.out.print("Title: ");
    	                                      String adminTitle = scanner.nextLine();
    	                                      System.out.print("Author: ");
    	                                      String adminAuthor = scanner.nextLine();
    	                                      System.out.print("ISBN: ");
    	                                      String adminIsbn = scanner.nextLine();
    	                                      System.out.print("Genre: ");
    	                                      String adminGenre = scanner.nextLine();
    	                                      System.out.print("Year: ");
    	                                      int adminYear = 0;
    	                                      try {
    	                                          adminYear = Integer.parseInt(scanner.nextLine());
    	                                      } catch (NumberFormatException e) {
    	                                          System.out.println("Please enter year in number.");
    	                                          continue;
    	                                      }
    	                                      admin.addBook(adminTitle, adminAuthor, adminIsbn, adminGenre, adminYear);
    	                                      break;
    	                                  case 2:
    	                                      System.out.print("Enter the title of the book to update: ");
    	                                      String updateTitle = scanner.nextLine();
    	                                      admin.updateBook(updateTitle);
    	                                      break;
    	                                  case 3:
    	                                      System.out.print("Enter the ISBN of the book to delete: ");
    	                                      String deleteIsbn = scanner.nextLine();
    	                                      admin.deleteBook(deleteIsbn);
    	                                      break;
    	                                  case 4:
    	                                      admin.generateReports();
    	                                      break;
    	                                  case 5:
    	                                      admin.logout();
    	                                      continueOperation = false;
    	                                      break;
    	                                  default:
    	                                      System.out.println("\nInvalid choice.");
    	                              }

    	                              if (continueOperation) {
    	                                  System.out.print("\nDo you want to perform another operation? (yes/no): ");
    	                                  String continueChoice = scanner.nextLine();
    	                                  if (!continueChoice.equalsIgnoreCase("yes")) {
    	                                      admin.logout();
    	                                      continueOperation = false;
    	                                  }
    	                              }
    	                          }
    	                      }
    	                      break;
    	             case 3:
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
    	                      break;
    	                  }
    	              }
    	          }
    	      }
    	      break;

    	              case 4:
    	                      continueRoleSelection = false;
    	                      break;
    	              default:
    	                      System.out.println("Invalid role.");
    	          }}

    	          scanner.close();
    	      }
    	  }


package Group_file_library_management_system;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

public class Library_Management_TestCases {

    private Admin admin;
    private LibraryPatron libraryPatron;
    private Author author;
    private InventoryManagementSystem inventorySystem;
    private UserManagementSystem userSystem;
    private UserInterfaceManagementSystem uiSystem;
    private UserSessionContext userSessionContext;
    private List<Book> bookCatalog;

    @Before
    public void setUp() {
        admin = Admin.getInstance();
        libraryPatron = new LibraryPatron();
        author = new Author();
        inventorySystem = InventoryManagementSystem.getInstance();
        userSystem = UserManagementSystem.getInstance();
        uiSystem = UserInterfaceManagementSystem.getInstance();
        userSessionContext = new UserSessionContext();

        // Clear the inventory by removing all books
        inventorySystem.getAllBooks().clear();

        bookCatalog = new ArrayList<>();
        bookCatalog.add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "9780439064866", "Fantasy", 1997, 5));
        bookCatalog.add(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "9780439064867", "Fantasy", 1998, 3));
        bookCatalog.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", "Fiction", 1925, 2));
    }

    @After
    public void tearDown() {
        admin = null;
        libraryPatron = null;
        author = null;
        inventorySystem = null;
        userSystem = null;
        uiSystem = null;
        userSessionContext = null;
        bookCatalog = null;
    }

    @Test
    public void testAdminLogin() {
        admin.login("admin", "admin123");
        assertTrue(admin.isLoggedIn());
    }

    @Test
    public void testLibraryPatronLogin() {
        libraryPatron.login("library_patron", "patron123");
        assertTrue(libraryPatron.isLoggedIn());
    }

    @Test
    public void testAuthorLogin() {
        author.login("author", "author123");
        assertTrue(author.isLoggedIn());
    }

    @Test
    public void testAddBook() {
        admin.addBook("Test Book", "Test Author", "1234567890", "Test Genre", 2024);
        List<Book> books = admin.getBooks();
        assertEquals(1, books.size());
    }

    @Test
    public void testSearchBooks() {
        admin.addBook("Test Book", "Test Author", "1234567890", "Test Genre", 2024);
        String keyword = "Test";
        libraryPatron.searchBooks(admin.getBooks(), keyword);
        
    }

    @Test
    public void testDisplayAuthorInformation() {
        admin.addBook("Test Book", "Test Author", "1234567890", "Test Genre", 2024);
        author.displayAuthorInformation(admin.getBooks());
       
    }

    @Test
    public void testCheckoutBook() {
        Book book = new Book("Checkout Book", "Test Author", "9876543211", "Test Genre", 2024, 3);
        inventorySystem.addBook(book);
        userSystem.checkoutBook("Checkout Book");
        List<Book> books = inventorySystem.getAllBooks();
        assertEquals(2, books.get(0).getQuantity()); // Decrease quantity by 1 after checkout
    }

    @Test
    public void testAddBookToInventory() {
        Book book = new Book("Test Title", "Test Author", "1234567890", "Test Genre", 2024, 5);
        inventorySystem.addBook(book);
        List<Book> books = inventorySystem.getAllBooks();
        assertEquals(1, books.size()); // Ensure only 1 book is added
        assertEquals(book, books.get(0)); // Assuming Book class overrides equals() method appropriately
    }

    @Test
    public void testDeleteBook() {
        Book book = new Book("Test Title", "Test Author", "1234567890", "Test Genre", 2024, 5);
        inventorySystem.addBook(book);
        inventorySystem.deleteBook("Test Title");
        List<Book> books = inventorySystem.getAllBooks();
        assertEquals(0, books.size()); // Ensure book is deleted
    }

    @Test
    public void testReserveBook() {
        Book book = new Book("Reserved Book", "Test Author", "9876543210", "Test Genre", 2024, 3);
        inventorySystem.addBook(book);
        userSystem.reserveBook("Reserved Book");
        List<Book> books = inventorySystem.getAllBooks();
        assertEquals(2, books.get(0).getQuantity()); // Decrease quantity by 1 after reservation
    }

    @Test
    public void testUpdateBookQuantity() {
        Book book = new Book("Test Title", "Test Author", "1234567890", "Test Genre", 2024, 5);
        inventorySystem.addBook(book);
        inventorySystem.updateBookQuantity("Test Title", 10);
        List<Book> books = inventorySystem.getAllBooks();
        assertEquals(10, books.get(0).getQuantity()); // Ensure quantity is correctly updated
    }

    @Test
    public void testSearchBooksByTitle() {
        SearchBooksState searchBooksState = new SearchBooksState(bookCatalog);
        userSessionContext.setState(searchBooksState);
        searchBooksState.searchByTitle("Harry Potter and the Philosopher's Stone");
       
    }

    @Test
    public void testSearchBooksByAuthor() {
        SearchBooksState searchBooksState = new SearchBooksState(bookCatalog);
        userSessionContext.setState(searchBooksState);
        searchBooksState.searchByAuthor("J.K. Rowling");
        
    }

    @Test
    public void testSearchBooksByGenre() {
        SearchBooksState searchBooksState = new SearchBooksState(bookCatalog);
        userSessionContext.setState(searchBooksState);
        searchBooksState.searchByGenre("Fantasy");
      
    }

    @Test
    public void testSearchBooksByISBN() {
        SearchBooksState searchBooksState = new SearchBooksState(bookCatalog);
        userSessionContext.setState(searchBooksState);
        searchBooksState.searchByISBN("9780439064866");
      
    }

    @Test
    public void testUpdateProfileChangePassword() {
        UpdateProfileState updateProfileState = new UpdateProfileState();
        userSessionContext.setState(updateProfileState);
        
    }

    @Test
    public void testUpdateProfileChangeName() {
        UpdateProfileState updateProfileState = new UpdateProfileState();
        userSessionContext.setState(updateProfileState);
       
    }

    @Test
    public void testViewBookDetailsByTitle() {
        ViewBookDetailsState viewBookDetailsState = new ViewBookDetailsState(bookCatalog);
        userSessionContext.setState(viewBookDetailsState);
        viewBookDetailsState.viewByTitle("Harry Potter and the Philosopher's Stone");
       
    }

    @Test
    public void testViewBookDetailsByAuthor() {
        ViewBookDetailsState viewBookDetailsState = new ViewBookDetailsState(bookCatalog);
        userSessionContext.setState(viewBookDetailsState);
        viewBookDetailsState.viewByAuthor("F. Scott Fitzgerald");
      
    }

    @Test
    public void testViewBookDetailsByGenre() {
        ViewBookDetailsState viewBookDetailsState = new ViewBookDetailsState(bookCatalog);
        userSessionContext.setState(viewBookDetailsState);
        viewBookDetailsState.viewByGenre("Fiction");
        
    }

    @Test
    public void testSingletonInstance() {
        UserInterfaceManagementSystem instance1 = UserInterfaceManagementSystem.getInstance();
        UserInterfaceManagementSystem instance2 = UserInterfaceManagementSystem.getInstance();
        assertSame(instance1, instance2); // Verify singleton instance
    }

    @Test
    public void testViewBookDetailsStateTransition() {
        userSessionContext.setState(new ViewBookDetailsState(bookCatalog));
        userSessionContext.request();
        assertTrue(userSessionContext.getState() instanceof ViewBookDetailsState); // Ensure state transition
    }

    @Test
    public void testUpdateProfileStateTransition() {
        userSessionContext.setState(new UpdateProfileState());
        userSessionContext.request();
        assertTrue(userSessionContext.getState() instanceof UpdateProfileState); // Ensure state transition
    }
}

package Product_management_system;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.ArrayList;

public class LibraryManagementSystemTest {

    private Admin admin;
    private LibraryPatron libraryPatron;
    private Author author;

    @Before
    public void setUp() {
        admin = Admin.getInstance();
        libraryPatron = new LibraryPatron();
        author = new Author();
    }

    @After
    public void tearDown() {
        admin = null;
        libraryPatron = null;
        author = null;
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
}
package user_management_subsystem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UserManagementSystemTest {

    private UserInterfaceManagementSystem uiSystem;
    private UserSessionContext userSessionContext;
    private List<Book> bookCatalog;

    @Before
    public void setUp() {
        uiSystem = UserInterfaceManagementSystem.getInstance();
        userSessionContext = new UserSessionContext();
        bookCatalog = new ArrayList<>();
        bookCatalog.add(new Book("Harry Potter and the Philosopher's Stone", "J.K. Rowling", "9780439064866", "Fantasy", 1997, 5));
        bookCatalog.add(new Book("Harry Potter and the Chamber of Secrets", "J.K. Rowling", "9780439064867", "Fantasy", 1998, 3));
        bookCatalog.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565", "Fiction", 1925, 2));
    }

    @After
    public void tearDown() {
        uiSystem = null;
        userSessionContext = null;
        bookCatalog = null;
    }

    @Test
    public void testSearchBooksByTitle() {
        SearchBooksState searchBooksState = new SearchBooksState(bookCatalog);
        userSessionContext.setState(searchBooksState);

        // Call method with specific argument for testing
        searchBooksState.searchByTitle("Harry Potter and the Philosopher's Stone");
       
    }

    @Test
    public void testSearchBooksByAuthor() {
        SearchBooksState searchBooksState = new SearchBooksState(bookCatalog);
        userSessionContext.setState(searchBooksState);

        // Call method with specific argument for testing
        searchBooksState.searchByAuthor("J.K. Rowling");
        
    }

    @Test
    public void testSearchBooksByGenre() {
        SearchBooksState searchBooksState = new SearchBooksState(bookCatalog);
        userSessionContext.setState(searchBooksState);

        // Call method with specific argument for testing
        searchBooksState.searchByGenre("Fantasy");
        
    }

    @Test
    public void testSearchBooksByISBN() {
        SearchBooksState searchBooksState = new SearchBooksState(bookCatalog);
        userSessionContext.setState(searchBooksState);

        // Call method with specific argument for testing
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

        // Call method with specific argument for testing
        viewBookDetailsState.viewByTitle("Harry Potter and the Philosopher's Stone");
        
    }

    @Test
    public void testViewBookDetailsByAuthor() {
        ViewBookDetailsState viewBookDetailsState = new ViewBookDetailsState(bookCatalog);
        userSessionContext.setState(viewBookDetailsState);

        // Call method with specific argument for testing
        viewBookDetailsState.viewByAuthor("F. Scott Fitzgerald");
       
    }

    @Test
    public void testViewBookDetailsByGenre() {
        ViewBookDetailsState viewBookDetailsState = new ViewBookDetailsState(bookCatalog);
        userSessionContext.setState(viewBookDetailsState);

        // Call method with specific argument for testing
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

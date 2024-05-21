package inventory_management_subsystem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class InventoryManagementSystemTest {

    private InventoryManagementSystem inventorySystem;
    private UserManagementSystem userSystem;

    @Before
    public void setUp() {
        inventorySystem = InventoryManagementSystem.getInstance();
        // Clear the inventory by removing all books
        inventorySystem.getAllBooks().clear();
        userSystem = UserManagementSystem.getInstance();
    }

    @After
    public void tearDown() {
        inventorySystem = null;
        userSystem = null;
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
    public void testAddBook() {
        Book book = new Book("Test Title", "Test Author", "1234567890", "Test Genre", 2024, 5);
        inventorySystem.addBook(book);
        List<Book> books = inventorySystem.getAllBooks();
        assertEquals(1, books.size()); // Ensure only 1 book is added
        assertEquals(book, books.get(0)); 
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
}

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.List;

public class BookManagerTest {
    private BookManager bookManager;

    @Before
    public void setUp() {
        bookManager = new BookManager();
    }

    @Test
    public void testAddBook() {
        bookManager.addBook("The Player Next Door", "BabyInACorner");
        List<Book> books = bookManager.getBooks();
        assertEquals(1, books.size());
        assertEquals("The Player Next Door", books.get(0).getTitle());
        assertEquals("BabyInACorner", books.get(0).getAuthor());
    }

    @Test
    public void testFindBookByTitle() {
        bookManager.addBook("The Player Next Door", "BabyInACorner");
        bookManager.addBook("After", "imaginator1D");
        
        Book book = bookManager.findBookByTitle("After");
        assertNotNull(book);
        assertEquals("After", book.getTitle());
        assertEquals("imaginator1D", book.getAuthor());
    }

    @Test
    public void testFindBookByTitleNotFound() {
        bookManager.addBook("The Player Next Door", "BabyInACorner");
        
        Book book = bookManager.findBookByTitle("Nonexistent");
        assertNull(book);
    }

    @Test
    public void testGetBooks() {
        bookManager.addBook("The Player Next Door", "BabyInACorner");
        bookManager.addBook("After", "imaginator1D");
        
        List<Book> books = bookManager.getBooks();
        assertEquals(2, books.size());
    }
}

package assessment02.assessment02;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

class BookCollectionTest {

    private static WebDriver webDriver;
    private static final String BASE_URL = "C:\\Users\\StephChan\\git\\assessment02\\files\\Library.html";
    private BookCollection bc;
    private Book b1;
    private Book b2;
    private Book b3;
    private Book b4;
    private final int INITIAL_BOOK_COLLECTION_SIZE = 11; // Initial size from Library.html

    @BeforeAll
    static void setUpWebDriver() {
        String chromeDriverPath = "C:\\Users\\StephChan\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterAll
    static void tearDownWebDriver() {
        if (webDriver != null) {
            webDriver.quit(); // Use quit() to close all windows and end the WebDriver session
        }
    }

    @BeforeEach
    void setUp() {
        bc = new BookCollection();
        b1 = new Book("0012", "Don't give up on me", "BabyInACorner");
        b2 = new Book("0013", "Cliché", "BabyInACorner");
        b3 = new Book("0014", "Hating The Player", "xThePineappleGirlx");
        b4 = new Book("0015", "After 2", "imaginator1D");
        bc.addBook(b1);
        bc.addBook(b2);
        bc.addBook(b3);
        bc.addBook(b4);
    }

    @AfterEach
    void tearDown() {
        bc = null;
        b1 = null;
        b2 = null;
        b3 = null;
        b4 = null;
    }

    // Book Tests
    @Test
    void testBookConstructors() {
        Book book = new Book("0016", "Test Title", "Test Author");
        assertNotNull(book);
    }

    @Test
    void testBookGettersAndSetters() {
        Book book = new Book("0016", "Test Title", "Test Author");
        assertEquals("0016", book.getId());
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Author", book.getAuthor());

        book.setId("0017");
        book.setTitle("New Title");
        book.setAuthor("New Author");

        assertEquals("0017", book.getId());
        assertEquals("New Title", book.getTitle());
        assertEquals("New Author", book.getAuthor());
    }

    @Test
    void testBookEqualsAndHashCode() {
        Book book1 = new Book("0018", "Another Title", "Another Author");
        Book book2 = new Book("0018", "Another Title", "Another Author");
        Book book3 = new Book("0019", "Different Title", "Different Author");

        assertTrue(book1.equals(book2));
        assertFalse(book1.equals(book3));
        assertEquals(book1.hashCode(), book2.hashCode());
        assertNotEquals(book1.hashCode(), book3.hashCode());
    }

    @Test
    void testBookComparators() {
        Book book1 = new Book("0020", "Alpha", "Author A");
        Book book2 = new Book("0021", "Beta", "Author B");

        assertTrue(Book.titleComparator.compare(book1, book2) < 0);
        assertTrue(Book.authorComparator.compare(book1, book2) < 0);
    }

    // BookCollection Tests
    @Test
    void testBookCollectionConstructor() {
        BookCollection newBc = new BookCollection();
        assertNotNull(newBc);
    }

    @Test
    void testBookCollectionParameterizedConstructor() {
        BookCollection newBc = new BookCollection(50);
        assertNotNull(newBc);
    }

    @Test
    void testGetBooks() {
        List<Book> books = bc.getBooks();
        assertNotNull(books);
        assertEquals(INITIAL_BOOK_COLLECTION_SIZE + 4, books.size()); // Initial size + added books
        assertTrue(books.contains(b1));
        assertTrue(books.contains(b2));
        assertTrue(books.contains(b3));
        assertTrue(books.contains(b4));
    }

    @Test
    void testAddBook() {
        Book newBook = new Book("0016", "New Book", "New Author");
        bc.addBook(newBook);
        List<Book> books = bc.getBooks();
        assertEquals(INITIAL_BOOK_COLLECTION_SIZE + 5, books.size());
        assertTrue(books.contains(newBook));
    }

    @Test
    void testAddBookAtCapacity() {
        BookCollection smallBc = new BookCollection(3);
        smallBc.addBook(b1);
        smallBc.addBook(b2);
        smallBc.addBook(b3);
        assertEquals(3, smallBc.getBooks().size());

        Book b4 = new Book("0015", "Fourth Book", "Author");
        smallBc.addBook(b4); // Should not be added
        assertEquals(3, smallBc.getBooks().size());
    }

    @Test
    void testSortBooksByTitle() {
        bc.sortBooksByTitle();
        List<Book> sortedBooks = bc.getBooks();
        assertEquals("After 2", sortedBooks.get(0).getTitle());
        assertEquals("Cliché", sortedBooks.get(1).getTitle());
        assertEquals("Don't give up on me", sortedBooks.get(2).getTitle());
        assertEquals("Hating The Player", sortedBooks.get(3).getTitle());
    }

    @Test
    void testSortBooksByAuthor() {
        bc.sortBooksByAuthor();
        List<Book> sortedBooks = bc.getBooks();
        assertEquals("BabyInACorner", sortedBooks.get(0).getAuthor());
        assertEquals("BabyInACorner", sortedBooks.get(1).getAuthor());
        assertEquals("imaginator1D", sortedBooks.get(2).getAuthor());
        assertEquals("xThePineappleGirlx", sortedBooks.get(3).getAuthor());
    }

    @Test
    void testFindBookById() {
        Book foundBook = bc.findBookById("0013");
        assertNotNull(foundBook);
        assertEquals(b2, foundBook);

        Book notFoundBook = bc.findBookById("9999");
        assertNull(notFoundBook);
    }

    @Test
    void testFindBookByTitle() {
        Book foundBook = bc.findBookByTitle("After 2");
        assertNotNull(foundBook);
        assertEquals(b4, foundBook);

        Book notFoundBook = bc.findBookByTitle("Nonexistent Title");
        assertNull(notFoundBook);
    }

    // Selenium Tests
    @Test
    void testNavbarLinks() {
        webDriver.navigate().to(BASE_URL);
        List<WebElement> navLinks = webDriver.findElements(By.cssSelector(".navbar a"));
        assertEquals(4, navLinks.size());

        String[] expectedLinks = {"Home", "Books", "Authors", "Contact"};
        for (int i = 0; i < navLinks.size(); i++) {
            assertEquals(expectedLinks[i], navLinks.get(i).getText());
        }
    }

    @Test
    void testTableContent() {
        webDriver.navigate().to(BASE_URL);

        WebElement table = webDriver.findElement(By.tagName("table"));
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        String[][] expectedData = {
            {"The Player Next Door", "BabyInACorner"},
            {"After", "imaginator1D"},
            {"The Bad Boy, Cupid & Me", "Slim_Shady"},
            {"Eyes Open", "HonorInTheRain"},
            {"Face Your Fears", "HonorInTheRain"},
            {"Play That Part", "BabyInACorner"},
            {"68 Days And Counting", "Nicole Nwosu"},
            {"The Bad Boy And The Tom Boy", "Nicole Nwosu"},
            {"Play No More", "BabyInACorner"},
            {"In 27 Days", "HonorInTheRain"},
            {"Something Inside", "OutOfMyLimit17"}
        };

        // Verify the number of rows matches the expected data length plus one for the header
        assertEquals(rows.size(), expectedData.length + 1);

        // Verify each row's content matches the expected data
        for (int i = 1; i < rows.size(); i++) { // Start from 1 to skip the header row
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
            assertEquals(cells.get(0).getText(), expectedData[i - 1][0]);
            assertEquals(cells.get(1).getText(), expectedData[i - 1][1]);
        }
    }

    @Test
    void testFooterPresence() {
        webDriver.navigate().to(BASE_URL);
        WebElement footer = webDriver.findElement(By.className("footer"));
        assertTrue(footer.isDisplayed());
        assertEquals("Important Library Notes", footer.getText().trim());
    }
}

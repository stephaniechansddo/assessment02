package assessment02.assessment02;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NewLibraryTest {
    private WebDriver webDriver;
    private static final String BASE_URL = "C:\\Users\\StephChan\\git\\assessment02\\files\\Library.html";

    @BeforeTest
    public void setUp() {
        String chromeDriverPath = "C:\\Users\\StephChan\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterTest
    public void tearDown() {
        if (webDriver != null) {
            webDriver.close();
        }
    }

    @Test
    public void testNavbarLinks() {
        webDriver.navigate().to(BASE_URL);
        List<WebElement> navLinks = webDriver.findElements(By.cssSelector(".navbar a"));
        Assert.assertEquals(navLinks.size(), 4);

        String[] expectedLinks = {"Home", "Books", "Authors", "Contact"};
        for (int i = 0; i < navLinks.size(); i++) {
            Assert.assertEquals(navLinks.get(i).getText(), expectedLinks[i]);
        }
    }

    @Test
    public void testTableContent() {
    webDriver.navigate().to(BASE_URL);

    // Find the table element
    WebElement table = webDriver.findElement(By.tagName("table"));

    // Find all rows in the table
    List<WebElement> rows = table.findElements(By.tagName("tr"));

    // Expected data
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
    Assert.assertEquals(rows.size(), expectedData.length + 1);

    // Verify each row's content matches the expected data
    for (int i = 1; i < rows.size(); i++) { // Start from 1 to skip the header row
        List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));
        Assert.assertEquals(cells.get(0).getText(), expectedData[i - 1][0]);
        Assert.assertEquals(cells.get(1).getText(), expectedData[i - 1][1]);
    }
}

    @Test
    public void testFooterPresence() {
        webDriver.navigate().to(BASE_URL);
        WebElement footer = webDriver.findElement(By.className("footer"));
        Assert.assertTrue(footer.isDisplayed());
        Assert.assertEquals(footer.getText().trim(), "Important Library Notes");
    }

}

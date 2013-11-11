package eu.fourFinance.web.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import eu.fourFinance.services.EvaluationService;
import eu.fourFinance.testsuites.categories.WebTests;

@Category(WebTests.class)
public class HappyPathWebSeleniuTest {

    private static final String URL = "http://localhost:8080/";

    private static final long WAIT = 2000l;

    private static final String CODE = "AAAA";
    private static final String LOAN = "3000";
    private static final String TERM = "10";

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testRate() {

        driver.get(URL);
        sleep(WAIT);

        WebElement element = driver.findElement(By.id("rate"));

        assertNotNull(element);

        assertEquals(String.valueOf(EvaluationService.LOAN_RATE), element.getText());

    }

    @Test
    public void testHappyPath() {

        driver.get(URL + "#new");

        driver.findElement(By.name("code")).sendKeys(CODE);
        driver.findElement(By.name("loan")).sendKeys(LOAN);
        driver.findElement(By.name("term")).sendKeys(TERM);
        driver.findElement(By.name("go")).click();

        sleep(WAIT);

        assertTrue(Double.parseDouble(LOAN) < Double.parseDouble(driver.findElement(By.name("totalPay")).getText()));

        driver.findElement(By.id("applayId")).click();

        sleep(WAIT);

        driver.get(URL + "#my");

        sleep(WAIT);

        assertEquals(1, driver.findElements(By.name("totalPay")).size());

        driver.findElement(By.id("extendId")).click();
        sleep(WAIT);

        assertEquals(2, driver.findElements(By.name("totalPay")).size());

    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }
}

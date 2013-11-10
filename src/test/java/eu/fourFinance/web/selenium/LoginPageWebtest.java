package eu.fourFinance.web.selenium;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import eu.fourFinance.testsuites.categories.UnitTests;
import eu.fourFinance.testsuites.categories.WebTests;
 
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
 
/**
 * Simple web test that just queries the login page through the controller.
 * @author Florian Hopf, Synyx GmbH & Co. KG, hopf@synyx.de
 */
@Ignore
@Category(WebTests.class)
public class LoginPageWebtest {
 
    @Test
    public void testPage() {
        WebDriver driver = new HtmlUnitDriver();
 
        driver.get("http://localhost:8081/url/that/redirects/to/login/");
 
        try {
            // Find the text input element by its name
            WebElement element = driver.findElement(By.name("username"));
 
            assertNotNull(element);
        } catch (NoSuchElementException ex) {
            fail("Startup of context failed. See console output for more information, : " + ex.getMessage());
        }
 
        //Close the browser
        driver.quit();
    }
}
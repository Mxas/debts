package eu.fourFinance.web.selenium;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import eu.fourFinance.testsuites.categories.UnitTests;
import eu.fourFinance.testsuites.categories.WebTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@Category(WebTests.class)
public class TestTest {

	@Test
	public void testPage() {
		int i;
	}

	@Test
	public void testPage2() {
		WebDriver driver = new HtmlUnitDriver(true);

		driver.get("http://localhost:8080/loan/");

		WebElement element = driver.findElement(By.id("rate"));

		assertNotNull(element);

		driver.quit();
	}

	@Test
	public void testFF() {
		WebDriver driver = new ChromeDriver();

		driver.get("http://localhost:8080/loan/");

		WebElement element = driver.findElement(By.id("rate"));

		assertNotNull(element);

		driver.quit();
	}

	public static void main(String[] args) {
		System.out.println("start...");
		WebDriver driver = new HtmlUnitDriver(true);
		System.out.println("ff......");
		driver.get("http://localhost:8080/loan/#/new");

		System.out.println("Page title is: " + driver.getTitle());

		// Find the text input element by its name
		WebElement element = driver
				.findElement(By.name("code"));
		assertNotNull(element);

		// Check the title of the page
		System.out.println("Page title is: " + element.getText());

		driver.quit();
	}

}
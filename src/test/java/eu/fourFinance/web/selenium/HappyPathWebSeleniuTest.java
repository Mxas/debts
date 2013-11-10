package eu.fourFinance.web.selenium;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import eu.fourFinance.services.EvaluationService;
import eu.fourFinance.testsuites.categories.UnitTests;
import eu.fourFinance.testsuites.categories.WebTests;

@Category(WebTests.class)
public class HappyPathWebSeleniuTest {

	private static final String DEFAULT_WAIT_PERIOD = "3000";
	private Selenium selenium;
	//private WebDriver driver = new FirefoxDriver();

	@Before
	public void setUp() throws Exception {
		String host = "http://localhost:8080/";
		String browser = "*firefox";
		// String browser = "*iexplore";

		// configure the selenium client
		selenium = new DefaultSelenium("localhost", 4444, browser, host);

		// launch the browser window
		selenium.start();
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}

	@Test
	public void verifySimpleFormAndRespone() {
		selenium.open("loan/");
		selenium.waitForPageToLoad(DEFAULT_WAIT_PERIOD);

		String rate = selenium.getValue("rate");

		// String name = "Patricia";
		// String surname = "Persson";

		// selenium.type("name", name);
		// selenium.type("surname", surname);

		// selenium.click("submit");
		// selenium.waitForPageToLoad(DEFAULT_WAIT_PERIOD);

		assertEquals(Integer.parseInt(rate), EvaluationService.LOAN_RATE);
		// assertTrue(selenium.isTextPresent(surname));
	}

}

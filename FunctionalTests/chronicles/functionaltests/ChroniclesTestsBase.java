package chronicles.functionaltests;

import org.junit.After;
import org.junit.Before;

import chronicles.pages.ChroniclesSelenium;

public class ChroniclesTestsBase {
	protected ChroniclesSelenium selenium;

	@Before
	public void setUp() {
		selenium = new ChroniclesSelenium();
	}

	protected void login(String username, String password) {
		selenium.type("username", username);
		selenium.type("password", password);
		selenium.clickAndWait("submit");
	}

	@After
	public void tearDown() {
		selenium.stop();
	}
}
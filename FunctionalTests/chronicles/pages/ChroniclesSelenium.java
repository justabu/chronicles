package chronicles.pages;

import com.thoughtworks.selenium.DefaultSelenium;

public class ChroniclesSelenium {
	private static final String DEFAULT_WAIT_TIME = "100000";
	DefaultSelenium defaultSelenium;

	public ChroniclesSelenium() {
		defaultSelenium = new DefaultSelenium("localhost", 4444, "*chrome",	"http://localhost:8080/");
		defaultSelenium.start();
	}

	public void open(String url) {
		defaultSelenium.open(url);
		waitDefaultTime();
	}

	public String getText(String locator) {
		return defaultSelenium.getText(locator);
	}

	public boolean isElementPresent(String locator) {
		return defaultSelenium.isElementPresent(locator);
	}

	public void type(String locator, String value) {
		defaultSelenium.type(locator, value);
		
	}
	
	public void chooseOkOnNextConfirmation(){
		defaultSelenium.chooseOkOnNextConfirmation();
	}
	
	public String getConfirmation(){
		return defaultSelenium.getConfirmation();
	}
	

	public void clickAndWait(String locator) {
		defaultSelenium.click(locator);
		waitDefaultTime();
	}

	public void stop() {
		 defaultSelenium.stop();
	}

	public void select(String value, String locator) {
		defaultSelenium.select(value, locator);
	}

	public String[] getAllLinks() {
		return defaultSelenium.getAllLinks();
	}

	public boolean isTextPresent(String pattern) {
		return defaultSelenium.isTextPresent(pattern);
	}

	public String[] getAllButtons() {
		return defaultSelenium.getAllButtons();
	}

	public String[] getAllFields() {
		return defaultSelenium.getAllFields();
	}

	public void click(String string) {
		defaultSelenium.click(string);
	}
	
	public String getValue(String locator) {
		return defaultSelenium.getValue(locator);
	}
	
	private void waitDefaultTime() {
		defaultSelenium.waitForPageToLoad(DEFAULT_WAIT_TIME);
	}
}

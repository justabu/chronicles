package chronicles.pages;


public class HomePage {

	private final ChroniclesSelenium chroniclesSelenium;

	public HomePage(ChroniclesSelenium chroniclesDefaultSelenium) {
		this.chroniclesSelenium = chroniclesDefaultSelenium;
	}

	public String addStoryLink() {
		return chroniclesSelenium.getText("link=Add Story");
	}

	public String browseStoryLinks() {
		return chroniclesSelenium.getText("link=Browse Stories");
	}

	public String manageProfileLink() {
		return chroniclesSelenium.getText("link=Manage Profile");
	}

	public boolean usernameIsDisplayed() {
		return chroniclesSelenium.isElementPresent("loggedInUser");
	}

	public BrowseStoriesPage search(String title) {
		chroniclesSelenium.type("query", title);
		chroniclesSelenium.clickAndWait("search");
		return new BrowseStoriesPage(chroniclesSelenium);
	}

	public Boolean hasText(String text) {
		return chroniclesSelenium.isTextPresent(text);
	}
	
}
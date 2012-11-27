package chronicles.pages;

public class ProfilePage {

	private final ChroniclesSelenium selenium;

	public ProfilePage(ChroniclesSelenium chroniclesSelenium) {
		this.selenium = chroniclesSelenium;
	}

	public Boolean hasText(String pattern) {
		return selenium.isTextPresent(pattern);
	}

	public EditProfilePage clickAndEditProfile() {
		selenium.clickAndWait("editProfile");
		return new EditProfilePage(selenium);
	}
	
	public HomePage clickHomeLink() {
		selenium.clickAndWait("home_link");
		return new HomePage(selenium);
	}

	public Boolean hasStoryInTable(String tableId, String title) {
		return selenium.isElementPresent(String.format("//table[@id='%s']//td//a[contains(text(), '%s')]", tableId, title));
	}

}

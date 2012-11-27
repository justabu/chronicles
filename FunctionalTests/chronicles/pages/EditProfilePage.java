package chronicles.pages;

public class EditProfilePage {

	private final ChroniclesSelenium chroniclesSelenium;

	public EditProfilePage(ChroniclesSelenium chroniclesSelenium) {
		this.chroniclesSelenium = chroniclesSelenium;
	}

	
	public ProfilePage editUserProfile(String shortDescription, String homeOffice){
		chroniclesSelenium.type("shortDescription", shortDescription);
		chroniclesSelenium.type("homeOffice", homeOffice);
		chroniclesSelenium.clickAndWait("save");
		return new ProfilePage(chroniclesSelenium);
	 }
	
	public Boolean hasText(String text) {
		return chroniclesSelenium.isTextPresent(text);
	}
}

package chronicles.pages;

import java.util.Arrays;
import java.util.List;


public class ViewStoryPage {
	private ChroniclesSelenium chroniclesSelenium;

	public ViewStoryPage(ChroniclesSelenium chroniclesSelenium) {
		this.chroniclesSelenium = chroniclesSelenium;
	}

	public Boolean hasText(String text) {
		return chroniclesSelenium.isTextPresent(text);
	}
	
	public Boolean hasDeleteButton() {
		return getListOfButtons().contains("delete");
	}
	
	public Boolean hasEditButton() {
		return getListOfButtons().contains("edit");
	}

	public void addTag(String tagText) {
		chroniclesSelenium.type("commaSeparatedTags", tagText);
		chroniclesSelenium.clickAndWait("addTag");
	}

	public void flagStory() {
		clickFlag();
	}
	
	public void unflagStory() {
		clickFlag();
	}

	public Integer getFlagCount() {
		 String message = chroniclesSelenium.getText("flag_message");
		 return Integer.valueOf(message.substring(0, 1));
	}
	
	public void addToUserFavourites() {
		chroniclesSelenium.clickAndWait("favourite");	
	}


	public EditStoryPage goToEditStory() {
		chroniclesSelenium.clickAndWait("edit");
		return new EditStoryPage(chroniclesSelenium);
		
	}


	public ProfilePage deleteStory() {
		chroniclesSelenium.clickAndWait("delete");
		chroniclesSelenium.getConfirmation();
		chroniclesSelenium.chooseOkOnNextConfirmation();
        return new ProfilePage(chroniclesSelenium);
	}
	
	private void clickFlag() {
		chroniclesSelenium.clickAndWait("inappropriate");
	}
	
	private List<String> getListOfButtons() {
		return Arrays.asList(chroniclesSelenium.getAllButtons());
	}

	public void commentStory(String comment) {
		chroniclesSelenium.type("content", comment);
		chroniclesSelenium.clickAndWait("publish");
	}

}

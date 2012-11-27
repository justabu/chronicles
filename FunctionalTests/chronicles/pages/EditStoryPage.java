package chronicles.pages;

public class EditStoryPage {

	private final ChroniclesSelenium chroniclesSelenium;


	public EditStoryPage(ChroniclesSelenium chroniclesSelenium) {
		this.chroniclesSelenium = chroniclesSelenium;
			
	}


	public ViewStoryPage editTheStoryTitle(String newTitle){
		chroniclesSelenium.type("title", newTitle);
		chroniclesSelenium.clickAndWait("publish");
		return new ViewStoryPage(chroniclesSelenium);
	}

 }

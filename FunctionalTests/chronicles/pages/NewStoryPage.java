package chronicles.pages;

public class NewStoryPage {

	private final ChroniclesSelenium chroniclesSelenium;
	

	public NewStoryPage(ChroniclesSelenium chroniclesSelenium) {
		this.chroniclesSelenium = chroniclesSelenium;
	}
	
	public ViewStoryPage addStory(String time) {
		return addStoryWithFields(time, "January", "2010", "Bangalore", "Random Story");
	}
	
	public ViewStoryPage addStoryWithFields(String title, String month, String year, String city, String text) {
		chroniclesSelenium.type("title", title);
		chroniclesSelenium.select("month", "label=" + month);
		chroniclesSelenium.type("year", year);
		chroniclesSelenium.type("city", city);
		chroniclesSelenium.type("tinymce", text);
		chroniclesSelenium.clickAndWait("publish");
		return new ViewStoryPage(chroniclesSelenium);
	}
	
	public void enterIllegalFieldsInForm() {
		chroniclesSelenium.type("title", "");
		chroniclesSelenium.select("month", "label=January");
		chroniclesSelenium.type("year", "2011");
		chroniclesSelenium.type("city", "");
		chroniclesSelenium.type("tinymce", "Random Story");
		chroniclesSelenium.click("publish");
	}

	public Boolean hasValidationError(String errorIfJSEnabled) {
		return chroniclesSelenium.isTextPresent(errorIfJSEnabled);
	}

}

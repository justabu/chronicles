package chronicles.pages;



public class BrowseStoriesPage {

	private final ChroniclesSelenium chroniclesSelenium;

	public BrowseStoriesPage(ChroniclesSelenium chroniclesSelenium) {
		this.chroniclesSelenium = chroniclesSelenium;
	}
	
	public boolean hasStoryWith(String title) {
		return chroniclesSelenium.isTextPresent(title);
	}

	public ViewStoryPage navigateToStoryPageWith(String title) {
		 chroniclesSelenium.clickAndWait(title);
		 return new ViewStoryPage(chroniclesSelenium);
	}

	public ProfilePage navigateToProfilePageForUser(String author) {
		chroniclesSelenium.clickAndWait(author);
		return new ProfilePage(chroniclesSelenium);
	}

	public BrowseStoriesPage search(String keyword) {
		chroniclesSelenium.type("keyword_query", keyword);
		chroniclesSelenium.clickAndWait("keyword_search");
		return new BrowseStoriesPage(chroniclesSelenium);
	}

	public boolean showsKeywordInSearchbox(String title) {
		return title.equals(chroniclesSelenium.getValue("id=keyword_query"));
	}
   
}

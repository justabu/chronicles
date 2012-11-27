package chronicles.pages;

public class NavigateTo {

	public static HomePage HomePage(ChroniclesSelenium chroniclesSelenium) {
		chroniclesSelenium.open("/chronicles/home");
		return new HomePage(chroniclesSelenium);
	}

	public static NewStoryPage NewStoryPage(ChroniclesSelenium chroniclesSelenium) {
		chroniclesSelenium.open("/chronicles/story/new");
		return new NewStoryPage(chroniclesSelenium);
	}

	public static BrowseStoriesPage BrowseStoriesPage(ChroniclesSelenium chroniclesSelenium) {
		chroniclesSelenium.open("/chronicles/story");
		return new BrowseStoriesPage(chroniclesSelenium);
	}

	public static ViewStoryPage viewStoryPage(
		Integer storyId,
		ChroniclesSelenium chroniclesSelenium
	) {
		chroniclesSelenium.open("chronicles/story/"+storyId);
		return new ViewStoryPage(chroniclesSelenium);
	}

	public static ProfilePage ProfilePage(ChroniclesSelenium chroniclesSelenium) {
		chroniclesSelenium.open("/chronicles/profile");
		return new ProfilePage(chroniclesSelenium);
	}
}

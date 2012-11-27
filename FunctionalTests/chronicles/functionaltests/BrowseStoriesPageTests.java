package chronicles.functionaltests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;

import chronicles.pages.BrowseStoriesPage;
import chronicles.pages.NavigateTo;
import chronicles.pages.ProfilePage;
import chronicles.pages.ViewStoryPage;

public class BrowseStoriesPageTests extends ChroniclesTestsBase {


	private BrowseStoriesPage browseStoriesPage;

	@Before
	public void setup() throws InterruptedException {
		browseStoriesPage = NavigateTo.BrowseStoriesPage(selenium);
		login("rod","rod");
	}

	@Test 
	public void shouldAddStoryAndCheckIfPresentInListOfStories() throws InterruptedException{
		assertThat(browseStoriesPage.hasStoryWith("Title1"), is(true));
		assertThat(browseStoriesPage.hasStoryWith("Title2"), is(true));
		assertThat(browseStoriesPage.hasStoryWith("Title3"), is(true));
	}

	@Test 
	public void shouldNavigateToViewPageOnClickingTitle() throws InterruptedException{
		ViewStoryPage viewStoryPage = browseStoriesPage.navigateToStoryPageWith("link=Title1");
		assertThat(viewStoryPage.hasText("Story 1"), is(true));
	}

	@Test 
	public void shouldNavigateToProfilePageOnClickingAuthorsLink() throws InterruptedException{
		ProfilePage profilePage = browseStoriesPage.navigateToProfilePageForUser("link=rod rod");
		assertThat(profilePage.hasText("My Stories"), is(true));
	}
}
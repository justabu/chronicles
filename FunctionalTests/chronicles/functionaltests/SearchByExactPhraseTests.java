package chronicles.functionaltests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import chronicles.pages.BrowseStoriesPage;
import chronicles.pages.HomePage;
import chronicles.pages.NavigateTo;
import chronicles.pages.NewStoryPage;
import chronicles.pages.ViewStoryPage;

public class SearchByExactPhraseTests extends ChroniclesTestsBase {
	private NewStoryPage newStoryPage;
	
	@Before
	public void setup() throws InterruptedException {
		
		newStoryPage = NavigateTo.NewStoryPage(selenium);
		login("rod", "rod");
	}
	
	@Test
	public void shouldAddStoryAndFindItInEmptySearch() {
		String title = addAStory();
		BrowseStoriesPage searchResults = getSearchResults("");
		assertThat(searchResults.hasStoryWith(title), is(true));
		ViewStoryPage viewStoryPage = searchResults.navigateToStoryPageWith("link=" + title);
		assertThat(viewStoryPage.hasText(title), is(true));
	}


	@Test
	public void shouldAddStoryAndSearchByTitle() throws Exception {
		String title = addAStory();
		BrowseStoriesPage searchResults = getSearchResults(title);
		assertThat(searchResults.hasStoryWith(title), is(true));
		ViewStoryPage viewStoryPage = searchResults.navigateToStoryPageWith("link=" + title);
		assertThat(viewStoryPage.hasText(title), is(true));
	}

	@Test
	public void shouldGiveErrorMessageWhenNoStoryMatchesKeyword() throws Exception {
		Date futureDate = new Date(new Date().getTime() + 500000);
		String notPresentTitle = futureDate.toString();
		BrowseStoriesPage searchResults = getSearchResults(notPresentTitle);
		assertThat(searchResults.hasStoryWith(notPresentTitle), is(false));
	}
	
	@Test
	public void shouldSearchFromNavigationBarAndReturnStoryInResults() throws Exception {
		String title = addAStory();
		HomePage homepage = NavigateTo.HomePage(selenium);
		BrowseStoriesPage searchResults = homepage.search(title);
		assertThat(searchResults.hasStoryWith(title), is(true));
		assertThat(searchResults.showsKeywordInSearchbox(title), is(true));
	}
	
	@Test
	public void shouldFindStoryWithSpecifiedFields() throws Exception {
		String title = "Thoughtworks Chronicles Test";
		newStoryPage.addStoryWithFields(title, "September", "2001", "Chicago", "Test Chronicles");
		BrowseStoriesPage browseStoriesPage = NavigateTo.BrowseStoriesPage(selenium);
		
		BrowseStoriesPage searchResults = browseStoriesPage.search("Test");
		assertThat(searchResults.hasStoryWith(title),is(true));
		
		searchResults = browseStoriesPage.search("Chicago");
		assertThat(searchResults.hasStoryWith(title),is(true));

		searchResults = browseStoriesPage.search("rod");
		assertThat(searchResults.hasStoryWith(title), is(true));
	}
	
	private String createTitle() {
		Date time = new Date();
		String title = time.toString();
		return title;
	}	

	private BrowseStoriesPage getSearchResults(String keyword) {
		BrowseStoriesPage browseStoriesPage = NavigateTo.BrowseStoriesPage(selenium);
		BrowseStoriesPage searchResults = browseStoriesPage.search(keyword);
		return searchResults;
	}
	
	private String addAStory() {
		String title = createTitle();
		newStoryPage.addStory(title);
		return title;
	}
}

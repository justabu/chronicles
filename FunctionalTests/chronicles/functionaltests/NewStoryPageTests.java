package chronicles.functionaltests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import chronicles.pages.BrowseStoriesPage;
import chronicles.pages.NavigateTo;
import chronicles.pages.NewStoryPage;
import chronicles.pages.ProfilePage;
import chronicles.pages.ViewStoryPage;

public class NewStoryPageTests extends ChroniclesTestsBase {
	private NewStoryPage newStoryPage;
	
	@Before
	public void setup() throws InterruptedException {
		
		newStoryPage = NavigateTo.NewStoryPage(selenium);
		login("rod", "rod");
	}

	@Test 
	public void shouldAddStoryAndCheckIfPresentInListOfStories() throws InterruptedException {
		String title = new Date().toString();
		ViewStoryPage viewStoryPage = newStoryPage.addStory(title);
		assertThat(viewStoryPage.hasText(title),is(true)); 
		BrowseStoriesPage browseStoriesPage = NavigateTo.BrowseStoriesPage(selenium);
		assertTrue(browseStoriesPage.hasStoryWith(title));
	}

	@Test
	public void shouldNotAddIllegalStory() throws InterruptedException {
		newStoryPage.enterIllegalFieldsInForm();
		
		String errorIfJSEnabled = "This field is required";
		assertThat(newStoryPage.hasValidationError(errorIfJSEnabled), is(true));
	}
	
    @Test
    @Ignore
	public void shouldAddLinkToImageWithinStory() throws Exception {
		String imgUrl = "/chronicles/js/tiny_mce/plugins/emotions/img/smiley-cry.gif";
//		newStoryPage.addStoryWithImageLink(imgUrl);
		assertTrue(selenium.isElementPresent(String.format("//img[@src='%s']", imgUrl)));
	}
}
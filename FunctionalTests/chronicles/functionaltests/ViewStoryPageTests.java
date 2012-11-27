package chronicles.functionaltests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import chronicles.pages.NavigateTo;
import chronicles.pages.ProfilePage;
import chronicles.pages.ViewStoryPage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/functionalTestsContext.xml")
public class ViewStoryPageTests extends ChroniclesTestsBase {
	
	@Autowired
	private DatabaseHelper databaseHelper;
	
	private ViewStoryPage viewStoryPage;

	
	@Test
	public void shouldViewAStoryAndCheckItContainsExpectedItems() throws InterruptedException{
		viewStoryPage = NavigateTo.viewStoryPage(1,selenium);
		login("rod","rod");
		assertThat(viewStoryPage.hasText("Title1"),is(true));
		assertThat(viewStoryPage.hasText("Story 1"),is(true));
	}

	@Test
	public void shouldAddATagAndCheckIt() throws InterruptedException{
		viewStoryPage = NavigateTo.viewStoryPage(1,selenium);
		login("rod","rod");
		String tagText = "tagtext";
		viewStoryPage.addTag(tagText);
		assertThat(viewStoryPage.hasText(tagText),is(true));
	}
	
	@Test 
	public void shouldDisplayEditAndDeleteButtonsWhenAuthorIsViewingTheirStory() throws Exception {
		viewStoryPage = NavigateTo.viewStoryPage(1, selenium);
		login("rod","rod");
		
		assertTrue(viewStoryPage.hasDeleteButton());
		assertTrue(viewStoryPage.hasEditButton());
	}
	
	@Test 
	public void shouldAddCurrentStoryAsCurrentUserFavourite() throws Exception {
		viewStoryPage = NavigateTo.viewStoryPage(1,selenium);
		login("rod","rod");
		viewStoryPage.addToUserFavourites();
		assertThat(viewStoryPage.hasText("One of my favourites"),is(true));
		databaseHelper.removeFavourite("rod",1);
	}
	
	@Test
	public void shouldAddStoryToFavouritesInProfileTab() throws Exception {
		viewStoryPage = NavigateTo.viewStoryPage(1, selenium);
		login("rod","rod");
		viewStoryPage.addToUserFavourites();
		ProfilePage profilePage = NavigateTo.ProfilePage(selenium);
		assertThat(profilePage.hasStoryInTable("favourite_stories", "Title1"), is(true));
		databaseHelper.removeFavourite("rod",1);
	}

	@Test 
	public void shouldNotDisplayEditAndDeleteButtonsWhenNonAuthorIsViewingAStory() throws Exception {
		viewStoryPage = NavigateTo.viewStoryPage(1, selenium);
		login("dianne","dianne");
		
		assertFalse(viewStoryPage.hasDeleteButton());
		assertFalse(viewStoryPage.hasEditButton());
	}
	
	@Test
	public void shouldFlagAStoryAsInappropriateIfNotYetFlagged() throws Exception {
		viewStoryPage = NavigateTo.viewStoryPage(1,selenium);
		login("rod","rod");
		viewStoryPage.flagStory();
		assertThat(viewStoryPage.getFlagCount(), is(1));
		viewStoryPage.unflagStory(); // unflag since lacking transactions
	}
	
	@Test
	public void shouldUnFlagAStoryFlaggedAsInappropriate() {
		viewStoryPage = NavigateTo.viewStoryPage(1,selenium);
		login("rod", "rod");
		viewStoryPage.flagStory();
		viewStoryPage.unflagStory();
		assertThat(viewStoryPage.getFlagCount(), is(0));
	}
	
	@Test
	public void shouldAddAComment() throws Exception {
		viewStoryPage=NavigateTo.viewStoryPage(1, selenium);
		login("rod", "rod");
		String comment = "comment";
		viewStoryPage.commentStory(comment);
		assertThat(viewStoryPage.hasText(comment), is(true));
	}
}

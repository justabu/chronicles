package chronicles.functionaltests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import chronicles.pages.BrowseStoriesPage;
import chronicles.pages.EditStoryPage;
import chronicles.pages.NavigateTo;
import chronicles.pages.NewStoryPage;
import chronicles.pages.ProfilePage;
import chronicles.pages.ViewStoryPage;

public class EditStoryPageTests extends ChroniclesTestsBase{
	
	private NewStoryPage newStoryPage;
	private String title;

	@Before
	public void setup(){
		newStoryPage = NavigateTo.NewStoryPage(selenium);
		login("rod", "rod");
		Date time = new Date();
		title = time.toString();
		
	}
	@Test
	public void shouldEditStory(){
		ViewStoryPage viewStoryPage = newStoryPage.addStory(title);
		EditStoryPage editStoryPage = viewStoryPage.goToEditStory();
		viewStoryPage = editStoryPage.editTheStoryTitle("New Title");
		assertThat(viewStoryPage.hasText("New Title"), is(true));
	}
	
	@Test
	public void shouldDeleteStory(){
		ViewStoryPage viewStoryPage = newStoryPage.addStory(title);
		ProfilePage profilePage = viewStoryPage.deleteStory();
		assertThat(profilePage.hasText("Your story has been deleted."), is(true));
		BrowseStoriesPage browseStoriesPage = NavigateTo.BrowseStoriesPage(selenium);
		assertThat(browseStoriesPage.hasStoryWith(title), is(false));
	}
	
}


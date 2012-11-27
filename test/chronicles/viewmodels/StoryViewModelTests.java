package chronicles.viewmodels;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import chronicles.models.Story;
import chronicles.models.User;

public class StoryViewModelTests {
	
	private StoryViewModel storyViewModel;

	@Before
	public void setup(){
		storyViewModel = new StoryViewModel();		
	}
	
	@Test
	public void shouldPrintDateForBrowseStoriesInCorrectFormat(){
		Date date = new Date(222222222);
	    assertThat(storyViewModel.getFormattedDate(date), is("03 Jan 1970"));
	}
	
	@Test
	public void shouldReturnFormattedDateOccured() throws Exception {
		User user = mock(User.class);
		user.setId(1);
		Story story = new Story("title", "2010", "content", user, "July", "city");
		story.setMonth("July");
		story.setYear("2010");
		assertThat(storyViewModel.getDateOccurred(story), is("Jul 2010"));
	}
	
	@Test
	public void shouldGetEvenTableRowClassForBrowseStorie() throws Exception {
		assertThat(storyViewModel.getTableRowClass(2),is("class=\"even\""));		
	}
	@Test
	public void shouldGetEmptyStringTableRowClassForBrowseStorie() throws Exception {
		assertThat(storyViewModel.getTableRowClass(9),is(""));		
	}
}

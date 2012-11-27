
package chronicles.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import chronicles.models.Story;
import chronicles.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class StoryRepositoryTests {

	private ChroniclesDAOSupport<Story> storyDAO;
	private StoryRepository storyRepository;
	private Story story;
	private User user;
	

	@Before
	public void setup() {
		storyDAO = mock(ChroniclesDAOSupport.class);
		storyRepository = new StoryRepository(storyDAO);
		user = new User("rsingham", "Roy", "Singham");
		user.setId(1);

		story = new Story("title", "2010", "blah blah blah", user, "January", "London");
	}

	@Test
	public void shouldSaveStory() {
		when(storyDAO.save(story)).thenReturn(true);
		
		assertThat(storyRepository.save(story), is(true));
		
		verify(storyDAO).save(story);
	}

	@Test
	public void shouldDeleteStory() {
		when(storyDAO.update(story)).thenReturn(true);

		assertThat(storyRepository.delete(story), is(true));
		assertThat(story.getDeleted(), is(true));
			
		verify(storyDAO).update(story);
	}

	@Test
	public void shouldReturnAllStoriesOrderByDateOfAddition() throws Exception {
		List<Story> allStories = new ArrayList<Story>();
		Story newStory = new Story();
		newStory.setDateAdded(new Date(12));
		Story oldStory = new Story();
		oldStory.setDateAdded(new Date(6));
		Story thirdStory = new Story();
		thirdStory.setDateAdded(new Date(9));
		
		allStories.add(thirdStory);
		allStories.add(oldStory);
		allStories.add(newStory);
		
		when(storyDAO.findAll("Story")).thenReturn(allStories);
		
		List<Story> foundStories = storyRepository.findAllOrderedByDateAdded();
		
		
		assertThat(foundStories.get(0), is(newStory	));
		assertThat(foundStories.get(1), is(thirdStory));
		assertThat(foundStories.get(2), is(oldStory));
	}
	
	@Test
	public void shouldSearchByKeyword() throws Exception {
		String searchPhrase = "title";
		
		ArrayList<Story> stories = new ArrayList<Story>();
		Story story = new Story();
		story.setTitle(searchPhrase);
		stories.add(story);
			
		when(storyDAO.findAllBy("Story", new SearchCriteriaEquals("deleted", "0"),
				   new SearchCriteriaLike("title",searchPhrase),
				   new SearchCriteriaLike("content", searchPhrase),
				   new SearchCriteriaLike("city", searchPhrase),
				   new SearchCriteriaLike("author.username", searchPhrase),
				   new SearchCriteriaLike("author.firstName", searchPhrase),
				   new SearchCriteriaLike("author.lastName", searchPhrase))).thenReturn(stories);

		List<Story> foundStories = storyRepository.searchByKeyword(searchPhrase);
		assertThat(foundStories.get(0), is(story));
	}
	
	@Test
	public void shouldReturnMostRecentFiveStories() throws Exception {
		User user= new User("roy","roy","singham");
		Story mostRecentStory = new Story("title", "2010", "Best story ever", user, "February", "Hamburg");
		mostRecentStory.setDateAdded(new Date(5));
		
		List<Story> recentStories = new ArrayList<Story>();
		populateRecentStories(mostRecentStory, recentStories);
		
		when(storyDAO.findAll("Story")).thenReturn(recentStories);
		
		List<Story> topFiveStories = storyRepository.getTopFiveStories();
		assertThat(topFiveStories, hasItem(mostRecentStory));
		assertThat(topFiveStories.size(),is(5));
	}   
	
	@Test
	public void shouldNotReturnStoryIfNotInFiveMostRecentStories() {
		User user= new User("roy","roy","singham");
		Story oldestStory = new Story("title", "2010", "Best story ever", user, "February", "Hamburg");
		oldestStory.setId(100);
		oldestStory.setDateAdded(new Date(0));
		
		List<Story> recentStories = new ArrayList<Story>();
		populateRecentStories(oldestStory, recentStories);
		
		when(storyDAO.findAll("Story")).thenReturn(recentStories);
		
		assertFalse(storyRepository.getTopFiveStories().contains(oldestStory));
	}

	private void populateRecentStories(Story mostRecentStory,
			List<Story> recentStories) {
		recentStories.add(mostRecentStory);				
		for(int i=0; i<5; i++){
			Story oldStory = new Story();
			oldStory.setDateAdded(new Date(1));
			recentStories.add(oldStory);
		}
	}
}
package chronicles.controllers;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;

public class HomeControllerTests {

	private StoryRepository storyRepository;
	private HomeController homeController;
	private User user= new User("roy","roy","singham");
	private Story recentStory = new Story("title", "2010", "Best story ever", user, "February", "Hamburg");

	@Before
	public void setUp() {
		storyRepository = mock(StoryRepository.class);
		homeController = new HomeController(storyRepository);
	}

	@Test
    public void shouldShowView()
    {
        ModelAndView modelAndView = homeController.home();
        assertThat(modelAndView.getViewName(),is("home"));
    }
	
	@Test
	public void shouldShowTop5Stories() throws Exception {
		ModelAndView modelAndView = homeController.home();
		assertThat(modelAndView.getModel().containsKey("stories"), is(true));
	}
	
	@Test
	public void shouldDisplayStoryWithinTopFiveStories() throws Exception {
		List<Story> recentStories = new ArrayList<Story>();
		recentStories.add(recentStory);
		for(int i=0; i<5; i++){
			recentStories.add(new Story());
		}
		
		when(storyRepository.findAllOrderedByDateAdded()).thenReturn(recentStories);
		
		ModelAndView modelAndView = homeController.home();
		List<Story> actualRecentStories = (List<Story>)modelAndView.getModel().get("stories");
		assertThat(recentStories, hasItem(recentStory));
	}
}

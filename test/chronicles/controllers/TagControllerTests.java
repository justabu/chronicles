package chronicles.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;

public class TagControllerTests {

	private StoryRepository storyRepository;
	private TagController tagController;
	private Story story;
	private User author;
	private HttpServletRequest request;

	@Before
	public void setup() {
		storyRepository = mock(StoryRepository.class);
		tagController = new TagController(storyRepository);
		author = new User("rsingham", "Roy", "Singham");
		author.setId(1);
		story = new Story("Title", "2010", "Content", author, "January", "Toronto");
        story.setId(1);
        request = mock(HttpServletRequest.class);
	    
	}
	
	@Test 
	public void shouldCreateAListOfTags() throws Exception {
		String testString = "testTag1, testtag2, testTag3";
		when(storyRepository.findById(1)).thenReturn(story);
		when(request.getParameter("commaSeparatedTags")).thenReturn(testString);
		when(storyRepository.update(story)).thenReturn(true);
		
		ModelAndView modelAndView = tagController.create(1, request);
		assertThat(modelAndView.getViewName(), is("redirect:/story/"+story.getId()));
	}
	@Test
	public void shouldReturnToStoryPageUponSuccessfulTagAdd(){
		String testString = "testTag1, testtag2, testTag3";
		when(storyRepository.findById(1)).thenReturn(story);
		when(request.getParameter("commaSeparatedTags")).thenReturn(testString);
		when(storyRepository.update(story)).thenReturn(true);
		ModelAndView modelAndView = tagController.create(story.getId(), request);
		assertThat(modelAndView.getViewName(), is(String.format("redirect:/story/%d",story.getId())));
	}
	@Test
	public void shouldReturnToErrorPageUponSuccessfulTagAdd(){
		String testString = "testTag1, testtag2, testTag3";
		when(storyRepository.findById(1)).thenReturn(story);
		when(request.getParameter("commaSeparatedTags")).thenReturn(testString);
		when(storyRepository.update(story)).thenReturn(false);
		ModelAndView modelAndView = tagController.create(story.getId(), request);
		assertThat(modelAndView.getViewName(), is(String.format("redirect:/error.jsp")));
	}
	
	@Test
	public void shouldRedirectWithInvalidTagMessageForOneInvalidTag() throws Exception {
		
		String invalidtags = "012345678900123456789001234567890012345678900123456789012";
		when(storyRepository.findById(1)).thenReturn(story);
		when(request.getParameter("commaSeparatedTags")).thenReturn(invalidtags);
		when(storyRepository.update(story)).thenReturn(true);
		ModelAndView modelAndView = tagController.create(story.getId(), request);
		
		ModelMap model = modelAndView.getModelMap();
		String invalidMessage=(String)model.get("tagMessage");
		assertThat(invalidMessage, is("'"+invalidtags+"' tag exceeds 50 characters. Please shorten and try again."));
		
	}
	
	
	
}

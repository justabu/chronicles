package chronicles.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;

public class InappropriateFlagControllerTests {
	private SecurityContextFacade securityContextFacade;
	private StoryRepository storyRepository;
	private InappropriateFlagController controller;
	private User currentUser;
	private Story story;

	@Before
	public void setup() {
		securityContextFacade = mock(SecurityContextFacade.class);
		storyRepository = mock(StoryRepository.class);
		
		controller = new InappropriateFlagController(storyRepository, securityContextFacade); 
		
		currentUser = new User("rsingham", "Roy", "Singham");
		story = new Story("title", "2010", "blah blah blah", currentUser, "January", "London");
		story.setId(25);

		when(securityContextFacade.getUser()).thenReturn(currentUser);
		when(storyRepository.findById(story.getId())).thenReturn(story);
	}
	
	@Test
	public void shouldAddAnInappropriateFlagToTheStoryForTheCurrentUser() throws Exception {
		story = mock(Story.class);		
		when(story.getId()).thenReturn(1);
		when(storyRepository.findById(story.getId())).thenReturn(story);
		controller.create(story.getId());
		verify(story).addInappropriateFlag(currentUser);
	}
	
	@Test
	public void shouldUpdateStoryOnCreate() throws Exception {
		controller.create(story.getId());
		verify(storyRepository).update(story);
	}
	
	@Test
	public void shouldRedirectToStoryPageOnSuccessfulCreation() throws Exception {
		when(storyRepository.update(story)).thenReturn(true);
		ModelAndView modelAndView = controller.create(story.getId());
		String viewName = String.format("redirect:/story/%d", story.getId());
		assertThat(modelAndView.getViewName(), is(viewName));
	}
	
	@Test
	public void shouldRedirectToErrorPageIfCreationFails() throws Exception {
		when(storyRepository.update(story)).thenReturn(false);
		ModelAndView modelAndView = controller.create(story.getId());
		assertThat(modelAndView.getViewName(), is("redirect:/error.jsp"));
	}
	
	@Test
	public void shouldRemoveAnInappropriateFlagFromTheStoryForTheCurrentUser() throws Exception {
		story = mock(Story.class);		
		when(story.getId()).thenReturn(1);
		when(storyRepository.findById(story.getId())).thenReturn(story);
		controller.delete(story.getId());
		verify(story).removeInappropriateFlag(currentUser);
	}
	
	@Test
	public void shouldUpdateStoryOnDelete() throws Exception {
		controller.delete(story.getId());
		verify(storyRepository).update(story);
	}
	
	@Test
	public void shouldRedirectToStoryPageOnSuccessfulDeletion() throws Exception {
		when(storyRepository.update(story)).thenReturn(true);
		ModelAndView modelAndView = controller.delete(story.getId());
		String viewName = String.format("redirect:/story/%d", story.getId());
		assertThat(modelAndView.getViewName(), is(viewName));
	}
	
	@Test
	public void shouldRedirectToErrorPageIfDeletionFails() throws Exception {
		when(storyRepository.update(story)).thenReturn(false);
		ModelAndView modelAndView = controller.delete(story.getId());
		assertThat(modelAndView.getViewName(), is("redirect:/error.jsp"));
	}
}

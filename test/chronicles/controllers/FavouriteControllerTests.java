package chronicles.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

public class FavouriteControllerTests {
	
	@Test
	public void shouldCreateFavoriteWithValidUserAndStory() throws Exception {
		User user = new User("Stew", "Stew", "Hall");
		user.setId(1337);
		Story story = new Story("Any", "1980", "any", user, "January", "Bengaluru");
		story.setId(1);
		SecurityContextFacade securityFacade = mock(SecurityContextFacade.class);
		UserRepository userRepository = mock(UserRepository.class);
		StoryRepository storyRepository = mock(StoryRepository.class);
		
		when(securityFacade.getUser()).thenReturn(user);
		
		when(storyRepository.findById(story.getId())).thenReturn(story);
		FavouriteController favController = new FavouriteController(securityFacade, userRepository, storyRepository);
		
		ModelAndView modelAndView = favController.create(story.getId()); 
		String view = "redirect:/story/1";
		
		assertEquals(view, modelAndView.getViewName());
	}
}

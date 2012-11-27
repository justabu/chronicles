package chronicles.controllers;

import static junit.framework.Assert.assertEquals;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

public class UserControllerTests {

	private UserRepository userRepository;
	private UserController userController;
	private SecurityContextFacade securityContextFacade;
	private HttpServletRequest request;
	private StoryRepository storyRepository;
	private Story story;
	private User user;
	
	
	@Before
	public void setUp() {
		request = mock(HttpServletRequest.class);
		userRepository = mock(UserRepository.class);
		storyRepository = mock(StoryRepository.class);
		securityContextFacade = mock(SecurityContextFacade.class);
		userController = new UserController(userRepository,storyRepository,securityContextFacade);
		user = new User("aa","sdf","Sdf");
		when(userRepository.findByName(anyString())).thenReturn(user);
		story = new Story("title", "year", "content", user, "month", "city");
		when(storyRepository.findById(anyInt())).thenReturn(story);
	}

	@Test
	public void shouldShowUserView() {
		ModelAndView modelAndView = userController.show(request);
		assertEquals("view_profile", modelAndView.getViewName());
		assertTrue(modelAndView.getModel().containsKey("user"));
		assertTrue(modelAndView.getModel().containsKey("favouriteStories"));
	}

	@Test
	public void shouldShowEditView() {
		ModelAndView modelAndView = userController.edit();
		assertEquals("edit_profile", modelAndView.getViewName());
		assertTrue(modelAndView.getModel().containsKey("user"));
		assertThat((Boolean)modelAndView.getModel().get("shouldDisplayEmptyFieldMessage"), is(false));
		verify(userRepository).findByName(anyString());
	}
	
	@Test
	public void shouldShowStrangerMessageForUserWithoutMinimumDetails() {
		user.setFirstName("");
		user.setLastName("");
		ModelAndView modelAndView = userController.edit();
		assertThat((Boolean)modelAndView.getModel().get("shouldDisplayEmptyFieldMessage"), is(true));
	}

	@Test @Ignore
	public void shouldSaveUserProfileData() {
		ModelAndView modelAndView = null;
		modelAndView = userController.update(user, null);
		assertEquals("save_profile", modelAndView.getViewName());
	}
	
	@Test
	public void shouldReturnOtherUserProfile() throws Exception {
		when(userRepository.findByName(anyString())).thenReturn(user);
		ModelAndView modelAndView = userController.show(user.getUsername());
		
		assertEquals("view_profile", modelAndView.getViewName());
		assertTrue(modelAndView.getModel().containsValue(user));
	}
	
	private void updateProfileSetup() {
		User currentUser = new User();
		when(securityContextFacade.getUserName()).thenReturn(currentUser.getUsername());
		when(userRepository.findByName(currentUser.getUsername())).thenReturn(currentUser);
	}
	
	@Test
	public void shouldNotUpdateProfileIfUserHasErrors() {
		updateProfileSetup();
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		
		User updatedUser = new User("updatedName", "first", "last");
		ModelAndView modelAndView = userController.update(updatedUser, bindingResult);
		assertThat(modelAndView.getViewName(), is("redirect:/error.jsp"));	
	}
	
	@Test
	public void shouldIncludeASuccessMessageInStoryList(){
		when(request.getParameter("ref")).thenReturn("1");
		ModelAndView modelAndView = userController.show(request);
		Map<String, Object> messageMap = modelAndView.getModel();
		String message = (String)messageMap.get("message");
		assertEquals(message, "Your story has been deleted.");
	}
	
	@Test
	public void shouldIncludeAFailureMessageInStoryList(){
		when(request.getQueryString()).thenReturn("ref=2");
		when(request.getParameter("ref")).thenReturn("2");
		ModelAndView modelAndView = userController.show(request);
		Map<String, Object> messageMap = modelAndView.getModel();
		String message = (String)messageMap.get("message");
		assertEquals(message, "Error deleting.");
	}
}
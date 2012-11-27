package chronicles.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import chronicles.exceptions.ResourceNotFoundException;
import chronicles.models.InappropriateFlag;
import chronicles.models.Story;
import chronicles.models.Tag;
import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

public class StoryControllerTests {

	private StoryRepository storyRepository;
	private UserRepository userRepository;
	private SecurityContextFacade securityContextFacade;
	private StoryController controller;
	private Story story;
	private User author;
	private User notAuthor;
    private Tag tag;
	private List<Tag> tags;
	private HttpServletRequest request;

	@Autowired
	private BindingResult bindingResult;

	@Before
	public void setup() {
		request = mock(HttpServletRequest.class);
		storyRepository = mock(StoryRepository.class);
		userRepository = mock(UserRepository.class);
		
		securityContextFacade = mock(SecurityContextFacade.class);
		
		author = new User("rsingham", "Roy", "Singham");
		author.setId(1);
		story = new Story("Title", "2010", "Content", author, "January", "Toronto");
        story.setId(1);
        notAuthor = new User("notAuthor", "Not", "Author");
        notAuthor.setId(10);
        
        tag = new Tag(1,"some tag");
        tags = new ArrayList<Tag>();
        tag.setId(1);
        tags.add(tag);

		controller = new StoryController(storyRepository, userRepository, securityContextFacade);
		
		when(storyRepository.findById(story.getId())).thenReturn(story);
		when(userRepository.findByName(author.getUsername())).thenReturn(author);
        when(userRepository.findByName(notAuthor.getUsername())).thenReturn(notAuthor);
        when(request.getParameter("commaSeparatedTags")).thenReturn("a,b");
	}

	@Test
	public void shouldShowNewStoryForm() {
		ModelAndView modelAndView = controller.newStory();
		assertEquals("new_story", modelAndView.getViewName());
	}

	@Test 
	public void ShouldNotSaveAnIllegalStory() {
		Story story = new Story("","1111","",author,"","");
		ModelAndView modelAndView = controller.create(story);

		assertEquals("redirect:/error.jsp", modelAndView.getViewName());
	}
	
	@Test
	public void shouldDeleteAStoryThatIsInChronicles(){
		when(storyRepository.delete(story)).thenReturn(true);
		when(securityContextFacade.getUserName()).thenReturn(author.getUsername());
		assertThat(controller.delete(1).getViewName(), is("redirect:../profile?ref=1"));
	}
	
	@Test
	public void shoulShowErrorIfDeletingUserDoesNotOwnStory() throws Exception {
		when(storyRepository.delete(story)).thenReturn(true);
		when(securityContextFacade.getUserName()).thenReturn(notAuthor.getUsername());
		
		assertThat(controller.delete(1).getViewName(), is("redirect:../profile?ref=3"));
		verify(storyRepository, never()).delete(isA(Story.class));
	}
		
	@Test
	public void shouldRedirectWithSuccessRefIfDeleteSucceeds(){
		when(storyRepository.delete(story)).thenReturn(true);
		when(securityContextFacade.getUserName()).thenReturn(author.getUsername());
		ModelAndView modelAndView = controller.delete(story.getId());
		assertThat(modelAndView.getViewName(), is("redirect:../profile?ref=1"));
	}
	
	@Test
	public void shouldRedirectWithErrorRefIfDeleteFails(){
		when(storyRepository.delete(story)).thenReturn(false);
		when(securityContextFacade.getUserName()).thenReturn(author.getUsername());
		ModelAndView modelAndView = controller.delete(story.getId());
		assertThat(modelAndView.getViewName(), is("redirect:../profile?ref=2"));
	}
	
	@Test
	public void shouldCallEditStoryViewOnEditForAllowedUser() {
        when(securityContextFacade.getUserName()).thenReturn(author.getUsername());
        ModelAndView modelAndView = controller.edit(story.getId());
		assertEquals("edit_story", modelAndView.getViewName());
	}

	@Test
	public void shouldRedirectToErrorViewOnEditIfNotAuthor() {
		when(securityContextFacade.getUserName()).thenReturn(notAuthor.getUsername());
		ModelAndView modelAndView = controller.edit(story.getId());
		assertThat(modelAndView.getViewName(), is("redirect:/error.jsp"));
	}
		
	@Test
	public void shouldRedirectToErrorIfNoSuchStoryExists(){
		when(storyRepository.findById(anyInt())).thenReturn(null);
		ModelAndView modelAndView = controller.edit(story.getId());
		assertEquals("redirect:/error.jsp", modelAndView.getViewName());
	}
	
	@Test
	public void shouldSendCorrectStoryToViewOnEdit(){
        when(securityContextFacade.getUserName()).thenReturn(author.getUsername());
        ModelAndView modelAndView = controller.edit(story.getId());
		assertEquals(story, (Story)modelAndView.getModel().get("story"));
	}
	
	@Test
	public void shouldCallUpdateOnRepositoryWithSavedStoryOnUpdate() {
		User user = new User("rsingham", "Roy", "Singham");
		user.setId(0);

		Story submittedStory = new Story("My story", "2010", "kljsdf", user, "January", "Bangalore");
		submittedStory.setId(1);
		
		Story savedStory = new Story("My OLD story", "2010", "kljsdf", author, "January", "Bangalore");
		savedStory.setId(1);
		
		when(securityContextFacade.getUserName()).thenReturn("rsingham");
		when(storyRepository.findById(submittedStory.getId())).thenReturn(savedStory);
		
		controller.update(submittedStory);
		
		assertThat(submittedStory.getAuthor().getId(), is(author.getId()));
		verify(storyRepository).update(savedStory);
	}
	
	@Test
	public void shouldCallViewStoryViewUponSuccessfulUpdate(){
        when(securityContextFacade.getUserName()).thenReturn(author.getUsername());
        when(storyRepository.update(story)).thenReturn(true);
		ModelAndView modelAndView = controller.update(story);
		assertEquals(String.format("redirect:/story/%d?ref=3", story.getId()), modelAndView.getViewName());
	}
	@Test
	public void shouldRedirectToErrorViewWhenUpdateIsNotAllowed(){
        when(securityContextFacade.getUserName()).thenReturn(notAuthor.getUsername());
        ModelAndView modelAndView = controller.update(story);
		assertEquals("redirect:/error.jsp", modelAndView.getViewName());
	}
	@Test
	public void shouldRedirectToErrorViewIfUpdateFails(){
        when(securityContextFacade.getUserName()).thenReturn(author.getUsername());
		when(storyRepository.update(story)).thenReturn(false);
		ModelAndView modelAndView = controller.update(story);
		assertEquals("redirect:/error.jsp", modelAndView.getViewName());
	}
	
	@Test
	public void shouldDisplayAStory() throws Exception {
		when(securityContextFacade.getUserName()).thenReturn(author.getUsername());
		Story retrievedStory = (Story)controller.view(1, request).getModel().get("story");
		assertThat(story.getTitle(), is(retrievedStory.getTitle()));	
	}
	
	@Test(expected=ResourceNotFoundException.class)
	public void shouldNotDisplayDeletedStory() throws Exception {
		Story story = new Story("myGreatTitle", "1999", "hello world", author, "January", "Sydney");
		story.setDeleted(true);
		when(storyRepository.findById(anyInt())).thenReturn(story);
		controller.view(1, request).getModel().get("story");
	}
	
	@Test
	public void shouldReturnBrowseStoriesModelAndView() {
		ModelAndView modelAndView = controller.allStories();
		assertEquals("browse_stories", modelAndView.getViewName());
	}


	@Test
	public void shouldReturnOnlyNonDeletedStories() throws Exception {
		story.setDeleted(true);
		Story notDeletedStory = new Story("title", "2009", "test content", author, "jan", "chennai");
		story.setId(2);
		List<Story> storiesToRepo = new ArrayList<Story>();
		storiesToRepo.add(story);
		storiesToRepo.add(notDeletedStory);
		when(storyRepository.findAllOrderedByDateAdded()).thenReturn(storiesToRepo);
		
		ModelAndView modelAndView = controller.allStories();
		List<Story> stories = (List<Story>) modelAndView.getModel().get("stories");
		
		
		Boolean deleted = false;
		for(Story story : stories){
			if(story.getDeleted()){
				deleted = true;
			}
		}
		assertFalse(deleted);
	}
	
	@Test
	public void shouldMergeSubmittedStoryDataIntoSavedStoryWhenEditing() throws Exception {
		Story savedStory = storyRepository.findById(1);
		Story submittedStory = new Story("test","2010","hello",new User("mj","m","j"), "July", "sydney");
		savedStory.merge(submittedStory);
		assertThat(savedStory.getAuthor(), is(submittedStory.getAuthor()));
		assertThat(savedStory.getCity(), is(submittedStory.getCity()));
		assertThat(savedStory.getContent(), is(submittedStory.getContent()));
		assertThat(savedStory.getMonth(), is(submittedStory.getMonth()));
		assertThat(savedStory.getTitle(), is(submittedStory.getTitle()));
		assertThat(savedStory.getYear(), is(submittedStory.getYear()));
	}
	
	@Test
	public void shouldRedirectToViewStoryAfterAdding() throws Exception {
		when (storyRepository.save(any(Story.class))).thenReturn(true);
		ModelAndView modelAndView = controller.create(story);
		String view = String.format("redirect:/story/%s",story.getId());
		assertEquals(view, modelAndView.getViewName());	
	}
}
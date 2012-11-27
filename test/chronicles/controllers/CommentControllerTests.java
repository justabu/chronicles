package chronicles.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Comment;
import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

public class CommentControllerTests {
	private StoryRepository storyRepository = mock(StoryRepository.class); 
	private SecurityContextFacade securityContextFacade = mock(SecurityContextFacade.class);
	private CommentController controller = new CommentController(storyRepository, securityContextFacade);
	
	@Test
	public void shouldAddCommentToStory() {
		Story story = new Story();
		story.setId(1);
		User user = new User();
		user.setId(1);
		when(securityContextFacade.getUser()).thenReturn(user);
		Comment comment = new Comment(story.getId(), user.getId(), "Molly starred in star trek", new Date());
		int initialNumberOfComments = story.getComments().size();
		when(storyRepository.findById(1)).thenReturn(story);
		
		controller.create(story.getId(), comment );
		
		assertThat(story.getComments().size(), is(initialNumberOfComments + 1));
		verify(storyRepository).update(story);
	}

	@Test
	public void shouldAddReplyToComment() {
		Story story = new Story();
		story.setId(1);
		User user = new User();
		user.setId(1);
		when(securityContextFacade.getUser()).thenReturn(user);

		Comment original = new Comment();
		original.setId(1);
		Set<Comment> comments = new HashSet<Comment>();
		comments.add(original);
		story.setComments(comments);

		when(storyRepository.findById(1)).thenReturn(story);
		when(storyRepository.update(story)).thenReturn(true);
		
		Comment reply = new Comment(1, 9, "Reply", new Date());
		controller.createReply(story.getId(), original.getId(), reply);

		List<Comment> actualComments = new ArrayList<Comment>(story.getComments());
		assertThat(actualComments.get(0).getReplies(), hasItem(reply));
	}
}

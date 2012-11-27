package chronicles.associations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import chronicles.models.Comment;
import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class CommentAssociationTests {

	@Autowired private UserRepository userRepository;
	@Autowired private StoryRepository storyRepository;
	private Story story;
	private User user;
	private Comment original;
		
	@Before
	public void setup() {
		user = new User("Ragavan", "first", "last");
		userRepository.save(user);
		
		story = new Story("Dogs of India", "2005", "Story Content", user, "March", "Denver");
		storyRepository.save(story);

		original = new Comment(story.getId(), user.getId(), "Original story", new Date());
		story.addComment(original);
		storyRepository.update(story);
	}
	
	@Test
	public void shouldAddReplyToComment() {	
		original = getOriginalComment();
		Comment reply = new Comment(story.getId(), user.getId(), "Reply to original story", new Date());
		original.addReply(reply);
		storyRepository.update(story);
		
		List<Comment> repliesToOriginalComment = getOriginalComment().getReplies();
		reply.setId(repliesToOriginalComment.get(0).getId());
		assertThat(repliesToOriginalComment, hasItem(reply));
	}
	
	@Test 
	public void shouldNotAddCommentAsReplyToFirstComment(){
		original = getOriginalComment();
		Comment secondComment = new Comment(story.getId(), user.getId(), "Reply to original story", new Date());
		story.addComment(secondComment);
		storyRepository.update(story);
		
		assertThat(story.getComments().size(), is(2));
		assertThat(getOriginalComment().getReplies().size(), is(0));
	}

	private Comment getOriginalComment() {
		List<Comment> comments = returnComments();
		return comments.get(0);
	}

	private List<Comment> returnComments() {
		Story storyFromRepository = storyRepository.findById(story.getId());
		Set<Comment> commentsFromStory = storyFromRepository.getComments();
		List<Comment> comments = new ArrayList<Comment>(commentsFromStory);
		return comments;
	}
}	

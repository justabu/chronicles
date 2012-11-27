package chronicles.associations;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Date;
import java.util.Set;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import chronicles.models.Comment;
import chronicles.models.InappropriateFlag;
import chronicles.models.Story;
import chronicles.models.Tag;
import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class StoryAssociationTests {
	@Autowired private StoryRepository storyRepository;
	@Autowired private UserRepository userRepository;
	
	@Test
	public void shouldDeleteTagFromStoryWhenStoryModelUpdated() throws Exception {
		Story story = storyRepository.findById(1);
		
		story.getTags().add(new Tag(story.getId(), "win"));
		storyRepository.update(story);
		
		story = storyRepository.findById(story.getId());
		story.getTags().clear();
		storyRepository.update(story);
		
		story = storyRepository.findById(story.getId());
		assertThat(story.getTags().size(), is(0));
	}
	
	@Test
	public void shouldAddTagsWhenEditingAStory() throws Exception {
		Story submittedStory = new Story("Title blargh","2010","hello Story 1",new User("mj","m","j"), "July", "sydney");
		submittedStory.setId(4);
		Story savedStory = storyRepository.findById(submittedStory.getId());
		savedStory.merge(submittedStory);
		assertThat(storyRepository.update(savedStory), is(true));
	}
	
	@Test
	public void shouldAddInappropriateFlagsToAnExistingStory() throws Exception {
		User author = new User("username", "first", "last");
		userRepository.save(author);
		Story story = new Story("MyStory", "2010", "blah blah", author, "July", "London");
		storyRepository.save(story);
		
		story.addInappropriateFlag(author);
		storyRepository.update(story);
		
		Story updatedStory = storyRepository.findById(story.getId());
		
		assertThat(updatedStory.isFlaggedBy(author), is(true));
		
		User otherUser = new User("blah", "first", "last");
		userRepository.save(otherUser);
		
		updatedStory.addInappropriateFlag(otherUser);
		storyRepository.update(updatedStory);
		
		Story retrievedStory = storyRepository.findById(updatedStory.getId());
		Set<InappropriateFlag> flags = retrievedStory.getInappropriateFlags();
		System.out.println("flags size is  sdfasdf " + flags.size());
		assertThat(retrievedStory.isFlaggedBy(author), is(true));
		assertThat(retrievedStory.isFlaggedBy(otherUser), is(true));
	}
		
	@Test
	public void shouldDeleteFlagWhenStoryIsUpdatedWithRemovedFlag(){
		User toby = new User("tclemson", "Toby", "Clemson");
		User ankit = new User("ankittsd", "Ankit", "Dhingra");
		userRepository.save(toby);
		userRepository.save(ankit);
		Story story = new Story("himym", "2010", "blah blah blah", ankit , "January", "London");
		storyRepository.save(story);
		
		story.addInappropriateFlag(toby);
		story.addInappropriateFlag(ankit);
		storyRepository.update(story);
		
		Story updatedStory = storyRepository.findById(story.getId());
		updatedStory.removeInappropriateFlag(toby);
		storyRepository.update(updatedStory);
		
		Story retrievedStory = storyRepository.findById(story.getId());
		
		assertThat(retrievedStory.isFlaggedBy(toby), is(false));
		assertThat(retrievedStory.isFlaggedBy(ankit), is(true));
	}
	
	@Test
	public void shouldRetrieveCommentsAssociatedWithStory(){
		User author = new User("test", "test", "test");
		userRepository.save(author);
		Story story = new Story("test", "2009", "test", author , "Jan", "Iowa");
		storyRepository.save(story);
		Comment comment = new Comment(story.getId(), author.getId(), "comment", new Date());
		story.addComment(comment);
		storyRepository.save(story);
		
		Story storyFromRepository = storyRepository.findById(story.getId());
		Set<Comment> comments = storyFromRepository.getComments();

		assertThat(comments, hasItem(comment));
	}
	
	@Test
	public void shouldAddReplyToStory() {	
		User user = new User("Ragavan", "first", "last");
		userRepository.save(user);
		
		Story story = new Story("Dogs of India", "2005", "Story Content", user, "March", "Denver");
		storyRepository.save(story);
		Comment original = new Comment(story.getId(), user.getId(), "Original story", new Date());
		Comment reply = new Comment(story.getId(), user.getId(), "Reply to original story", new Date());
		story.addComment(original);
		story.addComment(reply);
		storyRepository.save(story);
		
		Story storyFromRepository = storyRepository.findById(story.getId());
		Set<Comment> commentsFromStory = storyFromRepository.getComments();
		
		assertThat(commentsFromStory, hasItem(reply));
	}
	
	@Test @Ignore
	public void shouldReturnFalseOnUpdateWhenInvalid() {
		User author = new User("name", "firstName", "lastName");
		userRepository.save(author);
		
		Story savedStory = new Story("SomeTitle@sdf", "", "", author, "January", "SomeCity");
		storyRepository.save(savedStory);
		
		Story foundStory = storyRepository.findById(savedStory.getId());
		
		foundStory.setCity(null);

		assertThat(storyRepository.update(foundStory), is(false));
	}
}

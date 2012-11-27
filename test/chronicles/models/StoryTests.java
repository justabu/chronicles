package chronicles.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class StoryTests {
	
	private User author;
	private Story story;

	@Before
	public void setUp() {
		author = buildUser(24);
		story = buildStory(author);
	}

    @Test
    public void shouldAllowModificationFromTheAuthor() {
        assertThat(story.isOwnedBy(author), is(true));
    }

    @Test
    public void shouldNotAllowModificationFromAUserOtherThanTheAuthor() {
        User nonAuthor = buildUser(21);
        assertThat(story.isOwnedBy(nonAuthor), is(false));
    }
    
    @Test
	public void shouldNotValidateWithoutTitle() throws Exception {
		story.setTitle("");
		assertFalse(story.valid());
	}
    
    @Test
	public void shouldNotValidateWithBlankContent() throws Exception {
		story.setContent("");
		assertFalse(story.valid());
	}
    
    @Test
	public void shouldNotValidateForFutureYear() throws Exception {
		story.setYear("2050");
		assertFalse(story.valid());
    }
    
    @Test
	public void shouldNotValidateIfYearBefore1963() throws Exception {
    	story.setYear("1945");
		assertFalse(story.valid());
	}
    
    @Test
	public void shouldNotValidateForBlankCity() throws Exception {
		story.setCity("");
		assertFalse(story.valid());
	}
    @Test
	public void shouldNotValidateSameYearFutureMonth() throws Exception {
		story.setMonth("August");
		assertFalse(story.valid());
	}
    @Test
    public void shouldValidateSameYearPastMonth(){
    	story.setMonth("June");
    	assertTrue(story.valid());
    }
    
    @Test
    public void shouldValidateSameYearCurrentMonth(){
    	story.setMonth("July");
    	assertTrue(story.valid());
    }
    
    @Test
    public void shouldAddInappropriateFlag(){
    	story.addInappropriateFlag(author);
    	assertThat(story.isFlaggedBy(author), is(true));
    }
    
    @Test
    public void shouldRemoveInappropriateFlag(){
    	story.removeInappropriateFlag(author);
    	assertThat(story.isFlaggedBy(author), is(false));
    }
    
    
    @Test
    public void shouldReturnFalseWhenNoFlagsArePresent() {
    	assertThat(story.hasBeenFlagged(), is(false));
    }
    
    @Test
    public void shouldReturnTrueWhenFlagsArePresent(){
    	story.addInappropriateFlag(author);
    	assertThat(story.hasBeenFlagged(), is(true));
    }
    
    @Test
	public void shouldNotAddDuplicatedTags() throws Exception {
    	story.addTag("abc");
    	story.addTag("abc");
    	assertThat(story.getTags().size(), is(1));
	}
 
    @Test
	public void shouldReturnZeroForInappropriateFlagCount() throws Exception {
		assertThat(story.inappropriateFlagCount(), is(0));
	}
    
    @Test
	public void shouldReturnOneForInappropriateFlagCount() throws Exception {
		story.addInappropriateFlag(author);
		assertThat(story.inappropriateFlagCount(), is(1));
	}
    
    @Test
    public void shouldReturnTwoForInappropriateFlagCounWhenTwoUserFlagTheStory(){
    	User nonAuthor  = buildUser(26);
    	story.addInappropriateFlag(nonAuthor);
    	story.addInappropriateFlag(author);
    	assertThat(story.inappropriateFlagCount(), is(2));
    }
    @Test
    public void shouldDecreaseFlagCount(){
    	User nonAuthor  = buildUser(26);
    	story.addInappropriateFlag(nonAuthor);
    	story.addInappropriateFlag(author);
    	story.removeInappropriateFlag(nonAuthor);
    	assertThat(story.inappropriateFlagCount(), is(1));
    }
    
    @Test
    public void shouldCheckThatUserHasFlaggedAStory(){
    	story.addInappropriateFlag(author);
    	assertThat(story.isFlaggedBy(author), is(true));
    }
    
    @Test
    public void shouldCheckThatUserHasNotFlaggedAStory(){
    	assertThat(story.isFlaggedBy(author), is(false));
    }
    @Test
    public void shouldReturnAInappropriateFlagForAUser(){
    	author.setId(100);
    	story.addInappropriateFlag(author);
    	assertThat(story.getInappropriateFlag(author).getUserId(), is(author.getId()));
    }

    @Test
    public void shouldNotAddSimilarTagsWithDifferentIds(){
    	Set<Tag> tagList = new HashSet<Tag>();
    	Tag tag1 = new Tag(story.getId(), "wrote all this");
    	tag1.setId(10);
    	tagList.add(tag1);
    	story.setTags(tagList);
    	story.addTag("wrote all this");
    	assertThat(story.getTags().size(), is (1));
    	
    }
    
    @Test
    public void shouldGetOnlyDirectComments() throws Exception {
		Comment directOne = new Comment(story.getId(), author.getId(), "direct", new Date());
		directOne.setId(1);
		Comment directTwo = new Comment(story.getId(), author.getId(), "direct2", new Date());
		directTwo.setId(2);
		story.addComment(directOne);
		story.addComment(directTwo);
		Comment replyToDirectOne = new Comment(story.getId(), author.getId(), "reply to One", new Date());
		replyToDirectOne.setId(3);
		directOne.addReply(replyToDirectOne);
		
		List<Comment> commentsFromStory = story.fetchDirectComments();
		assertThat(commentsFromStory.contains(replyToDirectOne), is(false));
		
	} 

    private User buildUser(int id) {
    	User user = new User("tclemson", "Toby", "Clemson");
    	user.setId(id);
    	return user;
    }
    
    private Story buildStory(User author) {
    	return new Story("MyStory", "2010", "blah blah", author, "July", "London");
    }
    
}

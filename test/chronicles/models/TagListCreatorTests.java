package chronicles.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TagListCreatorTests {

	private User author;
	private Story story;
	private TagListCreator tagListCreator;

	@Before
	public void setUp() {
		author = buildUser(24);
		story = buildStory(author);
		tagListCreator = new TagListCreator();
	}

	private User buildUser(int id) {
		User user = new User("tclemson", "Toby", "Clemson");
		user.setId(id);
		return user;

	}

	private Story buildStory(User author) {
		return new Story("MyStory", "2010", "blah blah", author, "July",
				"London");
	}
	
	@Test
	public void shouldReturnAlphabetizedAndCommaSeperatedTagString() throws Exception {
		String stringA = "alpha";
		String stringZ = "zebra";
		String stringB = "beta";
		
		story.addTag(stringA);
		story.addTag(stringZ);
		story.addTag(stringB);
		
		String alphaString = tagListCreator.createAlphabeticalCommaSeparatedTagsString(new ArrayList<Tag>( story.getTags()));
		
		assertThat(alphaString, is("alpha, beta, zebra"));
	}
}

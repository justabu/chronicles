package chronicles.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import chronicles.models.InappropriateFlag;
import chronicles.models.Story;
import chronicles.models.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class ChroniclesHibernateDAOSupportTests {

	@Autowired private ChroniclesDAOSupport<User> userDAOSupport;
	@Autowired private ChroniclesDAOSupport<Story> storyDAOSupport;
	@Autowired private ChroniclesDAOSupport<InappropriateFlag> inappropriateFlagDAOSupport;

	@Test
	public void shouldFindByColumnName() {
		String userName = "bobm";
		User user = new User(userName, "Bob", "Marley");

		userDAOSupport.save(user);

		User retreivedUser = userDAOSupport
				.findBy("User", new SearchCriteriaEquals("username", userName));
		assertThat(retreivedUser.getUsername(), is(user.getUsername()));
		assertThat(retreivedUser.getFirstName(), is(user.getFirstName()));
	}

	@Test
	public void shouldReturnNullWhenModelNotFound() {
		String title = "unknown title";

		Story story = storyDAOSupport.findBy("Story", new SearchCriteriaEquals("title", title));
		assertNull(story);
	}

	@Test
	public void shouldSaveModel() throws Exception {
		User user = new User("roys", "Roy", "Singham");

		assertThat(userDAOSupport.save(user), is(true));

		User retrievedUser = userDAOSupport.findBy("User",new SearchCriteriaEquals("username", user
				.getUsername()));
		assertThat(retrievedUser.getUsername(), is(user.getUsername()));
	}

	@Test
	public void shouldReturnFalseWhenYearAndContentIsNull() {
		Story story = new Story("AnotherStory", null, null, buildUser(),
				"July", "Bangalore");
		assertThat(storyDAOSupport.save(story), is(false));
	}

	@Test
	public void shouldUpdateModel() {
		User user = new User("rsingham", "Roy", "Singham");
		userDAOSupport.save(user);

		user.setFirstName("abcd");
		user.setLastName("efgh");
		assertThat(userDAOSupport.update(user), is(true));

		User retreivedUser = userDAOSupport.findBy("User", new SearchCriteriaEquals( "username",
				"rsingham"));
		assertThat(retreivedUser.getFirstName(), is("abcd"));
		assertThat(retreivedUser.getLastName(), is("efgh"));
	}

	@Test
	public void shouldReturnAListOfAllModels() {
		InappropriateFlag firstInappropriateFlag = new InappropriateFlag(1, 1);
		inappropriateFlagDAOSupport.save(firstInappropriateFlag);

		InappropriateFlag secondInappropriateFlag = new InappropriateFlag(1, 2);
		inappropriateFlagDAOSupport.save(secondInappropriateFlag);

		List<InappropriateFlag> inappropriateFlags = inappropriateFlagDAOSupport
				.findAll("InappropriateFlag");
		assertThat(inappropriateFlags.size(), is(2));
	}

	@Test
	public void shouldCountNumberOfRowsInModelTable() throws Exception {
		InappropriateFlag firstInappropriateFlag = new InappropriateFlag(1, 1);
		inappropriateFlagDAOSupport.save(firstInappropriateFlag);

		InappropriateFlag secondInappropriateFlag = new InappropriateFlag(1, 2);
		inappropriateFlagDAOSupport.save(secondInappropriateFlag);
		
		assertThat(inappropriateFlagDAOSupport.countAll("InappropriateFlag"), is(2));
	}

	@Test
	public void shoulDeleteGivenRowFromModels() throws Exception {
		InappropriateFlag inappropriateFlag = new InappropriateFlag(1, 1);
		inappropriateFlagDAOSupport.save(inappropriateFlag);

		assertThat(inappropriateFlagDAOSupport.delete(inappropriateFlag),
				is(true));
	}

	@Test
	public void shouldFindRowsByGivenParametersInModels() throws Exception {
		InappropriateFlag inappropriateFlag = new InappropriateFlag(1, 1);
		inappropriateFlagDAOSupport.save(inappropriateFlag);

		Map<String, String> queryParameters = new HashMap<String, String>();
		queryParameters.put("userId", "1");
		queryParameters.put("storyId", "1");

		assertThat(inappropriateFlagDAOSupport.findBy("InappropriateFlag", 
				new SearchCriteriaEquals("userId", "1")),
				is(inappropriateFlag));
	}

	@Test
	public void shouldFindAStoryUsingASearchCriteria() throws Exception {

		Story story = storyDAOSupport.findBy("Story", new SearchCriteriaEquals(
				"title", "Title1"));
		Story story2 = storyDAOSupport.findBy("Story", new SearchCriteriaLike(
				"title", "Title2"));
		assertThat(story.getTitle(), is("Title1"));
		assertThat(story2.getTitle(), is("Title2"));
	}

	@Test
	public void shouldSearchAllStoriesWithWordTitleInTitleOrInContent()
			throws Exception {

		Story roysStory = new Story("lala", "1999", "Title lalala Title Roy",
				buildUser(), "January", "Bangalore");
		Story bharathsStory = new Story("lala", "1999", "Title lalala Title Bharath",
				buildUser(), "January", "Bangalore");
		Story abusStory = new Story("lala", "1999", "Title lalala Abu",
				buildUser(), "January", "Bangalore");
		storyDAOSupport.save(roysStory);
		storyDAOSupport.save(bharathsStory);
		storyDAOSupport.save(abusStory);
		
		String searchPhrase = "Title lalala";
		
		List<Story> listOfStories = storyDAOSupport.findAllBy("Story",
				new SearchCriteriaEquals("deleted", "0"),
				new SearchCriteriaLike("title", searchPhrase),
				new SearchCriteriaLike("content", searchPhrase),
				new SearchCriteriaLike("city", searchPhrase),
				new SearchCriteriaLike("author.username", searchPhrase),
				new SearchCriteriaLike("author.firstName", searchPhrase),
				new SearchCriteriaLike("author.lastName", searchPhrase));
		
		assertTrue(listOfStories.contains(roysStory));
		assertTrue(listOfStories.contains(bharathsStory));
		assertTrue(listOfStories.contains(abusStory));
	}

	private User buildUser() {
		return new User("name", "firstName", "lastName");
	}

	@Test
	public void shouldSaveUserThenStory() {
		User author = new User("name", "firstName", "lastName");
		assertTrue(userDAOSupport.save(author));
		Story story = new Story("title", "", "", author, "January", "SomeCity");
		assertTrue(storyDAOSupport.save(story));
	}
	
	
}
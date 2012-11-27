package chronicles.models;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UserTests {
	
	@Test
	public void shouldNotAllowUsernameLongerThan30Characters() {
		User user = new User("username", "first", "last");
		String username = buildStringOfLength(50);
		user.setUsername(username);
		assertThat(isValid(user), is(false));
	}
	
	@Test
	public void shouldNotAllowEmptyUsername(){
		User user = new User("username", "first", "last");
		user.setUsername("");
		assertThat(isValid(user), is(false));
	}
	
	@Test
	public void shouldNotAllowFirstNameLongerThan255Characters() {
		User user = new User("username", "first", "last");
		String firstName = buildStringOfLength(256);
		user.setFirstName(firstName);
		assertThat(isValid(user), is(false));
	}
	
	@Test
	public void shouldNotAllowEmptyFirstName(){
		User user = new User("username", "first", "last");
		user.setFirstName("");
		assertThat(isValid(user), is(false));
	}

	@Test
	public void shouldNotAllowLastNameLongerThan255Characters() throws Exception {
		User user = new User("username", "first", "last");
		String lastName = buildStringOfLength(256);
		user.setLastName(lastName);
		assertThat(isValid(user), is(false));	
	}
	
	@Test
	public void shouldNotAllowEmptyLastName(){
		User user = new User("username", "first", "last");
		user.setLastName("");
		assertThat(isValid(user), is(false));
	}
	
	@Test
	public void shouldAllowShortDescriptionToBeEmpty() throws Exception {
		User user = new User("username", "first", "last");
		user.setShortDescription("");
		assertThat(isValid(user), is(true));
	}
	
	@Test
	public void shouldNotAllowShortDescriptionLongerThan2000Characters() throws Exception {
		User user = new User("username", "first", "last");
		String shortDescription = buildStringOfLength(3000);
		user.setShortDescription(shortDescription);
		assertThat(isValid(user), is(false));	
	}
	
	@Test
	public void shouldNotAllowHomeOfficeLongerThan255Characters() throws Exception {
		User user = new User("username", "first", "last");
		String homeOffice = buildStringOfLength(256);
		user.setHomeOffice(homeOffice);
		assertThat(isValid(user), is(false));	
	}
	
	@Test
	public void shouldAllowEmptyHomeOffice(){
		User user = new User("username", "first", "last");
		user.setHomeOffice("");
		assertThat(isValid(user), is(true));
	}
	
	@Test
	public void shouldNotAllowCurrentOfficeLongerThan255Characters() throws Exception {
		User user = new User("username", "first", "last");
		String currentOffice = buildStringOfLength(256);
		user.setCurrentOffice(currentOffice);
		assertThat(isValid(user), is(false));	
	}
	
	@Test
	public void shouldAllowEmptyCurrentOffice(){
		User user = new User("username", "first", "last");
		user.setCurrentOffice("");
		assertThat(isValid(user), is(true));
	}
	
	@Test
	public void shouldAllowEmptyDateOfJoining() {
		User user = new User("username", "first", "last");
		user.setDateOfJoining(null);
		assertThat(isValid(user), is(true));
	}
	
	@Test
	public void shouldAllowTodayAsDateOfJoining() throws Exception {
		int fiveSecondsInMilliseconds = 5000;
		Date today = new Date(new Date().getTime() - fiveSecondsInMilliseconds);
		User user = new User("username", "first", "last");
		user.setDateOfJoining(today);
		assertThat(isValid(user), is(true));
	}
	
	@Test
	public void shouldAllowADateInThePastAsDateOfJoining() throws Exception {
		Date today = new GregorianCalendar(2000, 5, 10).getTime();
		User user = new User("username", "first", "last");
		user.setDateOfJoining(today);
		assertThat(isValid(user), is(true));
	}
	
	@Test
	public void shouldNotAllowDateOfJoiningInTheFuture() throws Exception {
		int oneDayInMilliseconds = 86400000;
		Date tomorrow = new Date(new Date().getTime() + oneDayInMilliseconds);
		User user = new User("username", "first", "last");
		user.setDateOfJoining(tomorrow);
		assertThat(isValid(user), is(false));
	}
	
	@Test
	public void shouldReturnTrueForVaildNames() {
		User userWithValidNames = new User("user", "firstName", "lastName");		
        assertThat(userWithValidNames.hasMinimumDetails(), is(true));
	}
	
	@Test
	public void shouldReturnFalseForSpaces() {		
		User userWithInvalidNames = new User("user", " ", "   ");
        assertThat(userWithInvalidNames.hasMinimumDetails(), is(false));
	}

	@Test
	public void shouldReturnFalseForSpacesInLastName() {		
		User userWithInvalidNames = new User("user", "first", "   ");
        assertThat(userWithInvalidNames.hasMinimumDetails(), is(false));
	}
	
	@Test
	public void shouldReturnFalseForNull() {
		User userWithNullNames = new User("user", null, null);
		assertThat(userWithNullNames.hasMinimumDetails(), is(false));
	}
	
	private boolean isValid(User user) {
		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
		return constraintViolations.isEmpty();
	}
	
	private String buildStringOfLength(int length) {
		StringBuilder string = new StringBuilder();
		for(int i = 0; i < length; i++) {
			string.append("a");
		}
		return string.toString();
	}
	
	@Test
	public void shouldReturnListOfStoriesSortedByDateAdded() throws Exception {
		User user = new User("bob", "bob", "bob");
		Story firstStory = new Story("first","2000","hello",user,"January","sydney");
		firstStory.setId(100);
		Story secondStory = new Story("second","2000","hello",user,"January","sydney");
		secondStory.setId(101);
		Story thirdStory = new Story("third","2000","hello",user,"January","sydney");
		thirdStory.setId(102);
		
		long baseTime = System.currentTimeMillis();
		firstStory.setDateAdded(new Date(baseTime));
		secondStory.setDateAdded(new Date(baseTime+1000));
		thirdStory.setDateAdded(new Date(baseTime+2000));
		
		user.getStories().add(thirdStory);
		user.getStories().add(firstStory);
		user.getStories().add(secondStory);
		
		List<Story> sortedStories = user.storiesByDescendingDateAdded();
		assertThat(sortedStories.get(0), is(thirdStory));
		assertThat(sortedStories.get(1), is(secondStory));
		assertThat(sortedStories.get(2), is(firstStory));
	}	
}


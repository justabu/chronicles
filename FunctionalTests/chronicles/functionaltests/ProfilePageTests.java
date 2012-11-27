package chronicles.functionaltests;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


import org.junit.Test;
import chronicles.models.Story;
import chronicles.models.User;
import chronicles.pages.EditProfilePage;
import chronicles.pages.HomePage;
import chronicles.pages.NavigateTo;
import chronicles.pages.ProfilePage;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/functionalTestsContext.xml")
public class ProfilePageTests  extends ChroniclesTestsBase {

	@Autowired
	StoryRepository storyRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void shouldReflectChangesToProfile(){
		ProfilePage profilePage = NavigateTo.ProfilePage(selenium);
		login("rod", "rod");
		EditProfilePage editProfilePage = profilePage.clickAndEditProfile();
		profilePage = editProfilePage.editUserProfile("hello world", "Bangalore");
		assertThat(profilePage.hasText("hello world"), is(true));
		assertThat(profilePage.hasText("Bangalore"), is(true));
	}

	@Test
	public void shouldRedirectUserWithoutAllDetailsToEditProfilePage() throws Exception {
		ProfilePage profilePage = NavigateTo.ProfilePage(selenium);
		login("scott", "scott");
		HomePage homePage = profilePage.clickHomeLink();
		assertThat(homePage.hasText("Profile for scott"), is(true));
	}
	
	@Test
	public void shouldShowWelcomeMessageForUserWithoutMinimumDetails() throws Exception {
		ProfilePage profilePage = NavigateTo.ProfilePage(selenium);
		login("scott", "scott");
		assertThat(profilePage.hasText("As you use Chronicles"), is(true));
	}
	
	@Test
	public void shouldNotShowWelcomeMessageForUserWithAllDetails() throws Exception {
		ProfilePage profilePage = NavigateTo.ProfilePage(selenium);
		login("rod", "rod");
		EditProfilePage editProfilePage = profilePage.clickAndEditProfile();
		assertThat(editProfilePage.hasText("As you use Chronicles"), is(false));
	}
	
	@Test
	public void shouldDisplayUsersStoriesOnProfilePage() throws Exception {
		User rod = userRepository.findByName("rod");
		String storyTitle = "RodsGreatStory";
		storyRepository.save(new Story(storyTitle, "2008", "Hello world", rod, "January", "Sydney"));
		ProfilePage profilePage = NavigateTo.ProfilePage(selenium);
		login("rod","rod");
		assertThat(profilePage.hasStoryInTable("authored_stories", storyTitle), is(true));
	}
}
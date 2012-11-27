package chronicles.functionaltests;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/functionalTestsContext.xml")
public class DatabaseHelper {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private StoryRepository storyRepository;


	@Autowired
	public DatabaseHelper(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void removeFavourite(String username, int storyId) {
		User user = userRepository.findByName(username);
		
		user.getFavouriteStories().remove(storyRepository.findById(storyId));
		userRepository.update(user);
	}

}
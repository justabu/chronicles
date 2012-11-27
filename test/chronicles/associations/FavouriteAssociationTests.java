package chronicles.associations;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.ChroniclesHibernateDAOSupport;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class FavouriteAssociationTests {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private ChroniclesHibernateDAOSupport<User> chroniclesHibernateDAOSupportUser;
	
	@Test
	public void shouldAddAStoryToFavourites() throws Exception {
		User user = new User("Duda", "Andrew", "lastName");
		Story story = new Story("title","2009","content", user, "July", "Memphis");		
		user.addFavouriteStories(story);
		assertThat(user.getFavouriteStories().contains(story), is(true));
	}
	
	@Test
	public void shouldRetrieveFavouriteStories() throws Exception {
		User user = new User("Duda", "Andrew", "lastName");
		userRepository.save(user);

		Story story1 = storyRepository.findById(1);
		Story story2 = storyRepository.findById(2);
		user.addFavouriteStories(story1);
		user.addFavouriteStories(story2);

		userRepository.update(user);
		
		User retrievedUser = userRepository.findById(user.getId());
		
		assertThat(retrievedUser.getFavouriteStories().contains(story1), is(true));
		assertThat(retrievedUser.getFavouriteStories().contains(story2), is(true));
		
		chroniclesHibernateDAOSupportUser.delete(retrievedUser);
	}
	
	
}

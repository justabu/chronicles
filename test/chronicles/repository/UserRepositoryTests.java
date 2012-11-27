package chronicles.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import chronicles.models.User;

public class UserRepositoryTests {
    private ChroniclesDAOSupport<User> userDAO;
    private UserRepository userRepository;
    private User user;


    @Before
    public void setup() {
        userDAO = mock(ChroniclesDAOSupport.class);
        user = new User("rsingham", "Roy", "Singham");
        when(userDAO.findBy(eq("User"), any(SearchCriteria.class))).thenReturn(user);
        userRepository = new UserRepository(userDAO);
    }

    @Test
    public void shouldFindByEmail() {
        String email = "roy@thoughtworks.com";

        assertThat(userRepository.findByEmail(email), is(user));

        verify(userDAO).findBy(eq("User"), any(SearchCriteria.class));
    }

    @Test
    public void shouldFindByName() {
        assertThat(userRepository.findByName(user.getUsername()), is(user));

        verify(userDAO).findBy(eq("User"), any(SearchCriteria.class));
    }

    @Test
    public void shouldFindById() {
        assertThat(userRepository.findById(5), is(user));

        verify(userDAO).findBy(eq("User"), any(SearchCriteria.class));
    }

    @Test
    public void shouldFindIdByName() {
        assertThat(userRepository.findIdByUserName("blah"), is(user.getId()));

        verify(userDAO).findBy(eq("User"), any(SearchCriteria.class));
    }


    @Test
    public void shouldSaveUser() {
        when(userDAO.save(user)).thenReturn(true);

        assertThat(userRepository.save(user), is(true));

        verify(userDAO).save(user);
    }

    @Test
    public void shouldReturnFalseWhenUnableToSaveUser() {
        when(userDAO.save(user)).thenReturn(false);

        assertThat(userRepository.save(user), is(false));

        verify(userDAO).save(user);
    }

    @Test
    public void shouldUpdateUser() {
        when(userDAO.update(user)).thenReturn(true);

        assertThat(userRepository.update(user), is(true));

        verify(userDAO).update(user);
    }

    @Test
    public void shouldReturnFalseWhenUnableToUpdateUser() {
        when(userDAO.update(user)).thenReturn(false);

        assertThat(userRepository.update(user), is(false));

        verify(userDAO).update(user);
    }
}

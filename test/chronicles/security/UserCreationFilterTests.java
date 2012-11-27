package chronicles.security;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import chronicles.models.User;
import chronicles.repository.UserRepository;

public class UserCreationFilterTests {
    @Test
    public void shouldNotCreateNewUserWhenUserExists() {
        UserRepository userRepository = mock(UserRepository.class);
        User existingUser = new User("ExistingUser", "", "");
        when(userRepository.findByName("ExistingUser")).thenReturn(existingUser);

        UserCreationFilter filter = new UserCreationFilter(userRepository);
        filter.checkUser("ExistingUser");

        verify(userRepository).findByName("ExistingUser");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldCreateNewUserWhenUserDoesNotExist() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findByName("NewUser")).thenReturn(null);

        UserCreationFilter filter = new UserCreationFilter(userRepository);
        filter.checkUser("NewUser");

        verify(userRepository).findByName("NewUser");
        verify(userRepository).save(any(User.class));
    }

}

package chronicles.security;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

public class ChroniclesUserDetailsServiceTests {
    @Test
    public void shouldCheckUser() {
        UserCreationFilter filter = mock(UserCreationFilter.class);
        ChroniclesUserDetailsService userDetailsService = new ChroniclesUserDetailsService(filter);
        userDetailsService.loadUserByUsername("SomeUser");
        verify(filter).checkUser("SomeUser");
    }

    @Test
    public void shouldReturnUserDetails() {
        UserCreationFilter filter = mock(UserCreationFilter.class);
        ChroniclesUserDetailsService userDetailsService = new ChroniclesUserDetailsService(filter);
        UserDetails userDetails = userDetailsService.loadUserByUsername("SomeUser");
        assertThat(userDetails.getUsername(), is("SomeUser"));
    }

}

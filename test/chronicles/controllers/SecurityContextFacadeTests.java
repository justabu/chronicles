package chronicles.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import chronicles.models.User;
import chronicles.repository.UserRepository;


public class SecurityContextFacadeTests {

	private SecurityContext securityContext;
	private UserRepository userRepository;
	
	@Test @Ignore
	public void shouldReturnLoggedInUserObjectFromDatabase() throws Exception {
		userRepository = mock(UserRepository.class);
		securityContext = mock(SecurityContext.class);
		Authentication auth = mock(Authentication.class);
		
		User user = new User("Stew", "Stew", "hall");
		
		when(userRepository.findByName("Stew")).thenReturn(user);
		when(securityContext.getAuthentication()).thenReturn(auth);
		when(auth.getName()).thenReturn("Stew");
		//SecurityContextFacade security = new SpringSecurityContextFacade(userRepository, securityContext);
		
		//assertEquals(security.getUser(), user);
	}
}

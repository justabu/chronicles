package chronicles.util;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.*;

import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import chronicles.models.User;
import chronicles.repository.UserRepository;

public class CustomSessionFilterTests {
	
	private UserRepository userRepository;
	private CustomSessionFilter filter;
	private Authentication authentication;
	private SecurityContext context;
	private MockHttpServletRequest request;
	private FilterChain chain;
	private MockHttpServletResponse response;

	@Before
	public void setup() {
		userRepository = mock(UserRepository.class);
		filter = new CustomSessionFilter(userRepository);
		authentication = mock(Authentication.class);
		context = mock(SecurityContext.class);
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();		
		chain = mock(FilterChain.class);
		
		SecurityContextHolder.setContext(context);
		request.setSession(new MockHttpSession());
	}

	@Test 
	public void shouldAddUserObjectToSession() throws Exception {
		User user = new User("name", "firstName", "lastName");
		
		when(userRepository.findByName("name")).thenReturn(user);
		when(authentication.getName()).thenReturn(user.getUsername());
		when(context.getAuthentication()).thenReturn(authentication);
		
		filter.doFilter(request, response, chain);

		assertThat((User) request.getSession().getAttribute("user"), is(user));
	}
	
	@Test
	public void shouldRedirectWhenNoNameExists() throws Exception {
		User user = new User("name", "", "");
		
		when(userRepository.findByName("name")).thenReturn(user);
		when(authentication.getName()).thenReturn(user.getUsername());
		when(context.getAuthentication()).thenReturn(authentication);
		
		filter.doFilter(request, response, chain);
		
		String responseString = response.getRedirectedUrl();
		
		assertThat(responseString, is("/profile/edit"));
		
	}
	
	@Test
	public void shouldNotRedirectPutRequestToProfile() throws Exception {
		User user = new User("name", "bob", "bob");
		
		when(userRepository.findByName("name")).thenReturn(user);
		when(authentication.getName()).thenReturn(user.getUsername());
		when(context.getAuthentication()).thenReturn(authentication);
		
		request.setMethod("PUT");
		request.setRequestURI("/chronicles/profile");
		filter.doFilter(request, response, chain);
		String responseString = response.getRedirectedUrl();
		
		assertThat(responseString, is(nullValue()));
	}
	
	@Test
	public void shouldNotRedirectForStaticContent() throws Exception {
		User user = new User("name", "", "");
		
		when(userRepository.findByName("name")).thenReturn(user);
		when(authentication.getName()).thenReturn(user.getUsername());
		when(context.getAuthentication()).thenReturn(authentication);
		request.setRequestURI("/chronicles/css/chronicles.css");

		filter.doFilter(request, response, chain);
		
		String responseString = response.getRedirectedUrl();
		assertThat(responseString, is(nullValue()));
	}
}

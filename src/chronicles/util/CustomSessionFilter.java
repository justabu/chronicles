package chronicles.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import chronicles.models.User;
import chronicles.repository.UserRepository;


public class CustomSessionFilter implements Filter {
	
	protected void setUserRepository(UserRepository userRepository){
		this.userRepository = userRepository;
	}
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public CustomSessionFilter(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;

		if (shouldPerformFilter(httpServletRequest)) {
			String name = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userRepository.findByName(name);
			httpServletRequest.getSession().setAttribute("user", user);
			if (!user.hasMinimumDetails()){
				redirectToEditProfile(httpServletRequest, httpServletResponse);
			}
		}
		chain.doFilter(request, response);			
	}

	private Boolean shouldPerformFilter(HttpServletRequest httpServletRequest) {
		String contentURI = httpServletRequest.getRequestURI();
		if (isStaticContent(contentURI)) return false;
		if (contentURI.equals("/chronicles/profile/edit")) return false;
		if (contentURI.equals("/chronicles/profile") && httpServletRequest.getMethod().equals("PUT")) return false;
		return true;
	}

	private boolean isStaticContent(String contentURI) {
		return contentURI.contains("/css/") || contentURI.contains("/js/") || contentURI.contains("/images/");
	}

	protected void redirectToEditProfile(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws ServletException,
			IOException {
		httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/profile/edit");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
}


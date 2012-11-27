package chronicles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import chronicles.models.User;
import chronicles.repository.UserRepository;

public class SpringSecurityContextFacade implements SecurityContextFacade {

	@Autowired UserRepository userRepository;
	
	@Autowired
	public SpringSecurityContextFacade(UserRepository userRepository){
		this.userRepository = userRepository;
	}

	public String getUserName() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public User getUser() {
		return userRepository.findByName(getUserName());
	}

}

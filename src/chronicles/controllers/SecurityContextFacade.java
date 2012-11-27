package chronicles.controllers;

import chronicles.models.User;

public interface SecurityContextFacade {
	String getUserName();
	
	User getUser();
}

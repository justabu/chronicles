package chronicles.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;
import chronicles.util.SiteMessage;

@Controller
public class UserController {

	@Autowired private UserRepository userRepository;
	@Autowired private SecurityContextFacade securityContextFacade;
	@Autowired private StoryRepository storyRepository;
	
	@Autowired
	public UserController(UserRepository userRepository,StoryRepository storyRepository, SecurityContextFacade securityContextFacade) {
		this.userRepository = userRepository;
		this.storyRepository = storyRepository;
		this.securityContextFacade = securityContextFacade;
	}

	@RequestMapping("/profile")
	public ModelAndView show(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("view_profile", "user", currentUser());
		modelAndView.addObject("message", SiteMessage.messageFor(request));
		modelAndView.addObject("favouriteStories", currentUser().getFavouriteStories());
		modelAndView.addObject("editable", true);
		return modelAndView;
	}


	@RequestMapping("/profile/edit")
	public ModelAndView edit() {
		User loggedInUser = currentUser();
		ModelAndView modelAndView = new ModelAndView("edit_profile", "user", loggedInUser);
		modelAndView.addObject("shouldDisplayEmptyFieldMessage", !loggedInUser.hasMinimumDetails());
		return modelAndView;
	}

	private User currentUser() {
		String loggedInUserName = securityContextFacade.getUserName();
		return userRepository.findByName(loggedInUserName);
	}

	@RequestMapping(value="/profile", method=RequestMethod.PUT)
	public ModelAndView update(@Valid User updatedUser, BindingResult validationResult) {
		User currentUser = currentUser();
		currentUser.setCurrentOffice(updatedUser.getCurrentOffice());
		currentUser.setFirstName(updatedUser.getFirstName());
		currentUser.setLastName(updatedUser.getLastName());
		currentUser.setHomeOffice(updatedUser.getHomeOffice());
		currentUser.setShortDescription(updatedUser.getShortDescription());
		currentUser.setDateOfJoining(updatedUser.getDateOfJoining());
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(!validationResult.hasErrors() && userRepository.update(currentUser)) {
			modelAndView.setViewName("redirect:profile");
		} else {
			modelAndView.setViewName("redirect:/error.jsp");
		}
		
		return modelAndView;
	}
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		binder.registerCustomEditor(Date.class, "dateOfJoining", editor);
    }
	
	@RequestMapping("/profile/{userName}")
	public ModelAndView show(@PathVariable("userName") String userName) {
		User user = userRepository.findByName(userName);
		ModelAndView modelAndView = new ModelAndView("view_profile", "user", user);
		modelAndView.addObject("editable", user.equals(currentUser()));
		modelAndView.addObject("favouriteStories", new HashSet<Story>());
		return modelAndView;
	}

}


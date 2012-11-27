package chronicles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;

@Controller
@RequestMapping("/story/{storyId}/favourite")
public class FavouriteController {


	@Autowired private SecurityContextFacade securityFacade;
	@Autowired private UserRepository userRepository;
	@Autowired private StoryRepository storyRepository;
	
	@Autowired
	public FavouriteController(SecurityContextFacade securityFacade ,
							   UserRepository userRepository, StoryRepository storyRepository) {
		this.securityFacade = securityFacade;
		this.userRepository = userRepository;
		this.storyRepository = storyRepository;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView create(@PathVariable("storyId") int storyId) {
		User currentUser = securityFacade.getUser();
		currentUser.addFavouriteStories(storyRepository.findById(storyId));
		userRepository.update(currentUser);
		ModelAndView modelAndView = new ModelAndView("redirect:/story/" + storyId);
		return modelAndView;
	}
}

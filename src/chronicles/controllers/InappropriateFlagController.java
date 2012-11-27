package chronicles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;

@Controller
@RequestMapping(value="/story/{storyId}/inappropriate_flag")
public class InappropriateFlagController {

	@Autowired private StoryRepository storyRepository;
	@Autowired private SecurityContextFacade securityContextFacade;
	
	@Autowired
	public InappropriateFlagController(
		StoryRepository storyRepository,
		SecurityContextFacade securityContextFacade
	) {
		this.storyRepository = storyRepository;
		this.securityContextFacade = securityContextFacade;
	}

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView create(@PathVariable("storyId") int storyId) {
		Story story = storyRepository.findById(storyId);
		story.addInappropriateFlag(currentUser());
		
		ModelAndView modelAndView = new ModelAndView();
		if(storyRepository.update(story)) {
			modelAndView.setViewName(String.format("redirect:/story/%d", story.getId()));
		} else {
			modelAndView.setViewName("redirect:/error.jsp");
		}
		
		return modelAndView;
	}

	@RequestMapping(method=RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable("storyId") int storyId) {
		Story story = storyRepository.findById(storyId);
		story.removeInappropriateFlag(currentUser());
		
		ModelAndView modelAndView = new ModelAndView();
		if(storyRepository.update(story)) {
			modelAndView.setViewName(String.format("redirect:/story/%d", story.getId()));
		} else {
			modelAndView.setViewName("redirect:/error.jsp");
		}
		
		return modelAndView;
	}

	private User currentUser() {
		return securityContextFacade.getUser();
	}

}



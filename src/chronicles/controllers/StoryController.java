package chronicles.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chronicles.exceptions.ResourceNotFoundException;
import chronicles.models.Comment;
import chronicles.models.Story;
import chronicles.models.User;
import chronicles.repository.StoryRepository;
import chronicles.repository.UserRepository;
import chronicles.util.SiteMessage;

@Controller
public class StoryController {
	@Autowired private StoryRepository storyRepository;
	@Autowired private SecurityContextFacade securityContextFacade;
	@Autowired private UserRepository userRepository;

	@Autowired
	public StoryController(StoryRepository storyRepository,
			UserRepository userRepository,
			SecurityContextFacade securityContextFacade) {
		this.storyRepository = storyRepository;
		this.userRepository = userRepository;
		this.securityContextFacade = securityContextFacade;
	}

	@RequestMapping(value = "/story/new", method = RequestMethod.GET)
	public ModelAndView newStory() {
		ModelAndView view = new ModelAndView("new_story", "story", new Story());
		view.addObject("monthOptions", monthOptions());
		return view;
	}

	@RequestMapping(value = "/story", method = RequestMethod.POST)
	public ModelAndView create(Story story) {
		story.setAuthor(userRepository.findByName(securityContextFacade
				.getUserName()));
		if (story.valid()) {
			storyRepository.save(story);
			return new ModelAndView(String.format("redirect:/story/%s",story.getId()));
		}
		return new ModelAndView("redirect:/error.jsp");
	}

	@RequestMapping(value = "/story/{id}")
	public ModelAndView view(@PathVariable("id") int storyId,
			HttpServletRequest request) {
		Story story = storyRepository.findById(storyId);
		if (!story.getDeleted()) {
			ModelAndView modelAndView = createModelAndViewToViewStory(request, story);
			modelAndView.addObject("comment", new Comment());
			return modelAndView;
		}
		throw new ResourceNotFoundException();
	}


	@RequestMapping(value = "/story/{id}", method = RequestMethod.DELETE)
	public ModelAndView delete(@PathVariable("id") int id) {
		Story story = storyRepository.findById(id);
		String view = "redirect:../profile?ref=3";
		if (story.isOwnedBy(currentUser())) {
			view = storyRepository.delete(story) ? "redirect:../profile?ref=1"
					: "redirect:../profile?ref=2";
		}
		return new ModelAndView(view);
	}

	@RequestMapping(value = "story/{storyId}/edit", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable int storyId) {
    	Story story = storyRepository.findById(storyId);
    	
    	if(story == null) {
    		return new ModelAndView("redirect:/error.jsp");
    	}
    	
    	ModelAndView modelAndView = new ModelAndView();
    	if(story.isOwnedBy(currentUser())){
    		modelAndView.setViewName("edit_story");
    		modelAndView.addObject("story", story);
    		modelAndView.addObject("monthOptions", monthOptions());
    	} else {
    		modelAndView.setViewName("redirect:/error.jsp");
    	}
    	return modelAndView;
    }
    
    @RequestMapping(value = "story/{storyId}/edit", method = RequestMethod.PUT)
    public ModelAndView update(@ModelAttribute("story") Story submittedStory){
    	Story savedStory = storyRepository.findById(submittedStory.getId());
    	
    	if(!savedStory.isOwnedBy(currentUser())|| !submittedStory.valid()) {
    		return new ModelAndView("redirect:/error.jsp");
    	}
    	
    	submittedStory.setAuthor(currentUser());
    	savedStory.merge(submittedStory);
    	
    	ModelAndView modelAndView = new ModelAndView();
		if(storyRepository.update(savedStory)){
			modelAndView.setViewName(String.format("redirect:/story/%s?ref=3", submittedStory.getId()));
		} else {
			modelAndView.setViewName("redirect:/error.jsp");
		}
		return modelAndView;
	} 

	@RequestMapping(value = "/story", method = RequestMethod.GET)
	public ModelAndView allStories() {
		ModelAndView modelAndView = new ModelAndView("browse_stories");

		List<Story> stories = storyRepository.findAllNotDeleted();
		modelAndView.addObject("stories", stories);

		return modelAndView;
	}

	@RequestMapping(value = "/story/search", method = RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request) {
		String searchPhrase = request.getParameter("searchPhrase");
		List<Story> stories = storyRepository
				.searchByKeyword(searchPhrase);
		ModelAndView modelAndView = new ModelAndView("browse_stories",
				"stories", stories);
		return modelAndView;
	}
	

	private ModelAndView createModelAndViewToViewStory(HttpServletRequest request, Story story) {
		User loggedInUser = currentUser();
		ModelAndView modelAndView = new ModelAndView("view_story", "story",story);
		modelAndView.addObject("user", story.getAuthor());
		modelAndView.addObject("directComments", story.fetchDirectComments());
		modelAndView.addObject("tags", story.commaSeparatedTags());
		modelAndView.addObject("flagCount", story.inappropriateFlagCount());
		modelAndView.addObject("userHasFlaggedStory", story.isFlaggedBy(loggedInUser));
		modelAndView.addObject("message", SiteMessage.messageFor(request));
		modelAndView.addObject("userFullName", story.getAuthor().fullName());
		modelAndView.addObject("currentStoryAlreadyUserFavourite", loggedInUser.hasAddedToFavourite(story.getId()));
		return modelAndView.addObject("allowEdit",
				story.isOwnedBy(loggedInUser));
	}
	
	
	
	private User currentUser() {
		return userRepository.findByName(securityContextFacade.getUserName());
	}

	private List<String> monthOptions() {
		List<String> months = new ArrayList<String>();
		months.add("");
		months.add("January");
		months.add("February");
		months.add("March");
		months.add("April");
		months.add("May");
		months.add("June");
		months.add("July");
		months.add("August");
		months.add("September");
		months.add("October");
		months.add("November");
		months.add("December");
		return months;
	}
}
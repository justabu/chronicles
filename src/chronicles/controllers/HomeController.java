package chronicles.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import chronicles.repository.StoryRepository;

@Controller
public class HomeController {

	@Autowired private StoryRepository storyRepository;
	
	@Autowired
	public HomeController(StoryRepository storyRepository){
		this.storyRepository = storyRepository;
	}
	
    @RequestMapping("/home")
     public ModelAndView home() {
    	ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        modelAndView.addObject("stories", storyRepository.getTopFiveStories());
        return modelAndView;
    }
}

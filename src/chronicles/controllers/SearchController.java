package chronicles.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Story;
import chronicles.repository.StoryRepository;

@Controller
public class SearchController {

	@Autowired
	private final StoryRepository storyRepository;

	@Autowired
	public SearchController(StoryRepository storyRepository) {
		this.storyRepository = storyRepository;
	}

	@RequestMapping(value="/search/story", method=RequestMethod.GET)
	public ModelAndView search(HttpServletRequest request) {
		return searchStoryByKeyword(request.getParameter("query"));
	}
	
	public ModelAndView searchStoryByKeyword(String keyword) {
		List<Story> foundStories = storyRepository.searchByKeyword(keyword);
		ModelAndView searchResults = new ModelAndView();
		searchResults.setViewName("browse_stories");
		searchResults.addObject("size",foundStories.size());
		searchResults.addObject("stories", foundStories);
		searchResults.addObject("query", keyword);
		return searchResults;
	}

}

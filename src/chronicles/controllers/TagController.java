package chronicles.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chronicles.repository.StoryRepository;
import chronicles.services.TagService;
import chronicles.services.TagValidationResult;

@Controller
@RequestMapping(value="/story/{storyId}/tag")
public class TagController {
	private static final String oneInvalidTagText = "tag exceeds 50 characters. Please shorten and try again.";
	private static final String multipleInvalidTagText = " tags exceed 50 characters. Please shorten and try again.";
	@Autowired
	private StoryRepository storyRepository;

	@Autowired
	public TagController(StoryRepository storyRepository) {
		this.storyRepository = storyRepository;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView create(	@PathVariable("storyId") int storyId,HttpServletRequest request){
		 
		TagService tagService = new TagService(storyRepository);
		String tagsString = request.getParameter("commaSeparatedTags");
		TagValidationResult tagValidationResult = tagService.validateAndSaveValidTags(tagsString, storyId);

		if(!tagValidationResult.isUpdateSuccessful())return new ModelAndView("redirect:/error.jsp");
		ModelAndView modelAndView = new ModelAndView(String.format("redirect:/story/%d",storyId));
		String invalidTagMessageString="";
		
		List<String> invalidTags = tagValidationResult.getInvalidTags();
		if(invalidTags.size() == 1){
			invalidTagMessageString += "'"+invalidTags.get(0)+"' "+oneInvalidTagText;
		} else if(invalidTags.size() > 1) {
			for (String invalidTag : invalidTags) {
				invalidTagMessageString += ",'"+invalidTag+"'";
			}
			invalidTagMessageString = invalidTagMessageString.substring(1);
			invalidTagMessageString += multipleInvalidTagText;
		}
		modelAndView.addObject("tagMessage", invalidTagMessageString);
		return modelAndView;
	}
}

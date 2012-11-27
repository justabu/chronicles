package chronicles.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import chronicles.models.Comment;
import chronicles.models.Story;
import chronicles.repository.StoryRepository;

@Controller
@RequestMapping(value="/story/{storyId}/comment")
public class CommentController {

	@Autowired
	private final StoryRepository storyRepository;
	@Autowired
	private final SecurityContextFacade securityContextFacade ;

	@Autowired
	public CommentController(StoryRepository storyRepository, SecurityContextFacade securityContextFacade) {
		this.storyRepository = storyRepository;
		this.securityContextFacade = securityContextFacade;
	}

	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView create(@PathVariable("storyId") int storyId,@ModelAttribute("comment") Comment comment) {
		Story story = storyRepository.findById(storyId);
		comment.setAuthorId(securityContextFacade.getUser().getId());
		story.addComment(comment);
		
		if(!storyRepository.update(story)) {
			return new ModelAndView("redirect:/error.jsp");
		}
		
		return new ModelAndView(String.format("redirect:/story/%d",storyId));		
	}

	@RequestMapping(value="/{commentId}/reply", method=RequestMethod.POST)
	public ModelAndView createReply(@PathVariable("storyId")  int storyId,@PathVariable("commentId")  int commentId, Comment reply) {
		Story story = addReply(storyId, commentId, reply);
		
		if(!storyRepository.update(story)) {
			return new ModelAndView("redirect:/error.jsp");
		}

		return new ModelAndView(String.format("redirect:/story/%d",storyId));		

	}
	
	
	@RequestMapping(value="/{commentId}/reply", method=RequestMethod.GET)
	public ModelAndView createReply() {
		return new ModelAndView("/home");
	}

	private Story addReply(int storyId, int commentId, Comment reply) {
		Story story = storyRepository.findById(storyId);
		List<Comment> comments = new ArrayList<Comment>(story.getComments());
		for(Comment comment : comments){
			if(comment.getId() == commentId)
				comment.addReply(reply);
		}
		return story;
	}
}

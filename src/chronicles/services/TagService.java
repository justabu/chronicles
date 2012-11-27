package chronicles.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import chronicles.models.Story;
import chronicles.repository.StoryRepository;


public class TagService {
	@Autowired
	StoryRepository storyRepository;

	public TagService(StoryRepository storyRepository) {
       this.storyRepository= storyRepository;
	}
	
	public TagValidationResult validateAndSaveValidTags(String commaSeparatedTags, int storyId) {
		ArrayList<String> invalidTagsList = new ArrayList<String>();
		ArrayList<String> validTagsList = new ArrayList<String>();
		String[] tagList = commaSeparatedTags.split(",");
		separateValidTagsFromInvalidTags(invalidTagsList, tagList, validTagsList);
		 
		return new TagValidationResult(invalidTagsList,saveValidTags(storyId, validTagsList));
	}

	private boolean saveValidTags(int storyId, ArrayList<String> validTagsList) {
		Story story = storyRepository.findById(storyId);
        story.addTags(validTagsList);
		return storyRepository.update(story);
	}

	private void separateValidTagsFromInvalidTags(
		ArrayList<String> invalidTags, String[] tagList,
		ArrayList<String> validTagsList) {
		for (String tag : tagList) {
			 tag=sanitize(tag);
				if(isInvalid(tag))
					invalidTags.add(tag);
				else if(isNotEmpty(tag)){
					validTagsList.add(tag);
				}
			}
	}

	private boolean isNotEmpty(String tag) {
		return !tag.equals("");
	}

	private boolean isInvalid(String tag) {
		return tag.length()>50;
	}
	
	protected String sanitize(String tagName) {
		tagName =tagName.toLowerCase();
		return tagName.trim().replaceAll("\\s+", " ");
	}
	
	
}
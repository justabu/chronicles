package chronicles.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class TagListCreator {

	private static final String COMMA_SEPARATOR = ", ";



	public String createAlphabeticalCommaSeparatedTagsString(List<Tag> tags) {
		ArrayList<String> tagList = createAlphabetizedTagList(tags);
		String tagString = convertTagListToString(tagList);
		return removeLastComma(tagString);
	}

	private String convertTagListToString(ArrayList<String> tagHolder) {
		String tagString = "";
		for (String tagName : tagHolder) {
			tagString += tagName + COMMA_SEPARATOR;
		}
		return tagString;
	}

	private ArrayList<String> createAlphabetizedTagList(List<Tag> tags) {
		ArrayList<String> tagHolder = new ArrayList<String>();
		for (Tag tag : tags) {
			tagHolder.add(tag.getTagName());
		}
		Collections.sort(tagHolder);
		return tagHolder;
	}

	private String removeLastComma(String tagString) {
		if (tagString.length() > 0)
			return tagString.substring(0,
					tagString.length() - COMMA_SEPARATOR.length());
		return "";
	}

}
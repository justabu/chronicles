package chronicles.util;

import java.util.Comparator;

import chronicles.models.Story;

public class StoryComparatorByDateAdded implements Comparator<Story> {

	@Override
	public int compare(Story firstStory, Story secondStory) {
		if (firstStory.getDateAdded().before(secondStory.getDateAdded()))
			return 1;
		if (firstStory.getDateAdded().after(secondStory.getDateAdded()))
			return -1;
		return 0;
	}

}

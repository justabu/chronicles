package chronicles.viewmodels;

import java.text.SimpleDateFormat;
import java.util.Date;

import chronicles.models.Story;
import chronicles.models.User;

public class StoryViewModel {

	public String getFormattedDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
		return simpleDateFormat.format(date);
	}

	public String getDateOccurred(Story currentStory) {
		 String storyOccuredMonth;
		if(currentStory.getMonth() == null || currentStory.getMonth() == "")
		    	storyOccuredMonth = "";
		    else
		    	storyOccuredMonth = currentStory.getMonth().substring(0, 3) + " ";
		return storyOccuredMonth + currentStory.getYear();
	}

	public String getTableRowClass(int i) {
		if(i%2 ==0)
			return "class=\"even\"";
		else
			return "";
	}
}

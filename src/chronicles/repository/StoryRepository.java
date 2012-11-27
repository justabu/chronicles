package chronicles.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import chronicles.models.Story;
import chronicles.util.StoryComparatorByDateAdded;

public class StoryRepository {

    @Autowired
    private ChroniclesDAOSupport<Story> chroniclesDAOSupport;

    @Autowired
    public StoryRepository(ChroniclesDAOSupport<Story> chroniclesDAOSupport) {
        this.chroniclesDAOSupport = chroniclesDAOSupport;
    }

    public Story findById(int id) {
		return chroniclesDAOSupport.findBy("Story", new SearchCriteriaEquals("id", String.format("%d", id)));
	}

	public boolean save(Story story) {
		return chroniclesDAOSupport.save(story);
	}

	public List<Story> findAllOrderedByDateAdded() {
		List<Story> stories = chroniclesDAOSupport.findAll("Story");
		Collections.sort(stories, new StoryComparatorByDateAdded());
		return stories;
	}

	public boolean delete(Story story) {
		story.setDeleted(true);
		return update(story);
	}
	
	public boolean update(Story story){
		return chroniclesDAOSupport.update(story);
	}
	
	public List<Story> searchByKeyword(String searchPhrase) {
		return chroniclesDAOSupport.findAllBy("Story", new SearchCriteriaEquals("deleted", "0"),
				   new SearchCriteriaLike("title",searchPhrase),
				   new SearchCriteriaLike("content", searchPhrase),
				   new SearchCriteriaLike("city", searchPhrase),
				   new SearchCriteriaLike("author.username", searchPhrase),
				   new SearchCriteriaLike("author.firstName", searchPhrase),
				   new SearchCriteriaLike("author.lastName", searchPhrase)
				   );
	}

	public List<Story> findAllNotDeleted() {
		List<Story> allStories = findAllOrderedByDateAdded();
		List<Story> notDeletedStories = new ArrayList<Story>();
		
		for(Story story : allStories){
			if(!story.getDeleted())
				notDeletedStories.add(story);
		}
		return notDeletedStories;
	}

	public List<Story> getTopFiveStories() {
		List<Story> storiesByDate = findAllOrderedByDateAdded();
	
		if(lessThanFiveRecentStories(storiesByDate)) {
			return storiesByDate;
		} 
		return storiesByDate.subList(0, 5);
	}
	
	public boolean lessThanFiveRecentStories(List<Story> storiesByDate) {
		return storiesByDate.size() < 5;
	}
}

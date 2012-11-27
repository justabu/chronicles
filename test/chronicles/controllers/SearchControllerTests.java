package chronicles.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

import chronicles.repository.StoryRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SearchControllerTests {

	private SearchController searchController;
	
	@Autowired
	private StoryRepository storyRepository;

	@Test
	public void shouldReturnAEmptyViewIfKeywordNotFound(){
		searchController = new SearchController(storyRepository);
		
		String inexistentKeyword = "Inexistent Keyword";
		
		ModelAndView searchView = searchController.searchStoryByKeyword(inexistentKeyword);
		assertThat(searchView.getViewName(), is("browse_stories"));
	}
	
	@Test
	public void shouldReturnSearchResults() throws Exception {
		searchController = new SearchController(storyRepository);
		
		String existingKeyword = "Title";
		
		ModelAndView searchView = searchController.searchStoryByKeyword(existingKeyword);
		assertThat (searchView.getViewName(),is("browse_stories"));
		
	}
	
}
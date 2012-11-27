package chronicles.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;


public class SearchCriteriaTests {

	@Test
	public void shouldReturnHqlQueryStringForASearchPair() throws Exception {
		SearchCriteria searchCriteria = new SearchCriteriaEquals("storyId", "1");
	
		assertThat(searchCriteria.toHQL(), is("model.storyId=\'1\'"));
	}
	
	@Test
	public void shouldCreateLikeCriteria() throws Exception {
		SearchCriteria searchCriteria = new SearchCriteriaLike("storyId", "1");
		
		assertThat(searchCriteria.toHQL(), is("model.storyId like \'%1%\'"));
	}
	
	
}

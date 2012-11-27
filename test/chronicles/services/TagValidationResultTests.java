package chronicles.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TagValidationResultTests {
	@Test
	public void hasInvalidTagsShouldReturnTrueifInvalidListHasOneOrMoreTags() throws Exception {
		List<String> invalidTags= new ArrayList<String>();
		String anInvalidTag = "1234567890";
		invalidTags.add(anInvalidTag);
		
		assertThat(new TagValidationResult(invalidTags, true).hasAnInvalidTag(),is(true));
	}
	
	@Test
	public void hasInvalidTagsShouldReturnFalseIfInvalidListHasNoTags() throws Exception {
		List<String> noInvalidTags = new ArrayList<String>();
		assertThat(new TagValidationResult(noInvalidTags, true).hasAnInvalidTag(), is(false));
	}
	@Test
	public void shouldContainListOfInvalidTags() throws Exception {
		List<String> invalidTags = new ArrayList<String>();
		String invalidTag = "122222";
		invalidTags.add(invalidTag);
		TagValidationResult tagValidationResult = new TagValidationResult(invalidTags, null);
		
		List<String> containedInvalidTags = tagValidationResult.getInvalidTags();
		assertThat(containedInvalidTags, is(invalidTags));
	}
		
}

package chronicles.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import chronicles.models.Story;
import chronicles.models.Tag;
import chronicles.repository.StoryRepository;

public class TagServiceTests {
	private TagService tagService;
	private Story story;
	StoryRepository storyRepository;
	@Before
	public void setUp(){
		
		this.story = new Story();
		this.storyRepository = mock(StoryRepository.class);
		this.tagService = new TagService(storyRepository);
		story.setId(1);
		
		when(storyRepository.findById(1)).thenReturn(story);
	}
	@Test
	public void shouldReturnTrueWhenTagIsInvalid() throws Exception {
		String invalidTag = "123456789012345678901234567890123456789012345678901";
		
		TagValidationResult tagValidationResult = tagService.validateAndSaveValidTags(invalidTag, story.getId());
		
		assertThat(tagValidationResult.hasAnInvalidTag(),is(true));
	}
	
	@Test
	public void shouldReturnTrueWhenTagIsValid() throws Exception {
		String validTag = "twu";
		TagValidationResult tagValidationResult = tagService.validateAndSaveValidTags(validTag, story.getId());
		
		assertThat(tagValidationResult.hasAnInvalidTag(),is(false));	
	}

	@Test
	public void shouldChangeTagsToLowerCase() throws Exception {
		String allCapsTag = "SAHIL";
		String lowerCaseResult = "sahil";
		assertThat(tagService.sanitize(allCapsTag), is(lowerCaseResult));
	}
	@Test
	public void shouldRemoveExcessiveSpacingFromEndsAndMiddle() throws Exception {
		String excessSpacedTag = "      sahil            andrew        ";
		
		String oneSpacedTag = "sahil andrew";
		assertThat(tagService.sanitize(excessSpacedTag), is(oneSpacedTag));
	}
	@Test
	public void shouldNotAddTagWithJustSpaces() throws Exception {
		String tagWithJustSpaces = "               ";
		tagService.validateAndSaveValidTags(tagWithJustSpaces, story.getId());
		assertThat(story.getTags().size(), is(0));
	}
	@Test
	public void shouldAddTagsToStory() throws Exception {
		String commaSeparatedTags = "andrew";
		tagService.validateAndSaveValidTags(commaSeparatedTags, story.getId());
		Tag expectedTag = new Tag(story.getId(), commaSeparatedTags);
		assertThat(story.getTags().contains(expectedTag), is(true));
	}
	@Test
	public void shouldReturnUpdateSuccessStatus() throws Exception {
		String tag = "sahil";
		when(storyRepository.update(story)).thenReturn(true);
		TagValidationResult tagValidationResult = tagService.validateAndSaveValidTags(tag, story.getId());
		
		assertThat(tagValidationResult.isUpdateSuccessful(), is(true));
		verify(storyRepository).update(story);
	}

}
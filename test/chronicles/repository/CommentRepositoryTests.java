package chronicles.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import chronicles.models.Comment;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
@TransactionConfiguration(defaultRollback=true)
@Transactional
public class CommentRepositoryTests {

	private ChroniclesHibernateDAOSupport<Comment> daoSupport = 
		mock(ChroniclesHibernateDAOSupport.class);
	
	@Test
	public void shouldAddFirstCommentToCommentRepository() {
		CommentRepository commentRepository = new CommentRepository(daoSupport);
		Comment comment = new Comment(1, 1, "content", new Date());
		when(daoSupport.save(comment)).thenReturn(true);
		
		assertThat(commentRepository.save(comment), is(true));
	}

	@Test
	public void shouldReturnFalseWhenAttemptingToSaveAnInvalidComment() {
		CommentRepository commentRepository = new CommentRepository(daoSupport);
		Comment comment = new Comment();
		when(daoSupport.save(comment)).thenReturn(false);
		
		assertThat(commentRepository.save(comment), is(false));
	}
	
	@Test
	public void shouldFindCommentById() {
		CommentRepository commentRepository = new CommentRepository(daoSupport);
		Comment comment = new Comment();
		comment.setId(1);
		SearchCriteria criteria = new SearchCriteriaEquals("id", "1");
		when(daoSupport.findBy("Comment", criteria)).thenReturn(comment);
		
		assertThat(commentRepository.findById(comment.getId()), is(comment));
	}
}

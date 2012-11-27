package chronicles.repository;

import org.springframework.beans.factory.annotation.Autowired;

import chronicles.models.Comment;

public class CommentRepository {

	@Autowired
	ChroniclesDAOSupport<Comment> chroniclesDAOSupport;
	
	@Autowired
	public CommentRepository(ChroniclesHibernateDAOSupport<Comment> chroniclesHibernateDAOSupport) {
		chroniclesDAOSupport = chroniclesHibernateDAOSupport;
	}

	public boolean save(Comment comment) {
		return chroniclesDAOSupport.save(comment);
	}

	public Comment findById(int id) {
		SearchCriteria equalsCriteria = new SearchCriteriaEquals("id", id + "");
		return chroniclesDAOSupport.findBy("Comment", equalsCriteria);
	}

}

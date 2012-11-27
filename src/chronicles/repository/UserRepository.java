package chronicles.repository;

import org.springframework.beans.factory.annotation.Autowired;

import chronicles.models.User;

public class UserRepository {

    @Autowired
    private final ChroniclesDAOSupport<User> chroniclesDAOSupport;

    @Autowired
    public UserRepository(ChroniclesDAOSupport<User> chroniclesDAOSupport) {
        this.chroniclesDAOSupport = chroniclesDAOSupport;
    }

	public User findByEmail(String email) {
        return findBy("email", email);
	}

	public User findByName(String name) {
        return findBy("username", name);
    }
	
	public User findById(Integer id) {
		return findBy("id", id.toString());
	}

    public int findIdByUserName(String name) {
        return findByName(name).getId();
    }

    private User findBy(String columnName, String query) {
        return chroniclesDAOSupport.findBy("User", new SearchCriteriaEquals(columnName, query));
    }

    public boolean save(User user) {
        return chroniclesDAOSupport.save(user);
    }

    public boolean update(User user){
        return chroniclesDAOSupport.update(user);
    }

}

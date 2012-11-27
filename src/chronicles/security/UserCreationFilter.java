package chronicles.security;

import org.springframework.beans.factory.annotation.Autowired;

import chronicles.models.User;
import chronicles.repository.UserRepository;

public class UserCreationFilter {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserCreationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkUser(String userName) {
        User user = userRepository.findByName(userName);
		if (user == null) {
			User newUser = new User(userName, "", "");
			userRepository.save(newUser);
		}
    }
}

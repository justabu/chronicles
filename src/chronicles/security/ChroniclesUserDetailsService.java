package chronicles.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ChroniclesUserDetailsService implements UserDetailsService {
    @Autowired
    private UserCreationFilter filter;

    @Autowired
    public ChroniclesUserDetailsService(UserCreationFilter filter) {
        this.filter = filter;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException, DataAccessException {
        filter.checkUser(userName);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new GrantedAuthority() {
        private static final long serialVersionUID = 1L;
            public String getAuthority() {
            	return "ROLE_AUTHENTICATED";
            }
        });
        return new User(userName, "", true, true, true, true, authorities);
    }
}

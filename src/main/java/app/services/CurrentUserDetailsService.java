package app.services;

import app.entities.CurrentUser;
import app.entities.User;
import app.interfaces.UserDAOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsService implements UserDetailsService {
    @Autowired
    UserDAOInterface userDAOInterface;

    public CurrentUser loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userDAOInterface.findByName(name);
        return new CurrentUser(user);
    }
}
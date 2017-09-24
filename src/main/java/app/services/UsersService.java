package app.services;

import app.entities.User;
import app.exceptions.InvalidEntityException;
import app.interfaces.UserDAOInterface;
import app.interfaces.UsersServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UsersService implements UsersServiceInterface {

    private UserDAOInterface userDAOInterface;

    @Autowired
    public UsersService(
            UserDAOInterface userDAOInterface) {
        this.userDAOInterface = userDAOInterface;
    }

    @Override
    public User createUser(User user) {
        User existingUser = userDAOInterface.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new InvalidEntityException("There is already a user with that email", Collections.emptyList());
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDAOInterface.save(user);

        return user;
    }

    @Override
    public List<User> getAllUsers() {
        Iterable<User> iterable = userDAOInterface.findAll();
        List<User> users = new ArrayList<>();
        iterable.forEach(users::add);
        return users;
    }

    @Override
    public User getUser(Long id) {
        return userDAOInterface.findOne(id);
    }

    @Override
    public User updateUser(User user) {
        userDAOInterface.save(user);
        return user;
    }

    @Override
    public void deleteUser(Long id) {
        userDAOInterface.delete(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAOInterface.findByEmail(email);
    }
}

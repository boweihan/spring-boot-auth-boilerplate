package app.resources;

import app.entities.User;
import app.exceptions.InvalidEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import app.interfaces.UserDAOInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersResource {

    private UserDAOInterface userDAOInterface;

    @Autowired
    public UsersResource(
            UserDAOInterface userDAOInterface) {
        this.userDAOInterface = userDAOInterface;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody User user) {
        User existingUser = userDAOInterface.findByEmail(user.getEmail());

        if (existingUser != null) {
            throw new InvalidEntityException("There is already a user with that email", Collections.emptyList());
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDAOInterface.save(user);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> readAll() {
        Iterable<User> iterable = userDAOInterface.findAll();
        List<User> users = new ArrayList<>();
        iterable.forEach(users::add);
        return users;
    }

    @RequestMapping(value = "/{id}")
    public User read(@PathVariable long id) {
        return userDAOInterface.findOne(id);
    }

    @RequestMapping(value = "/name")
    public void read() {
        String lastName = "last";
        userDAOInterface.findByName(lastName);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody User user) {
        userDAOInterface.save(user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        userDAOInterface.delete(id);
    }
}
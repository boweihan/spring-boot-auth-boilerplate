package app.resources;

import app.dto.LoginDTO;
import app.entities.User;
import app.interfaces.UsersServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersResource {

    private UsersServiceInterface usersServiceInterface;

    @Autowired
    public UsersResource(
            UsersServiceInterface usersServiceInterface) {
        this.usersServiceInterface = usersServiceInterface;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User create(@RequestBody User user) {
        return usersServiceInterface.createUser(user);
    }

    @GetMapping
    public List<User> readAll() {
        return usersServiceInterface.getAllUsers();
    }

    @GetMapping(value = "/{id}")
    public User read(@PathVariable long id) {
        return usersServiceInterface.getUser(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User update(@RequestBody User user) {
        return usersServiceInterface.updateUser(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) {
        usersServiceInterface.deleteUser(id);
    }

    /*
        Authentication token handled client-side, this endpoint just returns the user object
     */
    @PostMapping(value = "/login")
    public User login(@RequestBody LoginDTO loginDTO) {
        return usersServiceInterface.getUserByEmail(loginDTO.getEmail());
    }
}
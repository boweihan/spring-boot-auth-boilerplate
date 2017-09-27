package app.resources;

import app.dto.LoginDTO;
import app.dto.UserDTO;
import app.entities.Role;
import app.entities.User;
import app.interfaces.RoleDAOInterface;
import app.interfaces.UsersServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UsersResource {

    private UsersServiceInterface usersServiceInterface;
    private RoleDAOInterface roleDAOInterface;

    @Autowired
    public UsersResource(
            UsersServiceInterface usersServiceInterface,
            RoleDAOInterface roleDAOInterface) {
        this.usersServiceInterface = usersServiceInterface;
        this.roleDAOInterface = roleDAOInterface;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public User create(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(userDTO.isEnabled());
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
    public User update(@RequestBody UserDTO userDTO) {
        User user = usersServiceInterface.getUserByEmail(userDTO.getEmail());
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(userDTO.isEnabled());
        return usersServiceInterface.updateUser(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) {
        usersServiceInterface.deleteUser(id);
    }

    /*
     *  Current login implementation.  Let front end handle authentication token in browser session for now
     *  TODO: move to JWT
     */
    @PostMapping(value = "/login")
    public User login(@RequestBody LoginDTO loginDTO) {
        return usersServiceInterface.getUserByEmail(loginDTO.getEmail());
    }

    /*
     *  Unsecured SignUp Endpoint. Used to create users with base permissions.
     */
    @PostMapping(value = "/signup")
    public User signup(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setEnabled(true);

        Set<Role> roles = new HashSet<>();
        roles.add(roleDAOInterface.findByName("user"));

        user.setRoles(roles);
        return usersServiceInterface.createUser(user);
    }
}
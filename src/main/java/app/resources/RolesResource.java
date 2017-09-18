package app.resources;

import app.entities.Role;
import app.entities.User;
import app.exceptions.InvalidEntityException;
import app.interfaces.RoleDAOInterface;
import app.interfaces.UserDAOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesResource {

    @Autowired
    RoleDAOInterface roleDAOInterface;

    @Autowired
    UserDAOInterface userDAOInterface;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Role role) {
        Role existingRole = roleDAOInterface.findByName(role.getRole().toString());
        if (existingRole != null) {
            throw new InvalidEntityException("There is already a role with that name", Collections.emptyList());
        }
        roleDAOInterface.save(role);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Role> readAll() {
        Iterable<Role> iterable = roleDAOInterface.findAll();
        List<Role> roles = new ArrayList<>();
        iterable.forEach(roles::add);
        return roles;
    }

    @RequestMapping(value = "/{id}")
    public Role read(@PathVariable long id) {
        return roleDAOInterface.findOne(id);
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody Role role) {
        roleDAOInterface.save(role);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable long id) {
        roleDAOInterface.delete(id);
    }

    @RequestMapping(value = "/{id}/assign", method = RequestMethod.POST)
    public void assignUserToRole(@PathVariable long id, @PathParam("userId") long userId) {
        User user = userDAOInterface.findOne(userId);
        Role role = roleDAOInterface.findOne(id);
        role.addUser(user);
        roleDAOInterface.save(role);
    }
}

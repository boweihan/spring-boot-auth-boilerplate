package app.resources;

import app.entities.Role;
import app.entities.User;
import app.exceptions.InvalidEntityException;
import app.interfaces.RoleDAOInterface;
import app.interfaces.RolesServiceInterface;
import app.interfaces.UserDAOInterface;
import app.services.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/admin/roles")
public class RolesResource {

    private RolesServiceInterface rolesServiceInterface;

    @Autowired
    public RolesResource(
            RolesServiceInterface rolesServiceInterface) {
        this.rolesServiceInterface = rolesServiceInterface;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Role create(@RequestBody Role role) {
        return rolesServiceInterface.createRole(role);
    }

    @GetMapping
    public List<Role> readAll() {
        return rolesServiceInterface.getAllRoles();
    }

    @GetMapping(value = "/{id}")
    public Role read(@PathVariable long id) {
        return rolesServiceInterface.getRole(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Role update(@RequestBody Role role) {
        return rolesServiceInterface.updateRole(role);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable long id) {
        rolesServiceInterface.deleteRole(id);
    }

    @PostMapping(value = "/{id}/assign")
    public void assignUserToRole(@PathVariable long id, @PathParam("userId") long userId) {
        rolesServiceInterface.assignUserToRole(id, userId);
    }
}

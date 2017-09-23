package app.services;

import app.entities.Role;
import app.entities.User;
import app.exceptions.InvalidEntityException;
import app.interfaces.RoleDAOInterface;
import app.interfaces.RolesServiceInterface;
import app.interfaces.UserDAOInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RolesService implements RolesServiceInterface {

    private UserDAOInterface userDAOInterface;
    private RoleDAOInterface roleDAOInterface;

    @Autowired
    public RolesService(
            UserDAOInterface userDAOInterface,
            RoleDAOInterface roleDAOInterface) {
        this.userDAOInterface = userDAOInterface;
        this.roleDAOInterface = roleDAOInterface;
    }

    @Override
    public Role createRole(Role role) {
        Role existingRole = roleDAOInterface.findByName(role.getName());

        if (existingRole != null) {
            throw new InvalidEntityException("There is already a role with that name", Collections.emptyList());
        }

        roleDAOInterface.save(role);
        return role;
    }

    @Override
    public List<Role> getAllRoles() {
        Iterable<Role> iterable = roleDAOInterface.findAll();
        List<Role> roles = new ArrayList<>();
        iterable.forEach(roles::add);
        return roles;
    }

    @Override
    public Role getRole(Long id) {
        return roleDAOInterface.findOne(id);
    }

    @Override
    public Role updateRole(Role role) {
        roleDAOInterface.save(role);
        return role;
    }

    @Override
    public void deleteRole(Long id) {
        roleDAOInterface.delete(id);
    }

    @Override
    public void assignUserToRole(Long roleId, Long userId) {
        User user = userDAOInterface.findOne(userId);
        Role role = roleDAOInterface.findOne(roleId);
        role.addUser(user);
        roleDAOInterface.save(role);
    }
}

package app.interfaces;

import app.entities.Role;

import java.util.List;

public interface RolesServiceInterface {
    public Role createRole(Role role);
    public List<Role> getAllRoles();
    public Role getRole(Long id);
    public Role updateRole(Role role);
    public void deleteRole(Long id);
    public void assignUserToRole(Long roleId, Long userId);
}

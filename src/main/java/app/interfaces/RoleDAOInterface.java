package app.interfaces;

import app.entities.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleDAOInterface extends CrudRepository<Role, Long> {
    Role findByName(String roleName);
}
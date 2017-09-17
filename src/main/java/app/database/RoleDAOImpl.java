package app.database;

import app.entities.Role;
import app.interfaces.RoleDAOInterface;

public abstract class RoleDAOImpl implements RoleDAOInterface {
    @Override
    public Role findByName(String roleName) {
        return null;
    }
}

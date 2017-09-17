package app.database;

import app.interfaces.UserDAOInterface;
import app.entities.User;

public abstract class UserDAOImpl implements UserDAOInterface {
    @Override
    public User findByName(String lastName) {
        return null;
    }
}

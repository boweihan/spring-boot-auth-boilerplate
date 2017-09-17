package app.database;

import app.interfaces.UserDAOInterface;
import app.models.User;

import java.util.List;

public abstract class UserDAOImpl implements UserDAOInterface {
    @Override
    public User findByName(String lastName) {
        return null;
    }
}

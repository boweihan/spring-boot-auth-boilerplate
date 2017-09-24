package app.interfaces;

import app.entities.User;

import java.util.List;

public interface UsersServiceInterface {
    public User createUser(User user);
    public List<User> getAllUsers();
    public User getUser(Long id);
    public User updateUser(User user);
    public void deleteUser(Long id);
    public User getUserByEmail(String email);
}

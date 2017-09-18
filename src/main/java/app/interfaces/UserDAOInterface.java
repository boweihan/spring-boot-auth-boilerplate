package app.interfaces;

import org.springframework.data.repository.CrudRepository;
import app.entities.User;

public interface UserDAOInterface extends CrudRepository<User, Long> {
    User findByName(String lastName);
    User findByEmail(String email);
}
package app.interfaces;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import app.models.User;

public interface UserDAOInterface extends CrudRepository<User, Long>{
    List<User> findByName(String lastName);
}
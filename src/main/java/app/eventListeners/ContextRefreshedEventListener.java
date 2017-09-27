package app.eventListeners;

import app.entities.Role;
import app.entities.User;
import app.interfaces.RoleDAOInterface;
import app.interfaces.UserDAOInterface;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ContextRefreshedEventListener {

    private Logger logger = Logger.getLogger(ContextRefreshedEventListener.class);
    private JdbcTemplate jdbcTemplate;
    private UserDAOInterface userDAOInterface;
    private RoleDAOInterface roleDAOInterface;

    @Autowired
    public ContextRefreshedEventListener(
            JdbcTemplate jdbcTemplate,
            UserDAOInterface userDAOInterface,
            RoleDAOInterface roleDAOInterface) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDAOInterface = userDAOInterface;
        this.roleDAOInterface = roleDAOInterface;
    }

    /*
        This listens to Spring Boot's ContextRefreshedEvent, which happens on startup.
        We want to seed users into our database if they it doesn't exist. You may not to
        seed if you are OK to publicly expose your user API.
     */
    @EventListener
    public void seed(ContextRefreshedEvent event) {
        seedUser();
        seedAdminRole();
        seedUserRole();
        seedJoinTable();
    }

    /*
        Seed admin user
     */
    @Transactional
    private void seedUser() {
        String userSql = "SELECT email FROM users U WHERE U.email = \'admin@bsbs.com\' LIMIT 1";
        List<User> u = jdbcTemplate.query(userSql, (resultSet, rowNum) -> null);

        if(u == null || u.size() <= 0) {
            User user = new User();
            user.setName("admin");
            user.setEmail("admin@bsbs.com");
            user.setPassword(new BCryptPasswordEncoder().encode("password"));
            user.setEnabled(true);
            userDAOInterface.save(user);
            logger.info("Admin user seeded");
        } else {
            logger.info("Seeding not required");
        }
    }

    /*
        Seed admin role
     */
    @Transactional
    private void seedAdminRole() {
        String roleSql = "SELECT name FROM roles R WHERE R.name = \'admin\' LIMIT 1";
        List<Role> r = jdbcTemplate.query(roleSql, (resultSet, rowNum) -> null);

        if(r == null || r.size() <= 0) {
            Role role = new Role();
            role.setDescription("Admin User Role");
            role.setName("admin");
            roleDAOInterface.save(role);
            logger.info("Admin user seeded");
        } else {
            logger.info("Seeding not required");
        }
    }

    /*
    Seed user role
 */
    @Transactional
    private void seedUserRole() {
        String roleSql = "SELECT name FROM roles R WHERE R.name = \'user\' LIMIT 1";
        List<Role> r = jdbcTemplate.query(roleSql, (resultSet, rowNum) -> null);

        if(r == null || r.size() <= 0) {
            Role role = new Role();
            role.setDescription("Regular User Role");
            role.setName("user");
            roleDAOInterface.save(role);
            logger.info("Regular user seeded");
        } else {
            logger.info("Seeding not required");
        }
    }

    /*
        Assign admin user to admin role
     */
    @Transactional
    private void seedJoinTable() {
        String userSql = "SELECT * FROM users U WHERE U.email = \'admin@bsbs.com\' LIMIT 1";
        User u = jdbcTemplate.queryForObject(userSql, (resultSet, rowNum) -> {
            User user = new User();
            user.setId(resultSet.getLong("id"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setEnabled(resultSet.getBoolean("enabled"));
            user.setPassword(resultSet.getString("password"));
            return user;
        });

        String roleSql = "SELECT * FROM roles R WHERE R.name = \'admin\' LIMIT 1";
        Role r = jdbcTemplate.queryForObject(roleSql, (resultSet, rowNum) -> {
            Role role = new Role();
            role.setId(resultSet.getLong("id"));
            role.setName(resultSet.getString("name"));
            role.setDescription(resultSet.getString("description"));
            return role;
        });

        Set<Role> roles = new HashSet<>();
        roles.add(r);
        u.setRoles(roles);
        userDAOInterface.save(u);
    }

}

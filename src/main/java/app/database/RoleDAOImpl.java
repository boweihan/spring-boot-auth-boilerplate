package app.database;

import app.entities.Role;
import app.interfaces.RoleDAOInterface;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RoleDAOImpl implements RoleDAOInterface {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Role findByName(String name) {
        Criteria criteria = sessionFactory.openSession().createCriteria(Role.class);
        criteria.add(Restrictions.eq("name", name));
        return (Role) criteria.uniqueResult();
    }
}

package ua.goit.PMS.model.dao;

import org.hibernate.SessionFactory;
import ua.goit.PMS.model.entity.Developer;

public class DeveloperDAO extends AbstractDAO<Developer> {

    public DeveloperDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Developer.class);
    }

}

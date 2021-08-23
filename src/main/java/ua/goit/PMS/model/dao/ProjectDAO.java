package ua.goit.PMS.model.dao;

import org.hibernate.SessionFactory;
import ua.goit.PMS.model.entity.Project;

public class ProjectDAO extends AbstractDAO<Project> {

    public ProjectDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Project.class);
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE Project p WHERE p.id=:id";
    }
}

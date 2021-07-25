package ua.goit.PMS.model.dao;

import org.hibernate.SessionFactory;
import ua.goit.PMS.model.entity.Skill;

public class SkillDAO extends AbstractDAO<Skill> {

    public SkillDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Skill.class);
    }

}

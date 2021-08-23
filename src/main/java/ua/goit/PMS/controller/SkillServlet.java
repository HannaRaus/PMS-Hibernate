package ua.goit.PMS.controller;

import org.hibernate.SessionFactory;
import ua.goit.PMS.config.HibernateDatabaseConnector;
import ua.goit.PMS.model.dao.DeveloperDAO;
import ua.goit.PMS.model.dao.SkillDAO;
import ua.goit.PMS.model.entity.Developer;
import ua.goit.PMS.model.entity.Skill;
import ua.goit.PMS.service.Service;
import ua.goit.PMS.service.mappers.EntityFromJSPMapper;
import ua.goit.PMS.service.mappers.SkillFromJSPMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@WebServlet("/skills/*")
public class SkillServlet extends AbstractServlet<Skill> {
    private final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
    private final Service<Developer> developerService = new Service<>(new DeveloperDAO(sessionFactory));

    @Override
    protected EntityFromJSPMapper<Skill> initMapper() {
        return new SkillFromJSPMapper(developerService);
    }

    @Override
    protected Service<Skill> initService() {
        return new Service<>(new SkillDAO(sessionFactory));
    }

    @Override
    protected String getServletPath() {
        return "/skills";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/skills.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/skill.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/skillForm.jsp";
    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        Set<Developer> developerList = developerService.readAll();
        req.setAttribute("developerList", developerList);
    }
}


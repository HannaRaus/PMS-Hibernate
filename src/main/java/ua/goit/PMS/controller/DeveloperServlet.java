package ua.goit.PMS.controller;

import org.hibernate.SessionFactory;
import ua.goit.PMS.config.HibernateDatabaseConnector;
import ua.goit.PMS.model.dao.DeveloperDAO;
import ua.goit.PMS.model.dao.ProjectDAO;
import ua.goit.PMS.model.dao.SkillDAO;
import ua.goit.PMS.model.entity.Developer;
import ua.goit.PMS.model.entity.Project;
import ua.goit.PMS.model.entity.Skill;
import ua.goit.PMS.service.Service;
import ua.goit.PMS.service.mappers.DeveloperFromJspMapper;
import ua.goit.PMS.service.mappers.EntityFromJSPMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@WebServlet("/developers/*")
public class DeveloperServlet extends AbstractServlet<Developer> {
    private final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
    private final Service<Project> projectService = new Service<>(new ProjectDAO(sessionFactory));
    private final Service<Skill> skillService = new Service<>(new SkillDAO(sessionFactory));

    @Override
    protected EntityFromJSPMapper<Developer> initMapper() {
        return new DeveloperFromJspMapper(projectService, skillService);
    }

    @Override
    protected Service<Developer> initService() {
        return new Service<>(new DeveloperDAO(sessionFactory));
    }

    @Override
    protected String getServletPath() {
        return "/developers";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/developers.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/developer.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/developerForm.jsp";
    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        Set<Project> projectList = projectService.readAll();
        req.setAttribute("projectList", projectList);
        Set<Skill> skillList = skillService.readAll();
        req.setAttribute("skillList", skillList);
    }
}

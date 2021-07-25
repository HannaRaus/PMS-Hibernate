package ua.goit.PMS.controller;

import org.hibernate.SessionFactory;
import ua.goit.PMS.config.HibernateDatabaseConnector;
import ua.goit.PMS.model.dao.DeveloperDAO;
import ua.goit.PMS.model.dao.ProjectDAO;
import ua.goit.PMS.model.dao.SkillDAO;
import ua.goit.PMS.model.entity.Developer;
import ua.goit.PMS.model.entity.Project;
import ua.goit.PMS.model.entity.Sex;
import ua.goit.PMS.model.entity.Skill;
import ua.goit.PMS.service.Service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/developers/*")
public class DeveloperServlet extends AbstractServlet<Developer> {
    private final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
    private final Service<Project> projectService = new Service<>(new ProjectDAO(sessionFactory));
    private final Service<Skill> skillService = new Service<>(new SkillDAO(sessionFactory));

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
    protected Developer readJSPForm(HttpServletRequest req, Developer developer) {

        if (Objects.isNull(developer)) {
            developer = new Developer();
        }

        developer.setFirstName(req.getParameter("firstName"));
        developer.setLastName(req.getParameter("lastName"));
        developer.setSex(Sex.valueOf(req.getParameter("sex")));
        developer.setSalary(Double.parseDouble(req.getParameter("salary")));

        if (developer.getId() != 0) {
            String[] listOfProjectId = req.getParameterValues("projects");
            Set<Project> projects = new HashSet<>();
            if (listOfProjectId != null && listOfProjectId.length > 0) {
                projects = Arrays.stream(listOfProjectId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, projectService))
                        .collect(Collectors.toSet());
            }
            developer.setProjects(projects);

            Set<Skill> skills = new HashSet<>();
            String[] listOfSkillId = req.getParameterValues("skills");
            if (listOfSkillId != null && listOfSkillId.length > 0) {
                skills = Arrays.stream(listOfSkillId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, skillService))
                        .collect(Collectors.toSet());
            }
            developer.setSkills(skills);

        }
        return developer;

    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        Set<Project> projectList = projectService.readAll();
        req.setAttribute("projectList", projectList);
        Set<Skill> skillList = skillService.readAll();
        req.setAttribute("skillList", skillList);
    }
}

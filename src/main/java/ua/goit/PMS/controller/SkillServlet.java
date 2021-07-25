package ua.goit.PMS.controller;

import org.hibernate.SessionFactory;
import ua.goit.PMS.config.HibernateDatabaseConnector;
import ua.goit.PMS.model.dao.DeveloperDAO;
import ua.goit.PMS.model.dao.SkillDAO;
import ua.goit.PMS.model.entity.Branch;
import ua.goit.PMS.model.entity.Developer;
import ua.goit.PMS.model.entity.Skill;
import ua.goit.PMS.model.entity.SkillLevel;
import ua.goit.PMS.service.Service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/skills/*")
public class SkillServlet extends AbstractServlet<Skill> {
    private final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
    private final Service<Developer> developerService = new Service<>(new DeveloperDAO(sessionFactory));

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
    protected Skill readJSPForm(HttpServletRequest req, Skill skill) {

        if (Objects.isNull(skill)) {
            skill = new Skill();
        }

        skill.setBranch(Branch.valueOf(req.getParameter("branch")));
        skill.setLevel(SkillLevel.valueOf(req.getParameter("level")));

        if (skill.getId() != 0) {
            String[] listOfDeveloperId = req.getParameterValues("developers");
            Set<Developer> developers = new HashSet<>();
            if (listOfDeveloperId != null && listOfDeveloperId.length > 0) {
                developers = Arrays.stream(listOfDeveloperId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, developerService))
                        .collect(Collectors.toSet());
            }
            skill.setDevelopers(developers);

        }
        return skill;

    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        Set<Developer> developerList = developerService.readAll();
        req.setAttribute("developerList", developerList);
    }
}


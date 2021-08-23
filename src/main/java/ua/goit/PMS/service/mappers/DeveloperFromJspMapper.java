package ua.goit.PMS.service.mappers;

import ua.goit.PMS.exceptions.DAOException;
import ua.goit.PMS.model.entity.Developer;
import ua.goit.PMS.model.entity.Project;
import ua.goit.PMS.model.entity.Sex;
import ua.goit.PMS.model.entity.Skill;
import ua.goit.PMS.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperFromJspMapper implements EntityFromJSPMapper<Developer> {
    private final Service<Project> projectService;
    private final Service<Skill> skillService;

    public DeveloperFromJspMapper(Service<Project> projectService, Service<Skill> skillService) {
        this.projectService = projectService;
        this.skillService = skillService;
    }

    @Override
    public Developer readJSPForm(HttpServletRequest req) {
        Developer developer = new Developer();
        developer.setFirstName(req.getParameter("firstName"));
        developer.setLastName(req.getParameter("lastName"));
        developer.setSex(Sex.valueOf(req.getParameter("sex")));
        developer.setSalary(Double.parseDouble(req.getParameter("salary")));
        return developer;
    }

    @Override
    public Developer readJSPForm(HttpServletRequest req, Developer developer) {
        developer.setFirstName(req.getParameter("firstName"));
        developer.setLastName(req.getParameter("lastName"));
        developer.setSex(Sex.valueOf(req.getParameter("sex")));
        developer.setSalary(Double.parseDouble(req.getParameter("salary")));

        String[] listOfProjectId = req.getParameterValues("projects");
        Set<Project> projects = new HashSet<>();
        if (listOfProjectId != null && listOfProjectId.length > 0) {
            projects = Arrays.stream(listOfProjectId)
                    .mapToInt(Integer::parseInt)
                    .mapToObj(id -> {
                        Project project = null;
                        try {
                            project =projectService.findById(id);
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                        return project;
                    })
                    .collect(Collectors.toSet());
        }
        developer.setProjects(projects);

        Set<Skill> skills = new HashSet<>();
        String[] listOfSkillId = req.getParameterValues("skills");
        if (listOfSkillId != null && listOfSkillId.length > 0) {
            skills = Arrays.stream(listOfSkillId)
                    .mapToInt(Integer::parseInt)
                    .mapToObj(id -> {
                        Skill skill = null;
                        try {
                            skill = skillService.findById(id);
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                        return skill;
                    })
                    .collect(Collectors.toSet());
        }
        developer.setSkills(skills);

        return developer;
    }
}

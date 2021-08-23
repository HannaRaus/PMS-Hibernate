package ua.goit.PMS.service.mappers;

import ua.goit.PMS.exceptions.DAOException;
import ua.goit.PMS.model.entity.Company;
import ua.goit.PMS.model.entity.Project;
import ua.goit.PMS.service.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyFromJSPMapper implements EntityFromJSPMapper<Company> {
    private final Service<Project> projectService;

    public CompanyFromJSPMapper(Service<Project> projectService) {
        this.projectService = projectService;
    }

    @Override
    public Company readJSPForm(HttpServletRequest req) {
        Company company = new Company();
        company.setName(req.getParameter("name"));
        company.setHeadquarters(req.getParameter("headquarters"));
        return company;
    }

    @Override
    public Company readJSPForm(HttpServletRequest req, Company company) {
        company.setName(req.getParameter("name"));
        company.setHeadquarters(req.getParameter("headquarters"));

        String[] listOfProjectId = req.getParameterValues("projects");
        Set<Project> projects = new HashSet<>();
        if (listOfProjectId != null && listOfProjectId.length > 0) {
            projects = Arrays.stream(listOfProjectId)
                    .mapToInt(Integer::parseInt)
                    .mapToObj(id -> {
                        Project project = null;
                        try {
                            project = projectService.findById(id);
                        } catch (DAOException e) {
                            e.printStackTrace();
                        }
                        return project;
                    })
                    .collect(Collectors.toSet());
        }
        company.setProjects(projects);

        return company;
    }
}

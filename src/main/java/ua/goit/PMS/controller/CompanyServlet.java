package ua.goit.PMS.controller;

import org.hibernate.SessionFactory;
import ua.goit.PMS.config.HibernateDatabaseConnector;
import ua.goit.PMS.model.dao.CompanyDAO;
import ua.goit.PMS.model.dao.ProjectDAO;
import ua.goit.PMS.model.entity.Company;
import ua.goit.PMS.model.entity.Project;
import ua.goit.PMS.service.Service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/companies/*")
public class CompanyServlet extends AbstractServlet<Company> {
    private final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
    private final Service<Project> projectService = new Service<>(new ProjectDAO(sessionFactory));

    @Override
    protected Service<Company> initService() {
        return new Service<>(new CompanyDAO(sessionFactory));
    }

    @Override
    protected String getServletPath() {
        return "/companies";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/companies.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/company.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/companyForm.jsp";
    }

    @Override
    protected Company readJSPForm(HttpServletRequest req, Company company) {

        if (Objects.isNull(company)) {
            company = new Company();
        }

        company.setName(req.getParameter("name"));
        company.setHeadquarters(req.getParameter("headquarters"));

        if (company.getId() != 0) {

            String[] listOfProjectId = req.getParameterValues("projects");
            Set<Project> projects = new HashSet<>();
            if (listOfProjectId != null && listOfProjectId.length > 0) {
                projects = Arrays.stream(listOfProjectId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, projectService))
                        .collect(Collectors.toSet());
            }
            company.setProjects(projects);

        }

        return company;
    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        Set<Project> projectList = projectService.readAll();
        req.setAttribute("projectList", projectList);
    }
}

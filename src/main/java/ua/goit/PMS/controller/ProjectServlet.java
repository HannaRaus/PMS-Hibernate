package ua.goit.PMS.controller;

import org.hibernate.SessionFactory;
import ua.goit.PMS.config.HibernateDatabaseConnector;
import ua.goit.PMS.model.dao.CompanyDAO;
import ua.goit.PMS.model.dao.CustomerDAO;
import ua.goit.PMS.model.dao.DeveloperDAO;
import ua.goit.PMS.model.dao.ProjectDAO;
import ua.goit.PMS.model.entity.Company;
import ua.goit.PMS.model.entity.Customer;
import ua.goit.PMS.model.entity.Developer;
import ua.goit.PMS.model.entity.Project;
import ua.goit.PMS.service.Service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@WebServlet("/projects/*")
public class ProjectServlet extends AbstractServlet<Project> {
    private final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
    private final Service<Customer> customerService = new Service<>(new CustomerDAO(sessionFactory));
    private final Service<Company> companyService = new Service<>(new CompanyDAO(sessionFactory));
    private final Service<Developer> developerService = new Service<>(new DeveloperDAO(sessionFactory));

    @Override
    protected Service<Project> initService() {
        return new Service<>(new ProjectDAO(sessionFactory));
    }

    @Override
    protected String getServletPath() {
        return "/projects";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/projects.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/project.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/projectForm.jsp";
    }

    @Override
    protected Project readJSPForm(HttpServletRequest req, Project project) {

        if (Objects.isNull(project)) {
            project = new Project();
        }

        project.setName(req.getParameter("name"));
        project.setDescription(req.getParameter("description"));
        project.setCost(Double.parseDouble(req.getParameter("cost")));

        if (project.getId() != 0) {
            project.setDate(LocalDate.parse(req.getParameter("date")));
            String[] listOfCustomerId = req.getParameterValues("customers");
            Set<Customer> customers = new HashSet<>();
            if (listOfCustomerId != null && listOfCustomerId.length > 0) {
                customers = Arrays.stream(listOfCustomerId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(cus -> findById(cus, customerService))
                        .collect(Collectors.toSet());
            }
            project.setCustomers(customers);

            String[] listOfCompanyId = req.getParameterValues("companies");
            Set<Company> companies = new HashSet<>();
            if (listOfCompanyId != null && listOfCompanyId.length > 0) {
                companies = Arrays.stream(listOfCompanyId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, companyService))
                        .collect(Collectors.toSet());
            }
            project.setCompanies(companies);

            String[] listOfDeveloperId = req.getParameterValues("developers");
            Set<Developer> developers = new HashSet<>();
            if (listOfDeveloperId != null && listOfDeveloperId.length > 0) {
                developers = Arrays.stream(listOfDeveloperId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, developerService))
                        .collect(Collectors.toSet());
            }
            project.setDevelopers(developers);

        }
        return project;

    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        Set<Customer> customerList = customerService.readAll();
        Set<Company> companyList = companyService.readAll();
        Set<Developer> developerList = developerService.readAll();
        req.setAttribute("customerList", customerList);
        req.setAttribute("companyList", companyList);
        req.setAttribute("developerList", developerList);
    }
}

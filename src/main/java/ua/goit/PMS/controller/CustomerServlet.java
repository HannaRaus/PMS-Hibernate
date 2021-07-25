package ua.goit.PMS.controller;

import org.hibernate.SessionFactory;
import ua.goit.PMS.config.HibernateDatabaseConnector;
import ua.goit.PMS.model.dao.CustomerDAO;
import ua.goit.PMS.model.dao.ProjectDAO;
import ua.goit.PMS.model.entity.Customer;
import ua.goit.PMS.model.entity.Project;
import ua.goit.PMS.service.Service;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

;

@WebServlet("/customers/*")
public class CustomerServlet extends AbstractServlet<Customer> {
    private final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
    private final Service<Project> projectService = new Service<>(new ProjectDAO(sessionFactory));

    @Override
    protected Service<Customer> initService() {
        return new Service<>(new CustomerDAO(sessionFactory));
    }

    @Override
    protected String getServletPath() {
        return "/customers";
    }

    @Override
    protected String getEntitiesPage() {
        return "/view/customers.jsp";
    }

    @Override
    protected String getEntityPage() {
        return "/view/customer.jsp";
    }

    @Override
    protected String getFormPage() {
        return "/view/customerForm.jsp";
    }

    @Override
    protected Customer readJSPForm(HttpServletRequest req, Customer customer) {

        if (Objects.isNull(customer)) {
            customer = new Customer();
        }

        customer.setName(req.getParameter("name"));
        customer.setIndustry(req.getParameter("industry"));

        if (customer.getId() != 0) {

            String[] listOfProjectId = req.getParameterValues("projects");
            Set<Project> projects = new HashSet<>();
            if (listOfProjectId != null && listOfProjectId.length > 0) {
                projects = Arrays.stream(listOfProjectId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, projectService))
                        .collect(Collectors.toSet());
            }
            customer.setProjects(projects);

        }
        return customer;

    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        Set<Project> projectList = projectService.readAll();
        req.setAttribute("projectList", projectList);
    }
}

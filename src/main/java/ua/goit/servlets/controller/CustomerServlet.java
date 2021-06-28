package ua.goit.servlets.controller;

import ua.goit.servlets.config.DatabaseConnectionManager;
import ua.goit.servlets.dao.*;
import ua.goit.servlets.dto.*;
import ua.goit.servlets.service.Service;;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/customers/*")
public class CustomerServlet extends AbstractServlet<Customer> {
    private final Service<Project> projectService = new Service<>(new ProjectDAO(DatabaseConnectionManager.getDataSource()));

    @Override
    protected Service<Customer> initService() {
        return new Service<>(new CustomerDAO(DatabaseConnectionManager.getDataSource()));
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
            List<Project> projects = new ArrayList<>();
            if (listOfProjectId != null && listOfProjectId.length > 0) {
                projects = Arrays.stream(listOfProjectId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, projectService))
                        .collect(Collectors.toList());
            }
            customer.setProjects(projects);

        }
        return customer;

    }

    @Override
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        List<Project> projectList = projectService.readAll();
        req.setAttribute("projectList", projectList);
    }
}

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
import ua.goit.PMS.service.mappers.EntityFromJSPMapper;
import ua.goit.PMS.service.mappers.ProjectFromJSPMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@WebServlet("/projects/*")
public class ProjectServlet extends AbstractServlet<Project> {
    private final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
    private final Service<Customer> customerService = new Service<>(new CustomerDAO(sessionFactory));
    private final Service<Company> companyService = new Service<>(new CompanyDAO(sessionFactory));
    private final Service<Developer> developerService = new Service<>(new DeveloperDAO(sessionFactory));

    @Override
    protected EntityFromJSPMapper<Project> initMapper() {
        return new ProjectFromJSPMapper(customerService, companyService, developerService);
    }

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
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        Set<Customer> customerList = customerService.readAll();
        Set<Company> companyList = companyService.readAll();
        Set<Developer> developerList = developerService.readAll();
        req.setAttribute("customerList", customerList);
        req.setAttribute("companyList", companyList);
        req.setAttribute("developerList", developerList);
    }
}

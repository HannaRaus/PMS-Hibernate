package ua.goit.PMS.controller;

import org.hibernate.SessionFactory;
import ua.goit.PMS.config.HibernateDatabaseConnector;
import ua.goit.PMS.model.dao.CompanyDAO;
import ua.goit.PMS.model.dao.ProjectDAO;
import ua.goit.PMS.model.entity.Company;
import ua.goit.PMS.model.entity.Project;
import ua.goit.PMS.service.Service;
import ua.goit.PMS.service.mappers.CompanyFromJSPMapper;
import ua.goit.PMS.service.mappers.EntityFromJSPMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@WebServlet("/companies/*")
public class CompanyServlet extends AbstractServlet<Company> {
    private final SessionFactory sessionFactory = HibernateDatabaseConnector.getSessionFactory();
    private final Service<Project> projectService = new Service<>(new ProjectDAO(sessionFactory));

    @Override
    protected EntityFromJSPMapper<Company> initMapper() {
        return new CompanyFromJSPMapper(projectService);
    }

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
    protected void setAdditionalAttributesInForm(HttpServletRequest req) {
        Set<Project> projectList = projectService.readAll();
        req.setAttribute("projectList", projectList);
    }
}

package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.CompanyDAO;
import ua.goit.jdbc.dao.CustomerDAO;
import ua.goit.jdbc.dao.ProjectDAO;
import ua.goit.jdbc.dto.Company;
import ua.goit.jdbc.dto.Customer;
import ua.goit.jdbc.dto.Project;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/companies/*")
public class CompanyServlet extends HttpServlet {
    private Service<Company> companyService;
    private Service<Customer> customerService;
    private Service<Project> projectService;

    @Override
    public void init() throws ServletException {
        this.companyService = new Service<>(new CompanyDAO(DatabaseConnectionManager.getDataSource()));
        this.customerService = new Service<>(new CustomerDAO(DatabaseConnectionManager.getDataSource()));
        this.projectService = new Service<>(new ProjectDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String menu = req.getPathInfo();
        if (menu == null) {
            menu = req.getServletPath();
        }

        switch (menu) {
            case "/new" -> newCompanyForm(req, resp);
            case "/create" -> createCompany(req, resp);
            case "/details" -> readCompany(req, resp);
            case "/search" -> searchForm(req, resp);
            case "/edit" -> editCompanyForm(req, resp);
            case "/update" -> updateCompany(req, resp);
            case "/delete" -> deleteCompany(req, resp);
            default -> readCompanies(req, resp);
        }
    }

    private void newCompanyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("endpoint", "new");
        req.getRequestDispatcher("/view/companyForm.jsp").forward(req, resp);
    }

    private void createCompany(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Company company = readJSPForm(req, new Company());
        try {
            companyService.create(company);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        resp.sendRedirect("/companies");
    }

    private void readCompany(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        Company company = findById(id, companyService);
        req.setAttribute("company", company);
        req.getRequestDispatcher("/view/company.jsp").forward(req, resp);
    }


    private void searchForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int amountInDB = companyService.readAll().size();
        req.setAttribute("amountInDB", amountInDB);
        req.getRequestDispatcher("/view/search.jsp").forward(req, resp);
    }

    private void editCompanyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Company company = findById(id, companyService);
        List<Customer> customerList = customerService.readAll();
        List<Project> projectList = projectService.readAll();
        req.setAttribute("company", company);
        req.setAttribute("customerList", customerList);
        req.setAttribute("projectList", projectList);
        req.getRequestDispatcher("/view/companyForm.jsp").forward(req, resp);
    }

    private void updateCompany(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Company company = readJSPForm(req, findById(id, companyService));
        try {
            companyService.update(company);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        resp.sendRedirect("/companies");
    }

    private void deleteCompany(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            companyService.delete(id);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        resp.sendRedirect("/companies");

    }

    private void readCompanies(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Company> companies = companyService.readAll();
        req.setAttribute("companies", companies);
        try {
            req.getRequestDispatcher("/view/companies.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }

    private <T> T findById(long id, Service<T> service) {
        T entity = null;
        try {
            entity = service.findById(id);
        } catch (DAOException exception) {
            exception.printStackTrace();
        }
        return entity;
    }

    private Company readJSPForm(HttpServletRequest req, Company company) {
        company.setName(req.getParameter("name"));
        company.setHeadquarters(req.getParameter("headquarters"));
        if (company.getId() == 0) {
            String[] listOfCustomerId = req.getParameterValues("customers");
            List<Customer> customers = new ArrayList<>();
            if (listOfCustomerId != null && listOfCustomerId.length > 0) {
                customers = Arrays.stream(listOfCustomerId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(cus -> findById(cus, customerService))
                        .collect(Collectors.toList());
            }
            company.setCustomers(customers);
            String[] listOfProjectId = req.getParameterValues("projects");
            List<Project> projects = new ArrayList<>();
            if (listOfProjectId != null && listOfProjectId.length > 0) {
                projects = Arrays.stream(listOfProjectId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, projectService))
                        .collect(Collectors.toList());
            }
            company.setProjects(projects);
        }
        return company;
    }
}

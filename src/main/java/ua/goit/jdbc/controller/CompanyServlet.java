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
import java.util.List;

@WebServlet("/companies/*")
public class CompanyServlet extends HttpServlet {
    private Service<Company> service;
    private Service<Customer> customerService;
    private Service<Project> projectService;

    @Override
    public void init() throws ServletException {
        this.service = new Service<>(new CompanyDAO(DatabaseConnectionManager.getDataSource()));
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
            case "/edit" -> editCompanyForm(req, resp);
            case "/update" -> updateCompany(req, resp);
            case "/delete" -> deleteCompany(req, resp);
            default -> readCompanies(req, resp);
        }
    }

    private void newCompanyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/newCompanyForm.jsp").forward(req, resp);
    }

    private void createCompany(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String headquarters = req.getParameter("headquarters");
        Company company = new Company(name, headquarters);
        try {
            service.create(company);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        resp.sendRedirect("/companies");
    }

    private void readCompany(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            Company company = service.findById(id);
            req.setAttribute("company", company);
            req.getRequestDispatcher("/view/company.jsp").forward(req, resp);
        } catch (DAOException | ServletException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }

    private void editCompanyForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> customers = customerService.readAll();
        List<Project> projects = projectService.readAll();
        req.setAttribute("customers", customers);
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/view/editCompanyForm.jsp").forward(req, resp);
    }

    private void updateCompany(HttpServletRequest req, HttpServletResponse resp) {

    }

    private void deleteCompany(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            service.delete(id);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        resp.sendRedirect("/companies");

    }

    private void readCompanies(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Company> companies = service.readAll();
        req.setAttribute("companies", companies);
        try {
            req.getRequestDispatcher("/view/companies.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }

}

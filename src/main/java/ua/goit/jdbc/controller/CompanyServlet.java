package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.CompanyDAO;
import ua.goit.jdbc.dto.Company;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/companies/findById")
public class CompanyServlet extends HttpServlet {
    private Service<Company> service;

    @Override
    public void init() throws ServletException {
        this.service = new Service<>(new CompanyDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            Company company = service.findById(id);
            req.setAttribute("company", company);
            req.getRequestDispatcher("/view/company.jsp").forward(req, resp);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}

package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.DeveloperDAO;
import ua.goit.jdbc.dto.Developer;
import ua.goit.jdbc.dto.Project;
import ua.goit.jdbc.dto.Sex;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/developers")
public class DevelopersServlet extends HttpServlet {
    private Service<Developer> service;

    @Override
    public void init() throws ServletException {
        this.service = new Service<>(new DeveloperDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Developer> developers = service.readAll();
        req.setAttribute("developers", developers);
        try {
            req.getRequestDispatcher("/view/developers.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        Sex gender = Sex.valueOf(req.getParameter("sex").toUpperCase());
        double salary = Double.parseDouble(req.getParameter("salary"));
        Developer developer = new Developer(firstName, lastName, gender, salary);
        try {
            service.create(developer);
            req.getRequestDispatcher("/view/created.jsp").forward(req, resp);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}

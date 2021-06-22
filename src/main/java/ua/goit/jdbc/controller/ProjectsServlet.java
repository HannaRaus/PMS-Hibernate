package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.ProjectDAO;
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

@WebServlet("/projects")
public class ProjectsServlet extends HttpServlet {
    private Service<Project> service;

    @Override
    public void init() throws ServletException {
        this.service = new Service<>(new ProjectDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Project> projects = service.readAll();
        req.setAttribute("projects", projects);
        try {
            req.getRequestDispatcher("/view/projects.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        double cost = Double.parseDouble(req.getParameter("cost"));
        Project project = new Project(name, description, cost);
        try {
            service.create(project);
            req.getRequestDispatcher("/view/created.jsp").forward(req, resp);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}

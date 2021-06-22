package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.ProjectDAO;
import ua.goit.jdbc.dto.Project;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/projects/findById")
public class ProjectServlet extends HttpServlet {
    private Service<Project> service;

    @Override
    public void init() throws ServletException {
        this.service = new Service<>(new ProjectDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            Project project = service.findById(id);
            req.setAttribute("project", project);
            req.getRequestDispatcher("/view/project.jsp").forward(req, resp);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}

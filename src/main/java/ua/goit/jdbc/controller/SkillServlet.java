package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.SkillDAO;
import ua.goit.jdbc.dto.Skill;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/skills/findById")
public class SkillServlet extends HttpServlet {
    private Service<Skill> service;

    @Override
    public void init() throws ServletException {
        this.service = new Service<>(new SkillDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            Skill skill = service.findById(id);
            req.setAttribute("skill", skill);
            req.getRequestDispatcher("/view/skill.jsp").forward(req, resp);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}

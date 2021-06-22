package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.SkillDAO;
import ua.goit.jdbc.dto.Skill;
import ua.goit.jdbc.service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/skills")
public class SkillsServlet extends HttpServlet {
    private Service<Skill> service;

    @Override
    public void init() throws ServletException {
        this.service = new Service<>(new SkillDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Skill> skills = service.readAll();
        req.setAttribute("skills", skills);
        try {
            req.getRequestDispatcher("/view/skills.jsp").forward(req, resp);
        } catch (ServletException | IOException ex) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}

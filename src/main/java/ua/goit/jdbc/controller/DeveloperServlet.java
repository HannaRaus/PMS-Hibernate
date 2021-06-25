package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
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

@WebServlet("/developers/*")
public class DeveloperServlet extends HttpServlet {
    private Service<Developer> developerService;
    private Service<Project> projectService;
    private Service<Skill> skillService;

    @Override
    public void init() throws ServletException {
        this.developerService = new Service<>(new DeveloperDAO(DatabaseConnectionManager.getDataSource()));
        this.projectService = new Service<>(new ProjectDAO(DatabaseConnectionManager.getDataSource()));
        this.skillService = new Service<>(new SkillDAO(DatabaseConnectionManager.getDataSource()));
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
            case "/new" -> newDeveloperForm(req, resp);
            case "/create" -> createDeveloper(req, resp);
            case "/details" -> readDeveloper(req, resp);
            case "/search" -> searchForm(req, resp);
            case "/edit" -> editDeveloperForm(req, resp);
            case "/update" -> updateDeveloper(req, resp);
            case "/delete" -> deleteDeveloper(req, resp);
            default -> readDevelopers(req, resp);
        }
    }

    private void newDeveloperForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("endpoint", "new");
        req.getRequestDispatcher("/view/developerForm.jsp").forward(req, resp);
    }

    private void createDeveloper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Developer developer = readJSPForm(req, new Developer());
        try {
            developerService.create(developer);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        resp.sendRedirect("/developers");
    }

    private void readDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        Developer developer = findById(id, developerService);
        req.setAttribute("developer", developer);
        req.getRequestDispatcher("/view/developer.jsp").forward(req, resp);
    }


    private void searchForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int amountInDB = developerService.readAll().size();
        req.setAttribute("amountInDB", amountInDB);
        req.getRequestDispatcher("/view/search.jsp").forward(req, resp);
    }

    private void editDeveloperForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Developer developer = findById(id, developerService);
        List<Project> projectList = projectService.readAll();
        List<Skill> skillList = skillService.readAll();
        req.setAttribute("developer", developer);
        req.setAttribute("projectList", projectList);
        req.setAttribute("skillList", skillList);
        req.getRequestDispatcher("/view/developerForm.jsp").forward(req, resp);
    }

    private void updateDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Developer developer = readJSPForm(req, findById(id, developerService));
        try {
            developerService.update(developer);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        resp.sendRedirect("/developers");
    }

    private void deleteDeveloper(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            developerService.delete(id);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
        resp.sendRedirect("/developers");

    }

    private void readDevelopers(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Developer> developers = developerService.readAll();
        req.setAttribute("developers", developers);
        try {
            req.getRequestDispatcher("/view/developers.jsp").forward(req, resp);
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

    private Developer readJSPForm(HttpServletRequest req, Developer developer) {
        developer.setFirstName(req.getParameter("firstName"));
        developer.setLastName(req.getParameter("lastName"));
        developer.setSex(Sex.valueOf(req.getParameter("sex")));
        developer.setSalary(Double.parseDouble(req.getParameter("salary")));
        if (developer.getId() == 0) {
            String[] listOfProjectId = req.getParameterValues("projects");
            List<Project> projects = new ArrayList<>();
            if (listOfProjectId != null && listOfProjectId.length > 0) {
                projects = Arrays.stream(listOfProjectId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, projectService))
                        .collect(Collectors.toList());
            }
            developer.setProjects(projects);
            List<Skill> skills = new ArrayList<>();
            String[] listOfSkillId = req.getParameterValues("skills");
            if (listOfSkillId != null && listOfSkillId.length > 0) {
                skills = Arrays.stream(listOfSkillId)
                        .mapToInt(Integer::parseInt)
                        .mapToObj(proj -> findById(proj, skillService))
                        .collect(Collectors.toList());
            }
            developer.setSkills(skills);
        }
        return developer;
    }
}

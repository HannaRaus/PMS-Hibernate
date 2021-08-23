package ua.goit.PMS.controller;

import ua.goit.PMS.exceptions.DAOException;
import ua.goit.PMS.service.Service;
import ua.goit.PMS.service.mappers.EntityFromJSPMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public abstract class AbstractServlet<T> extends HttpServlet {
    protected Service<T> service;
    private EntityFromJSPMapper<T> mapper;

    @Override
    public void init() throws ServletException {
        this.service = initService();
        this.mapper = initMapper();
    }

    protected abstract EntityFromJSPMapper<T> initMapper();

    protected abstract Service<T> initService();

    protected abstract String getServletPath();

    protected abstract String getEntitiesPage();

    protected abstract String getEntityPage();

    protected abstract String getFormPage();

    protected abstract void setAdditionalAttributesInForm(HttpServletRequest req);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String menu = req.getPathInfo();
        if (menu == null) {
            menu = req.getContextPath();
        }

        switch (menu) {
            case "/new" -> newEntityForm(req, resp);
            case "/create" -> createEntity(req, resp);
            case "/details" -> readEntity(req, resp);
            case "/search" -> searchForm(req, resp);
            case "/edit" -> editEntityForm(req, resp);
            case "/update" -> updateEntity(req, resp);
            case "/delete" -> deleteEntity(req, resp);
            default -> readEntities(req, resp);
        }
    }

    private void newEntityForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("endpoint", "new");
        req.getRequestDispatcher(getFormPage()).forward(req, resp);
    }

    private void createEntity(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        T entity = mapper.readJSPForm(req);
        try {
            service.create(entity);
        } catch (DAOException exception) {
            req.setAttribute("message", exception.getMessage());
            req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
        }
        resp.sendRedirect(getServletPath());
    }

    private void readEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        T entity = null;
        try {
            entity = service.findById(id);
        } catch (DAOException exception) {
            req.setAttribute("message", exception.getMessage());
            req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
        }
        req.setAttribute("entity", entity);
        req.getRequestDispatcher(getEntityPage()).forward(req, resp);
    }

    private void searchForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/search.jsp").forward(req, resp);
    }

    private void editEntityForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        T entity = findById(id, service);
        req.setAttribute("entity", entity);
        setAdditionalAttributesInForm(req);
        req.getRequestDispatcher(getFormPage()).forward(req, resp);
    }

    private void updateEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        T entity = mapper.readJSPForm(req, findById(id, service));
        try {
            service.update(entity);
        } catch (DAOException exception) {
            req.setAttribute("message", exception.getMessage());
            req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
        }
        resp.sendRedirect(getServletPath());
    }

    private void deleteEntity(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        try {
            service.delete(id);
        } catch (DAOException exception) {
            req.setAttribute("message", exception.getMessage());
            req.getRequestDispatcher("/view/error.jsp").forward(req, resp);
        }
        resp.sendRedirect(getServletPath());
    }

    private void readEntities(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Set<T> entities = service.readAll();
        req.setAttribute("entities", entities);
        req.getRequestDispatcher(getEntitiesPage()).forward(req, resp);
    }


    private <V> V findById(int id, Service<V> service) {
        V entity = null;
        try {
            entity = service.findById(id);
        } catch (DAOException exception) {
            exception.printStackTrace();
        }
        return entity;
    }
}

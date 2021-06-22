package ua.goit.jdbc.controller;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.CustomerDAO;
import ua.goit.jdbc.dto.Customer;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/customers/findById")
public class CustomerServlet extends HttpServlet {
    private Service<Customer> service;

    @Override
    public void init() throws ServletException {
        this.service = new Service<>(new CustomerDAO(DatabaseConnectionManager.getDataSource()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            Customer customer = service.findById(id);
            req.setAttribute("customer", customer);
            req.getRequestDispatcher("/view/customer.jsp").forward(req, resp);
        } catch (DAOException exception) {
            resp.sendRedirect(req.getContextPath() + "/error.jsp");
        }
    }
}

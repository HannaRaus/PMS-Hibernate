package ua.goit.jdbc.dao;

import ua.goit.jdbc.dto.Customer;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO extends AbstractDAO<Customer> {

    public CustomerDAO(DatabaseConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO customers (customer_id, customer_name, industry) " +
                "VALUES(?, ?, ?) RETURNING customer_id;";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE customers SET customer_name=?, industry=? " +
                "WHERE customer_id=? RETURNING customer_id;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT customer_id, customer_name, industry " +
                "FROM customers WHERE customer_id = ?;";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM customers ORDER BY customer_id;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM customers WHERE customer_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(customer_id) FROM customers;";
    }

    @Override
    protected void sendEntity(PreparedStatement statement, Customer customer) throws DAOException {
        try {
            if (customer.getId() == 0) {
                //CREATE
                customer.setId(getLastId() + 1);
                statement.setLong(1, customer.getId());
                statement.setString(2, customer.getName());
                statement.setString(3, customer.getIndustry());
            } else {
                //UPDATE
                statement.setString(1, customer.getName());
                statement.setString(2, customer.getIndustry());
                statement.setLong(3, customer.getId());
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    protected Customer getEntity(ResultSet resultSet) throws DAOException {
        Customer customer = new Customer();
        try {
            customer.setId(resultSet.getInt("customer_id"));
            customer.setName(resultSet.getString("customer_name"));
            customer.setIndustry(resultSet.getString("industry"));
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return customer;
    }


}

package ua.goit.jdbc.dao;

import ua.goit.jdbc.DTO.Customer;
import ua.goit.jdbc.config.DatabaseConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO extends AbstractDAO<Customer> {

    public CustomerDAO(DatabaseConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected String getCreateQuery(Customer object) {
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
    protected String getDeleteQuery() {
        return "DELETE FROM customers WHERE customer_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(customer_id) FROM customers;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, Integer id, Customer object) throws SQLException {
        if (id == null) {
            //CREATE
            object.setId(getLastId() + 1);
            statement.setInt(1, object.getId());
            statement.setString(2, object.getName());
            statement.setString(3, object.getIndustry());
        } else {
            //UPDATE
            statement.setString(1, object.getName());
            statement.setString(2, object.getIndustry());
            statement.setInt(3, id);
        }
    }

    @Override
    protected Customer convertToObject(ResultSet resultSet) throws SQLException {
        Customer customer = new Customer();
        customer.setId(resultSet.getInt("customer_id"));
        customer.setName(resultSet.getString("customer_name"));
        customer.setIndustry(resultSet.getString("industry"));
        return customer;
    }


}

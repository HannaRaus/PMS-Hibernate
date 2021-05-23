package ua.goit.jdbc.dao;

import ua.goit.jdbc.DTO.Developer;
import ua.goit.jdbc.DTO.Sex;
import ua.goit.jdbc.config.DatabaseConnectionManager;

import java.sql.*;

public class DeveloperDAO extends AbstractDAO<Developer> {

    public DeveloperDAO(DatabaseConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected String getCreateQuery(Developer object) {
        return "INSERT INTO developers (developer_id, first_name, last_name, sex, salary) " +
                "VALUES (?, ?, ?, CAST(? AS sex), ?) RETURNING developer_id;";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE developers SET first_name=?, last_name=?, sex=CAST(? AS sex), salary=? " +
                "WHERE developer_id=? RETURNING developer_id;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT developer_id, first_name, last_name, sex, salary " +
                "FROM developers WHERE developer_id = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM developers WHERE developer_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(developer_id) FROM developers;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, Integer id, Developer object) throws SQLException {
        if (id == null) {
            //CREATE
            object.setId(getLastId() + 1);
            statement.setInt(1, object.getId());
            statement.setString(2, object.getFirstName());
            statement.setString(3, object.getLastName());
            statement.setString(4, object.getSex().getName());
            statement.setInt(5, object.getSalary());
        } else {
            //UPDATE
            statement.setString(1, object.getFirstName());
            statement.setString(2, object.getLastName());
            statement.setString(3, object.getSex().getName());
            statement.setInt(4, object.getSalary());
            statement.setInt(5, id);
        }
    }

    @Override
    protected Developer convertToObject(ResultSet resultSet) throws SQLException {
        Developer developer = new Developer();
        developer.setId(resultSet.getInt("developer_id"));
        developer.setFirstName(resultSet.getString("first_name"));
        developer.setLastName(resultSet.getString("last_name"));
        developer.setSex(Sex.findByName(resultSet.getString("sex")));
        developer.setSalary(resultSet.getInt("salary"));
        return developer;
    }
}

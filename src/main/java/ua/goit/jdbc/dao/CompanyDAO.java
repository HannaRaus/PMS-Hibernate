package ua.goit.jdbc.dao;

import ua.goit.jdbc.DTO.Company;
import ua.goit.jdbc.config.DatabaseConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyDAO extends AbstractDAO<Company> {

    public CompanyDAO(DatabaseConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected String getCreateQuery(Company object) {
        return "INSERT INTO companies (company_id, company_name, headquarters) " +
                "VALUES(?, ?, ?) RETURNING company_id;";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE companies SET company_name=?, headquarters=? " +
                "WHERE company_id=? RETURNING company_id;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT company_id, company_name, headquarters " +
                "FROM companies WHERE company_id = ?;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM companies WHERE company_id=?;";
    }

    public String getLastIdQuery() {
        return "SELECT max(company_id) FROM companies;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, Integer id, Company object) throws SQLException {
        if (id == null) {
            //CREATE
            object.setId(getLastId() + 1);
            statement.setInt(1, object.getId());
            statement.setString(2, object.getName());
            statement.setString(3, object.getHeadquarters());
        } else {
            //UPDATE
            statement.setString(1, object.getName());
            statement.setString(2, object.getHeadquarters());
            statement.setInt(3, id);
        }
    }

    @Override
    protected Company convertToObject(ResultSet resultSet) throws SQLException {
        Company company = new Company();
        company.setId(resultSet.getInt("company_id"));
        company.setName(resultSet.getString("company_name"));
        company.setHeadquarters(resultSet.getString("headquarters"));
        return company;
    }
}

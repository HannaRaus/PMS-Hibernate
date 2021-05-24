package ua.goit.jdbc.dao;

import ua.goit.jdbc.dto.Company;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.exceptions.DAOException;

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
    protected String getSelectAllQuery() {
        return "SELECT * FROM companies ORDER BY company_id;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM companies WHERE company_id=?;";
    }

    public String getLastIdQuery() {
        return "SELECT max(company_id) FROM companies;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, long id, Company object) throws DAOException {
        try {
            if (id == 0) {
                //CREATE
                object.setId(getLastId() + 1);
                statement.setLong(1, object.getId());
                statement.setString(2, object.getName());
                statement.setString(3, object.getHeadquarters());
            } else {
                //UPDATE
                statement.setString(1, object.getName());
                statement.setString(2, object.getHeadquarters());
                statement.setLong(3, id);
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    protected Company convertToObject(ResultSet resultSet) throws DAOException {
        Company company = new Company();
        try {
            company.setId(resultSet.getInt("company_id"));
            company.setName(resultSet.getString("company_name"));
            company.setHeadquarters(resultSet.getString("headquarters"));
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return company;
    }
}

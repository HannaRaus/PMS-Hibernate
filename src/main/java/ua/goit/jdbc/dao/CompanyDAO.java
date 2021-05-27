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
    protected String getCreateQuery() {
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
    protected void sendEntity(PreparedStatement statement, Company company) throws DAOException {
        try {
            if (company.getId() == 0) {
                //CREATE
                company.setId(getLastId() + 1);
                statement.setLong(1, company.getId());
                statement.setString(2, company.getName());
                statement.setString(3, company.getHeadquarters());
            } else {
                //UPDATE
                statement.setString(1, company.getName());
                statement.setString(2, company.getHeadquarters());
                statement.setLong(3, company.getId());
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    protected Company getEntity(ResultSet resultSet) throws DAOException {
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

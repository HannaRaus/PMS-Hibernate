package ua.goit.servlets.dao;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.servlets.dto.Company;
import ua.goit.servlets.dto.Project;
import ua.goit.servlets.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyDAO extends AbstractDAO<Company> {

    public CompanyDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO companies (company_id, company_name, headquarters) " +
                "VALUES(?, ?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE companies SET company_name=?, headquarters=? " +
                "WHERE company_id=?;";
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
                if (company.getProjects() != null) {
                    sendProjects(company);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    private void sendProjects(Company company) throws DAOException {
        String query = "INSERT INTO company_projects (company_id, project_id) " +
                "VALUES (?, ?);";
        List<Project> projectsInDB = receiveProjects(company);
        List<Project> newProjects = company.getProjects();

        deleteProjects(projectsInDB, newProjects);

        if (areNotEquals(projectsInDB, newProjects)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                newProjects.removeIf(projectsInDB::contains);
                for (Project project : newProjects) {
                    statement.setLong(1, company.getId());
                    statement.setLong(2, project.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private void deleteProjects(List<Project> projectsInDB, List<Project> newProjects) throws DAOException {
        String query = "DELETE FROM company_projects WHERE project_id=?;";

        List<Project> projectsToDelete = projectsInDB.stream()
                .filter(info -> !newProjects.contains(info))
                .collect(Collectors.toList());

        if (projectsToDelete.size() > 0) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Project project : projectsToDelete) {
                    statement.setLong(1, project.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Project> receiveProjects(Company company) throws DAOException {
        String query = String.format("SELECT p.project_id, p.project_name, p.project_description, p.cost, p.create_date " +
                "FROM projects p INNER JOIN company_projects cp ON cp.project_id = p.project_id " +
                "WHERE cp.company_id = %s ORDER by p.project_id;", company.getId());
        return new ProjectDAO(getConnectionManager()).getListByQuery(query, false);

    }

    @Override
    protected Company getEntity(ResultSet resultSet, boolean getRelatedEntity) throws DAOException {
        Company company = new Company();
        try {
            company.setId(resultSet.getInt("company_id"));
            company.setName(resultSet.getString("company_name"));
            company.setHeadquarters(resultSet.getString("headquarters"));
            if (getRelatedEntity) {
                company.setProjects(receiveProjects(company));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return company;
    }
}

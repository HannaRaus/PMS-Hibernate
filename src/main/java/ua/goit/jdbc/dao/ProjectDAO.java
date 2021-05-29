package ua.goit.jdbc.dao;

import ua.goit.jdbc.dto.Company;
import ua.goit.jdbc.dto.Customer;
import ua.goit.jdbc.dto.Developer;
import ua.goit.jdbc.dto.Project;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProjectDAO extends AbstractDAO<Project> {

    public ProjectDAO(DatabaseConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO projects (project_id, project_name, project_description, cost) " +
                "VALUES(?, ?, ?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE projects SET project_name=?, project_description=? , cost=?" +
                "WHERE project_id=?;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT project_id, project_name, project_description, cost " +
                "FROM projects WHERE project_id = ?;";
    }

    @Override
    protected String getSelectAllQuery() {
        return "SELECT * FROM projects ORDER BY project_id;";
    }

    @Override
    protected String getDeleteQuery() {
        return "DELETE FROM projects WHERE project_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(project_id) FROM projects;";
    }

    @Override
    protected void sendEntity(PreparedStatement statement, Project project) throws DAOException {
        try {
            if ( project.getId()== 0) {
                //CREATE
                project.setId(getLastId() + 1);
                statement.setLong(1, project.getId());
                statement.setString(2, project.getName());
                statement.setString(3, project.getDescription());
                statement.setDouble(4, project.getCost());
            } else {
                //UPDATE
                statement.setString(1, project.getName());
                statement.setString(2, project.getDescription());
                statement.setDouble(3, project.getCost());
                statement.setLong(4, project.getId());
                if (project.getDevelopers() != null) {
                    sendProjectDevelopers(project);
                }
                if (project.getCompanies() != null && project.getCustomers() != null) {
                    sendProjectCompaniesAndCustomers(project);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    @Override
    protected Project getEntity(ResultSet resultSet) throws DAOException {
        Project project = new Project();
        try {
            project.setId(resultSet.getInt("project_id"));
            project.setName(resultSet.getString("project_name"));
            project.setDescription(resultSet.getString("project_description"));
            project.setCost(resultSet.getDouble("cost"));
            project.setDevelopers(receiveProjectDevelopers(project));
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return project;
    }

    private void sendProjectDevelopers(Project project) throws DAOException {
        String query = "INSERT INTO project_developers (project_id, developer_id)" +
                "VALUES (?, ?);";
        List<Developer> developersInDB = receiveProjectDevelopers(project);
        List<Developer> newDevelopers = project.getDevelopers();
        if (compareInfoFromDB(developersInDB, newDevelopers)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Developer dev : newDevelopers) {
                    statement.setLong(1, project.getId());
                    statement.setLong(2, dev.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Developer> receiveProjectDevelopers(Project project) throws DAOException {
        String query = String.format("SELECT d.developer_id, d.first_name, d.last_name, d.sex, d.salary" +
                "FROM developers d INNER JOIN project_developers pd ON pd.developer_d = d.developer_id " +
                "WHERE project_id = %s ORDER by d.developer_id;", project.getId());
        return new DeveloperDAO(getConnectionManager()).getListByQuery(query);
    }


    private void sendProjectCompaniesAndCustomers(Project project) throws DAOException {
        String query = "INSERT INTO customers_companies (project_id, company_id, customer_id)" +
                "VALUES (?, ?, ?);";
        List<Company> companiesInDB = receiveProjectCompanies(project);
        List<Company> newCompanies = project.getCompanies();
        List<Customer> customersInDB = receiveProjectCustomers(project);
        List<Customer> newCustomers = project.getCustomers();
        if (compareInfoFromDB(companiesInDB, newCompanies) && compareInfoFromDB(customersInDB,newCustomers)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Company company : newCompanies) {
                    for (Customer customer : newCustomers) {
                        statement.setLong(1, project.getId());
                        statement.setLong(2, company.getId());
                        statement.setLong(3, customer.getId());
                        statement.execute();
                    }
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Company> receiveProjectCompanies(Project project) throws DAOException {
        String query = String.format("SELECT c.company_id, c.company_name, c.headquarters" +
                "FROM companies c INNER JOIN customers_companies cc ON cc.company_id = c.company_id " +
                "WHERE project_id = %s ORDER by c.company_id;", project.getId());
        return new CompanyDAO(getConnectionManager()).getListByQuery(query);
    }

    private List<Customer> receiveProjectCustomers(Project project) throws DAOException {
        String query = String.format("SELECT c.customer_id, c.customer_name, c.industry" +
                "FROM customers c INNER JOIN customers_companies cc ON cc.customer_id = c.customer_id " +
                "WHERE project_id = %s ORDER by c.customer_id;", project.getId());
        return new CustomerDAO(getConnectionManager()).getListByQuery(query);
    }

}

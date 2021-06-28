package ua.goit.servlets.dao;

import com.zaxxer.hikari.HikariDataSource;
import ua.goit.servlets.dto.*;
import ua.goit.servlets.exceptions.DAOException;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectDAO extends AbstractDAO<Project> {

    public ProjectDAO(HikariDataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO projects (project_id, project_name, project_description, cost, create_date) " +
                "VALUES(?, ?, ?, ?, ?);";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE projects SET project_name=?, project_description=? , cost=?, create_date=? " +
                "WHERE project_id=?;";
    }

    @Override
    protected String getSelectByIdQuery() {
        return "SELECT project_id, project_name, project_description, cost, create_date " +
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
            if (project.getId() == 0) {
                //CREATE
                project.setId(getLastId() + 1);
                statement.setLong(1, project.getId());
                statement.setString(2, project.getName());
                statement.setString(3, project.getDescription());
                statement.setDouble(4, project.getCost());
                statement.setDate(5, Date.valueOf(LocalDate.now()));
            } else {
                //UPDATE
                statement.setString(1, project.getName());
                statement.setString(2, project.getDescription());
                statement.setDouble(3, project.getCost());
                statement.setDate(4, Date.valueOf(project.getDate()));
                statement.setLong(5, project.getId());
                if (project.getDevelopers() != null) {
                    sendDevelopers(project);
                }
                if (project.getCustomers() != null) {
                    sendCustomers(project);
                }
                if (project.getCompanies() != null) {
                    sendCompanies(project);
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
    }

    private void sendDevelopers(Project project) throws DAOException {
        String query = "INSERT INTO project_developers (project_id, developer_id) " +
                "VALUES (?, ?);";
        List<Developer> developersInDB = receiveDevelopers(project);
        List<Developer> newDevelopers = project.getDevelopers();

        deleteDevelopers(developersInDB, newDevelopers);

        if (areNotEquals(developersInDB, newDevelopers)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                newDevelopers.removeIf(developersInDB::contains);
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

    private void deleteDevelopers(List<Developer> developersInDB, List<Developer> newDevelopers) throws DAOException {
        String query = "DELETE FROM project_developers WHERE developer_id=?;";

        List<Developer> developersToDelete = developersInDB.stream()
                .filter(info -> !newDevelopers.contains(info))
                .collect(Collectors.toList());

        if (developersToDelete.size() > 0) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Developer developer : developersToDelete) {
                    statement.setLong(1, developer.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Developer> receiveDevelopers(Project project) throws DAOException {
        String query = String.format("SELECT d.developer_id, d.first_name, d.last_name, d.sex, d.salary " +
                "FROM developers d INNER JOIN project_developers pd ON pd.developer_id = d.developer_id " +
                "WHERE pd.project_id = %s ORDER by d.developer_id;", project.getId());
        return new DeveloperDAO(getConnectionManager()).getListByQuery(query, false);
    }


    private void sendCompanies(Project project) throws DAOException {
        String query = "INSERT INTO company_projects (project_id, company_id) " +
                "VALUES (?, ?);";
        List<Company> companiesInDB = receiveCompanies(project);
        List<Company> newCompanies = project.getCompanies();

        deleteCompanies(companiesInDB, newCompanies);

        if (areNotEquals(companiesInDB, newCompanies)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                newCompanies.removeIf(companiesInDB::contains);
                for (Company company : newCompanies) {
                    statement.setLong(1, project.getId());
                    statement.setLong(2, company.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private void deleteCompanies(List<Company> companiesInDB, List<Company> newCompanies) throws DAOException {
        String query = "DELETE FROM company_projects WHERE company_id=?;";

        List<Company> companiesToDelete = companiesInDB.stream()
                .filter(info -> !newCompanies.contains(info))
                .collect(Collectors.toList());

        if (companiesToDelete.size() > 0) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Company company : companiesToDelete) {
                    statement.setLong(1, company.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private void sendCustomers(Project project) throws DAOException {
        String query = "INSERT INTO customer_projects (project_id, customer_id) " +
                "VALUES (?, ?);";
        List<Customer> customersInDB = receiveCustomers(project);
        List<Customer> newCustomers = project.getCustomers();

        deleteCustomers(customersInDB, newCustomers);

        if (areNotEquals(customersInDB, newCustomers)) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                newCustomers.removeIf(customersInDB::contains);
                for (Customer customer : newCustomers) {
                    statement.setLong(1, project.getId());
                    statement.setLong(2, customer.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private void deleteCustomers(List<Customer> customersInDB, List<Customer> newCustomers) throws DAOException {
        String query = "DELETE FROM customer_projects WHERE customer_id=?;";

        List<Customer> customersToDelete = customersInDB.stream()
                .filter(info -> !newCustomers.contains(info))
                .collect(Collectors.toList());

        if (customersToDelete.size() > 0) {
            try (Connection connection = getConnectionManager().getConnection();
                 PreparedStatement statement = connection.prepareStatement(query)) {
                for (Customer customer : customersToDelete) {
                    statement.setLong(1, customer.getId());
                    statement.execute();
                }
            } catch (SQLException ex) {
                throw new DAOException(ex.getMessage());
            }
        }
    }

    private List<Company> receiveCompanies(Project project) throws DAOException {
        String query = String.format("SELECT c.company_id, c.company_name, c.headquarters " +
                "FROM companies c INNER JOIN company_projects cp ON cp.company_id = c.company_id " +
                "WHERE cp.project_id = %s ORDER by c.company_id;", project.getId());
        return new CompanyDAO(getConnectionManager()).getListByQuery(query, false);
    }

    private List<Customer> receiveCustomers(Project project) throws DAOException {
        String query = String.format("SELECT c.customer_id, c.customer_name, c.industry " +
                "FROM customers c INNER JOIN customer_projects cp ON cp.customer_id = c.customer_id " +
                "WHERE cp.project_id = %s ORDER by c.customer_id;", project.getId());
        return new CustomerDAO(getConnectionManager()).getListByQuery(query, false);
    }

    @Override
    protected Project getEntity(ResultSet resultSet, boolean getRelatedEntity) throws DAOException {
        Project project = new Project();
        try {
            project.setId(resultSet.getInt("project_id"));
            project.setName(resultSet.getString("project_name"));
            project.setDescription(resultSet.getString("project_description"));
            project.setCost(resultSet.getDouble("cost"));
            project.setDate(resultSet.getObject("create_date", LocalDate.class));
            if (getRelatedEntity) {
                project.setDevelopers(receiveDevelopers(project));
                project.setCompanies(receiveCompanies(project));
                project.setCustomers(receiveCustomers(project));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return project;
    }

}

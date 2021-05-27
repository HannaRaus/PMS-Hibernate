package ua.goit.jdbc.dao;

import ua.goit.jdbc.dto.Project;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDAO extends AbstractDAO<Project> {

    public ProjectDAO(DatabaseConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected String getCreateQuery() {
        return "INSERT INTO projects (project_id, project_name, project_description, cost) " +
                "VALUES(?, ?, ?, ?) RETURNING project_id;";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE projects SET project_name=?, project_description=? , cost=?" +
                "WHERE project_id=? RETURNING project_id;";
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
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage(), ex);
        }
        return project;
    }
}

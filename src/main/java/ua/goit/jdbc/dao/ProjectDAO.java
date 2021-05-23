package ua.goit.jdbc.dao;

import ua.goit.jdbc.DTO.Project;
import ua.goit.jdbc.config.DatabaseConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectDAO extends AbstractDAO<Project> {

    public ProjectDAO(DatabaseConnectionManager connectionManager) {
        super(connectionManager);
    }

    @Override
    protected String getCreateQuery(Project object) {
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
    protected String getDeleteQuery() {
        return "DELETE FROM projects WHERE project_id=?;";
    }

    @Override
    protected String getLastIdQuery() {
        return "SELECT max(project_id) FROM projects;";
    }

    @Override
    protected void setObjectStatement(PreparedStatement statement, Integer id, Project object) throws SQLException {
        if (id == null) {
            //CREATE
            object.setId(getLastId() + 1);
            statement.setInt(1, object.getId());
            statement.setString(2, object.getName());
            statement.setString(3, object.getDescription());
            statement.setInt(4, object.getCost());

        } else {
            //UPDATE
            statement.setString(1, object.getName());
            statement.setString(2, object.getDescription());
            statement.setInt(3, object.getCost());
            statement.setInt(4, id);
        }
    }

    @Override
    protected Project convertToObject(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getInt("project_id"));
        project.setName(resultSet.getString("project_name"));
        project.setDescription(resultSet.getString("project_description"));
        project.setCost(resultSet.getInt("cost"));
        return project;
    }
}

package ua.goit.jdbc.dao;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.exceptions.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractDAO<T> implements GenericDAO<T> {
    private final DatabaseConnectionManager connectionManager;

    protected AbstractDAO(DatabaseConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    protected abstract String getCreateQuery(T object);

    protected abstract String getUpdateQuery();

    protected abstract String getSelectByIdQuery();

    protected abstract String getDeleteQuery();

    protected abstract String getLastIdQuery();

    protected abstract void setObjectStatement(PreparedStatement statement, Integer id, T object) throws SQLException;

    protected abstract T convertToObject(ResultSet resultSet) throws SQLException;

    @Override
    public T create(T entity) throws DAOException {
        String createQuery = getCreateQuery(entity);
        T createdEntity = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(createQuery)) {
            setObjectStatement(statement, null, entity);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                createdEntity = read(resultSet.getInt(1));
            } else {
                throw new DAOException("Problems with creating the object");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return createdEntity;
    }

    @Override
    public T read(Integer id) throws DAOException {
        T entity = null;
        String selectByIdQuery = getSelectByIdQuery();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(selectByIdQuery)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = convertToObject(resultSet);
            } else {
                throw new DAOException("There is no object with such ID!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return entity;
    }

    @Override
    public T update(Integer id, T entity) throws DAOException {
        String updateQuery = getUpdateQuery();
        T updated = null;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(updateQuery)) {
            setObjectStatement(statement, id, entity);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                updated = read(resultSet.getInt(1));
            } else {
                throw new DAOException("There are problems with updating the object");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return updated;
    }

    @Override
    public void delete(Integer id) {
        String deleteQuery = getDeleteQuery();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Integer getLastId() {
        String lastIdQuery = getLastIdQuery();
        int result = 0;
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(lastIdQuery)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result = resultSet.getInt("max");
            }
            return result;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}

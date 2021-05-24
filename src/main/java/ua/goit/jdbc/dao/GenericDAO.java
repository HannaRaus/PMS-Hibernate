package ua.goit.jdbc.dao;

import ua.goit.jdbc.exceptions.DAOException;

public interface GenericDAO<T> {

    T create(T entity) throws DAOException;

    T read(long id) throws DAOException;

    T update(long id, T entity) throws DAOException;

    void delete(long id);

}

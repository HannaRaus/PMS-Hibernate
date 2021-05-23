package ua.goit.jdbc.dao;

import ua.goit.jdbc.exceptions.DAOException;

public interface GenericDAO<T> {

    T create(T entity) throws DAOException;

    T read(Integer id) throws DAOException;

    T update(Integer id, T entity) throws DAOException;

    void delete(Integer id);

}

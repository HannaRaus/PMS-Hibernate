package ua.goit.PMS.model.dao;

import ua.goit.PMS.exceptions.DAOException;

import java.util.Set;

public interface GenericDAO<T> {

    void create(T entity) throws DAOException;

    T read(int id) throws DAOException;

    void update(T entity) throws DAOException;

    void delete(int id) throws DAOException;

    Set<T> readAll();

}

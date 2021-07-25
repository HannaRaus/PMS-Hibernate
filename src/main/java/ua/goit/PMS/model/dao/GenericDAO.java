package ua.goit.PMS.model.dao;

import java.util.Set;

public interface GenericDAO<T> {

    void create(T entity);

    T read(int id);

    void update(T entity);

    void delete(int id);

    Set<T> readAll();

}

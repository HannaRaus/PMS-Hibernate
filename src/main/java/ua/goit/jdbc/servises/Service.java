package ua.goit.jdbc.servises;

import ua.goit.jdbc.dao.GenericDAO;
import ua.goit.jdbc.exceptions.DAOException;

import java.util.List;

public class Service<T> {
    private final GenericDAO<T> repository;

    public Service(GenericDAO<T> repository) {
        this.repository = repository;
    }

    public T create(T company) {
        T created = null;
        try {
            created = repository.create(company);
        } catch (DAOException ex) {
            ex.getStackTrace();
        }
        return created;
    }

    public T update(T entity) {
        T updated = null;
        try {
            updated = repository.update(entity);
        } catch (DAOException ex) {
            ex.getStackTrace();
        }
        return updated;
    }

    public void delete(long id) {
        try {
            repository.read(id);
            repository.delete(id);
        } catch (DAOException ex) {
            ex.getStackTrace();
        }
    }

    public T findById(long id) {
        T founded = null;
        try {
            founded = repository.read(id);
        } catch (DAOException ex) {
            ex.printStackTrace();
        }
        return founded;
    }

    public List<T> readAll() {
        List<T> entities = null;
        try {
            entities = repository.readAll();
        } catch (DAOException ex) {
            ex.printStackTrace();
        }
        return entities;
    }

}

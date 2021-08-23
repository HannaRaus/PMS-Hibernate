package ua.goit.PMS.model.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.goit.PMS.exceptions.DAOException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class AbstractDAO<T> implements GenericDAO<T> {
    private final static Logger LOG = LoggerFactory.getLogger(AbstractDAO.class);

    private final SessionFactory sessionFactory;
    private final Class<T> entityClass;


    protected AbstractDAO(SessionFactory sessionFactory, Class<T> entityClass) {
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    protected abstract String getDeleteQuery();

    @Override
    public void create(T entity) throws DAOException {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception ex) {
            LOG.error("create. ", ex);
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            throw new DAOException("Problems with creating an object");
        }
    }

    @Override
    public T read(int id) throws DAOException {
        T entity = null;
        try (Session session = sessionFactory.openSession()) {
            entity = session.get(entityClass, id);
        } catch (Exception ex) {
            LOG.error("read. ", ex);
        }
        if (Objects.isNull(entity)) {
            throw new DAOException("There is no object with such id");
        }
        return entity;
    }

    @Override
    public void update(T entity) throws DAOException {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception ex) {
            LOG.error("update. ", ex);
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            throw new DAOException("Problems with updating an object");
        }
    }

    @Override
    public void delete(int id) throws DAOException {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createQuery(getDeleteQuery()).setParameter("id", id).executeUpdate();
            transaction.commit();
        } catch (Exception ex) {
            LOG.error("delete. ", ex);
            if (Objects.nonNull(transaction)) {
                transaction.rollback();
            }
            throw new DAOException("Problems with deleting an object");
        }
    }

    @Override
    public Set<T> readAll() {
        Set<T> entities = null;
        try (Session session = sessionFactory.openSession()) {
            entities = new HashSet<>(session.createQuery("FROM " + entityClass.getName(), entityClass).list());
        } catch (Exception ex) {
            LOG.error("readAll. ", ex);
        }
        return entities;
    }
}

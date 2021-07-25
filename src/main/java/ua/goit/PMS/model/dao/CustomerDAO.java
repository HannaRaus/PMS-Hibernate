package ua.goit.PMS.model.dao;

import org.hibernate.SessionFactory;
import ua.goit.PMS.model.entity.Customer;

public class CustomerDAO extends AbstractDAO<Customer> {

    public CustomerDAO(SessionFactory sessionFactory) {
        super(sessionFactory, Customer.class);
    }

}

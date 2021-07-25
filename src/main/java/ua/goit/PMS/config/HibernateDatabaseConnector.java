package ua.goit.PMS.config;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateDatabaseConnector {
    private static SessionFactory sessionFactory;
    private final static Logger LOG = LoggerFactory.getLogger(HibernateDatabaseConnector.class);

    public static synchronized void init() {
        try {
            MetadataSources source = new MetadataSources(new StandardServiceRegistryBuilder().configure().build());
            final Metadata metadata = source.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            LOG.error("init. ", e);
        }
    }

    public static synchronized void destroy() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}


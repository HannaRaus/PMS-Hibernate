package ua.goit.PMS.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class AppInit implements ServletContextListener {

    private final static Logger LOG = LoggerFactory.getLogger(AppInit.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Database init finished");
        LOG.debug("Database init finished");
        System.out.println("Init hibernate");
        HibernateDatabaseConnector.init();
        System.out.println("Hibernate initialisation finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Hibernate destroy");
        HibernateDatabaseConnector.destroy();
        System.out.println("Hibernate destroy finished");
    }
}


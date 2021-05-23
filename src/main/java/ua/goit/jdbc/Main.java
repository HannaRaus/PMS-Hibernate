package ua.goit.jdbc;

import ua.goit.jdbc.DTO.Developer;
import ua.goit.jdbc.DTO.Sex;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.DeveloperDAO;
import ua.goit.jdbc.dao.GenericDAO;
import ua.goit.jdbc.servises.Service;
import ua.goit.jdbc.util.PropertiesLoader;

public class Main {
    public static void main(String[] args) {
        DatabaseConnectionManager connectionManager = getConnectionManager();

        GenericDAO<Developer> repository = new DeveloperDAO(connectionManager);
        Service<Developer> service = new Service<>(repository);

        //CREATE
        Developer created = new Developer("Hanna", "Raus", Sex.FEMALE, 1000);
        System.out.println("Created " + service.create(created));

        //READ
        System.out.println("Find by id " + service.findById(5));

        //UPDATE
        Developer updated = new Developer("Hanna", "Raus", Sex.MALE, 2000);
        System.out.println("Updated " + service.update(51, updated));

        //DELETE
        service.delete(51);
        System.out.println("Find by id " + service.findById(51));

    }

    private static DatabaseConnectionManager getConnectionManager() {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadPropertiesFile("application.properties");

        return new DatabaseConnectionManager(propertiesLoader.getProperty("host"),
                propertiesLoader.getProperty("database.name"),
                propertiesLoader.getProperty("username"),
                propertiesLoader.getProperty("password"));
    }

}

package ua.goit.jdbc;

import ua.goit.jdbc.controller.MainController;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.service.util.PropertiesLoader;
import ua.goit.jdbc.view.Console;
import ua.goit.jdbc.view.View;


public class Main {
    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        propertiesLoader.loadPropertiesFile("application.properties");
        DatabaseConnectionManager connectionManager = new DatabaseConnectionManager(propertiesLoader);

        View view = new Console(System.in, System.out);
        MainController controller = new MainController(view, connectionManager);

        controller.run();

    }

}

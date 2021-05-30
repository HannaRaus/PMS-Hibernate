package ua.goit.jdbc.controller;

import ua.goit.jdbc.command.*;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.dao.*;
import ua.goit.jdbc.dto.*;
import ua.goit.jdbc.service.Service;
import ua.goit.jdbc.view.View;

import java.util.*;

public class MainController {
    private final View view;
    private final List<Command> commands;
//    private Service<Customer> customerService;
//    private Service<Company> companyService;
//    private Service<Project> projectService;
//    private Service<Developer> developerService;
//    private Service<Skill> skillService;

    public MainController(View view, DatabaseConnectionManager connectionManager) {
        this.view = view;
//        initServices(connectionManager);
        this.commands = new ArrayList<>(Arrays.asList(new Help(view), new Read(view, connectionManager),
                new Delete(view, connectionManager)));
    }

//    private void initServices(DatabaseConnectionManager connectionManager) {
//        customerService = new Service<>(new CustomerDAO(connectionManager));
//        companyService = new Service<>(new CompanyDAO(connectionManager));
//        projectService = new Service<>(new ProjectDAO(connectionManager));
//        developerService = new Service<>(new DeveloperDAO(connectionManager));
//        skillService = new Service<>(new SkillDAO(connectionManager));
//    }

    public void run() {
        view.write("Welcome to the Project management system!");
        doCommand();
    }

    private void doCommand() {
        boolean running = true;
        while (running) {
            view.write("Please enter a command or help to retrieve command list");
            String inputCommand = view.read();
            for (Command command : commands) {
                if (command.canProcess(inputCommand)) {
                    command.process();
                    break;
                } else if (inputCommand.equalsIgnoreCase("exit")) {
                    view.write("Good Bye!");
                    running = false;
                    break;
                }
            }
        }
    }
}

package ua.goit.jdbc.controller;

import ua.goit.jdbc.command.Command;
import ua.goit.jdbc.command.Help;
import ua.goit.jdbc.command.Read;
import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainController {
    private final View view;
    private final List<Command> commands;

    public MainController(View view, DatabaseConnectionManager connectionManager) {
        this.view = view;
        this.commands = new ArrayList<>(Arrays.asList(new Help(view), new Read(view, connectionManager)));
    }

    public void run() {
        view.write("Welcome to the Project management system!");
        doCommand();
    }

    private void doCommand() {
        boolean isNotExit = true;
        while (isNotExit) {
            view.write("Please enter a command or help to retrieve command list");
            String inputCommand = view.read();
            for (Command command : commands) {
                if (command.canProcess(inputCommand)) {
                    command.process();
                    break;
                } else if (inputCommand.equalsIgnoreCase("exit")) {
                    view.write("Good Bye!");
                    isNotExit = false;
                    break;
                } else if (inputCommand.equalsIgnoreCase("return")) {
                    break;
                }
            }
        }
    }
}

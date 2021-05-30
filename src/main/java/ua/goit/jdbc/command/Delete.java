package ua.goit.jdbc.command;

import ua.goit.jdbc.config.DatabaseConnectionManager;
import ua.goit.jdbc.exceptions.DAOException;
import ua.goit.jdbc.service.Service;
import ua.goit.jdbc.view.View;

public class Delete extends Commands implements Command {
    private final View view;

    public Delete(View view, DatabaseConnectionManager connectionManager) {
        super(view, connectionManager);
        this.view = view;
    }

    @Override
    public String commandName() {
        return "delete";
    }

    @Override
    public void process() {
        boolean running = true;
        while (running) {
            view.write("""
                    Please, enter the number according to list below
                    1 - delete customer
                    2 - delete company
                    3 - delete project
                    4 - delete developer
                    5 - delete skill
                    return - go back to main menu""");
            String section = view.read();
            switch (section) {
                case "1" -> delete(getCustomerService());
                case "2" -> delete(getCompanyService());
                case "3" -> delete(getProjectService());
                case "4" -> delete(getDeveloperService());
                case "5" -> delete(getSkillService());
                case "return" -> running = false;
                default -> view.write("Please, enter the correct command\n");
            }
        }
    }

    private <T> void delete(Service<T> service) {
        long id = getIntegerFromConsole("Enter id");
        try {
            service.delete(id);
        } catch (DAOException ex) {
            view.write(ex.getMessage());
        }
        view.write("Deleted successfully\n");
    }
}
